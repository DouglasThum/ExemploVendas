package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.IProdutoDAO;
import dao.ProdutoDAOMock;
import domain.Produto;
import exception.TipoChaveNaoEncontradoException;
import service.ProdutoService;
import service.generic.IGenericService;

public class ProdutoServiceTest {
	
	private Produto produto;
	private IGenericService<Produto, Long> service;
	
	public ProdutoServiceTest() {
		IProdutoDAO dao = new ProdutoDAOMock();
		service = new ProdutoService(dao);
	}
	
	@Before
	public void init() {
		produto = new Produto();
		produto.setCod(12345L);
		produto.setNome("Produto1");
		produto.setDescricao("Produto descrição");
		produto.setValor(50.0);
	}
	
	@Test
	public void pesquisarProduto() {
		Produto produtoConsultado = service.consultar(produto.getCod());
		Assert.assertNotNull(produtoConsultado);
	}

	@Test
	public void salvarProduto() throws TipoChaveNaoEncontradoException {
		Boolean retorno = service.cadastrar(produto);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void excluirProduto() {
		service.excluir(produto.getCod());
	}
	
	@Test
	public void alterarProduto() throws TipoChaveNaoEncontradoException {
		produto.setNome("Douglas Oliveira");
		service.alterar(produto);
		
		Assert.assertEquals("Douglas Oliveira", produto.getNome());
	}
}
