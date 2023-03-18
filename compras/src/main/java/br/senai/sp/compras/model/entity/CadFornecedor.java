package br.senai.sp.compras.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Table(name = "CadFornecedores")
@Data
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
	
	@Column(name = "cpf_Cnpj")
	private String cpf_Cnpj; // Cpf OU Cnpj
	
	@Column(name = "tipoPessoa") // Pode ser Pessoa fisica ou Juridica
	private String tipoPessoa;
	
	
	
}
