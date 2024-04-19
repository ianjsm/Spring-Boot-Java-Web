package com.eventosapp.eventsapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventosapp.eventsapp.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}