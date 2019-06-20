package com.siw.project.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siw.project.models.Customer;
import com.siw.project.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
private CustomerRepository customerRepository;
	
	@Transactional
	public Customer add(Customer customer) {
		return this.customerRepository.save(customer);
	}
}
