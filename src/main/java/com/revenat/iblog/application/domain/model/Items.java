package com.revenat.iblog.application.domain.model;

import java.util.List;

import com.revenat.iblog.application.domain.entity.AbstractEntity;

public class Items<T extends AbstractEntity<?>> extends AbstractModel {
	private List<T> items;
	private int count;
	
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
