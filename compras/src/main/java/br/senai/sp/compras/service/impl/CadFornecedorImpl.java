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
		if(cadFornecedor.getBairro() == null || cadFornecedor.getBairro().trim().equals("")) {
			throw new RegraNegocioException("Informe um Bairro válido.");
		}
		
		if(cadFornecedor.getNome() == null || cadFornecedor.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe o nome válido.");
		}
		
		if(cadFornecedor.getComplemento() == null || cadFornecedor.getComplemento().trim().equals("")) {
			throw new RegraNegocioException("Informe um complemento válido.");
		}
		
		if(cadFornecedor.getCpf_Cnpj() == null || cadFornecedor.getCpf_Cnpj().trim().equals("")) {
			throw new RegraNegocioException("Informe o Cpf ou Cnpj válido.");
		}

		if(cadFornecedor.getEndereço() == null || cadFornecedor.getEndereço().trim().equals("")) {
			throw new RegraNegocioException("Informe o endereço válido.");
		}
		
		if(cadFornecedor.getCep() == null || cadFornecedor.getCep().trim().equals("")) {
			throw new RegraNegocioException("Informe um cep válido.");
		}
		
		if(cadFornecedor.getEmail() == null || cadFornecedor.getEmail().trim().equals("")) {
			throw new RegraNegocioException("Informe um email válido.");
		}
		
		if(cadFornecedor.getCidade() == null || cadFornecedor.getCidade().trim().equals("")) {
			throw new RegraNegocioException("Informe cidade válido.");
		}
		
		if(cadFornecedor.getTipo() == null) {
			throw new RegraNegocioException("Informe um tipo de Pessoa.");
		}
		

		if(cadFornecedor.getNumero() == null || cadFornecedor.getNumero()< 1 || cadFornecedor.getNumero() >5) {
			throw new RegraNegocioException("Informe um numero válido.");
	
		}
	}
			

	@Override
	public Optional<CadFornecedor> obterPorId(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public boolean validarTelefone(String telefone) {
		  //retira todos os caracteres não-numéricos (incluindo espaço,tab, etc)
	    telefone = telefone.replaceAll("\\D","");
	    
	    //verifica se tem a qtde de numeros correta
	    if (!(telefone.length() >= 10 && telefone.length() <= 11)) return false;
	 
	    //Se tiver 11 caracteres, verificar se começa com 9 o celular
	    if (telefone.length() == 11 && Integer.parseInt(telefone.substring(2, 3)) != 9) return false;
	 
	    //verifica se o numero foi digitado com todos os dígitos iguais
	    java.util.regex.Pattern p = java.util.regex.Pattern.compile(telefone.charAt(0)+"{"+telefone.length()+"}");
	    java.util.regex.Matcher m = p.matcher(telefone);
	    if(m.find()) return false;
	    
	    //DDDs validos
	    Integer[] codigosDDD = {
	        11, 12, 13, 14, 15, 16, 17, 18, 19,
	        21, 22, 24, 27, 28, 31, 32, 33, 34,
	        35, 37, 38, 41, 42, 43, 44, 45, 46,
	        47, 48, 49, 51, 53, 54, 55, 61, 62,
	        64, 63, 65, 66, 67, 68, 69, 71, 73,
	        74, 75, 77, 79, 81, 82, 83, 84, 85,
	        86, 87, 88, 89, 91, 92, 93, 94, 95,
	        96, 97, 98, 99};
	    //verifica se o DDD é valido (sim, da pra verificar rsrsrs)
	    if ( java.util.Arrays.asList(codigosDDD).indexOf(Integer.parseInt(telefone.substring(0, 2))) == -1) return false;
	    
	    //Se o número só tiver dez digitos não é um celular e por isso o número logo após o DDD deve ser 2, 3, 4, 5 ou 7 
	    Integer[] prefixos = {2, 3, 4, 5, 7};
	    
	    if (telefone.length() == 10 && java.util.Arrays.asList(prefixos).indexOf(Integer.parseInt(telefone.substring(2, 3))) == -1) return false;
	    
	    //se passar por todas as validações acima, então está tudo certo
	    return true;
		
		
	}

	

}
