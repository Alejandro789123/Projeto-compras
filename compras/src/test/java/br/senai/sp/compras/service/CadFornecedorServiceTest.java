package br.senai.sp.compras.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.senai.sp.compras.exception.RegraNegocioException;
import br.senai.sp.compras.model.entity.CadFornecedor;
import br.senai.sp.compras.model.enums.StatusCadFornecedor;
import br.senai.sp.compras.model.repository.CadFornecedorRepository;
import br.senai.sp.compras.model.repository.CadFornecedorRepositoryTest;
import br.senai.sp.compras.service.impl.CadFornecedorServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CadFornecedorServiceTest {
	
	@SpyBean
	CadFornecedorServiceImpl service;
	@MockBean
	CadFornecedorRepository repository;
	
	@Test
	public void deveSalvarUmFornecedor() {
		//cenário
		CadFornecedor fornecedorASalvar = CadFornecedorRepositoryTest.criarFornecedor();
		doNothing().when(service).validar(fornecedorASalvar);
		
		CadFornecedor fornecedorSalvo = CadFornecedorRepositoryTest.criarFornecedor();
		fornecedorSalvo.setId(1l);
		fornecedorSalvo.setStatus(StatusCadFornecedor.PENDENTE);
		when(repository.save(fornecedorASalvar)).thenReturn(fornecedorSalvo);
		
		//execucao
		CadFornecedor fornecedor = service.salvar(fornecedorASalvar);
		
		//verificação
		assertThat( fornecedor.getId() ).isEqualTo(fornecedorSalvo.getId());
		assertThat(fornecedor.getStatus()).isEqualTo(StatusCadFornecedor.PENDENTE);
	}
	
	@Test
	public void naoDeveSalvarUmFornecedorQuandoHouverErroDeValidacao() {
		//cenário
		CadFornecedor fornecedorASalvar = CadFornecedorRepositoryTest.criarFornecedor();
		doThrow( RegraNegocioException.class ).when(service).validar(fornecedorASalvar);
		
		//execucao e verificacao
		catchThrowableOfType( () -> service.salvar(fornecedorASalvar), RegraNegocioException.class );
		verify(repository, never()).save(fornecedorASalvar);
	}
	
	@Test
	public void deveAtualizarUmFornecedor() {
		//cenário
		CadFornecedor fornecedorSalvo = CadFornecedorRepositoryTest.criarFornecedor();
		fornecedorSalvo.setId(1l);
		fornecedorSalvo.setStatus(StatusCadFornecedor.PENDENTE);
		
		
		doNothing().when(service).validar(fornecedorSalvo);
		
		when(repository.save(fornecedorSalvo)).thenReturn(fornecedorSalvo);
		//execucao
		service.atualizar(fornecedorSalvo);
		
		//verificação
		verify(repository, times(1)).save(fornecedorSalvo);
	}
	
	@Test
	public void deveLancarErroAoTentarAtualizarUmFornecedorQueAindaNaoFoiSalvo() {
		//cenário
		CadFornecedor fornecedor = CadFornecedorRepositoryTest.criarFornecedor();
		
		//execucao e verificacao
		catchThrowableOfType( () -> service.atualizar(fornecedor), NullPointerException.class );
		verify(repository, never()).save(fornecedor);
	}
	
	@Test
	public void deveDeletarUmFornecedor() {
		//cenário
		CadFornecedor fornecedor = CadFornecedorRepositoryTest.criarFornecedor();
		fornecedor.setId(1l);
		
		//execucao
		service.deletar(fornecedor);
		
		//verificacao
		verify( repository ).delete(fornecedor);
	}
	
	@Test
	public void deveLancarErroAoTentarDeletarUmFornecedorQueAindaNaoFoiSalvo() {
		
		//cenário
		CadFornecedor fornecedor = CadFornecedorRepositoryTest.criarFornecedor();
		
		//execucao
		catchThrowableOfType( () -> service.deletar(fornecedor), NullPointerException.class );
		
		//verificacao
		verify( repository, never() ).delete(fornecedor);
	}
	
	@Test
	public void deveFiltrarFornecedor() {
		//cenário
		CadFornecedor fornecedor = CadFornecedorRepositoryTest.criarFornecedor();
		fornecedor.setId(1l);
		
		List<CadFornecedor> lista = Arrays.asList(fornecedor);
		when( repository.findAll(any(Example.class)) ).thenReturn(lista);
		
		//execucao
		List<CadFornecedor> resultado = service.buscar(fornecedor);
		
		//verificacoes
		assertThat(resultado)
			.isNotEmpty()
			.hasSize(1)
			.contains(fornecedor);
	}
	
	@Test
	public void deveAtualizarOStatusDeUmLancamento() {
		//cenário
		CadFornecedor fornecedor = CadFornecedorRepositoryTest.criarFornecedor();
		fornecedor.setId(1l);
		fornecedor.setStatus(StatusCadFornecedor.PENDENTE);
		
		StatusCadFornecedor novoStatus = StatusCadFornecedor.EFETIVADO;
		doReturn(fornecedor).when(service).atualizar(fornecedor);
		
		//execucao
		service.atualizarStatus(fornecedor, novoStatus);
		
		//verificacoes
		assertThat(fornecedor.getStatus()).isEqualTo(novoStatus);
		verify(service).atualizar(fornecedor);	
	}
	
	@Test
	public void deveObterUmLancamentoPorID() {
		//cenário
		Long id = 1l;
		
		CadFornecedor fornecedor = CadFornecedorRepositoryTest.criarFornecedor();
		fornecedor.setId(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(fornecedor));
		
		//execucao
		Optional<CadFornecedor> resultado =  service.obterPorId(id);
		
		//verificacao
		assertThat(resultado.isPresent()).isTrue();
	}
	
	@Test
	public void deveREtornarVazioQuandoOLancamentoNaoExiste() {
		//cenário
		Long id = 1l;
		
		CadFornecedor fornecedor = CadFornecedorRepositoryTest.criarFornecedor();
		fornecedor.setId(id);
		
		when( repository.findById(id) ).thenReturn( Optional.empty() );
		
		//execucao
		Optional<CadFornecedor> resultado =  service.obterPorId(id);
		
		//verificacao
		assertThat(resultado.isPresent()).isFalse();
	}
	
	@Test
	public void deveLancarErrosAoValidarUmFornecedor() {
		CadFornecedor fornecedor = new CadFornecedor();
		
		Throwable erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe o nome válido.");
		
		fornecedor.setNome("");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe o nome válido.");
		
		fornecedor.setNome("Alejandro");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um cep válido.");
		
		fornecedor.setCep("");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um cep válido.");
		
		fornecedor.setCep("99999-999");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe cidade válido.");
		
		fornecedor.setCidade("");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe cidade válido.");
		
		fornecedor.setCidade("São paulo");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Bairro válido.");
		
		fornecedor.setBairro("");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Bairro válido.");
		
		fornecedor.setBairro("jardim vilas boas");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe o endereço válido.");
		
		fornecedor.setEndereco("");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe o endereço válido.");
		
		fornecedor.setEndereco("rua luis stolb");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um complemento válido.");
		
		fornecedor.setComplemento("");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um complemento válido.");
		
		fornecedor.setComplemento("casa");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um numero válido.");
		
		fornecedor.setNumero("");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um numero válido.");
		
		fornecedor.setNumero("238");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um email válido.");
		
		fornecedor.setEmail("");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um email válido.");
		
		fornecedor.setEmail("alejandro@gmail.com");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um telefone válido.");
		
		fornecedor.setTelefone("");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um telefone válido.");
		
		fornecedor.setTelefone("1194198-9573");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe o Cpf ou Cnpj válido.");
		
		fornecedor.setCpf_Cnpj("");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe o Cpf ou Cnpj válido.");
		
		fornecedor.setCpf_Cnpj("239710588-84");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um tipo de Pessoa válida.");
		
		erro = Assertions.catchThrowable( () -> service.validar(fornecedor) );
		assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um tipo de Pessoa válida.");
		
	}
}
