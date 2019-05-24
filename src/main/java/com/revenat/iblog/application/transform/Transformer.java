package com.revenat.iblog.application.transform;

import java.io.Serializable;

import com.revenat.iblog.domain.entity.AbstractEntity;
import com.revenat.iblog.infrastructure.transform.Transformable;

/**
 * Represents transformation engine for converting business entities
 * into DTO objects
 * 
 * @author Vitaly Dragun
 *
 */
public interface Transformer {
	
	/**
	 * Converts specified entity into DTO object
	 * 
	 * @param entity entity instance to convert from
	 * @param dtoClass Class of the specified DTO object to convert to
	 * @return instance of the specified DTO
	 */
	<K extends Serializable, T extends AbstractEntity<K>, P extends Transformable<T>> P transform(T entity, Class<P> dtoClass);
	
	/**
	 * Converts specified entity into existing DTO object
	 * @param entity specified entity to convert from
	 * @param dto specified DTO to convert to
	 */
	<K extends Serializable, T extends AbstractEntity<K>, P extends Transformable<T>> void transform(T entity, P dto);
	
	/**
	 * Converts specified DTO object into business entity
	 * 
	 * @param dto DTO instance to convert from
	 * @param entityClass Class of the specified entity to convert to
	 * @return instance of the specified entity
	 */
	<K extends Serializable, T extends AbstractEntity<K>, P extends Transformable<T>> T untransform(P dto, Class<T> entityClass);

}

