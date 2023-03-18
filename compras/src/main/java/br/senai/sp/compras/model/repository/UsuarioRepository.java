package br.senai.sp.compras.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sp.compras.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


		boolean existsByEmail(String email);

		
}
