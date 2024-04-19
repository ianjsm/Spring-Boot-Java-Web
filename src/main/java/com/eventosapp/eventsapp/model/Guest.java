package com.eventosapp.eventsapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Guest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String name;

	@ManyToMany(mappedBy = "guests")
	private List<Event> events = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Event> getEvents() {
		return events;
	}
}