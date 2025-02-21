package infinihedron.pixelControl;

/*
 * Simple Open Pixel Control client for Processing,
 * designed to sample each LED's color from some point on the canvas.
 *
 * Micah Elizabeth Scott, 2013
 * This file is released into the public domain.
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;

public class OPC implements Runnable {
	public static final int FIRST_PIXEL_OFFSET = 4;
	public static final int BYTES_PER_PIXEL = 3;

	private Thread thread;
	private Socket socket;
	private OutputStream output, pending;
	private String host;
	private int port;

	private byte[] packetData;
	private byte firmwareConfig;
	private String colorCorrection;

	public OPC(String host, int port) {
		this.host = host;
		this.port = port;
		thread = new Thread(this);
		thread.start();
	}

	public boolean isConnected() {
		return output != null;
	}

	// Enable or disable dithering. Dithering avoids the "stair-stepping" artifact
	// and increases color
	// resolution by quickly jittering between adjacent 8-bit brightness levels
	// about 400 times a second.
	// Dithering is on by default.
	public void setDithering(boolean enabled) {
		if (enabled)
			firmwareConfig &= ~0x01;
		else
			firmwareConfig |= 0x01;
		sendFirmwareConfigPacket();
	}

	// Enable or disable frame interpolation. Interpolation automatically blends
	// between consecutive frames
	// in hardware, and it does so with 16-bit per channel resolution. Combined with
	// dithering, this helps make
	// fades very smooth. Interpolation is on by default.
	public void setInterpolation(boolean enabled) {
		if (enabled)
			firmwareConfig &= ~0x02;
		else
			firmwareConfig |= 0x02;
		sendFirmwareConfigPacket();
	}

	// Put the Fadecandy onboard LED under automatic control. It blinks any time the
	// firmware processes a packet.
	// This is the default configuration for the LED.
	public void statusLedAuto() {
		firmwareConfig &= 0x0C;
		sendFirmwareConfigPacket();
	}

	// Manually turn the Fadecandy onboard LED on or off. This disables automatic
	// LED control.
	public void setStatusLed(boolean on) {
		firmwareConfig |= 0x04; // Manual LED control
		if (on)
			firmwareConfig |= 0x08;
		else
			firmwareConfig &= ~0x08;
		sendFirmwareConfigPacket();
	}

	// Set the color correction parameters
	public void setColorCorrection(float gamma, float red, float green, float blue) {
		colorCorrection = "{ \"gamma\": " + gamma + ", \"whitepoint\": [" + red + "," + green + "," + blue + "]}";
		sendColorCorrectionPacket();
	}

	// Set custom color correction parameters from a string
	public void setColorCorrection(String s) {
		colorCorrection = s;
		sendColorCorrectionPacket();
	}

	// Send a packet with the current firmware configuration settings
	public void sendFirmwareConfigPacket() {
		if (pending == null) {
			// We'll do this when we reconnect
			return;
		}

		byte[] packet = new byte[9];
		packet[0] = (byte) 0x00; // Channel (reserved)
		packet[1] = (byte) 0xFF; // Command (System Exclusive)
		packet[2] = (byte) 0x00; // Length high byte
		packet[3] = (byte) 0x05; // Length low byte
		packet[4] = (byte) 0x00; // System ID high byte
		packet[5] = (byte) 0x01; // System ID low byte
		packet[6] = (byte) 0x00; // Command ID high byte
		packet[7] = (byte) 0x02; // Command ID low byte
		packet[8] = (byte) firmwareConfig;

		try {
			pending.write(packet);
		} catch (Exception e) {
			dispose();
		}
	}

	// Send a packet with the current color correction settings
	public void sendColorCorrectionPacket() {
		if (colorCorrection == null) {
			// No color correction defined
			return;
		}
		if (pending == null) {
			// We'll do this when we reconnect
			return;
		}

		byte[] content = colorCorrection.getBytes();
		int packetLen = content.length + 4;
		byte[] header = new byte[8];
		header[0] = (byte) 0x00; // Channel (reserved)
		header[1] = (byte) 0xFF; // Command (System Exclusive)
		header[2] = (byte) (packetLen >> 8); // Length high byte
		header[3] = (byte) (packetLen & 0xFF); // Length low byte
		header[4] = (byte) 0x00; // System ID high byte
		header[5] = (byte) 0x01; // System ID low byte
		header[6] = (byte) 0x00; // Command ID high byte
		header[7] = (byte) 0x01; // Command ID low byte

		try {
			pending.write(header);
			pending.write(content);
		} catch (Exception e) {
			dispose();
		}
	}

	// Change the number of pixels in our output packet.
	// This is normally not needed; the output packet is automatically sized
	// by draw() and by setPixel().
	public void setPixelCount(int numPixels) {
		int numBytes = 3 * numPixels;
		int packetLen = 4 + numBytes;
		if (packetData == null || packetData.length != packetLen) {
			// Set up our packet buffer
			packetData = new byte[packetLen];
			packetData[0] = (byte) 0x00; // Channel
			packetData[1] = (byte) 0x00; // Command (Set pixel colors)
			packetData[2] = (byte) (numBytes >> 8); // Length high byte
			packetData[3] = (byte) (numBytes & 0xFF); // Length low byte
		}
	}

	// Directly manipulate a pixel in the output buffer. This isn't needed
	// for pixels that are mapped to the screen.
	public void setPixel(int number, int c) {
		int offset = 4 + number * 3;
		if (packetData == null || packetData.length < offset + 3) {
			setPixelCount(number + 1);
		}

		packetData[offset] = (byte) (c >> 16);
		packetData[offset + 1] = (byte) (c >> 8);
		packetData[offset + 2] = (byte) c;
	}

	public void setPixel(int offset, byte r, byte g, byte b) {
		packetData[offset] = (byte) r;
		packetData[offset + 1] = (byte) g;
		packetData[offset + 2] = (byte) b;
	}

	// Read a pixel from the output buffer. If the pixel was mapped to the display,
	// this returns the value we captured on the previous frame.
	public int getPixel(int number) {
		int offset = 4 + number * 3;
		if (packetData == null || packetData.length < offset + 3) {
			return 0;
		}
		return (packetData[offset] << 16) | (packetData[offset + 1] << 8) | packetData[offset + 2];
	}

	// Transmit our current buffer of pixel values to the OPC server. This is
	// handled
	// automatically in draw() if any pixels are mapped to the screen, but if you
	// haven't
	// mapped any pixels to the screen you'll want to call this directly.
	public void writePixels() {
		if (packetData == null || packetData.length == 0) {
			// No pixel buffer
			return;
		}
		if (output == null) {
			return;
		}

		try {
			output.write(packetData);
		} catch (Exception e) {
			dispose();
		}
	}

	public void dispose() {
		// Destroy the socket. Called internally when we've disconnected.
		// (Thread continues to run)
		if (output != null) {
			System.out.println("Disconnected from OPC server");
		}
		socket = null;
		output = pending = null;
	}

	public void run() {
		System.out.println("OPC thread begin");
		// Thread tests server connection periodically, attempts reconnection.
		// Important for OPC arrays; faster startup, client continues
		// to run smoothly when mobile servers go in and out of range.
		while(true) {

			if (output == null) { // No OPC connection?
				try { // Make one!
					System.out.println("host: " + host + ":" + port);
					socket = new Socket(host, port);
					socket.setTcpNoDelay(true);
					pending = socket.getOutputStream(); // Avoid race condition...
					System.out.println("Connected to OPC server");
					sendColorCorrectionPacket(); // These write to 'pending'
					sendFirmwareConfigPacket(); // rather than 'output' before
					output = pending; // rest of code given access.
					// pending not set null, more config packets are OK!
				} catch (ConnectException e) {
					dispose();
				} catch (IOException e) {
					dispose();
				}
			}

			// Pause thread to avoid massive CPU load
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
}
