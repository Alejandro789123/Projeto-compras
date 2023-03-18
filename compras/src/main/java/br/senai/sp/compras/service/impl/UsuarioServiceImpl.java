package br.senai.sp.compras.service.impl;

import org.springframework.stereotype.Service;

import br.senai.sp.compras.exception.RegraNegocioException;
import br.senai.sp.compras.model.entity.Usuario;
import br.senai.sp.compras.model.repository.UsuarioRepository;
import br.senai.sp.compras.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

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
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Ja existe um usuario cadastrado com este email.");
		}
	}
}
