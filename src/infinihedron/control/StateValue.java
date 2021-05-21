package infinihedron.control;

import infinihedron.scenes.SceneType;

public enum StateValue {
	SceneA(SceneType.class),
	BpmA(Integer.class);

	private Class<?> valueType;
	StateValue(Class<?> type) {
		this.valueType = type;
	}

	public boolean isValid(Object value) {
		return valueType.isInstance(value);
	}

	public boolean isType(Class<?> type) {
		return valueType == type;
	}
}
