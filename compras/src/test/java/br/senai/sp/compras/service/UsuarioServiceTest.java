package br.senai.sp.compras.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.senai.sp.compras.exception.RegraNegocioException;
import br.senai.sp.compras.model.entity.Usuario;
import br.senai.sp.compras.model.repository.UsuarioRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;
	
	@Test()
	public void deveValidaEmail() {
		// cenario
		repository.deleteAll();
		
		// acao
		service.validarEmail("usuario@easyconsys.com");
		
	}
	
	@Test()
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado( ) {
		// cenario
		Usuario usuario = Usuario.builder().nome("usuario").email("usuario@easyconsys.com").build();
		repository.save(usuario);
		
		/*
		 * expected = RegraNegocioException.class
		 * 
		 * // acao service.validarEmail("usuario@easyconsys.com");
		 */
	
		
		
	}
}
