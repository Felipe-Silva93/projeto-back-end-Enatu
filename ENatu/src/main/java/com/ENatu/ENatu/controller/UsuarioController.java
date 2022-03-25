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

import com.ENatu.ENatu.model.UserLogin;
import com.ENatu.ENatu.model.Usuario;
import com.ENatu.ENatu.services.UsuarioServices;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*" ,  allowedHeaders = "*")
public class UsuarioController {

	private @Autowired UsuarioServices services;

	@GetMapping("/todosUsuarios")
	public ResponseEntity<List<Usuario>> pegarUsuarios() {
		return services.pegarTodos();
	}

	@GetMapping("/id/{idUsuario}")
	public ResponseEntity<Usuario> GetById(@Valid @PathVariable long idUsuario) {
		return services.pegarPorId(idUsuario);
	}

	@GetMapping("/nome")
	public ResponseEntity<List<Usuario>> findByNome(@RequestParam(defaultValue = "") String nome) {
		return services.pegarNomeUsuario(nome);

	}

	@PutMapping("/alterar")
	public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario alterUsuario) {
		return services.atualizarUsuario(alterUsuario);
	}

	@DeleteMapping("/deletar/{idUsuario}")
	public ResponseEntity<Object> deleteUsuario(@PathVariable long idUsuario) {
		return services.deletarUsuario(idUsuario);
	}

	@PostMapping("/logar")
	public ResponseEntity<?> Autentication(@Valid @RequestBody UserLogin usuarioParaAutenticar) {
		return services.logarUsuario(usuarioParaAutenticar);
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> Post(@Valid @RequestBody Usuario usuario) {
		return services.cadastrarUsuario(usuario);
	}

}