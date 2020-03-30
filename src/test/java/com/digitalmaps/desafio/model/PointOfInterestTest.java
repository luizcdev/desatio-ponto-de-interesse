package com.digitalmaps.desafio.model;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Time;

import org.junit.Test;


public class PointOfInterestTest {
	
	
	@Test
	public void TestIsAlwaysOpenedTrue() throws Exception {
		
		PointOfInterest pointOfInterest = new PointOfInterest();		
		assertTrue(pointOfInterest.isAlwaysOpened());
		
	}
	
	@Test
	public void TestIsAlwaysOpenedFalse() throws Exception  {
		
		PointOfInterest pointOfInterest = new PointOfInterest();
		
		pointOfInterest.setOpened("08:00");
		pointOfInterest.setClosed("18:00");
		
		assertFalse(pointOfInterest.isAlwaysOpened());
		
	}
	
	@Test
	public void TestStatusAlwaysOpened() throws Exception  {
		
		PointOfInterest pointOfInterest = new PointOfInterest();
		assertEquals("aberto", pointOfInterest.getStatus(Time.valueOf("10:00:00")));
		
	}
	
	@Test
	public void TestStatusOpened() throws Exception  {
		
		PointOfInterest pointOfInterest = new PointOfInterest();
		
		
		pointOfInterest.setOpened("08:00");
		pointOfInterest.setClosed("18:00");
		
		assertEquals("aberto", pointOfInterest.getStatus(Time.valueOf("10:00:00")));
		
	}
	
	@Test
	public void TestStatusClosed() throws Exception {
		
		PointOfInterest pointOfInterest = new PointOfInterest();
		
		pointOfInterest.setOpened("08:00");
		pointOfInterest.setClosed("18:00");
		
		assertEquals("fechado", pointOfInterest.getStatus(Time.valueOf("19:00:00")));
		
	}
	
	@Test
	public void TestGetDistanceSamePoint() {
		
		int x = 10;
		int y = 10;	
		
		PointOfInterest pointOfInterest = new PointOfInterest();
		pointOfInterest.setX(x);
		pointOfInterest.setY(y);	
		
		assertEquals(0, pointOfInterest.getDistance(x, y));
		
	}
	
	@Test
	public void TestGetDistanceWithoutRound() {
		
		PointOfInterest pointOfInterest = new PointOfInterest();
		pointOfInterest.setX(1);
		pointOfInterest.setY(1);	
		
		assertEquals(2, pointOfInterest.getDistance(3, 1));
		
	}
	
	@Test
	public void TestGetDistanceWithtRound() {
		
		PointOfInterest pointOfInterest = new PointOfInterest();
		pointOfInterest.setX(1);
		pointOfInterest.setY(1);	
		
		assertEquals(14, pointOfInterest.getDistance(11, 11));
		
	}
	
	@Test(expected = Exception.class)
	public void TestOpenedException() throws Exception{
		
		PointOfInterest pointOfInterest = new PointOfInterest();
		pointOfInterest.setOpened("25:00");	
		
	}
	
	@Test(expected = Exception.class)
	public void TestClosedException() throws Exception{
		
		PointOfInterest pointOfInterest = new PointOfInterest();
		pointOfInterest.setClosed("25:00");	
		
	}
	
	
}
