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
	public void atualizarDados(Produto entityNovo, Produto entityCadastrado) {
		entityCadastrado.setCodigo(entityNovo.getCodigo());
		entityCadastrado.setNome(entityNovo.getNome());
		entityCadastrado.setDescricao(entityNovo.getDescricao());
		entityCadastrado.setValor(entityNovo.getValor());
	}

	@Override
	public String getQueryInsercao() {
		// TODO Auto-generated method stub
		return null;
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
	protected void setParametrosQueryInsercao(PreparedStatement stmInsert, Produto entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setParametrosQueryExclusao(PreparedStatement stmDelete, String valor) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, Produto entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setParametrosQuerySelect(PreparedStatement stmSelect, String valor) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
