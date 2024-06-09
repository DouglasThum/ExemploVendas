package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import dao.factory.ProdutoQuantidadeFactory;
import dao.factory.VendaFactory;
import domain.Venda;
import domain.Venda.Status;
import exception.DAOException;
import exception.MaisDeUmRegistroException;
import exception.TipoChaveNaoEncontradoException;
import generics.GenericDAO;
import domain.ProdutoQuantidade;

public class VendaDAO extends GenericDAO<Venda, String> implements IVendaDAO {

	@Override
	public Class<Venda> getClassType() {
		return Venda.class;
	}
	
	@Override
	public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			String sql = "UPDATE TB_VENDA SET STATUS_VENDA = ? WHERE ID = ?";
			connection = getConnection();
			stm = connection.prepareStatement(sql);
			stm.setString(1, Status.CONCLUIDA.name());
			stm.setLong(2, venda.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("ERRO ATUALIZANDO OBJETO ", e);
		} finally {
			closeConnection(connection, stm, null);
		}
	}
	
	@Override
	public void cancelarVenda(Venda venda) throws DAOException, SQLException {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			String sql = "UPDATE FROM TB_VENDA SET STATUS_VENDA = ? WHERE ID = ?";
			connection = getConnection();
			stm = connection.prepareStatement(sql);
			stm.setString(1, Status.CANCELADA.name());
			stm.setLong(2, venda.getId());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("ERRO ATUALIZANDO OBJETO ", e);
		} finally {
			closeConnection(connection, stm, null);
		}
	}
	
	@Override
	public Venda consultar(String valor) throws DAOException, MaisDeUmRegistroException, SQLException, TipoChaveNaoEncontradoException {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		StringBuilder sb = sqlBaseSelect();
		sb.append("WHERE V.CODIGO = ? ");
		try {
    		validarMaisDeUmRegistro(valor);
    		connection = getConnection();
			stm = connection.prepareStatement(sb.toString());
			setParametrosQuerySelect(stm, valor);
			rs = stm.executeQuery();
		    if (rs.next()) {
		    	Venda venda = VendaFactory.convert(rs);
		    	buscarAssociacaoVendaProdutos(connection, venda);
		    	return venda;
		    }
		    
		} catch (SQLException e) {
			throw new DAOException("ERRO CONSULTANDO OBJETO ", e);
		} finally {
			closeConnection(connection, stm, rs);
		}
    	return null;
	}

	@Override
	public void excluir(String valor) {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");	
	}
	
	@Override
	public void alterar(Venda venda) {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");	
	}

	@Override
	public String getQueryInsercao() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO TB_VENDA ");
		sb.append("(ID, CODIGO, ID_CLIENTE_FK, VALOR_TOTAL, DATA_VENDA, STATUS_VENDA) ");
		sb.append("VALUES (nextval('sq_venda'), ?, ?, ?, ?, ?, ?)");
		return sb.toString();
	}

	@Override
	protected void setParametrosQueryInsercao(PreparedStatement stmInsert, Venda entity) throws SQLException {
		stmInsert.setString(1, entity.getCodigo());
		stmInsert.setLong(2, entity.getCliente().getId());
		stmInsert.setBigDecimal(3, entity.getValorTotal());
		stmInsert.setTimestamp(4, Timestamp.from(entity.getDataVenda()));
		stmInsert.setString(5, entity.getStatus().name());
	}
	
	@Override
	public String getQueryExclusao() {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
	}
	
	@Override
	protected void setParametrosQueryExclusao(PreparedStatement stmDelete, String valor) throws SQLException {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");	
	}

	@Override
	public String getQueryAtualizacao() {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
	}

	@Override
	protected void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, Venda entity) throws SQLException {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");		
	}

	@Override
	protected void setParametrosQuerySelect(PreparedStatement stmSelect, String valor) throws SQLException {
		stmSelect.setString(1, valor);		
	}
	
	private StringBuilder sqlBaseSelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT V.ID AS ID_VENDA, V.CODIGO, V.VALOR_TOTAL, V.DATA_VENDA, V.STATUS_VENDA, ");
		sb.append("C.ID AS ID_CLIENTE, C.NOME, C.CPF, C.TEL, C.ENDERECO, C.NUMERO, C.CIDADE, C.ESTADO ");
		sb.append("FROM TB_VENDA V ");
		sb.append("INNER JOIN TB_CLIENTE C ON V.ID_CLIENTE_FK = C.ID ");
		return sb;
	}
	
	private void buscarAssociacaoVendaProdutos(Connection connection, Venda venda) throws SQLException, DAOException {
		PreparedStatement stmProd = null;
		ResultSet rsProd = null;
		try {
			StringBuilder sbProd = new StringBuilder();
		    sbProd.append("SELECT PQ.ID, PQ.QUANTIDADE, PQ.VALOR_TOTAL, ");
		    sbProd.append("P.ID AS ID_PRODUTO, P.CODIGO, P.NOME, P.DESCRICAO, P.VALOR ");
		    sbProd.append("FROM TB_PRODUTO_QUANTIDADE PQ ");
		    sbProd.append("INNER JOIN TB_PRODUTO P ON P.ID = PQ.ID_PRODUTO_FK ");
		    sbProd.append("WHERE PQ.ID_VENDA_FK = ?");
		    stmProd = connection.prepareStatement(sbProd.toString());
		    stmProd.setLong(1, venda.getId());
		    rsProd = stmProd.executeQuery();
		    Set<ProdutoQuantidade> produtos = new HashSet<>();
		    while(rsProd.next()) {
		    	ProdutoQuantidade prodQ = ProdutoQuantidadeFactory.convert(rsProd);
		    	produtos.add(prodQ);
		    }
		    venda.setProdutos(produtos);
		    venda.recalcularValorTotalVenda();
		} catch (SQLException e) {
			throw new DAOException("ERRO CONSULTANDO OBJETO ", e);
		} finally {
			closeConnection(connection, stmProd, rsProd);
		}
	}
}
