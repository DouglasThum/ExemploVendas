package dao;

import generics.GenericDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.Produto;

public class ProdutoDAO extends GenericDAO<Produto, String> implements IProdutoDAO {

	public ProdutoDAO() {
		super();
	}
	
	@Override
	public Class<Produto> getClassType() {
		return Produto.class;
	}

	@Override
	public String getQueryInsercao() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO TB_PRODUTO ");
		sb.append("(ID, CODIGO, NOME, DESCRICAO, VALOR) ");
		sb.append("VALUES (nextval('sq_produto'),?,?,?,?)");
		return sb.toString();
	}
	
	@Override
	protected void setParametrosQueryInsercao(PreparedStatement stmInsert, Produto entity) throws SQLException {
		stmInsert.setString(1, entity.getCodigo());
		stmInsert.setString(2, entity.getNome());
		stmInsert.setString(3, entity.getDescricao());
		stmInsert.setBigDecimal(4, entity.getValor());	
	}
	
	@Override
	protected void setParametrosQuerySelect(PreparedStatement stmSelect, String valor) throws SQLException {
		stmSelect.setString(1, valor);
	}

	@Override
	public String getQueryExclusao() {
		return "DELETE FROM TB_PRODUTO WHERE CODIGO = ?";
	}
	
	@Override
	protected void setParametrosQueryExclusao(PreparedStatement stmDelete, String valor) throws SQLException {
		stmDelete.setString(1, valor);		
	}

	@Override
	public String getQueryAtualizacao() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE TB_PRODUTO ");
		sb.append("SET NOME = ?, ");
		sb.append("DESCRICAO = ?, ");
		sb.append("VALOR = ? ");
		sb.append("WHERE CODIGO = ?");
		return sb.toString();
	}

	@Override
	protected void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, Produto entity) throws SQLException {
		stmUpdate.setString(1, entity.getNome());
		stmUpdate.setString(2, entity.getDescricao());
		stmUpdate.setBigDecimal(3, entity.getValor());	
		stmUpdate.setString(4, entity.getCodigo());
	}
}
