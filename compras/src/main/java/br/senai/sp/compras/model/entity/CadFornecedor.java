package br.senai.sp.compras.model.entity;

import java.time.LocalDate;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import br.senai.sp.compras.model.enums.StatusCadFornecedor;
import br.senai.sp.compras.model.enums.TipoPessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	private Integer cep ;
	
	@Column(name = "bairro")
	private String bairro;
	
	@Column(name = "endereço")
	private String endereço;
	
	@Column(name = "complemento")
	private String complemento;
	
	@Column(name = "numero")
	private Integer numero;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "telefone")
	private Integer telefone;
	
	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataCadastro;
	
	@Column(name = "cpf_Cnpj")
	private String cpf_Cnpj; // Cpf OU Cnpj
	
	@Column(name = "tipoPessoa") // Pode ser Pessoa fisica ou Juridica
	@Enumerated(value = EnumType.STRING)
	private TipoPessoa tipoPssoa;
	
	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private StatusCadFornecedor status;
	
}
