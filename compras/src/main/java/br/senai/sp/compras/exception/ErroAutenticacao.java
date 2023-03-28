package br.senai.sp.compras.exception;

public class ErroAutenticacao  extends RuntimeException{
	public ErroAutenticacao(String mensagem) {
		super(mensagem); 
	}
}
