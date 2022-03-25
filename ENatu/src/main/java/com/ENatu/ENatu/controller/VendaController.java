package com.ENatu.ENatu.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ENatu.ENatu.model.Venda;
import com.ENatu.ENatu.services.VendaServices;

@RestController
@RequestMapping("/pedido")
public class VendaController {

	private @Autowired VendaServices services;

	@GetMapping("/todos")
	public ResponseEntity<List<Venda>> pegarVendas() {
		return services.pegarVendas();
	}

	@GetMapping("/id/{idVenda}")
	public ResponseEntity<Venda> PegarIdVenda(@Valid @PathVariable long idVenda) {
		return services.PegarIdVenda(idVenda);
	}

	@PostMapping("/novo/usuario/{id_usuario}/produto/{id_produto}")
	public ResponseEntity<Venda> novaVenda(@Valid @RequestBody Venda novaVenda,
			@PathVariable(value = "id_usuario") Long idUsuario, @PathVariable(value = "id_produto") Long idProduto) {

		return services.novaVenda(idProduto, idUsuario, novaVenda);
	}

	@DeleteMapping("/deletarPedido/{idVenda}")
	public ResponseEntity<Object> deletarPedido(@PathVariable long idVenda) {
		return services.deletarVenda(idVenda);
	}
}
