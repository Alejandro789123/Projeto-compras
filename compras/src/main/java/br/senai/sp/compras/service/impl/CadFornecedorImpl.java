package br.senai.sp.compras.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.compras.exception.RegraNegocioException;
import br.senai.sp.compras.model.entity.CadFornecedor;
import br.senai.sp.compras.model.enums.StatusCadFornecedor;
import br.senai.sp.compras.model.repository.CadFornecedorRepository;
import br.senai.sp.compras.service.CadFornecedorService;

@Service
public class CadFornecedorImpl implements CadFornecedorService{

	private CadFornecedorRepository repository;
	
	public CadFornecedorImpl(CadFornecedorRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public CadFornecedor salvar(CadFornecedor cadFornecedor) {
		validar(cadFornecedor);
		cadFornecedor.setStatus(StatusCadFornecedor.PENDENTE);
		return repository.save(cadFornecedor);
	}

	@Override
	@Transactional
	public CadFornecedor atualizar(CadFornecedor cadFornecedor) {
		Objects.requireNonNull(cadFornecedor.getId());
		validar(cadFornecedor);
		return repository.save(cadFornecedor);
	}
	@Override
	@Transactional
	public void deletar(CadFornecedor cadFornecedor) {
		Objects.requireNonNull(cadFornecedor.getId());
		repository.delete(cadFornecedor);
	}
	
	@Override
	public void atualizarStatus(CadFornecedor cadFornecedor, StatusCadFornecedor status) {
		cadFornecedor.setStatus(status);
		atualizar(cadFornecedor);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CadFornecedor> buscar(CadFornecedor cadFornecedorFiltro) {
		Example example = Example.of( cadFornecedorFiltro, 
				ExampleMatcher.matching()
					.withIgnoreCase()
					.withStringMatcher(StringMatcher.CONTAINING) );
		
		return repository.findAll(example);
	}

	@Override
	public void validar(CadFornecedor cadFornecedor) {
		/*if(cadFornecedor.getBairro() == null || cadFornecedor.getBairro().trim().equals("")) {
			throw new RegraNegocioException("Informe um Bairro válido.");
		}
		
		if(cadFornecedor.getNome() == null || cadFornecedor.getMes() < 1 || lancamento.getMes() > 12) {
			throw new RegraNegocioException(".");
		}
		
		if(cadFornecedor.getComplemento() == null || cadFornecedor.getComplemento().toString().length() != 4 ) {
			throw new RegraNegocioException(".");
		}
		
		if(cadFornecedor.getCpf_Cnpj() == null || cadFornecedor.getCpf_Cnpj().getId() == null) {
			throw new RegraNegocioException(".");
		}
		
		if(cadFornecedor.getEndereço() == null || cadFornecedor.getEndereço().compareTo(BigDecimal.ZERO) < 1 ) {
			throw new RegraNegocioException(".");
		}
		
		if(cadFornecedor.getCep() == null || cadFornecedor.getEndereço().compareTo(BigDecimal.ZERO) < 1 ) {
			throw new RegraNegocioException(".");
		}
		
		if(cadFornecedor.getNumero() == null || cadFornecedor.getNumero().getId() == null) {
			throw new RegraNegocioException(".");
		}
		if(cadFornecedor.getEmail() == null || cadFornecedor.getEmail().getId() == null) {
			throw new RegraNegocioException(".");
		}
	
		if(cadFornecedor.getTipoPssoa() == null) {
			throw new RegraNegocioException(".");
		}
		
		if(cadFornecedor.getTelefone() == null || cadFornecedor.getTelefone().trim().equals("")) {
			throw new RegraNegocioException("Informe um Bairro válido.");
		}*/
		
	}
		

	@Override
	public Optional<CadFornecedor> obterPorId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	

}
