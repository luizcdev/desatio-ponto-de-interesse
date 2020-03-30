package com.digitalmaps.desafio.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import com.digitalmaps.desafio.core.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class PointOfInterest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Transient
	final String STATUS_OPENED = "aberto";
	
	@Transient
	final String STATUS_CLOSED = "fechado";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;
	
	@ApiModelProperty(value = "Nome do ponto de interesse")
	@NotEmpty(message = "O campo nome deve ser informado")
	public String name;	
	
	@ApiModelProperty(value = "Posição X do ponto de interesse")
	@Positive(message="O campo X deve ser positivo")
	public int x;	
	
	@ApiModelProperty(value = "Posição Y do ponto de interesse")
	@Positive(message="O campo Y deve ser positivo")
	public int y;
	
	@ApiModelProperty(value = "Horário de abertura do ponto de interesse em HH:mm")
	@Temporal(TemporalType.TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtil.PATTERN_HOUR_MINUTE, timezone= TimeUtil.TIME_ZONE_SAO_PAULO)
	public Date opened;
	
	@ApiModelProperty(value = "Horário de fechamento do ponto de interesse em HH:mm")
	@Temporal(TemporalType.TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtil.PATTERN_HOUR_MINUTE, timezone= TimeUtil.TIME_ZONE_SAO_PAULO)
	public Date closed;

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Date getOpened() {
		return opened;
	}

	public void setOpened(String opened) throws  Exception{
		try {
			this.opened = TimeUtil.stringToTime(TimeUtil.PATTERN_HOUR_MINUTE, opened);
		} catch (Exception e) {
			throw new Exception("Horário de abertura inválido");
		}
	}

	public Date getClosed() {
		return closed;
	}

	public void setClosed(String closed)  throws Exception{
		try {
			this.closed = TimeUtil.stringToTime(TimeUtil.PATTERN_HOUR_MINUTE, closed);
		} catch (Exception e) {
			throw new Exception("Horário de fechamento inválido");
		}
	}

	
	@JsonIgnore
	public boolean isAlwaysOpened() {
		return (this.opened == null) || (this.closed ==null);
	}
	
	@JsonIgnore
	public String getStatus(Time hour) {
		
		if (isAlwaysOpened())
			return this.STATUS_OPENED;
		
		if (TimeUtil.isBetweenTwoTimes(hour, new Time(this.opened.getTime()), new Time(this.closed.getTime())))
			return this.STATUS_OPENED;
		else
			return this.STATUS_CLOSED;
	}
	
	@JsonIgnore 
	public int getDistance(int referenceX, int referenceY) {
		
		int vectorX = Math.abs(this.x - referenceX);
		int vectorY = Math.abs(this.y - referenceY);
		
		return (int) Math.hypot(vectorX, vectorY);
		
	}
	

}
