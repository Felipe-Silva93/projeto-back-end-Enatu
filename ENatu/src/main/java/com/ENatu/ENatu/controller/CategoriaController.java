package com.ENatu.ENatu.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ENatu.ENatu.model.Categoria;
import com.ENatu.ENatu.services.CategoriaServices;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*" ,  allowedHeaders = "*")
public class CategoriaController {

	private @Autowired CategoriaServices services;

	@GetMapping("/todas")
	public ResponseEntity<List<Categoria>> pegarCategorias() {
		return services.pegarTodos();
	}

	@GetMapping("/id/{idCategoria}")
	public ResponseEntity<Categoria> GetById(@Valid @PathVariable long idCategoria) {
		return services.pegarPorId(idCategoria);
	}

	@GetMapping("/nome")
	public ResponseEntity<List<Categoria>> findByNome(@RequestParam(defaultValue = "") String nomeCategoria) {
		return services.pegarNomeCategoria(nomeCategoria);

	}

	@PostMapping("/salvar")
	public ResponseEntity<Categoria> salvarCategoria(@Valid @RequestBody Categoria novaCategoria) {
		return services.salvarCategoria(novaCategoria);
	}

	@PutMapping("/atualizar/{idCategoria}")
	public ResponseEntity<Categoria> putCategoria(@PathVariable long idCategoria,
			@Valid @RequestBody Categoria alterCategoria) {
		return services.atualizarCategoria(idCategoria, alterCategoria);
	}

	@DeleteMapping("/deletar/{idCategoria}")
	public ResponseEntity<Object> deletarCategoria(@PathVariable long idCategoria) {
		return services.deletarCategoria(idCategoria);
	}
}
