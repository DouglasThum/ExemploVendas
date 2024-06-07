package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;

import org.junit.After;
import org.junit.Test;

import dao.ProdutoDAO;
import domain.Produto;
import exception.DAOException;
import exception.MaisDeUmRegistroException;
import exception.TabelaException;
import exception.TipoChaveNaoEncontradoException;
import generics.IGenericDAO;

public class ProdutoDAOTest {
	
	private Produto produto;
	
	private IGenericDAO<Produto, String> produtoDao;
	
	public ProdutoDAOTest() {
		produtoDao = new ProdutoDAO();
	}
	
	@After
	public void end() throws DAOException {
		Collection<Produto> list = produtoDao.buscarTodos();
		list.forEach(prod -> {
			try {
				produtoDao.excluir(prod.getCodigo());
			} catch (DAOException | MaisDeUmRegistroException | TabelaException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Test
	public void pesquisarProduto() throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException, TipoChaveNaoEncontradoException {
		produto = instanciarProduto();
		assertTrue(produtoDao.cadastrar(produto));
		
		Produto produtoConsultado = produtoDao.consultar(produto.getCodigo());
		assertNotNull(produtoConsultado);
		produtoDao.excluir(produto.getCodigo());
	}
	
	@Test
	public void salvarProduto() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		produto = instanciarProduto();
		
		assertTrue(produtoDao.cadastrar(produto));
		Produto produtoConsultado = produtoDao.consultar(produto.getCodigo());
		assertNotNull(produtoConsultado);
		produtoDao.excluir(produto.getCodigo());
	}
	
	@Test
	public void alterarProduto() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		produto = instanciarProduto();
		assertTrue(produtoDao.cadastrar(produto));
		
		Produto produtoConsultado = produtoDao.consultar(produto.getCodigo());
		assertNotNull(produtoConsultado);
		
		produtoConsultado.setNome("PC Gamer");
		produtoDao.alterar(produtoConsultado);
		
		Produto produtoAlterado = produtoDao.consultar(produto.getCodigo());
		assertNotNull(produtoAlterado);
		assertEquals("PC Gamer", produtoAlterado.getNome());
		
		produtoDao.excluir(produto.getCodigo());
		produtoConsultado = produtoDao.consultar(produto.getCodigo());
		assertNull(produtoConsultado);
	}
	
	@Test
	public void excluirProduto() throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException, TipoChaveNaoEncontradoException {
		produto = instanciarProduto();
		assertTrue(produtoDao.cadastrar(produto));
		
		Produto produtoConsultado = produtoDao.consultar(produto.getCodigo());
		assertNotNull(produtoConsultado);
		produtoDao.excluir(produto.getCodigo());
		assertNull(produtoDao.consultar(produto.getCodigo()));
	}
	
	private Produto instanciarProduto(){
		produto = new Produto();
		produto.setCodigo("6179");
		produto.setNome("Computador");
		produto.setDescricao("Computador gamer");
		produto.setValor(BigDecimal.valueOf(4000.0));
		
		return produto;
	}
}
