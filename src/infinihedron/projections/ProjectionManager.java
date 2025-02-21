package infinihedron.projections;

import java.util.HashMap;
import java.util.Map;

import infinihedron.util.ClassUtilities;

public class ProjectionManager {

	private final Map<ProjectionType, Projection> projections = new HashMap<ProjectionType, Projection>();

	public ProjectionManager() {
		for (ProjectionType type : ProjectionType.values()) {
			try {
				Projection instance = ClassUtilities.instantiate(type.clazz);
				projections.put(type, instance);
			} catch (Exception e) {
				System.out.println("Failed to instantiate projection: " + type);
				e.printStackTrace();
			}
		}
	}

	public Projection get(ProjectionType type) {
		return projections.get(type);
	}	
}
