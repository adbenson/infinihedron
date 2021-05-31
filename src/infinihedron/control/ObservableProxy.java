package infinihedron.control;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

enum MethodType {
	Setter, MutableGetter, Other;
}

public class ObservableProxy<T> implements InvocationHandler {

	private final Map<Method, MethodType> methodTypes = new HashMap<>();

	private final Map<Object, Object> childProxies = new HashMap<>();

	private final Collection<ChangeListener<T>> listeners = new HashSet<ChangeListener<T>>();

	private final T original;
	private final T observed;

	public ObservableProxy(T original) {
		this.original = original;
		this.observed = proxy(original, this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = method.invoke(original, args);
		MethodType type = getMethodType(method);

		// System.out.println("TYPE: " + method.getName() + ":\t" + type.toString());
		if (type == MethodType.Setter) {
			notifyListeners(method);
		} else if (type == MethodType.MutableGetter) {
			return getChildProxy(result, getPropName(method));
		}
		return result;
	}

	public void addChangeListener(ChangeListener<T> listener) {
		listeners.add(listener);
	}

	public T getObserved() {
		return observed;
	}

	protected String getPropName(Method method) {
		String propName = method.getName().substring(3);
		return propName.substring(0, 1).toLowerCase() + propName.substring(1);
	}

	@SuppressWarnings("unchecked")
	private <TChild> TChild getChildProxy(TChild value, String propName) {
		if (!childProxies.containsKey(value)) {
			childProxies.put(value, wrapChild(value, propName));
		}
		return (TChild) childProxies.get(value);
	}

	private <TChild> TChild wrapChild(TChild value, String propName) {
		return proxy(value, new ChildProxy<TChild>(value, propName));
	}

	@SuppressWarnings("unchecked")
	private <Tx> Tx proxy(Tx value, ObservableProxy<Tx> handler) {
		Class<?>[] interfaces = value.getClass().getInterfaces();
		if (interfaces.length < 1) {
			throw new RuntimeException("Class to be proxied does not have an interface: " + value.getClass());
		}
		return (Tx) Proxy.newProxyInstance(
			ObservableProxy.class.getClassLoader(),
			new Class[] { interfaces[0] },
			handler
		);
	}

	private MethodType getMethodType(Method method) {
		if (!methodTypes.containsKey(method)) {
			methodTypes.put(method, determineMethodType(method));
		}
		return methodTypes.get(method);
	}

	private MethodType determineMethodType(Method method) {
		int numArgs = method.getParameterCount();
		boolean returnsVoid = method.getReturnType().equals(Void.TYPE);
		boolean returnsMutable = !isWrapperType(method.getReturnType());

		boolean prefixSet = method.getName().matches("^set[A-Z].*");
		boolean prefixGet = method.getName().matches("^get[A-Z].*");

// System.out.println("DETER: " + method.getName() + ": \t" + numArgs + returnsVoid + returnsMutable + prefixGet + prefixSet);

		if (numArgs == 1 && returnsVoid && prefixSet) {
			return MethodType.Setter;
		}
		if (numArgs == 0 && !returnsVoid && returnsMutable && prefixGet) {
			return MethodType.MutableGetter;
		}
		return MethodType.Other;
	}

	protected void notifyListeners(Method method) {
		for (ChangeListener<T> listener : listeners) {
			listener.changed(observed, getPropName(method));
		}
	}

	private boolean isWrapperType(Class<?> clazz) {
		return clazz.isPrimitive() || clazz == String.class || clazz.isEnum() || WRAPPER_TYPES.contains(clazz);
	}

	private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

	private static Set<Class<?>> getWrapperTypes() {
		Set<Class<?>> ret = new HashSet<Class<?>>();
		ret.add(Boolean.class);
		ret.add(Character.class);
		ret.add(Byte.class);
		ret.add(Short.class);
		ret.add(Integer.class);
		ret.add(Long.class);
		ret.add(Float.class);
		ret.add(Double.class);
		ret.add(Void.class);
		return ret;
	}

	private class ChildProxy<TChild> extends ObservableProxy<TChild> {
		
		private final String propName;

		ChildProxy(TChild target, String propName) {
			super(target);
			this.propName = propName;
		}
		
		@Override
		protected String getPropName(Method method) {
			return propName + "." + super.getPropName(method);
		}

		@Override
		protected void notifyListeners(Method method) {
			for (ChangeListener<T> listener : ObservableProxy.this.listeners) {
				listener.changed(observed, getPropName(method));
			}
		}
	}
}
