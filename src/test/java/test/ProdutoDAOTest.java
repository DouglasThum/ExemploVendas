package test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.ProdutoDAOMock;
import domain.Produto;
import exception.TipoChaveNaoEncontradoException;
import generics.IGenericDAO;

public class ProdutoDAOTest {
	
	private Produto produto;
	
	private IGenericDAO<Produto, String> produtoDao;
	
	public ProdutoDAOTest() {
		produtoDao = new ProdutoDAOMock();
	}

	@Before
	public void init() throws TipoChaveNaoEncontradoException {
		produto = new Produto();
		produto.setCodigo("String");
		produto.setNome("Produto1");
		produto.setDescricao("Produto descrição");
		produto.setValor(BigDecimal.valueOf(50.0));
		produtoDao.cadastrar(produto);
	}
	
	@Test
	public void pesquisarProduto() {
		Produto produtoConsultado = produtoDao.consultar(produto.getCodigo());
		
		Assert.assertNotNull(produtoConsultado);
	}
	
	@Test
	public void salvarProduto() throws TipoChaveNaoEncontradoException {
		Boolean retorno = produtoDao.cadastrar(produto);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void excluirProduto() {
		produtoDao.excluir(produto.getCodigo());
	}
	
	@Test
	public void alterarProduto() throws TipoChaveNaoEncontradoException {
		produtoDao.alterar(produto);
	}
}
