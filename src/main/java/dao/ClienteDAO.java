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
	public String getQueryInsercao() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO TB_CLIENTE ");
		sb.append("(ID, NOME, CPF, TEL, ENDERECO, NUM, CIDADE, ESTADO) ");
		sb.append("VALUES (nextval('sq_cliente'),?,?,?,?,?,?,?)");
		return sb.toString();
	}
	
	@Override
	protected void setParametrosQueryInsercao(PreparedStatement stmInsert, Cliente entity) throws SQLException {
		stmInsert.setString(1, entity.getNome());
		stmInsert.setLong(2, entity.getCpf());
		stmInsert.setLong(3, entity.getTel());
		stmInsert.setString(4, entity.getEndereco());
		stmInsert.setLong(5, entity.getNum());
		stmInsert.setString(6, entity.getCidade());
		stmInsert.setString(7, entity.getEstado());
	}

	@Override
	public String getQueryExclusao() {
		return "DELETE FROM TB_CLIENTE WHERE CPF = ?";
	}
	
	@Override
	protected void setParametrosQueryExclusao(PreparedStatement stmDelete, Long valor) throws SQLException {
		stmDelete.setLong(1, valor);
		
	}

	@Override
	public String getQueryAtualizacao() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE TB_CLIENTE ");
		sb.append("SET NOME = ?,");
		sb.append("TEL = ?,");
		sb.append("ENDERECO = ?,");
		sb.append("NUM = ?,");
		sb.append("CIDADE = ?,");
		sb.append("ESTADO = ?");
		sb.append(" WHERE CPF = ?");
		return sb.toString();
	}

	@Override
	protected void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, Cliente entity) throws SQLException {
		stmUpdate.setString(1, entity.getNome());
		stmUpdate.setLong(2, entity.getTel());	
		stmUpdate.setString(3, entity.getEndereco());
		stmUpdate.setLong(4, entity.getNum());
		stmUpdate.setString(5, entity.getCidade());
		stmUpdate.setString(6, entity.getEstado());
	}

	@Override
	protected void setParametrosQuerySelect(PreparedStatement stmSelect, Long valor) throws SQLException {
		stmSelect.setLong(1, valor);		
	}
}
