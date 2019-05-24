package com.revenat.iblog.application.model;

import java.util.List;

import com.revenat.iblog.application.dto.base.BaseDTO;
import com.revenat.iblog.infrastructure.util.CommonUtil;

public class Items<T extends BaseDTO<?, ?>> extends AbstractModel {
	private List<T> items;
	private long count;

	public Items(List<T> items, long count) {
		this.items = items;
		this.count = count;
	}

	public List<T> getItems() {
		return CommonUtil.getSafeList(items);
	}

	public long getCount() {
		return count;
	}
}
