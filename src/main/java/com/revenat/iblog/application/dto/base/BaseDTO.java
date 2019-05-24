package com.revenat.iblog.application.dto.base;

import java.io.Serializable;

import com.revenat.iblog.domain.entity.AbstractEntity;
import com.revenat.iblog.infrastructure.transform.Transformable;
import com.revenat.iblog.infrastructure.util.CommonUtil;

/**
 * Base class for all DTO classes
 * 
 * @author Vitaly Dragun
 *
 */
public abstract class BaseDTO<K extends Serializable, T extends AbstractEntity<K>> implements Transformable<T> {
	/**
	 * Unique entity identifier
	 */
	private K id;

	/**
	 * Should be overridden in the derived classes if the additional transformation
	 * logic domain model -> DTO is needed. Overridden methods should call
	 * super.transform()
	 */
	public void transform(T entity) {
		this.id = entity.getId();
	}

	/**
	 * Should be overridden in the derived classes if the additional transformation
	 * logic DTO -> domain model is needed.
	 */
	public T untransform(T entity) {
		entity.setId(getId());
		return entity;
	}

	public K getId() {
		return id;
	}

	public void setId(K id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return CommonUtil.toString(this);
	}
}
