package de.holmarku.restapidescription.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.holmarku.restapidescription.model.ApiField;

public interface ApiFieldRepository extends JpaRepository<ApiField, Long> {
	List<ApiField> findByNameStartsWithIgnoreCase(String name);
}
