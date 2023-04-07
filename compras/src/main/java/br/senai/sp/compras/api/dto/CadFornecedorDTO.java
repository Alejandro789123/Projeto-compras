package br.senai.sp.compras.api.dto;

import br.senai.sp.compras.model.enums.StatusCadFornecedor;
import br.senai.sp.compras.model.enums.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CadFornecedorDTO {
	private Long id;
	private String nome;
	private String cep ;
	private String cidade ;
	private String bairro;
	private String endereço;
	private String complemento;
	private String numero;
	private String email;
	private String telefone;
	private String cpf_Cnpj;
	private String tipo;
	private String status;
}
