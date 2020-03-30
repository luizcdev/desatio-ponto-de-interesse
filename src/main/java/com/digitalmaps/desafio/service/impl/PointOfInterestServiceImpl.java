package com.digitalmaps.desafio.service.impl;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalmaps.desafio.model.PointOfInterest;
import com.digitalmaps.desafio.repository.PointOfInterestRepository;
import com.digitalmaps.desafio.service.PointOfInterestService;

@Service
public class PointOfInterestServiceImpl implements PointOfInterestService {
	
	@Autowired
	private PointOfInterestRepository pontoDeInteresseRepository;
	
	@Override
	public PointOfInterest save(PointOfInterest pontoDeInteresse) {		
		return pontoDeInteresseRepository.save(pontoDeInteresse);
	}

	@Override
	public List<PointOfInterest> findAll() {
		return pontoDeInteresseRepository.findAll();
	}

	@Override
	public List<String> findByProximity(int x, int y, int distance, Time hour) {
		
		//Busca todos os pontos possíveis, com base na distância máxima em relação  apenas ao X ou Y
		List<PointOfInterest> possiblePoints = pontoDeInteresseRepository.findByPossiblePointsInMaxRange(x, y, distance);
		
		//Filtra apenas os pontos que estão a um raio de distância menor ou igual à distância informada e  
		List<String> response =  possiblePoints.stream()
				.filter(point -> point.getDistance(x, y) <= distance)
				.map(point -> point.getName() + ", " + point.getStatus(hour))
				.collect(Collectors.toList());

		return response;
		
	}

}
