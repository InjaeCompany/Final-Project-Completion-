package com.injaecompany.pandemichotel.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.injaecompany.pandemichotel.models.Reservation;
import com.injaecompany.pandemichotel.service.IReservationService;
import com.injaecompany.pandemichotel.repository.ReservationRepository;

@Service
public class ReservationService implements IReservationService {
	
	@Autowired
	private ReservationRepository userRepository;
	
	@Override
	public Reservation save(Reservation entity) {
		return userRepository.save(entity);
	}

	@Override
	public Reservation update(Reservation entity) {
		return userRepository.save(entity);
	}

	@Override
	public void delete(Reservation entity) {
		userRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public Reservation find(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public List<Reservation> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void deleteInBatch(List<Reservation> users) {
		userRepository.deleteInBatch(users);
	}
	
}
