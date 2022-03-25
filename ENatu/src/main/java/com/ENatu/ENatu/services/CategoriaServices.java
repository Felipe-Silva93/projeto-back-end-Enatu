package com.ENatu.ENatu.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ENatu.ENatu.model.Categoria;
import com.ENatu.ENatu.repository.CategoriaRepository;

@Service
public class CategoriaServices {
	private @Autowired CategoriaRepository repository;
	
	/**
	 * Metodo utilizado para buscar todas as categorias
	 * 
	 * @return Uma lista de categoria
	 * @since 1.0
	 * @author EN@tu
	 */

	public ResponseEntity<List<Categoria>> pegarTodos() {
		List<Categoria> listaDeCategorias = repository.findAll();
		if (listaDeCategorias.isEmpty()) {
			return ResponseEntity.status(204).build();

		} else {
			return ResponseEntity.status(200).body(listaDeCategorias);
		}
	}

	/**
	 * Metodo utilizado para buscar categorias por ID
	 * 
	 * @param IdCategoria
	 * @return categorias específicas dos produtos buscando pelo seu ID
	 * @since 1.0
	 * @author EN@tu
	 */
	
	public ResponseEntity<Categoria> pegarPorId(long idCategoria) {
		return repository.findById(idCategoria).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	/**
	 * Metodo utilizado para buscar categoria por nome
	 * 
	 * @param nomeCategoria
	 * @return categorias específicas dos produtos buscando pelo seu nome
	 * @since 1.0
	 * @author EN@tu
	 */
	
	public ResponseEntity<List<Categoria>> pegarNomeCategoria(String nomeCategoria) {

		List<Categoria> listaDeCategorias = repository.findAllByNomeCategoriaContainingIgnoreCase(nomeCategoria);

		if (listaDeCategorias.isEmpty()) {

			return ResponseEntity.status(204).build();
		} else {

			return ResponseEntity.status(200).body(listaDeCategorias);
		}
	}
	
	/**
	 * Metodo utilizado para salvar uma nova categoria 
	 * 
	 * @param novaCategoria
	 * @return Se a categoria existe, retorna que já é presente (406). Se não existe salva como uma nova categoria.
	 * @since 1.0
	 * @author EN@tu
	 */
	public ResponseEntity<Categoria> salvarCategoria(Categoria novaCategoria) {
		Optional<Categoria> categoriaExistente = repository.findByNomeCategoria(novaCategoria.getNomeCategoria());

		if (categoriaExistente.isPresent()) {
			return ResponseEntity.status(406).build();
		} else {
			return ResponseEntity.status(201).body(repository.save(novaCategoria));
		}
	}
	
	/**
	 * Metodo utilizado para atualizar a categoria
	 * 
	 * @param idCategoria e alterCategoria
	 * @return Se a categoria já existir, é atualizada. Se não, retora (405) não existe
	 * @since 1.0
	 * @author EN@tu
	 */
	public ResponseEntity<Categoria> atualizarCategoria(Long idCategoria, Categoria alterCategoria) {
		Optional<Categoria> idCategoriaExistente = repository.findById(idCategoria);

		if (idCategoriaExistente.isPresent()) {

			idCategoriaExistente.get().setNomeCategoria(alterCategoria.getNomeCategoria());
			idCategoriaExistente.get().setDescricao(alterCategoria.getDescricao());
			return ResponseEntity.status(202).body(repository.save(idCategoriaExistente.get()));

		} else {
			return ResponseEntity.status(405).build();
		}
	}
	
	/**
	 * Metodo utilizado para deletar a categoria
	 * 
	 * @param idCategoria
	 * @return Se a categoria estiver vazia, não tem como deletar. Se não tiver é possível apagar a categoria.
	 * @since 1.0
	 * @author EN@tu
	 */

	public ResponseEntity<Object> deletarCategoria(Long idCategoria) {
		Optional<Categoria> CategoriaExistente = repository.findById(idCategoria);

		if (CategoriaExistente.isEmpty()) {
			return ResponseEntity.status(400).build();
		} else {
			repository.deleteById(idCategoria);
			return ResponseEntity.status(200).build();
		}
	}
}
