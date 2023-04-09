package br.senai.sp.compras.api.dto;

import br.senai.sp.compras.model.enums.Perfil;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class UsuarioDTO {
	
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private Perfil perfil;
}
