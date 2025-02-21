package infinihedron.projections;

public enum ProjectionType {
	STEREOGRAPHIC(StereographicProjection.class),
	CYLINDRICAL(CylindricalProjection.class),
	DIRECT_ADDRESS(DirectAddressProjection.class);

	public final Class<? extends Projection> clazz;
	ProjectionType(Class<? extends Projection> clazz) {
		this.clazz = clazz;
	}
}
