package com.alexguesser.oswork.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexguesser.oswork.domain.exception.EntidadeNaoEncontradaException;
import com.alexguesser.oswork.domain.exception.NegocioException;
import com.alexguesser.oswork.domain.model.Cliente;
import com.alexguesser.oswork.domain.model.Comentario;
import com.alexguesser.oswork.domain.model.OrdemServico;
import com.alexguesser.oswork.domain.model.StatusOrdemServico;
import com.alexguesser.oswork.domain.repository.ClienteRepository;
import com.alexguesser.oswork.domain.repository.ComentarioRepository;
import com.alexguesser.oswork.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
		
	}
	
	public Comentario adicionarComentario(Long ordemServicoId,String descricao) {
		
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}
	
	public void finalizarOS(Long ordemServicoId) {
		
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
		
		ordemServico.finalizaOrdemServico();
		
		ordemServicoRepository.save(ordemServico);
		
	}
	
public void cancelarOS(Long ordemServicoId) {
		
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
		
		ordemServico.cancelaOrdemServico();
		
		ordemServicoRepository.save(ordemServico);
		
		
	}

}
