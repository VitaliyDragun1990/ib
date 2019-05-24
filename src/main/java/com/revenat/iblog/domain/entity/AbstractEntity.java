package com.revenat.iblog.domain.entity;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractEntity<K extends Serializable> implements Serializable {
	private static final long serialVersionUID = 3911762542288653980L;

	private K id;

	public K getId() {
		return id;
	}

	public void setId(K id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity<?> other = (AbstractEntity<?>) obj;
		return Objects.equals(id, other.id);
	}
}
