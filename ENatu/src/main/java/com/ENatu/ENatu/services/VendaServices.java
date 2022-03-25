package com.ENatu.ENatu.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ENatu.ENatu.model.Produtos;
import com.ENatu.ENatu.model.Usuario;
import com.ENatu.ENatu.model.Venda;
import com.ENatu.ENatu.repository.ProdutosRepository;
import com.ENatu.ENatu.repository.UsuarioRepository;
import com.ENatu.ENatu.repository.VendaRepository;

@Service
public class VendaServices {
	
	private @Autowired VendaRepository repository;
	private @Autowired UsuarioRepository repositoryU;
	private @Autowired ProdutosRepository repositoryP;
	
	/**
	 * Metodo utilizado para buscar todas as vendas
	 * 
	 * @return Uma lista com todas as vendas
	 * @since 1.0
	 * @author EN@tu
	 */
	public ResponseEntity<List<Venda>> pegarVendas(){
		List<Venda> listaDeVenda = repository.findAll();
		if(listaDeVenda.isEmpty()) {
			return ResponseEntity.status(204).build();
			
			}else {
				return ResponseEntity.status(200).body(listaDeVenda);
			}
	}
	
	/**
	 * Metodo utilizado para buscar uma venda específica por ID
	 * 
	 * @param IdVenda
	 * @return vendas específicas buscando pelo seu ID
	 * @since 1.0
	 * @author EN@tu
	 */
	public ResponseEntity<Venda> PegarIdVenda(@Valid @PathVariable long idVenda) {
		return repository.findById(idVenda)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	/**
	 * Metodo utilizado para registrar uma nova venda no sistema
	 * 
	 * @param idProduto, idUsuario, novaVenda
	 * @return Se o produto e o usuário estiver presente, salva a venda. Se não, retorna 400, não realizando o salvamento.
	 * @since 1.0
	 * @author EN@tu
	 */
	public ResponseEntity<Venda> novaVenda(long idProduto, long idUsuario, Venda novaVenda ) {
		Optional<Produtos> produtoExistente = repositoryP.findById(idProduto);
		Optional<Usuario> usuarioExistente = repositoryU.findById(idUsuario);
		
		if (produtoExistente.isPresent() && usuarioExistente.isPresent()) {
			novaVenda.setProdutos(produtoExistente.get());
			novaVenda.setUsuario(usuarioExistente.get());
			return ResponseEntity.status(201).body(repository.save(novaVenda));
		} else {
			return ResponseEntity.status(400).build();
		}
	}
	
	/**
	 * Metodo utilizado para deletar uma venda
	 * 
	 * @param idVenda
	 * @returnão Se a venda estiver vazia(não existir) apresenta uma bad request. Se não estiver, é possível deletar pelo Id da venda.
	 * @since 1.0
	 * @author EN@tu
	 */
	public ResponseEntity<Object> deletarVenda(Long idVenda) {
        Optional<Venda> VendaExistente = repository.findById(idVenda);

        if (VendaExistente.isEmpty()) {
            return ResponseEntity.status(400).build();
        } else {
            repository.deleteById(idVenda);
            return ResponseEntity.status(200).build();
        }
    }
}
