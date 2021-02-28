/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.injaecompany.pandemichotel.services.impl;

import com.injaecompany.pandemichotel.models.Customer;
import com.injaecompany.pandemichotel.repository.CustomerRepository;
import com.injaecompany.pandemichotel.service.ICustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lenovo
 */

@Service
public class CustomerService implements ICustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Customer save(Customer entity) {
		return customerRepository.save(entity);
	}

	@Override
	public Customer update(Customer entity) {
		return customerRepository.save(entity);
	}

	@Override
	public void delete(Customer entity) {
		customerRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		customerRepository.deleteById(id);
	}

	@Override
	public Customer find(Long id) {
		return customerRepository.findById(id).orElse(null);
	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public void deleteInBatch(List<Customer> customer) {
		customerRepository.deleteInBatch(customer);
	}
	
}


