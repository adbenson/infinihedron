package infinihedron.projections;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import infinihedron.models.Segment;
import infinihedron.models.Vertex;
import infinihedron.models.Layer;

public class MapReader {
	
	public static Collection<Segment> get(String name) throws IOException {
		String json = read(name);
		Collection<MapJson> map = parse(json);
		return toSegments(map);
	}
	
	private static String read(String name) throws IOException {
		Path fileName = Paths.get("resources", name);
		return Files.readString(fileName);
	}

	private static Collection<MapJson> parse(String json) {
		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<MapJson>>(){}.getType();
		return gson.fromJson(json, collectionType);
	}
	
	private static Collection<Segment> toSegments(Collection<MapJson> json) {
		return json.stream().map(segment -> toSegment(segment)).collect(Collectors.toList());
	}
	
	private static Segment toSegment(MapJson json) {
		Vertex start = toVertex(json.start);
		Vertex end = toVertex(json.end);
		return new Segment(start, end, json.channel, json.segment);
	}
	
	private static Vertex toVertex(String vertex) {
		Layer layer = toLayer(vertex.substring(0, 1));
		int longitude = Integer.valueOf(vertex.substring(1, 2));
		return new Vertex(layer, longitude);
	}
	
	private static Layer toLayer(String layer) {
		return Layer.valueOf(layer);
	}
}
