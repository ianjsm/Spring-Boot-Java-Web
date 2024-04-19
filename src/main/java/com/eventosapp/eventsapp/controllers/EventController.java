package com.eventosapp.eventsapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eventosapp.eventsapp.model.Event;
import com.eventosapp.eventsapp.model.Guest;
import com.eventosapp.eventsapp.repositories.EventRepository;
import com.eventosapp.eventsapp.repositories.GuestRepository;

@Controller
public class EventController {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private GuestRepository guestRepository;
	
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
	
	@GetMapping(value = "/event/{id}")
	public ModelAndView eventDetails(@PathVariable(value = "id") Long id) {
		ModelAndView mv = new ModelAndView("Event/eventDetails");
		Optional<Event> eventDetailsOptional = eventRepository.findById(id);
		Event event = eventDetailsOptional.orElse(null);
		mv.addObject("event", event);
		return mv;
	}
	
	@PostMapping(value = "/event/{id}")
	public String addGuestToEvent(@PathVariable(value = "id") Long id, Guest guest) {
		Optional<Event> optionalEvent = eventRepository.findById(id);
		Event event = optionalEvent.get();
		guestRepository.save(guest);
		event.getGuests().add(guest);
		eventRepository.save(event);
		return "redirect:/event/{id}";
	}
}