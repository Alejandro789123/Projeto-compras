package br.senai.sp.compras.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.compras.exception.ErroAutenticacao;
import br.senai.sp.compras.exception.RegraNegocioException;
import br.senai.sp.compras.model.entity.Usuario;
import br.senai.sp.compras.model.repository.UsuarioRepository;
import br.senai.sp.compras.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	private UsuarioRepository  repository;
	

	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}
 

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
		}
	
		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha inválida.");
		}
		
		usuario.get().setSenha("");
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarUsuario(usuario);
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}
	
	

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
		}
	}


	@Override
	public Optional<Usuario> obterPorId(Long id) {
		return repository.findById(id);
	}


	@Override
	@Transactional(readOnly = true)
	public List<Usuario> buscar(Usuario usuarioFiltro) {
		Example example = Example.of( usuarioFiltro, 
				ExampleMatcher.matching()
					.withIgnoreCase()
					.withStringMatcher(StringMatcher.CONTAINING) );
		
		return repository.findAll(example);

	}


	@Override
	public void validarUsuario(Usuario usuario) {
		if(usuario.getNome() == null || usuario.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe o nome válido.");
		}

		if(usuario.getEmail() == null || usuario.getEmail().trim().equals("")) {
			throw new RegraNegocioException("Informe um Email válido.");
		}
		
		if(usuario.getSenha() == null || usuario.getSenha().trim().equals("")) {
			throw new RegraNegocioException("Informe senha válido.");
		}
		if(usuario.getPerfil() == null) {
			throw new RegraNegocioException("Informe um perfil .");
		}
		
	}


	
}
