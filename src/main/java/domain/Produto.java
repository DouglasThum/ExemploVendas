package domain;

import annotation.TipoChave;

public class Produto implements Persistente {
	
	@TipoChave("getCodigo")
	private Long cod;
	private String nome;
	private String descricao;
	private Double valor;
	public Produto(Long cod, String nome, String descricao, Double valor) {
		super();
		this.cod = cod;
		this.nome = nome;
		this.descricao = descricao;
		this.valor = valor;
	}
	
	public Produto() {
	}
	
	public Long getCod() {
		return cod;
	}
	
	public void setCod(Long cod) {
		this.cod = cod;
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
	
	public Double getValor() {
		return valor;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	}
}
