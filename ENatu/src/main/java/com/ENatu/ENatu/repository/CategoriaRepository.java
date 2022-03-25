package com.ENatu.ENatu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ENatu.ENatu.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	List<Categoria> findAllByNomeCategoriaContainingIgnoreCase(String nomeCategoria);

	Optional<Categoria> findByNomeCategoria(String nomeCategoria);
}
