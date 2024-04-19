package com.eventosapp.eventsapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventosapp.eventsapp.model.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long>{

}
