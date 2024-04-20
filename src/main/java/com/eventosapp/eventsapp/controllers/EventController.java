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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String form(Event event, RedirectAttributes attributes) {
		if (newEventValidation(event) == true) {
			eventRepository.save(event);
			attributes.addFlashAttribute("mensagem", "Evento adicionado com sucesso");
			return "redirect:/events";
		} else {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/registerEvent";
		}
	}

	private boolean newEventValidation(Event event) {
		if (event.getName().isBlank() || event.getLocal().isBlank() || event.getDate().isBlank() || event.getTime().isBlank()) {
			return false;
		} else {
			return true;
		}
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

		List<Guest> guests = event.getGuests();
		mv.addObject("guests", guests);

		return mv;
	}

	@PostMapping(value = "/event/{id}")
	public String addGuestToEvent(@PathVariable(value = "id") Long id, Guest guest, RedirectAttributes attributes) {
		Optional<Event> optionalEvent = eventRepository.findById(id);
		Event event = optionalEvent.orElse(null);
		if (newGuestValidation(guest)) {
			guestRepository.save(guest);
			event.getGuests().add(guest);
			eventRepository.save(event);
			attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso");
			return "redirect:/event/{id}";
		} else {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/event/{id}";
		}
	}

	private boolean newGuestValidation(Guest guest) {
		if (guest.getId() != null && guest.getName() != "") {
			return true;
		} else {
			return false;
		}
	}
	
	@PostMapping(value = "/events/delete/{id}")
	public String deleteEvent(@PathVariable(value = "id") Long id) {
	    eventRepository.deleteById(id);
	    return "redirect:/events";
	}
}