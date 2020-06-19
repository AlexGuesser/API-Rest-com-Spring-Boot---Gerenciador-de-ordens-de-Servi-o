package com.alexguesser.oswork.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alexguesser.oswork.domain.model.Cliente;
import com.alexguesser.oswork.domain.repository.ClienteRepository;
import com.alexguesser.oswork.domain.service.CadastroClienteService;;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	
	@Autowired
	private CadastroClienteService cadastroCliente;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping 
	public List<Cliente> listarClientes() {
		return clienteRepository.findAll();
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscarCliente(@PathVariable Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente criarCliente(@Valid @RequestBody Cliente cliente) {	
		return cadastroCliente.salvar(cliente);			
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizarCliente(@Valid @PathVariable Long clienteId,@RequestBody Cliente cliente){
		
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(clienteId);
	    cliente=cadastroCliente.salvar(cliente);
		
		return ResponseEntity.ok(cliente);
		
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> deletarCliente(@PathVariable Long clienteId){
		
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}	
		cadastroCliente.excluir(clienteId);
		return ResponseEntity.noContent().build();
	}

}










