package br.senai.sp.compras.model.repository;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.senai.br.compras.model.Usuario;



@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioRepositoryTest {

	@Autowired
	Usuario repository;
	
	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		
	}
}
