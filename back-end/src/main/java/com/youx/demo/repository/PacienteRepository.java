package com.youx.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.youx.demo.model.Paciente;

@Repository
public interface PacienteRepository extends MongoRepository<Paciente, String> {

	Paciente findByCpf(String cpf);
	
}
