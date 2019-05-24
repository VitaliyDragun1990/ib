package com.revenat.iblog.application.model;

import com.revenat.iblog.infrastructure.util.CommonUtil;

public abstract class AbstractModel {

	@Override
	public String toString() {
		return CommonUtil.toString(this);
	}
}
