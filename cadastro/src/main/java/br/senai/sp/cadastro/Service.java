package br.senai.sp.cadastro;

import org.springframework.beans.factory.annotation.Autowired;

import br.senai.sp.cadastro.dao.LoginDAO;

@org.springframework.stereotype.Service
public class Service {
	
	@Autowired
	LoginDAO dao;
	
	public void listarTodos() {
		dao.ListarTodos();
	}

}
