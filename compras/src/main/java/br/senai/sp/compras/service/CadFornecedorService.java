package br.senai.sp.compras.service;

import br.senai.sp.compras.model.entity.CadFornecedor;
import br.senai.sp.compras.model.enums.StatusCadFornecedor;

import java.util.List;
import java.util.Optional;

public interface CadFornecedorService {

	CadFornecedor salvar(CadFornecedor cadFornecedor);

	CadFornecedor atualizar(CadFornecedor cadFornecedor);

	void deletar(CadFornecedor cadFornecedor);

	List<CadFornecedor> buscar(CadFornecedor cadFornecedor);
	
	void atualizarStatus(CadFornecedor cadFornecedor, StatusCadFornecedor status);

	void validar(CadFornecedor cadFornecedor);

	Optional<CadFornecedor> obterPorId(Long id);
	
	void validarEmail(String email);

}
