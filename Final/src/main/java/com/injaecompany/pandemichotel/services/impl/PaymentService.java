/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.injaecompany.pandemichotel.services.impl;

import com.injaecompany.pandemichotel.models.Payment;
import com.injaecompany.pandemichotel.repository.PaymentRepository;
import com.injaecompany.pandemichotel.service.IPaymentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lenovo
 */

@Service
public class PaymentService implements IPaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Override
	public Payment save(Payment entity) {
		return paymentRepository.save(entity);
	}

	@Override
	public Payment update(Payment entity) {
		return paymentRepository.save(entity);
	}

	@Override
	public void delete(Payment entity) {
		paymentRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		paymentRepository.deleteById(id);
	}

	@Override
	public Payment find(Long id) {
		return paymentRepository.findById(id).orElse(null);
	}

	@Override
	public List<Payment> findAll() {
		return paymentRepository.findAll();
	}

	@Override
	public void deleteInBatch(List<Payment> payment) {
		paymentRepository.deleteInBatch(payment);
	}
	
}


