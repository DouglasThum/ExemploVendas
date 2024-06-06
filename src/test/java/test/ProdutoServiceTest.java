package test;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.IProdutoDAO;
import dao.ProdutoDAOMock;
import domain.Produto;
import exception.DAOException;
import exception.MaisDeUmRegistroException;
import exception.TabelaException;
import exception.TipoChaveNaoEncontradoException;
import service.ProdutoService;
import service.generic.IGenericService;

public class ProdutoServiceTest {
	
	private Produto produto;
	private IGenericService<Produto, String> service;
	
	public ProdutoServiceTest() {
		IProdutoDAO dao = new ProdutoDAOMock();
		service = new ProdutoService(dao);
	}
	
	@Before
	public void init() {
		produto = new Produto();
		produto.setCodigo("12345");
		produto.setNome("Produto1");
		produto.setDescricao("Produto descrição");
		produto.setValor(BigDecimal.valueOf(50.0));
	}
	
	@Test
	public void pesquisarProduto() throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException {
		Produto produtoConsultado = service.consultar(produto.getCodigo());
		Assert.assertNotNull(produtoConsultado);
	}

	@Test
	public void salvarProduto() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		Boolean retorno = service.cadastrar(produto);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void excluirProduto() throws MaisDeUmRegistroException, TabelaException, DAOException {
		service.excluir(produto.getCodigo());
	}
	
	@Test
	public void alterarProduto() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		produto.setNome("Douglas Oliveira");
		service.alterar(produto);
		
		Assert.assertEquals("Douglas Oliveira", produto.getNome());
	}
}
