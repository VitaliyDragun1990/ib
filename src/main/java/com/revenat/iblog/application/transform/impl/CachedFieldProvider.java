package com.revenat.iblog.application.transform.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revenat.iblog.infrastructure.util.ReflectionUtil;

public class CachedFieldProvider extends FieldProvider {

	/**
	 * Mapping between transformation pair(concatenated class names) and field list
	 */
	private final Map<String, List<String>> cache;
	
	public CachedFieldProvider() {
		this.cache = new HashMap<>();
	}

	/**
	 * Returns list of similar field names for source/destination classes
	 */
	@Override
	public List<String> getFieldNames(Class<?> source, Class<?> dest) {
		String key = source.getSimpleName() + dest.getSimpleName();
		return cache.computeIfAbsent(key, k -> ReflectionUtil.findSimilarFields(source, dest));
	}
}
