package com.digitalmaps.desafio.service;

import java.sql.Time;
import java.util.List;

import com.digitalmaps.desafio.model.PointOfInterest;

public interface PointOfInterestService {

	PointOfInterest save(PointOfInterest pontoDeInteresse);
	List<PointOfInterest> findAll();
	List<String> findByProximity(int x, int y, int distance, Time hour);
	
	
}
