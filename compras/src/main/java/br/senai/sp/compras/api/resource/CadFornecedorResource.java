package br.senai.sp.compras.api.resource;

import java.util.Optional;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.compras.api.dto.AtualizaStatusDTO;
import br.senai.sp.compras.api.dto.CadFornecedorDTO;
import br.senai.sp.compras.exception.RegraNegocioException;
import br.senai.sp.compras.model.entity.CadFornecedor;
import br.senai.sp.compras.model.enums.StatusCadFornecedor;
import br.senai.sp.compras.model.enums.TipoPessoa;
import br.senai.sp.compras.service.CadFornecedorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cadFornecedor")
@RequiredArgsConstructor
public class CadFornecedorResource {

	private final CadFornecedorService service;
   
	
	
	@GetMapping
	public ResponseEntity buscar(
			@RequestParam(value ="nome" , required = false) String nome,
			@RequestParam(value = "cep", required = false) String cep,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "tipo", required = false)  TipoPessoa tipo ,
			@RequestParam("cadFornecedor") Long id
			) {
		
		CadFornecedor fornecedorFiltro = new CadFornecedor();
		fornecedorFiltro.setNome(nome);
		fornecedorFiltro.setCep(cep);
		fornecedorFiltro.setEmail(email);
		
		Optional<CadFornecedor> fornecedor = service.obterPorId(id);
		if(!fornecedor.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado para o Id informado.");
		}else {
			fornecedorFiltro = fornecedor.get();
		}
		
		 List<CadFornecedor> fornecedores = service.buscar(fornecedorFiltro);
		return ResponseEntity.ok(fornecedores);
	}
	
    
    @GetMapping("{id}")
	public ResponseEntity obterFornecedor( @PathVariable("id") Long id ) {
		return service.obterPorId(id)
					.map( forncedor -> new ResponseEntity(converter(forncedor), HttpStatus.OK) )
					.orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND) );
	}

	@PostMapping
	public ResponseEntity salvar( @RequestBody CadFornecedorDTO dto ) {
		try {
			CadFornecedor entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar( @PathVariable("id") Long id, @RequestBody CadFornecedorDTO dto ) {
		return service.obterPorId(id).map( entity -> {
			try {
				CadFornecedor forncedor = converter(dto);
				forncedor.setId(entity.getId());
				service.atualizar(forncedor);
				return ResponseEntity.ok(forncedor);
			}catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () ->
			new ResponseEntity("Fornecedor não encontrado na base de Dados.", HttpStatus.BAD_REQUEST) );
	}

	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus( @PathVariable("id") Long id , @RequestBody AtualizaStatusDTO dto ) {
		return service.obterPorId(id).map( entity -> {
			StatusCadFornecedor statusSelecionado = StatusCadFornecedor.valueOf(dto.getStatus());
			
			if(statusSelecionado == null) {
				return ResponseEntity.badRequest().body("Não foi possível atualizar o status do fornecedor, envie um status válido.");
			}
			
			try {
				entity.setStatus(statusSelecionado);
				service.atualizar(entity);
				return ResponseEntity.ok(entity);
			}catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		
		}).orElseGet( () ->
		new ResponseEntity("Fornecedor não encontrado na base de Dados.", HttpStatus.BAD_REQUEST) );
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar( @PathVariable("id") Long id ) {
		return service.obterPorId(id).map( entidade -> {
			service.deletar(entidade);
			return new ResponseEntity( HttpStatus.NO_CONTENT );
		}).orElseGet( () -> 
			new ResponseEntity("Forncedor não encontrado na base de Dados.", HttpStatus.BAD_REQUEST) );
	}
	
	
	
	
	private CadFornecedorDTO converter(CadFornecedor forncedor) {
		return CadFornecedorDTO.builder()
					.id(forncedor.getId())
					.nome(forncedor.getNome())
					.cep(forncedor.getCep())
					.cidade(forncedor.getCidade())
					.bairro(forncedor.getBairro())
					.status(forncedor.getStatus().name())
					.tipo(forncedor.getTipo().name())
					.endereço(forncedor.getEndereço())
					.complemento(forncedor.getComplemento())
					.numero(forncedor.getNumero())
					.email(forncedor.getEmail())
					.telefone(forncedor.getTelefone())
					.cpf_Cnpj(forncedor.getCpf_Cnpj())
					.build();
					
	}
	private CadFornecedor converter(CadFornecedorDTO dto) {
		CadFornecedor forncedor = new CadFornecedor();
		forncedor.setId(dto.getId());
		forncedor.setNome(dto.getNome());
		forncedor.setCep(dto.getCep());
		forncedor.setCidade(dto.getCidade());
		forncedor.setBairro(dto.getBairro());
		forncedor.setEndereço(dto.getEndereço());
		forncedor.setComplemento(dto.getComplemento());
		forncedor.setNumero(dto.getNumero());
		forncedor.setEmail(dto.getEmail());
		forncedor.setTelefone(dto.getTelefone());
		forncedor.setCpf_Cnpj(dto.getCpf_Cnpj());

		
		/*Usuario usuario = usuarioService 
				.obterPorId(dto.Usuario()) // buscar por id na base de dados 
				.orElseThrow( () -> new RegraNegocioException("Usuário não encontrado para o Id informado.") );
			
		forncedor.setUsuario(usuario);*/
			
		if(dto.getTipo() != null) {
			forncedor.setTipo(TipoPessoa.valueOf(dto.getTipo()));
		}
		
		if(dto.getStatus() != null) {
			forncedor.setStatus(StatusCadFornecedor.valueOf(dto.getStatus()));
		}
		
		return forncedor;
	}
}
