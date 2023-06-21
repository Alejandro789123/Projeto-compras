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
@RequestMapping("/api/cadFornecedores")
@RequiredArgsConstructor
public class CadFornecedorResource {

	private final CadFornecedorService service;

	@GetMapping
	public ResponseEntity buscar(
			@RequestParam(value ="nome" , required = false) String nome,
			@RequestParam(value = "email", required = false) String email ,
			@RequestParam(value = "tipo", required = false) TipoPessoa tipo,
			@RequestParam(value = "id", required = false) Long id
			) {
		
			CadFornecedor cadFornecedor = new CadFornecedor();
			
			cadFornecedor.setNome(nome);
			cadFornecedor.setEmail(email);
			cadFornecedor.setTipo(tipo);
			cadFornecedor.setId(id);
		
		List<CadFornecedor> fornecedores = service.buscar(cadFornecedor);
		return ResponseEntity.ok(fornecedores);
	}
	
	@GetMapping("{id}")
	public ResponseEntity obterFornecedor(@PathVariable("id") Long id){
		return service.obterPorId(id)
				.map( fornecedor -> new ResponseEntity(converter(fornecedor), HttpStatus.OK) )
				.orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
	}    
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody CadFornecedorDTO dto) {
		try {
			CadFornecedor entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CadFornecedorDTO dto) {
		return service.obterPorId(id).map(entity -> {
			try {
				CadFornecedor forncedor = converter(dto);
				forncedor.setId(entity.getId());
				service.atualizar(forncedor);
				return ResponseEntity.ok(forncedor);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Fornecedor não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entidade -> {
			service.deletar(entidade);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Forncedor não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
	}

	
	  @PutMapping("{id}/atualiza-status") 
	  public ResponseEntity atualizarStatus( @PathVariable("id") Long id , @RequestBody AtualizaStatusDTO dto ) { 
	 return service.obterPorId(id).map( entity -> { StatusCadFornecedor
	 statusSelecionado = StatusCadFornecedor.valueOf(dto.getStatus());
	  
	  if(statusSelecionado == null) { 
	  return ResponseEntity.badRequest().body("Não foi possível atualizar o status do fornecedor, envie um status válido."); 
	  }
	  
	  try { entity.setStatus(statusSelecionado); 
	  service.atualizar(entity); 
	  return ResponseEntity.ok(entity);
	  
	  }catch (RegraNegocioException e) { return
	  ResponseEntity.badRequest().body(e.getMessage()); }
	  
	  }).orElseGet( () -> 
	  new ResponseEntity("Fornecedor não encontrado na base de Dados.",HttpStatus.BAD_REQUEST) ); } 

	   
	private CadFornecedorDTO converter(CadFornecedor fornecedor){
		return CadFornecedorDTO.builder()
				.id(fornecedor.getId())
				.nome(fornecedor.getNome())
				.cep(fornecedor.getCep())
				.cidade(fornecedor.getCidade())
				.bairro(fornecedor.getBairro())
				.endereco(fornecedor.getEndereco())
				.complemento(fornecedor.getComplemento())
				.numero(fornecedor.getNumero())
				.email(fornecedor.getEmail())
				.telefone(fornecedor.getTelefone())
				.cpf_Cnpj(fornecedor.getCpf_Cnpj())
				.tipo(fornecedor.getTipo().name())
				.status(fornecedor.getStatus().name())
				.build();
				
} 
	 
	private CadFornecedor converter(CadFornecedorDTO dto) {
		CadFornecedor fornecedor = new CadFornecedor();
		fornecedor.setId(dto.getId());
		fornecedor.setNome(dto.getNome());
		fornecedor.setCep(dto.getCep());
		fornecedor.setCidade(dto.getCidade());
		fornecedor.setBairro(dto.getBairro());
		fornecedor.setEndereco(dto.getEndereco());
		fornecedor.setComplemento(dto.getComplemento());
		fornecedor.setNumero(dto.getNumero());
		fornecedor.setEmail(dto.getEmail());
		fornecedor.setTelefone(dto.getTelefone());
		fornecedor.setCpf_Cnpj(dto.getCpf_Cnpj());

		if (dto.getTipo() != null) {
			fornecedor.setTipo(TipoPessoa.valueOf(dto.getTipo()));
		}

		if (dto.getStatus() != null) {
			fornecedor.setStatus(StatusCadFornecedor.valueOf(dto.getStatus()));
		}

		return fornecedor;
	}
}
