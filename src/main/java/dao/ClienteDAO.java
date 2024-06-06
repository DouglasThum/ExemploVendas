package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.Cliente;
import generics.GenericDAO;

public class ClienteDAO extends GenericDAO<Cliente, Long> implements IClienteDAO {

	public ClienteDAO() {
		super();
	}
	
	@Override
	public Class<Cliente> getClassType() {
		return Cliente.class;
	}

	@Override
	public void atualizarDados(Cliente entityNovo, Cliente entityCadastrado) {
		entityCadastrado.setCpf(entityNovo.getCpf());
		entityCadastrado.setNome(entityNovo.getNome());
		entityCadastrado.setTel(entityNovo.getTel());
		entityCadastrado.setRua(entityNovo.getRua());
		entityCadastrado.setNum(entityNovo.getNum());
		entityCadastrado.setCidade(entityNovo.getCidade());
		entityCadastrado.setEstado(entityNovo.getEstado());
	}

	@Override
	public String getQueryInsercao() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO TB_CLIENTE ");
		sb.append("(ID, NOME, CPF, TEL, RUA, NUMERO, CIDADE, ESTADO) ");
		sb.append("VALUES (nextval('sq_cliente'),?,?,?,?,?,?,?)");
		return sb.toString();
	}
	
	@Override
	protected void setParametrosQueryInsercao(PreparedStatement stmInsert, Cliente entity) throws SQLException {
		stmInsert.setString(1, entity.getNome());
		stmInsert.setLong(2, entity.getCpf());
		stmInsert.setLong(3, entity.getTel());
		stmInsert.setString(4, entity.getRua());
		stmInsert.setLong(5, entity.getNum());
		stmInsert.setString(6, entity.getCidade());
		stmInsert.setString(7, entity.getEstado());
	}

	@Override
	public String getQueryExclusao() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM TB_CLIENTE ");
		sb.append("WHERE ID = ?");
		return sb.toString();
	}
	
	@Override
	protected void setParametrosQueryExclusao(PreparedStatement stmDelete, Long valor) throws SQLException {
		stmDelete.setLong(1, valor);
		
	}

	@Override
	public String getQueryAtualizacao() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE FROM TB_CLIENTE ");
		sb.append("WHERE ID = ?");
		return sb.toString();
	}

	@Override
	protected void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, Cliente entity) throws SQLException {
		stmUpdate.setLong(1, entity.getCpf());		
	}

	@Override
	protected void setParametrosQuerySelect(PreparedStatement stmSelect, Long valor) throws SQLException {
		stmSelect.setLong(1, valor);		
	}
}
