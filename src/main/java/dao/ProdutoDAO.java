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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQueryAtualizacao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setParametrosQueryExclusao(PreparedStatement stmDelete, String valor) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, Produto entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
