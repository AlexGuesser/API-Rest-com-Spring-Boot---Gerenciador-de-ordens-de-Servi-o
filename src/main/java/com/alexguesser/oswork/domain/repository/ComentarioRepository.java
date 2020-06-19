package com.alexguesser.oswork.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexguesser.oswork.domain.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario,Long>{
	
	

}
