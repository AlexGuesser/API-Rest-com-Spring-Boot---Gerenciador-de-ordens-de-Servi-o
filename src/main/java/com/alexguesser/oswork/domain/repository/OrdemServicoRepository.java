package com.alexguesser.oswork.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexguesser.oswork.domain.model.Cliente;
import com.alexguesser.oswork.domain.model.OrdemServico;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long>{

}
