package br.senai.sp.compras.service.impl;

import br.senai.br.compras.model.Usuario;
import br.senai.sp.compras.model.repository.UsuarioRepository;
import br.senai.sp.compras.service.UsuarioService;

public class UsuarioServiceImpl  implements UsuarioService{

	private UsuarioRepository repository;
	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validarEmail(String email) {
		// TODO Auto-generated method stub
		
	}

}
