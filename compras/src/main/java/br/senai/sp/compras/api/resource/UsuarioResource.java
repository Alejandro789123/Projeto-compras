package br.senai.sp.compras.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.compras.api.dto.CadFornecedorDTO;
import br.senai.sp.compras.api.dto.LoginDTO;
import br.senai.sp.compras.api.dto.UsuarioDTO;
import br.senai.sp.compras.exception.ErroAutenticacao;
import br.senai.sp.compras.exception.RegraNegocioException;
import br.senai.sp.compras.model.entity.CadFornecedor;
import br.senai.sp.compras.model.entity.Usuario;
import br.senai.sp.compras.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {

	private final UsuarioService service;
	
	
     @GetMapping("{id}")
		public ResponseEntity obterFornecedor( @PathVariable("id") Long id ) {
			return service.obterPorId(id)
						.map( usuario -> new ResponseEntity(converter(usuario), HttpStatus.OK) )
						.orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND) );
		}
	 
	@PostMapping("/autenticar")
	public ResponseEntity autenticar( @RequestBody LoginDTO dto ) {
		try {
			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		}catch (ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity salvar( @RequestBody UsuarioDTO dto ) {
		
		Usuario usuario = Usuario.builder()
					.nome(dto.getNome())
					.email(dto.getEmail())
					.senha(dto.getSenha())
					.perfil(dto.getPerfil())
					.build();
		
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	
	private UsuarioDTO converter(Usuario usuario) {
		return UsuarioDTO.builder()
					.id(usuario.getId())
					.email(usuario.getEmail())
					.nome(usuario.getNome())
					.senha(usuario.getSenha())
					.perfil(usuario.getPerfil())
					.build();
					
	}
	
}
