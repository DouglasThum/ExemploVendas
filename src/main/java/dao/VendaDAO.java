package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.Venda;
import domain.Venda.Status;
import exception.DAOException;
import exception.TipoChaveNaoEncontradoException;
import generics.GenericDAO;

public class VendaDAO extends GenericDAO<Venda, String> implements IVendaDAO {

	@Override
	public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradoException, DAOException {
		venda.setStatus(Status.CONCLUIDA);
		super.alterar(venda);
	}

	@Override
	public Class<Venda> getClassType() {
		return Venda.class;
	}

	@Override
	public void atualizarDados(Venda entityNovo, Venda entityCadastrado) {
		entityCadastrado.setCodigo(entityNovo.getCodigo());
		entityCadastrado.setCliente(entityNovo.getCliente());
		entityCadastrado.setDataVenda(entityNovo.getDataVenda());
		entityCadastrado.setValorTotal(entityNovo.getValorTotal());
		entityCadastrado.setStatus(entityNovo.getStatus());
	}
	
	@Override
	public void excluir(String valor) {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
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
	protected void setParametrosQueryInsercao(PreparedStatement stmInsert, Venda entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setParametrosQueryExclusao(PreparedStatement stmDelete, String valor) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, Venda entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setParametrosQuerySelect(PreparedStatement stmSelect, String valor) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
