package infinihedron.control;

public interface ChangeListener<T> {
	void changed(T object, String propertyName);
}
