package de.holmarku.restapidescription.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.holmarku.restapidescription.model.ApiDescription;

public interface ApiDescriptionRepository extends JpaRepository<ApiDescription, Long> {
	List<ApiDescription> findByApiTitleStartsWithIgnoreCase(String apiTitle);
}
