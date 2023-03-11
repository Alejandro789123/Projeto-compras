package br.senai.br.compras.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CadFornecedores")
public class CadFornecedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "cep")
	private String cep ;
	
	@Column(name = "bairro")
	private String bairro;
	
	@Column(name = "endereço")
	private String endereço;
	
	@Column(name = "complemento")
	private String complemento;
	
	@Column(name = "numero")
	private String numero;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "telefone")
	private Long telefone;
	
	private String cpf_Cnpj;
	
	//Documento 
	private String tipoPessoa;
	
	
	
}
