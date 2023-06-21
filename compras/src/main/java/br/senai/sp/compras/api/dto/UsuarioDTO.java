package br.senai.sp.compras.api.dto;

import br.senai.sp.compras.model.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
	
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private Perfil perfil;
}
