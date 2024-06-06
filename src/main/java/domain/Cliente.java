package domain;

import annotation.ColunaTabela;
import annotation.Tabela;
import annotation.TipoChave;

@Tabela("TB_CLIENTE")
public class Cliente implements Persistente{
	
	@ColunaTabela(dbName = "id", setJavaName = "setId")
	private Long id;
	
	@ColunaTabela(dbName = "id", setJavaName = "setNome")
	private String nome;
	
	@TipoChave("getCpf")
	@ColunaTabela(dbName = "cpf", setJavaName = "setCpf")
	private Long cpf;
	
	@ColunaTabela(dbName = "tel", setJavaName = "setTel")
	private Long tel;
	
	@ColunaTabela(dbName = "rua", setJavaName = "setRua")
	private String rua;
	
	@ColunaTabela(dbName = "num", setJavaName = "setNum")
	private Long num;
	
	@ColunaTabela(dbName = "cidade", setJavaName = "setCidade")
	private String cidade;
	
	@ColunaTabela(dbName = "estado", setJavaName = "setEstado")
	private String estado;

	public Cliente() {
	}

	public Cliente(Long id, String nome, Long cpf, Long tel, String rua, Long num, String cidade, String estado) {
		super();
		this.id = id;
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

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
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

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;		
	}
}
