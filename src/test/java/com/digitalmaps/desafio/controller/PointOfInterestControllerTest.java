package com.digitalmaps.desafio.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.digitalmaps.desafio.model.PointOfInterest;
import com.digitalmaps.desafio.repository.PointOfInterestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.digitalmaps.desafio.DesafioApplication.class,webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class PointOfInterestControllerTest {
	
	private List<PointOfInterest> pointOfInterestList = new ArrayList<PointOfInterest>();
	
	@Autowired
	MockMvc mockMvc;
	
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
		pointOfInterest.setName("Posto de combustível");
		pointOfInterest.setX(1);
		pointOfInterest.setY(100);
		pointOfInterestRepository.save(pointOfInterest);
		pointOfInterestList.add(pointOfInterest);
		
		pointOfInterest= new PointOfInterest();
		pointOfInterest.setName("Barbearia");
		pointOfInterest.setX(50);
		pointOfInterest.setY(50);
		pointOfInterest.setOpened("12:00");
		pointOfInterest.setClosed("19:30");
		pointOfInterestRepository.save(pointOfInterest);
		pointOfInterestList.add(pointOfInterest);
		
	}
	
	@After
	public void tearDown() {
		pointOfInterestRepository.deleteAll();
		pointOfInterestRepository.flush();
	}
	
	@Test
	public void TestFindAll() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pointofinterest")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(objectoToJson(pointOfInterestList)));
		
	}
	
	@Test
	public void TestFindByProximity() throws Exception {
		
		List<String> response = new ArrayList<String>();
		response.add("Praça, aberto");
		response.add("Padaria, fechado");
		response.add("Posto de combustível, aberto");
		response.add("Barbearia, aberto");
		
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pointofinterest/proximity/1/1/100/19:00")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(objectoToJson(response)));
		
	}
	
	@Test
	public void TestFindByProximityEmpty() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pointofinterest/proximity/1000/1000/1/18:00")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().string("[]"));
		
	}
	
	@Test
	public void TestFindByProximityXInvalid() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pointofinterest/proximity/a/1/1/18:00")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().string("Formato inesperado para o parâmetro: x"));
		
	}
	
	@Test
	public void TestFindByProximityYInvalid() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pointofinterest/proximity/1/a/1/25:00")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().string("Formato inesperado para o parâmetro: y"));
		
	}
	
	@Test
	public void TestFindByProximityDistanceInvalid() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pointofinterest/proximity/1/1/a/25:00")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().string("Formato inesperado para o parâmetro: distance"));
		
	}
	
	@Test
	public void TestFindByProximityTimeInvalid() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/pointofinterest/proximity/1000/1000/1/25:00")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().string(containsString("Formato inesperado para o parâmetro time, utilize o padrão HH:mm")));
		
	}
	
	@Test
	public void TestPost() throws Exception {
		
		
		PointOfInterest request = new PointOfInterest();
		request.setName("Cafeteria");
		request.setX(50);
		request.setY(100);
		request.setOpened("06:00");
		request.setClosed("18:00");
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/pointofinterest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectoToJson(request)))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Cafeteria"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.x").value(50))
			.andExpect(MockMvcResultMatchers.jsonPath("$.y").value(100))
			.andExpect(MockMvcResultMatchers.jsonPath("$.opened").value("06:00:00"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.closed").value("18:00:00"));
		
	}
	
	@Test
	public void TestPostValidationFail() throws Exception {
		
		
		JSONObject request = new JSONObject().put("name","")
				.put("x", 0)
				.put("y", 0);
		
		List<String> response = new ArrayList<String>();
		response.add("O campo nome deve ser informado");
		response.add("O campo Y deve ser positivo");
		response.add("O campo X deve ser positivo");
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/pointofinterest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request.toString()))
			.andExpect(status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().json(objectoToJson(response)));
		
	}
	
	@Test
	public void TestPostValidationOpenedInvalid() throws Exception {
		
		
		JSONObject request = new JSONObject().put("name","Invalid Opened")
				.put("x", 1)
				.put("y", 1)
				.put("opened", "25:00")
				.put("closed", "20:00");
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/pointofinterest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request.toString()))
			.andExpect(status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().string(containsString("Horário de abertura inválido")));
		
	}
	
	@Test
	public void TestPostValidationClosedInvalid() throws Exception {
		
		
		JSONObject request = new JSONObject().put("name","Invalid Closed")
				.put("x", 1)
				.put("y", 1)
				.put("opened", "08:00")
				.put("closed", "25:00");
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/pointofinterest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request.toString()))
			.andExpect(status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().string(containsString("Horário de fechamento inválido")));
		
	}
	
	private String objectoToJson(Object object) throws Exception{
		return new ObjectMapper().writeValueAsString(object);
	}
	

}
