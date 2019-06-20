package com.siw.project.repository;

import org.springframework.data.repository.CrudRepository;

import com.siw.project.models.UsageRequest;

public interface UsageRequestRepository extends CrudRepository<UsageRequest, Long> {

}
