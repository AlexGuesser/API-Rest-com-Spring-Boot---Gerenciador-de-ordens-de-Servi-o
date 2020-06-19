package com.alexguesser.oswork.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alexguesser.oswork.api.model.ComentarioInput;
import com.alexguesser.oswork.api.model.ComentarioModel;
import com.alexguesser.oswork.domain.exception.EntidadeNaoEncontradaException;
import com.alexguesser.oswork.domain.model.Comentario;
import com.alexguesser.oswork.domain.model.OrdemServico;
import com.alexguesser.oswork.domain.repository.OrdemServicoRepository;
import com.alexguesser.oswork.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servicos/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar (@PathVariable Long ordemServicoId,
			@Valid @RequestBody ComentarioInput comentarioInput) {
		
		Comentario comentario = gestaoOrdemServicoService.adicionarComentario(ordemServicoId, comentarioInput.getDescricao());
		return toModel(comentario);
		
	}
	
	@GetMapping
	public List<ComentarioModel> listaComentarios(@PathVariable Long ordemServicoId){
		
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() ->new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
		
		return toCollectionModel(ordemServico.getComentarios());
		
		
		
		
	}
	
	private ComentarioModel toModel(Comentario comentario) {
		
		return modelMapper.map(comentario,ComentarioModel.class);
		
	}
	
	private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios){
		
		return comentarios.stream()
				.map(comentario -> toModel(comentario))
				.collect(Collectors.toList());
		
	}
	
}
