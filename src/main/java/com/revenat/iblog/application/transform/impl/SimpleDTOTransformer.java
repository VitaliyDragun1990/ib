package com.revenat.iblog.application.transform.impl;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revenat.iblog.application.transform.Transformer;
import com.revenat.iblog.domain.entity.AbstractEntity;
import com.revenat.iblog.infrastructure.transform.Transformable;
import com.revenat.iblog.infrastructure.util.Checks;
import com.revenat.iblog.infrastructure.util.CommonUtil;
import com.revenat.iblog.infrastructure.util.ReflectionUtil;

/**
 * Default transformation engine that uses reflection to transform objects
 * 
 * @author Vitaly Dragun
 *
 */
public class SimpleDTOTransformer implements Transformer {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDTOTransformer.class);
	
	private final FieldProvider fieldProvider;
	
	public SimpleDTOTransformer(FieldProvider fieldProvider) {
		this.fieldProvider = fieldProvider;
	}

	@Override
	public <K extends Serializable, T extends AbstractEntity<K>, P extends Transformable<T>> P transform(final T entity, final Class<P> dtoClass) {
		checkParams(entity, dtoClass);
		
		return handleTransformation(entity, ReflectionUtil.createInstance(dtoClass));
	}	

	@Override
	public <K extends Serializable, T extends AbstractEntity<K>, P extends Transformable<T>> void transform(final T entity, final P dto) {
		checkParam(entity, "Source transformation object is not initialized");
		checkParam(dto, "Destination object is not initialized");
		
		handleTransformation(entity, dto);
	}

	@Override
	public <K extends Serializable, T extends AbstractEntity<K>, P extends Transformable<T>> T untransform(final P dto, final Class<T> entityClass) {
		checkParams(dto, entityClass);
		
		// Create entity instance
		T entity = ReflectionUtil.createInstance(entityClass);
		// Copy all the similar fields
		ReflectionUtil.copyFields(dto, entity, fieldProvider.getFieldNames(dto.getClass(), entityClass));
		// Some custom transformation logic
		dto.untransform(entity);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SimpleDTOTransformer.transform: {} entity object",
					CommonUtil.toString(entity));	
		}
		
		return entity;
	}
	
	private <K extends Serializable, T extends AbstractEntity<K>, P extends Transformable<T>> P handleTransformation(final T entity, final P dto) {
		// Copy all the similar fields
		ReflectionUtil.copyFields(entity, dto, fieldProvider.getFieldNames(entity.getClass(), dto.getClass()));
		// Some custom transformation logic
		dto.transform(entity);
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("SimpleDTOTransformer.transform: {} DTO object",
					CommonUtil.toString(dto));	
		}
		
		return dto;
	}
	
	private void checkParams(final Object param, Class<?> clz) {
		checkParam(param, "Source transformation object is not inirtialized");
		checkParam(clz, "No target class is defined for transformation");
	}
	
	private void checkParam(final Object param, final String errorMessage) {
		Checks.checkParam(param != null, errorMessage);
	}
}
