package domain;

import java.math.BigDecimal;

import annotation.TipoChave;

public class Produto implements Persistente {
	
	@TipoChave("getCodigo")
	private String codigo;
	private String nome;
	private String descricao;
	private BigDecimal valor;
	public Produto(String codigo, String nome, String descricao, BigDecimal valor) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.descricao = descricao;
		this.valor = valor;
	}
	
	public Produto() {
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
