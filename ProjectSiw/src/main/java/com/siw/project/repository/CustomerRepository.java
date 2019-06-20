package com.siw.project.repository;

import org.springframework.data.repository.CrudRepository;

import com.siw.project.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}