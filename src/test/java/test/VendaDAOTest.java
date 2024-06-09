package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import connection.ConnectionFactory;
import dao.ClienteDAO;
import dao.IClienteDAO;
import dao.IProdutoDAO;
import dao.IVendaDAO;
import dao.ProdutoDAO;
import dao.VendaDAO;
import domain.Cliente;
import domain.Produto;
import domain.Venda;
import domain.Venda.Status;
import exception.DAOException;
import exception.MaisDeUmRegistroException;
import exception.TabelaException;
import exception.TipoChaveNaoEncontradoException;

public class VendaDAOTest {
private IVendaDAO vendaDao;
	
	private IClienteDAO clienteDao;
	
	private IProdutoDAO produtoDao;

	private Cliente cliente;
	
	private Produto produto;
	
	public VendaDAOTest() {
		vendaDao = new VendaDAO();
		clienteDao = new ClienteDAO();
		produtoDao = new ProdutoDAO();
	}
	
	@Before
	public void init() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		this.cliente = cadastrarCliente();
		this.produto = cadastrarProduto("A1", BigDecimal.TEN);
	}

	@After 
	public void end() throws DAOException, MaisDeUmRegistroException, TabelaException, SQLException {
		excluirVendas();
		excluirProdutos();
		excluirClientes();
	}
	
	@Test
	public void pesquisar() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		Venda venda = criarVenda("A1");
		assertTrue(vendaDao.cadastrar(venda));
		Venda vendaConsultada = vendaDao.consultar(venda.getCodigo());
		assertNotNull(vendaConsultada);
		assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
	}
	
	@Test
	public void salvar() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		Venda venda = criarVenda("A2");
		assertTrue(vendaDao.cadastrar(venda));
		assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(20)));
		assertTrue(venda.getStatus().equals(Status.INICIADA));
	} 
	
	
	@Test
	public void cancelarVenda() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		String codigoVenda = "A3";
		Venda venda = criarVenda(codigoVenda);
		assertTrue(vendaDao.cadastrar(venda));
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCodigo());
		
		vendaDao.cancelarVenda(venda);
		
		Venda vendaConsultada = vendaDao.consultar(codigoVenda);
		assertEquals(codigoVenda, vendaConsultada.getCodigo());
		assertEquals(Status.CANCELADA, vendaConsultada.getStatus());
	}
	
	@Test
	public void adicionarMaisProdutosDoMesmo() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		String codigoVenda = "A4";
		Venda venda = criarVenda(codigoVenda);
		assertTrue(vendaDao.cadastrar(venda));
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCodigo());
		
		Venda vendaConsultada = vendaDao.consultar(codigoVenda);
		vendaConsultada.adicionarProduto(produto, 1);
		
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
		
		BigDecimal valorTotal = BigDecimal.valueOf(30).setScale(2, RoundingMode.HALF_DOWN);
		assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
		assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
	} 
	
	@Test
	public void adicionarMaisProdutosDiferentes() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		String codigoVenda = "A5";
		Venda venda = criarVenda(codigoVenda);
		assertTrue(vendaDao.cadastrar(venda));
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCodigo());
		
		String codigoProduto = codigoVenda;
		
		Produto prod = cadastrarProduto(codigoProduto, BigDecimal.valueOf(50));
		assertNotNull(prod);
		assertEquals(codigoProduto, prod.getCodigo());
		
		Venda vendaConsultada = vendaDao.consultar(codigoVenda);
		vendaConsultada.adicionarProduto(prod, 1);
		
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
		BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
		assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
		assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
	} 
	
	@Test(expected = DAOException.class)
	public void salvarProdutoExistente() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		Venda venda = criarVenda("A6");
		assertTrue(vendaDao.cadastrar(venda));
	
		Boolean retorno = vendaDao.cadastrar(venda);
		assertFalse(retorno);
		assertTrue(venda.getStatus().equals(Status.INICIADA));
	} 
	
	@Test
	public void removerProduto() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		String codigoVenda = "A7";
		Venda venda = criarVenda(codigoVenda);
		assertTrue(vendaDao.cadastrar(venda));
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCodigo());
		
		String codigoProduto = codigoVenda;
		
		Produto prod = cadastrarProduto(codigoProduto, BigDecimal.valueOf(50));
		assertNotNull(prod);
		assertEquals(codigoProduto, prod.getCodigo());
		
		Venda vendaConsultada = vendaDao.consultar(codigoVenda);
		vendaConsultada.adicionarProduto(prod, 1);
		BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
		assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
		
		
		vendaConsultada.removerProduto(prod, 1);
		assertTrue(venda.getQuantidadeTotalProdutos() == 2);
		valorTotal = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
		assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
		assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
	} 
	
	@Test
	public void removerApenasUmProduto() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		String codigoVenda = "A8";
		Venda venda = criarVenda(codigoVenda);
		assertTrue(vendaDao.cadastrar(venda));
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCodigo());
		
		String codigoProduto = codigoVenda;
		
		Produto prod = cadastrarProduto(codigoProduto, BigDecimal.valueOf(50));
		assertNotNull(prod);
		assertEquals(codigoProduto, prod.getCodigo());
		
		Venda vendaConsultada = vendaDao.consultar(codigoVenda);
		vendaConsultada.adicionarProduto(prod, 1);
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
		BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
		assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
		
		
		vendaConsultada.removerProduto(prod, 1);
		valorTotal = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_DOWN);
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 2);
		assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
		assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
	} 
	
	@Test
	public void removerTodosProdutos() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		String codigoVenda = "A9";
		Venda venda = criarVenda(codigoVenda);
		assertTrue(vendaDao.cadastrar(venda));
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCodigo());
		
		String codigoProduto = codigoVenda;
		
		Produto prod = cadastrarProduto(codigoProduto, BigDecimal.valueOf(50));
		assertNotNull(prod);
		assertEquals(codigoProduto, prod.getCodigo());
		
		Venda vendaConsultada = vendaDao.consultar(codigoVenda);
		BigDecimal valorTotal = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_DOWN);
		vendaConsultada.adicionarProduto(prod, 1);
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 3);
		assertTrue(vendaConsultada.getValorTotal().equals(valorTotal));
		
		
		vendaConsultada.removerTodosProdutos();
		assertTrue(vendaConsultada.getQuantidadeTotalProdutos() == 0);
		assertTrue(vendaConsultada.getValorTotal().equals(BigDecimal.valueOf(0)));
		assertTrue(vendaConsultada.getStatus().equals(Status.INICIADA));
	} 
	
	@Test
	public void finalizarVenda() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		String codigoVenda = "A10";
		Venda venda = criarVenda(codigoVenda);
		assertTrue(vendaDao.cadastrar(venda));
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCodigo());
		vendaDao.finalizarVenda(venda);
		
		/*Venda vendaConsultada = vendaDao.consultar(codigoVenda);
		assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
		assertEquals(Status.CONCLUIDA, vendaConsultada.getStatus());*/
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void tentarAdicionarProdutosVendaFinalizada() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		String codigoVenda = "A11";
		Venda venda = criarVenda(codigoVenda);
		assertTrue(vendaDao.cadastrar(venda));
		assertNotNull(venda);
		assertEquals(codigoVenda, venda.getCodigo());
		
		vendaDao.finalizarVenda(venda);
		Venda vendaConsultada = vendaDao.consultar(codigoVenda);
		assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
		assertEquals(venda.getStatus(), vendaConsultada.getStatus());
		
		vendaConsultada.adicionarProduto(this.produto, 1);
		
	} 

	private Produto cadastrarProduto(String codigo, BigDecimal valor) throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		Produto produto = new Produto();
		produto.setCodigo(codigo);
		produto.setDescricao("Produto 1");
		produto.setNome("Produto 1");
		produto.setValor(valor);
		produto.setCategoria("Eletrônico");
		produtoDao.cadastrar(produto);
		return produto;
	}

	private Cliente cadastrarCliente() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		cliente = new Cliente();
		cliente.setNome("Douglas");
		cliente.setCpf(1234567890L);
		cliente.setTel(51999999999L);
		cliente.setEndereco("Bento Gonçalves");
		cliente.setNum(2L);
		cliente.setCidade("Porto Alegre");
		cliente.setEstado("RS");
		cliente.setIdade(25L);
		clienteDao.cadastrar(cliente);
		return cliente;
	}
	
	private Venda criarVenda(String codigo) {
		Venda venda = new Venda();
		venda.setCodigo(codigo);
		venda.setDataVenda(Instant.now());
		venda.setCliente(this.cliente);
		venda.setStatus(Status.INICIADA);
		venda.adicionarProduto(this.produto, 2);
		return venda;
	}
	
	private void excluirProdutos() throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException {
		Collection<Produto> lista = produtoDao.buscarTodos();
		for (Produto prod : lista) {
			produtoDao.excluir(prod.getCodigo());
		}		
	}
	
	private void excluirClientes() throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException {
		Collection<Cliente> lista = clienteDao.buscarTodos();
		for (Cliente cliente : lista) {
			clienteDao.excluir(cliente.getCpf());
		}		
	}
	
	private void excluirVendas() throws DAOException, MaisDeUmRegistroException, TabelaException, SQLException {
		String sqlProd = "DELETE FROM TB_PRODUTO_QUANTIDADE";
		executeDelete(sqlProd);
		
		String sqlV = "DELETE FROM TB_VENDA";
		executeDelete(sqlV);	
	}
	
	private void executeDelete(String sql) throws DAOException {
		Connection connection = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
    		connection = getConnection();
			stm = connection.prepareStatement(sql);
			stm.executeUpdate();
		    
		} catch (SQLException e) {
			throw new DAOException("ERRO EXLUINDO OBJETO ", e);
		} finally {
			closeConnection(connection, stm, rs);
		}
	}
		
	protected Connection getConnection() throws DAOException {
		try {
			return ConnectionFactory.getConnection();
		} catch (SQLException e) {
			throw new DAOException("ERRO ABRINDO CONEXAO COM BANCO DE DADOS ", e);
		}
	}
	
	protected void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (stm != null && !stm.isClosed()) {
				stm.close();
			}
			if (connection != null && !stm.isClosed()) {
				connection.close();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
