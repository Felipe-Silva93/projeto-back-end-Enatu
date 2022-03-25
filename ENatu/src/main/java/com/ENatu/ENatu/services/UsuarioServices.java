package com.ENatu.ENatu.services;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ENatu.ENatu.model.UserLogin;
import com.ENatu.ENatu.model.Usuario;
import com.ENatu.ENatu.repository.UsuarioRepository;

@Service
public class UsuarioServices {

	private @Autowired UsuarioRepository repository;

	/**
	 * Metodo utilizado para buscar todos os usuários
	 * 
	 * @return Uma lista de usuários
	 * @since 1.0
	 * @author EN@tu
	 */

	public ResponseEntity<List<Usuario>> pegarTodos() {
		List<Usuario> listaDeNomes = repository.findAll();
		if (listaDeNomes.isEmpty()) {
			return ResponseEntity.status(204).build();

		} else {
			return ResponseEntity.status(200).body(listaDeNomes);
		}
	}

	/**
	 * Metodo utilizado para buscar usuários por ID
	 * 
	 * @param IdUsuario
	 * @return usuários específicos buscando pelo seu ID
	 * @since 1.0
	 * @author EN@tu
	 */
	public ResponseEntity<Usuario> pegarPorId(long idUsuario) {
		return repository.findById(idUsuario).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Metodo utilizado para buscar usuário por nome
	 * 
	 * @param nome
	 * @return usuários específicos buscando pelo seu nome
	 * @since 1.0
	 * @author EN@tu
	 */

	public ResponseEntity<List<Usuario>> pegarNomeUsuario(String nome) {

		List<Usuario> listaDeNomes = repository.findAllByNomeContainingIgnoreCase(nome);

		if (listaDeNomes.isEmpty()) {

			return ResponseEntity.status(204).build();
		} else {

			return ResponseEntity.status(200).body(listaDeNomes);
		}
	}

	/**
	 * Metodo utilizado para cadastrar usuários
	 * 
	 * @param novoUsuario
	 * @return usuário é salvo,caso ainda não seja cadastrado, se já estiver
	 *         cadastrado, não aceita
	 * @since 1.0
	 * @author EN@tu
	 */

	public ResponseEntity<Usuario> cadastrarUsuario(Usuario novoUsuario) {
		Optional<Usuario> usuarioExistente = repository.findByEmail(novoUsuario.getEmail());

		if (usuarioExistente.isPresent()) {
			return ResponseEntity.status(406).build();
		} else {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			String senhaEncoder = encoder.encode(novoUsuario.getSenha());
			novoUsuario.setSenha(senhaEncoder);

			return ResponseEntity.ok(repository.save(novoUsuario));

		}
	}

	/**
	 * Metodo utilizado para alterar um usuario
	 * 
	 * @param usuarioParaAlterar
	 * @return Uma entidade Usuario
	 * @since 1.0
	 * @author EN@tu
	 */
	public ResponseEntity<Usuario> atualizarUsuario(Usuario usuarioParaAlterar) {
		return repository.findByEmail(usuarioParaAlterar.getEmail()).map(usuarioExistente -> {
			usuarioExistente.setNome(usuarioParaAlterar.getNome());
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String senhaCriptografada = encoder.encode(usuarioParaAlterar.getSenha());
			usuarioExistente.setSenha(senhaCriptografada);
			return ResponseEntity.status(201).body(repository.save(usuarioExistente));
		}).orElse(ResponseEntity.status(401).build());
	}

	/**
	 * Metodo utilizado para deletar usuários
	 * 
	 * @param idUsuario
	 * @return usuário é deletado
	 * @since 1.0
	 * @author EN@tu
	 */

	public ResponseEntity<Object> deletarUsuario(Long idUsuario) {
		Optional<Usuario> idUsuarioExistente = repository.findById(idUsuario);

		if (idUsuarioExistente.isEmpty()) {
			return ResponseEntity.status(400).build();
		} else {
			repository.deleteById(idUsuario);
			return ResponseEntity.status(200).build();
		}
	}

	/**
	 * Metodo utilizado para fazer Autenticação do usuario
	 * 
	 * @param usuarioParaAutenticar do tipo UserLogin
	 * @return UserLogin com atributos preenchidos
	 * @since 1.0
	 * @author EN@tu
	 */
	public ResponseEntity<?> logarUsuario(UserLogin usuarioParaAutenticar) {
		return repository.findByEmail(usuarioParaAutenticar.getEmail()).map(usuarioExistente -> {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			if (encoder.matches(usuarioParaAutenticar.getSenha(), usuarioExistente.getSenha())) {
				String estruturaBasic = usuarioParaAutenticar.getEmail() + ":" + usuarioParaAutenticar.getSenha();
				byte[] autorizacaoBasic64 = Base64.encodeBase64(estruturaBasic.getBytes(Charset.forName("US-ASCII")));
				String autorizacaoHeader = "Basic " + new String(autorizacaoBasic64);

				usuarioParaAutenticar.setToken(autorizacaoHeader);
				usuarioParaAutenticar.setNome(usuarioExistente.getNome());
				usuarioParaAutenticar.setSenha(usuarioExistente.getSenha());
				usuarioParaAutenticar.setId(usuarioExistente.getIdUsuario());
				return ResponseEntity.status(200).body(usuarioParaAutenticar);
			} else {
				return ResponseEntity.status(401).build();
			}
		}).orElse(ResponseEntity.status(401).build());
	}

}