package infinihedron.scenes;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import infinihedron.Infinihedron;
import infinihedron.control.SceneType;
import infinihedron.pixelControl.models.Layer;
import infinihedron.pixelControl.models.Point;
import infinihedron.pixelControl.models.Segment;
import infinihedron.pixelControl.models.SegmentLine;
import infinihedron.pixelControl.models.Vertex;
import infinihedron.projections.Projection;
import infinihedron.scenes.SpriteManager.Sprite;

public class FirefliesScene extends Scene {

	private final Map<Vertex, Vertex[]> adjacencies;
	private final Map<Vertex, Point> vertexToPoint;
	private final Vertex[] vertices;

	private final SpriteManager<Firefly> fireflies;

	public FirefliesScene(Infinihedron infinihedron) {
		super(infinihedron, SceneType.Fireflies);
		
		List<SegmentLine> segments = infinihedron.getProjection().getSegments();

		this.adjacencies = buildAdjacencyMap(segments);

		vertices = adjacencies.keySet().toArray(new Vertex[0]);

		this.vertexToPoint = new HashMap<>();
		for (SegmentLine segment : segments) {
			this.vertexToPoint.put(segment.start, segment.startPoint);
			this.vertexToPoint.put(segment.end, segment.endPoint);
		}

		this.fireflies = new SpriteManager<Firefly>(
			100, 100, 20, 40,
			() -> spawnFirefly()
		);
	}

	@Override
	public void draw() {
		fireflies.draw();
	}

	@Override
	public void beat() {
		fireflies.beat();
	}

	private Firefly spawnFirefly() {
		Vertex start = vertices[random.nextInt(vertices.length)];
		Vertex end = adjacencies.get(start)[random.nextInt(3)];
		return new Firefly(start, end);
	}

	private Map<Vertex, Vertex[]> buildAdjacencyMap(List<SegmentLine> segments) {
		Map<Vertex, Vertex[]> adjacencies = new HashMap<>();

		for (Segment segment : segments) {
			Vertex start = segment.start;
			Vertex end = segment.end;

			if (!adjacencies.containsKey(start)) {
				adjacencies.put(start, new Vertex[3]);
			}
			if (!adjacencies.containsKey(end)) {
				adjacencies.put(end, new Vertex[3]);
			}

			pushToArray(adjacencies.get(start), end);
			pushToArray(adjacencies.get(end), start);
		}

		for (Vertex vertex : adjacencies.keySet()) {
			Vertex[] adjacent = adjacencies.get(vertex);
			System.out.println("Vertex: " + vertex + " -> " + adjacent[0] + ", " + adjacent[1] + ", " + adjacent[2]);
		}

		return adjacencies;
	}

	private void pushToArray(Vertex[] array, Vertex vertex) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null) {
				array[i] = vertex;
				return;
			}
		}
	}

	private Vertex chooseOther(Vertex start, Vertex exclude) {
		Vertex[] options = adjacencies.get(start);
		Vertex choice = options[random.nextInt(3)];
		while (choice.equals(exclude)) {
			choice = options[random.nextInt(3)];
		}
		return choice;
	}

	class Firefly extends Sprite {
		private final float gradientStep = 0.1f;

		private volatile Vertex start;
		private volatile Point startPoint;

		private volatile Vertex end;
		private volatile Point endPoint;

		private Color c;

		public Firefly(Vertex start, Vertex end) {
			super();
			updateVertices(start, end);
			this.c = palette.getRandomColor();
		}

		private synchronized void updateVertices(Vertex start, Vertex end) {
System.out.println("updateVertices: " + start + " -> " + end);
			this.start = start;
			this.end = end;
			this.startPoint = vertexToPoint.get(start);
			this.endPoint = vertexToPoint.get(end);
		}

		private synchronized void updateEndpoint(Vertex newEnd) {
			this.start = this.end;
			this.end = newEnd;
			this.startPoint = this.endPoint;
			this.endPoint = vertexToPoint.get(newEnd);
		}
		
		@Override
		public void draw() {
			float f = getBeatFraction();
			Point point = Projection.pointOnLine(startPoint, endPoint, getBeatFraction());
			int baseSize = (start.layer == Layer.D && end.layer == Layer.D) ? 150 : 100;
			int radius = Math.max((int)((1 - (Math.abs(f * 2 - 1))) * baseSize), 25);
			gradientCircle((int)point.x, (int)point.y, c, radius, 1);
		}

		@Override
		public void beat() {
			System.out.println("before: " + startPoint + " -> " + endPoint);
			Vertex newEnd = chooseOther(end, start);

			if (newEnd.equals(start)) {
				System.out.println("bounceback");
			}

			updateEndpoint(newEnd);

			// if (!oldEndPoint.equals(startPoint)) {
			// 	System.out.println("teleported!");
			// }
			System.out.println("after: " + startPoint + " -> " + endPoint);
		}

		private void gradientCircle(int x, int y, Color c, int radius, float maxAlpha) {
			for (float step = gradientStep; step < 1; step += gradientStep) {
				p.fill(adjustAlpha(c, (1 - step) * maxAlpha));
				p.circle(x, y, radius * step);
			}
		}
	}

}
