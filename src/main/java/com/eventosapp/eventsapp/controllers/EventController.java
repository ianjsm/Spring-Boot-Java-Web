package com.eventosapp.eventsapp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eventosapp.eventsapp.model.Event;
import com.eventosapp.eventsapp.repositories.EventRepository;

@Controller
public class EventController {
	
	@Autowired
	private EventRepository eventRepository;
	
	@GetMapping(value = "/registerEvent")
	public String form() {
		return "Event/formEvent";
	}
	
	@PostMapping(value = "/registerEvent")
	public String form(Event event) {
		
		eventRepository.save(event);
		return "redirect:/registerEvent";
	}
	
	@GetMapping(value = "/events")
	public ModelAndView eventsList() {
		ModelAndView mv = new ModelAndView("index");
		List<Event> events = new ArrayList<>();
		events = eventRepository.findAll();
		mv.addObject("eventos", events);
		return mv;
	}
}