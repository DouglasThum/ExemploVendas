package test;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void init() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		produto = new Produto();
		produto.setCodigo("String");
		produto.setNome("Produto1");
		produto.setDescricao("Produto descrição");
		produto.setValor(BigDecimal.valueOf(50.0));
		produtoDao.cadastrar(produto);
	}
	
	@Test
	public void pesquisarProduto() throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException {
		Produto produtoConsultado = produtoDao.consultar(produto.getCodigo());
		
		Assert.assertNotNull(produtoConsultado);
	}
	
	@Test
	public void salvarProduto() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		Boolean retorno = produtoDao.cadastrar(produto);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void excluirProduto() throws MaisDeUmRegistroException, TabelaException, DAOException {
		produtoDao.excluir(produto.getCodigo());
	}
	
	@Test
	public void alterarProduto() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		produtoDao.alterar(produto);
	}
}
