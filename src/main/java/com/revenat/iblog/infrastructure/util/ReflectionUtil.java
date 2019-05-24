package com.revenat.iblog.infrastructure.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.revenat.iblog.infrastructure.annotation.Ignore;
import com.revenat.iblog.infrastructure.exception.ConfigurationException;

/**
 * Contains reflection-related utility operations
 * 
 * @author Vitaly Dragun
 *
 */
public class ReflectionUtil {

	private ReflectionUtil() {
	}
	
	/**
	 * Creates an instance of the specified class. This method throws unchecked
	 * exception if creation fails
	 * 
	 * @param clz Class for instance to create
	 * @return instance of the specified class
	 * @throws ConfigurationException unchecked exception if creation fails
	 */
	public static <T> T createInstance(Class<T> clz) throws ConfigurationException {
		try {
			return clz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ConfigurationException(e);
		}
	}
	
	/**
	 * Returns list of field names of specified classes for fields that have identical names
	 * excluding final and static ones.
	 * 
	 */
	public static List<String> findSimilarFields(Class<?> clz1, Class<?> clz2) throws ConfigurationException {
		try {
			List<Field> fields = getFields(clz1);
			
			List<String> targetFields = getFields(clz2).stream()
					.filter(field -> !field.isAnnotationPresent(Ignore.class))
					.map(Field::getName)
					.collect(Collectors.toList());
			
			return fields.stream()
					.filter(field -> !field.isAnnotationPresent(Ignore.class))
					.filter(field -> !Modifier.isStatic(field.getModifiers()))
					.filter(field -> !Modifier.isFinal(field.getModifiers()))
					.map(Field::getName)
					.filter(targetFields::contains)
					.collect(Collectors.toList());
			
		} catch (SecurityException ex) {
			throw new ConfigurationException(ex);
		}
	}
	
	/**
	 * Returns all declared fields in the specified class and all its superclasses
	 */
	public static List<Field> getFields(Class<?> cls) {
		List<Field> fields = new ArrayList<>();
		while (cls != null) {
			fields.addAll(Arrays.asList(cls.getDeclaredFields()));
			cls = cls.getSuperclass();
		}
		
		return fields;
	}
	
	/**
	 * Copy specified fields values from the source to the destination object.
	 * 
	 * @param source Object to copy fields values from
	 * @param destination Object to copy fields values to
	 * @param fieldNames list of field names to copy values from
	 */
	public static void copyFields(Object source, Object destination, List<String> fieldNames) throws ConfigurationException {
		Checks.checkParam(source != null, "Source object is not initialized");
		Checks.checkParam(destination != null, "Destination object is not initialized");
		
		try {
			for (String fieldName : fieldNames) {
				Field sourceField = getField(source.getClass(), fieldName);
				// Skip unknown fields
				if (sourceField != null) {
					sourceField.setAccessible(true);
					Object valueToCopy = sourceField.get(source);
					
					Field destField = getField(destination.getClass(), fieldName);
					
					if (destField != null) {
						destField.setAccessible(true);
						destField.set(destination, valueToCopy);
					}
				}
			}
		} catch (SecurityException | ReflectiveOperationException | IllegalArgumentException ex) {
			throw new ConfigurationException(ex);
		}
	}
	
	/**
	 * returns class field by its name. This method supports base classes as well
	 * @param clz Class instance to look for the field in
	 * @param fieldName name of the field to look for
	 * @return field instance if found
	 * @throws {@link ConfigurationException} if no field with given name was found
	 */
	public static <T> Field getField(final Class<T> clz, final String fieldName) throws ConfigurationException {
		Class<?> current = clz;
		while (current != null) {
			try {
				return current.getDeclaredField(fieldName);
			} catch (NoSuchFieldException | SecurityException e) {
				current = current.getSuperclass();
			}
		}
		throw new ConfigurationException("No field " + fieldName + " in the class " + clz);
	}

}
