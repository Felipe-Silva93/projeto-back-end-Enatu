package com.ENatu.ENatu.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idUsuario;
	private @NotNull(message = "Passar um valor aqui,não pode ser nulo.") String nome;
	private @NotNull(message = "Passar um valor aqui,não pode ser nulo.") @Size(min = 5, max = 100) String email;

	private @NotNull(message = "Passar um valor aqui,não pode ser nulo. Mínimo de 6 caracteres") @Size(min = 6) String senha;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({ "vendas", "usuario" })

	private List<Venda> minhasCompras = new ArrayList<>();

	public List<Venda> getMinhasCompras() {
		return minhasCompras;
	}

	public void setMinhasCompras(List<Venda> minhasCompras) {
		this.minhasCompras = minhasCompras;
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
