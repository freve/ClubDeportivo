package ufps.microservicios.servicioclub.dao;

import org.springframework.data.repository.CrudRepository;

import ufps.microservicios.servicioclub.models.Club;

public interface ClubDao extends CrudRepository<Club, Integer> {

}
