package br.senai.br.compras.model;

import java.util.Objects;

import br.senai.sp.compras.model.enums.Perfil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table( name = "Usuario")
public class Usuario {
	
	@Id 
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "email")
	@Email
	private String email;
	
	@Column(name = "senha")
	@Size(min = 8, max = 16)
	private String senha;
	
	@Column(name = "perfil")
	@Enumerated(value = EnumType.STRING)
	private Perfil perfil;
	
	
}
