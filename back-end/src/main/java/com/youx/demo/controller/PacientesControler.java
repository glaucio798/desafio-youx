package com.youx.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.youx.demo.repository.PacienteRepository;
import com.youx.demo.config.TextEncoder;
import com.youx.demo.model.Paciente;

@RestController
@RequestMapping("/pacientes")
public class PacientesControler {
	

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private TextEncoder te;
	
	@GetMapping
	public List<Paciente> listar() {
		List<Paciente> pacientes =  pacienteRepository.findAll();
		
		
		for(int i=0; i<pacientes.size(); i++) 
			pacientes.get(i).setCpf(te.bte().decrypt(pacientes.get(i).getCpf()));
		
		
		return pacientes;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Paciente adicionar(@Valid @RequestBody Paciente paciente) {

		List<Paciente> pacientes =  pacienteRepository.findAll();
		
		boolean existCPF = false;

		for(int i=0; i<pacientes.size(); i++) {
			if(te.bte().decrypt(pacientes.get(i).getCpf()).equals(paciente.getCpf())) {
				existCPF = true;
			}
		}
				
		if(existCPF) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já cadastrado");
		}
		
		
		paciente.setCpf(te.bte().encrypt(paciente.getCpf()));
		
		return pacienteRepository.save(paciente);
	}
	
}
 