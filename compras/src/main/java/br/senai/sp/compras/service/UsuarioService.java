package br.senai.sp.compras.service;

import java.util.List;
import java.util.Optional;

import br.senai.sp.compras.model.entity.CadFornecedor;
import br.senai.sp.compras.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);
	
	Optional<Usuario> obterPorId(Long id);
	
	List<Usuario> buscar(Usuario usuarioFiltro);
	
	void validarUsuario(Usuario usuario);
	
	
}
