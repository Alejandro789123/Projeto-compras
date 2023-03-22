package br.senai.sp.compras.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.senai.sp.compras.model.entity.Usuario;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {

		// cenário
		Usuario usuario = Usuario.builder().nome("usuario").email("usuario@easyconsys.com").build();
		repository.save(usuario);

		// ação/ execução
		boolean result = repository.existsByEmail("usuario@easyconsys.com");

		// verificação
		Assertions.assertThat(result).isTrue();

	}

	@Test
	public void deveRetornaFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		// cenario
		repository.deleteAll();

		// acao
		boolean result = repository.existsByEmail("usuario@easyconsys.com");

		// verificacao
		Assertions.assertThat(result).isFalse();
	}
}
