package br.senai.sp.compras.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.senai.sp.compras.model.entity.CadFornecedor;
import br.senai.sp.compras.model.enums.StatusCadFornecedor;
import br.senai.sp.compras.model.enums.TipoPessoa;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class CadFornecedorRepositoryTest {
		
		@Autowired
		CadFornecedorRepository repository;
		
		@Autowired
		TestEntityManager entityManager;
		
		@Test
		public void deveSalvarUmFornecedor() {
			CadFornecedor fornecedor = criarFornecedor();
			
			fornecedor = repository.save(fornecedor);
			
			assertThat(fornecedor.getId()).isNotNull();
		}
		
		@Test
		public void deveDeletarUmFornecedor() {
			CadFornecedor fornecedor = criarEPersistirUmFornecedor();
			
			fornecedor = entityManager.find(CadFornecedor.class, fornecedor.getId());
			
			repository.delete(fornecedor);
			
			CadFornecedor fornecedorInexistente = entityManager.find(CadFornecedor.class, fornecedor.getId());
			assertThat(fornecedorInexistente).isNull();
		}
		
		@Test
		public void deveAtualizarUmFornecedor() {
			CadFornecedor fornecedor = criarEPersistirUmFornecedor();
			
			fornecedor.setNome("alejandro");
			fornecedor.setEmail("alejandro@gmail.com");
			fornecedor.setStatus(StatusCadFornecedor.CANCELADO);
			
			repository.save(fornecedor);
			
			CadFornecedor fornecedorAtualizado = entityManager.find(CadFornecedor.class, fornecedor.getId());
			
			assertThat(fornecedorAtualizado.getNome()).isEqualTo("alejandro");
			assertThat(fornecedorAtualizado.getEmail()).isEqualTo("alejandro@gmail.com");
			assertThat(fornecedorAtualizado.getStatus()).isEqualTo(StatusCadFornecedor.CANCELADO);
		}
		
		@Test
		public void deveBuscarUmFornecedorPorId() {
			CadFornecedor fornecedor = criarEPersistirUmFornecedor();
			
			Optional<CadFornecedor> fornecedorEncontrado = repository.findById(fornecedor.getId());
			
			assertThat(fornecedorEncontrado.isPresent()).isTrue();
		}

		
		private CadFornecedor criarEPersistirUmFornecedor() {
			CadFornecedor fornecedor = criarFornecedor();
			entityManager.persist(fornecedor);
			return fornecedor;
		}
		
		public static CadFornecedor criarFornecedor() {
			return CadFornecedor.builder()
			.nome("alejandro")
			.cep("")
			.cidade("")
			.bairro("")
			.endereco("")
			.complemento("")
			.numero("")
			.email("alejandro@gmail.com")
			.telefone("")
			.cpf_Cnpj("")
			.tipo(TipoPessoa.CPF)
			.status(StatusCadFornecedor.PENDENTE)
			.dataCadastro(LocalDate.now())
			.build();
		}
		
}
