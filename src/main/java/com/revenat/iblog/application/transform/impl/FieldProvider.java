package com.revenat.iblog.application.transform.impl;

import java.util.List;

import com.revenat.iblog.infrastructure.util.ReflectionUtil;

/**
 * Base functionality of the field preparation
 * 
 * @author Vitaly Dragun
 *
 */
public class FieldProvider {

	public List<String> getFieldNames(Class<?> source, Class<?> dest) {
		return ReflectionUtil.findSimilarFields(source, dest);
	}
}
