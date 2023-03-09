package br.senai.sp.cadastro.dao;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.senai.sp.cadastro.model.Login;

@Repository
public class LoginDAO {

	 @Autowired
	    private JdbcTemplate jdbcTemplate;
	
	public List<Login> ListarTodos(){
		String sql = "SELECT * FROM login";
        
		List<Login> lista = jdbcTemplate.query(sql, 
                BeanPropertyRowMapper.newInstance(Login.class));
        
		//int result = jdbcTemplate.update(sql, l.getUsuario(), l.getSenha());
		
		return lista;	
	}
	
	public Login PegarPorId(int id) {
		
		String sql = "SELECT * FROM login WHERE id = ?";
		
		Object[] params = {id}; 
		
		Login l = jdbcTemplate.queryForObject(sql,
				BeanPropertyRowMapper.newInstance(Login.class), params);
 
		return l;
	}
	
	public int Novo(Login l) {

		String sql = "INSERT INTO Login (usuario, senha) VALUES (?, ?)";
        
        int result = jdbcTemplate.update(sql, l.getUsuario(), l.getSenha());
		
        return result;
	}
	
	public int Editar(Login l, int id) {
		
		  String sql = "UPDATE login SET usuario=?, senha=? WHERE id=?";
		    
		  Object[] params = {l.getUsuario(), l.getSenha(), id};
		    
		  int result = jdbcTemplate.update(sql, params);
		
		  return result; 
	}
	
	public int Deletar(int id) {
		String sql = "DELETE FROM login WHERE id=?";
		
		Object[] params = {id}; 
		
		int result = jdbcTemplate.update(sql, params);
		
		return result; 
	}
}
