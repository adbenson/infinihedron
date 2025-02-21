package infinihedron.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClassUtilities {

	public static <T> T instantiate(Class<T> clazz) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<T> ctor = clazz.getConstructor();
		return ctor.newInstance();
	}
	
	public static <T> T instantiate(Class<T> clazz, Object ... args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?>[] argTypes = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			argTypes[i] = args[i].getClass();
		}
		Constructor<T> ctor = clazz.getConstructor(argTypes);
		return ctor.newInstance(args);
	}
}
