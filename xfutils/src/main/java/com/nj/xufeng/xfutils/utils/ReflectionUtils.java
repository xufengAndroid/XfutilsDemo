package com.nj.xufeng.xfutils.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * 
 * @Title: ReflectionTool.java
 * @Package com.archermind.xf.tool
 * @Description: TODO(反射工具)
 * @author 徐峰004245
 * @version V1.0
 */
public class ReflectionUtils {

	private ReflectionUtils() {
	}

	
	
	/**
	 * 得到某个对象的属性
	 * 
	 * @param owner
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static <T>T getProperty(Object owner, String fieldName) throws Exception {
		Class ownerClass = owner.getClass();
		Field field = ownerClass.getField(fieldName);
		Object property = field.get(owner);
		return (T) property;
	}

	/**
	 * 得到某个类的静态属性
	 * 
	 * @param className
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static <T>T getStaticProperty(String className, String fieldName)
			throws Exception {
		Class ownerClass = Class.forName(className);
		Field field = ownerClass.getField(fieldName);
		Object property = field.get(ownerClass);
		return (T) property;
	}

	/**
	 * 执行某对象的方法
	 * 
	 * @param owner
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static <T>T invokeMethod(Object owner, String methodName, Object... args)
			throws Exception {
		Class ownerClass = owner.getClass();
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return (T) method.invoke(owner, args);
	}

	/**
	 * 执行某个类的静态方法
	 * 
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static <T>T invokeStaticMethod(String className, String methodName,
			Object... args) throws Exception {
		Class ownerClass = Class.forName(className);

		Class[] argsClass = new Class[args.length];

		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}

		Method method = ownerClass.getMethod(methodName, argsClass);

		return (T) method.invoke(null, args);
	}

	/**
	 * 新建实例
	 * @param className
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static <T>T newInstance(String className, Object... args) throws Exception {
		Class newoneClass = Class.forName(className);

		Class[] argsClass = new Class[args.length];

		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}

		Constructor cons = newoneClass.getConstructor(argsClass);

		return (T) cons.newInstance(args);

	}
	/**
	 * 判断是否为某个类的实例
	 * @param obj
	 * @param cls
	 * @return
	 */
	public static boolean isInstance(Object obj, Class cls) {
	     return cls.isInstance(obj);
	}
	/**
	 * 得到数组中的某个元素
	 * @param array
	 * @param index
	 * @return
	 */
	public static <T>T getByArray(Object array, int index) {
	     return (T) Array.get(array,index);
	}
	
	/**
	 * 判断该对像是否有此属性
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static boolean hasField(Object object, String fieldName){
		try {
			Class ownerClass = object.getClass();
			Field field = ownerClass.getField(fieldName);
			return true;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static Object getFieldValue(final Object object, final String fieldName) throws IllegalArgumentException, IllegalAccessException {
		Field field = getDeclaredField(object, fieldName);
		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		makeAccessible(field);
		Object result = null;
		result = field.get(object);
		return result;
	}

	/**
	 * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) throws IllegalArgumentException, IllegalAccessException {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

		makeAccessible(field);

			field.set(object, value);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 */
	protected static Field getDeclaredField(final Object object, final String fieldName) {
		return getDeclaredField(object.getClass(), fieldName);
	}

	/**
	 * 循环向上转型,获取类的DeclaredField.
	 */
	@SuppressWarnings("unchecked")
	protected static Field getDeclaredField(final Class clazz, final String fieldName) {
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 强制转换fileld可访问.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的泛型参数的类型. 如public UserDao extends HibernateDao<User>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be
	 *         determined
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的泛型参数的类型. 如public UserDao extends
	 * HibernateDao<User,Long>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be
	 *         determined
	 */

	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

}
