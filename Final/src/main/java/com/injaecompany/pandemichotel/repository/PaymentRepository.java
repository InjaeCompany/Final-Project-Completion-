/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.injaecompany.pandemichotel.repository;

import com.injaecompany.pandemichotel.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lenovo
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	//User findByEmail(String email);
}


