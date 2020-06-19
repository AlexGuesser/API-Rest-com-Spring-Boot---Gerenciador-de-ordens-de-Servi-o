package com.alexguesser.oswork.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alexguesser.oswork.api.model.OrdemServicoInput;
import com.alexguesser.oswork.api.model.OrdemServicoModel;
import com.alexguesser.oswork.domain.model.OrdemServico;
import com.alexguesser.oswork.domain.model.StatusOrdemServico;
import com.alexguesser.oswork.domain.repository.OrdemServicoRepository;
import com.alexguesser.oswork.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servicos")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criaOS(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		OrdemServico ordemServico = toEntity(ordemServicoInput);
		return toModel(gestaoOrdemServico.criar(ordemServico));
		
	}
	
	@GetMapping
	public List<OrdemServicoModel> listaOS(){
		return toCollectionModel(ordemServicoRepository.findAll());
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModel> buscaOS(@PathVariable Long ordemServicoId){
		
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
		
		if(ordemServico.isPresent()) {
			OrdemServicoModel model = toModel(ordemServico.get());	
			return ResponseEntity.ok(model);	
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId) {
		
		gestaoOrdemServico.finalizarOS(ordemServicoId);
		
	}
	
	@PutMapping("/{ordemServicoId}/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable Long ordemServicoId) {
		
		gestaoOrdemServico.cancelarOS(ordemServicoId);
		
	}
	
	
	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoModel.class);	
	}
	
	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico){
		
		return ordensServico.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
		
	}
	
	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		
		return modelMapper.map(ordemServicoInput, OrdemServico.class);
	}

}
