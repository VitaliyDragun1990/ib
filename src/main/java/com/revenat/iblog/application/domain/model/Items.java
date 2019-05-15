package com.revenat.iblog.application.domain.model;

import java.util.List;

import com.revenat.iblog.application.domain.entity.AbstractEntity;

// TODO: consider making this component immutable
public class Items<T extends AbstractEntity<?>> extends AbstractModel {
	private List<T> items;
	private long count;
	
	public Items() {
	}
	
	public Items(List<T> items, long count) {
		this.items = items;
		this.count = count;
	}
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
}
