package com.revenat.iblog.persistence.repository;

import java.util.List;

import com.revenat.iblog.application.domain.entity.Category;

public interface CategoryRepository {

	List<Category> getAll();
}
