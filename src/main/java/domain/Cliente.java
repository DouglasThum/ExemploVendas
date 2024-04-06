package domain;

import annotation.TipoChave;

public class Cliente implements Persistente{
	
	private String nome;
	@TipoChave("getCpf")
	private Long cpf;
	private Long tel;
	private String rua;
	private Integer num;
	private String cidade;
	private String estado;

	public Cliente() {
	}

	public Cliente(String nome, Long cpf, Long tel, String rua, Integer num, String cidade, String estado) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.tel = tel;
		this.rua = rua;
		this.num = num;
		this.cidade = cidade;
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Long getTel() {
		return tel;
	}

	public void setTel(Long tel) {
		this.tel = tel;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
