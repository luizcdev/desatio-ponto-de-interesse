package com.digitalmaps.desafio.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.digitalmaps.desafio.model.PointOfInterest;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.digitalmaps.desafio.DesafioApplication.class,webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class PointOfInterestRepositoryTest {
	
	private List<PointOfInterest> pointOfInterestList = new ArrayList<PointOfInterest>();
	
	@Autowired
	PointOfInterestRepository pointOfInterestRepository;

	@Before
	public void setUp() throws Exception {
		
		PointOfInterest	pointOfInterest;
		
		pointOfInterest= new PointOfInterest();
		pointOfInterest.setX(1);
		pointOfInterest.setY(1);
		pointOfInterest.setName("Praça");
		pointOfInterestRepository.save(pointOfInterest);
		pointOfInterestList.add(pointOfInterest);
		
		pointOfInterest= new PointOfInterest();
		pointOfInterest.setName("Padaria");
		pointOfInterest.setX(10);
		pointOfInterest.setY(10);
		pointOfInterest.setOpened("05:00");
		pointOfInterest.setClosed("18:00");
		pointOfInterestRepository.save(pointOfInterest);
		pointOfInterestList.add(pointOfInterest);
		
		pointOfInterest= new PointOfInterest();
		pointOfInterest.setName("Mercado");
		pointOfInterest.setX(200);
		pointOfInterest.setY(200);
		pointOfInterest.setOpened("08:00");
		pointOfInterest.setClosed("22:00");
		pointOfInterestRepository.save(pointOfInterest);
		pointOfInterestList.add(pointOfInterest);
		
		pointOfInterest= new PointOfInterest();
		
	}
	
	@After
	public void tearDown() {
		pointOfInterestRepository.deleteAll();
		pointOfInterestRepository.flush();
	}
	
	@Test
	public void TestFindAll() throws Exception {
		
		//apenas o Mercado não será retornado
		List<PointOfInterest> expected = pointOfInterestList.stream()
				.filter(point -> !point.getName().equals("Mercado"))
				.collect(Collectors.toList());
		
		List<PointOfInterest> result = pointOfInterestRepository.findByPossiblePointsInMaxRange(5, 5, 8);
		assertEquals(objectoToJson(expected),  objectoToJson(result));
			
	}
	
	private String objectoToJson(Object object) throws Exception{
		return new ObjectMapper().writeValueAsString(object);
	}
	

}
