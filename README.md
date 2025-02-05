# INFINIHEDRON

The Infinihedron is a large 12-faced polygon (dodecahedron) wiht half-mirroed faces and LEDs lining the edges. When illuminated, this creates the effect of infinite lights in a kalidescopic pattern.

[Here's a quick video of it in action](https://photos.app.goo.gl/cjdmcP2U8k5sayQv9), along with a glance at the software running it.
[More photos and videos](https://photos.app.goo.gl/n18Prc7EzE4qecTB7)

## Goals

Beyond looking cool, there were a few design goals for this project:

* Individually addressable LEDs - Most projects like this just repeat the same led pattern on all edges, which is cool, but I wanted to make more 3-dimensional effects
* Really good software support - I want to be able to play this thing like a DJ: Set a beat, choose an effect and a color palette, and then switch to other effects on the fly. Crucial to this is having software that makes it easy to create new effects and color palettes.

## Design

* Frame pieces modeled in SCAD and 3D printed in silver PLA
  * 30 edges, 200mm long
  * 20 vertices
* Panels are hand cut and filed acrylic (way too big for my laser cutter!)
* "Neopixel" compatible LED strips
  * 12 per edge * 30 edges
  * 360 total RGB LEDs
* FadeCandy USB-to-Neopixel driver
* Raspberry Pi Zero W wireless controller
* Software in Processing (based on Java)
* Controlled on Microsoft Surface Go 2 tablet

There's still a lot of work potentially left to do on the software, but it does work! I look forward to getting back to it soon.
