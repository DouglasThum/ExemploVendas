package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.ProdutoDAOMock;
import domain.Produto;
import exception.TipoChaveNaoEncontradoException;
import generics.IGenericDAO;

public class ProdutoDAOTest {
	
	private Produto produto;
	
	private IGenericDAO<Produto, Long> produtoDao;
	
	public ProdutoDAOTest() {
		produtoDao = new ProdutoDAOMock();
	}

	@Before
	public void init() throws TipoChaveNaoEncontradoException {
		produto = new Produto();
		produto.setCod(12345L);
		produto.setNome("Produto1");
		produto.setDescricao("Produto descrição");
		produto.setValor(50.0);
		produtoDao.cadastrar(produto);
	}
	
	@Test
	public void pesquisarProduto() {
		Produto produtoConsultado = produtoDao.consultar(produto.getCod());
		
		Assert.assertNotNull(produtoConsultado);
	}
	
	@Test
	public void salvarProduto() throws TipoChaveNaoEncontradoException {
		Boolean retorno = produtoDao.cadastrar(produto);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void excluirProduto() {
		produtoDao.excluir(produto.getCod());
	}
	
	@Test
	public void alterarProduto() throws TipoChaveNaoEncontradoException {
		produtoDao.alterar(produto);
	}
}
