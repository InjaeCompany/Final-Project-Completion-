/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.injaecompany.pandemichotel.repository;

import com.injaecompany.pandemichotel.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lenovo
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	//User findByEmail(String email);
}

