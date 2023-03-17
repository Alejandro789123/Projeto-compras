package br.senai.sp.compras.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.br.compras.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


		boolean existsByEmail(String email);

		
}
