package test;

import static org.junit.Assert.assertTrue;

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
	
	@Test
	public void pesquisarProduto() throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException, TipoChaveNaoEncontradoException {
		produto = instanciarProduto();
		assertTrue(produtoDao.cadastrar(produto));
		
		Produto produtoConsultado = produtoDao.consultar(produto.getCodigo());
		Assert.assertNotNull(produtoConsultado);
	}
	
/*	@Test
	public void salvarProduto() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		Boolean retorno = produtoDao.cadastrar(produto);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void excluirProduto() throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException {
		produtoDao.excluir(produto.getCodigo());
	}
	
	@Test
	public void alterarProduto() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		produtoDao.alterar(produto);
	}*/
	
	private Produto instanciarProduto(){
		produto = new Produto();
		produto.setCodigo("6179");
		produto.setNome("Computador");
		produto.setDescricao("Computador gamer");
		produto.setValor(BigDecimal.valueOf(4000.0));
		
		return produto;
	}
}
