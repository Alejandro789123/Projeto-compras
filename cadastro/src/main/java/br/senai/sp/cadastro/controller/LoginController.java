package br.senai.sp.cadastro.controller;


import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cadastro.dao.LoginDAO;
import br.senai.sp.cadastro.dto.LoginDTO;
import br.senai.sp.cadastro.model.Login;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	LoginDAO dao;
	
	@GetMapping
	public List<Login> buscarTodos() {
		return dao.ListarTodos();
	}
	
	@GetMapping("/{id}")
	public Login buscarPorId(@PathVariable int id) {
		
		return dao.PegarPorId(id); 
	}
	
	@PostMapping
	public Login inserir(@RequestBody Login login ) {
		
		dao.Novo(login);
		
		return login;
	}
	
	@PutMapping("/{id}")
	public LoginDTO alterar(@RequestBody LoginDTO logindto, @PathVariable int id ) {
		
		Login l = new Login(-1, logindto.getUsuario(), logindto.getSenha());
		
		dao.Editar(l, id);
		
		return logindto;
	}
	
	@DeleteMapping("/{id}")
	public int excluir(@PathVariable int id) {
		
		dao.Deletar(id);
		
		return id;   
	}
}
