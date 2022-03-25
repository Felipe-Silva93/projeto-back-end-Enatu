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

import com.ENatu.ENatu.model.Produtos;
import com.ENatu.ENatu.services.ProdutoServices;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*" ,  allowedHeaders = "*")
public class ProdutosController {

	private @Autowired ProdutoServices services;

	@GetMapping("/todos")
	public ResponseEntity<List<Produtos>> pegarProdutos() {
		return services.pegarTodos();
	}

	@GetMapping("/id/{idProduto}")
	public ResponseEntity<Produtos> GetById(@Valid @PathVariable long idProduto) {
		return services.pegarPorId(idProduto);
	}

	@GetMapping("/nome")
	public ResponseEntity<List<Produtos>> findByNome(@RequestParam(defaultValue = "") String nomeProduto) {
		return services.pegarNomeProduto(nomeProduto);

	}

	@PostMapping("/salvar")
	public ResponseEntity<Object> salvarProduto(@Valid @RequestBody Produtos novoProduto) {
		return services.salvarProduto(novoProduto);
	}

	@PutMapping("/atualizar/{idProduto}")
	public ResponseEntity<Produtos> putProduto(@PathVariable long idProduto,
			@Valid @RequestBody Produtos alterProduto) {
		return services.atualizarProduto(idProduto, alterProduto);
	}

	@DeleteMapping("/deletar/{idProduto}")
	public ResponseEntity<Object> deletarProduto(@PathVariable long idProduto) {
		return services.deletarProduto(idProduto);
	}
}
