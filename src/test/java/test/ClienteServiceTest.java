package test;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.ClienteDAOMock;
import dao.IClienteDAO;
import domain.Cliente;
import exception.DAOException;
import exception.MaisDeUmRegistroException;
import exception.TabelaException;
import exception.TipoChaveNaoEncontradoException;
import service.ClienteService;
import service.generic.IGenericService;

public class ClienteServiceTest {
	
	private Cliente cliente;
	private IGenericService<Cliente, Long> service;
	
	public ClienteServiceTest() {
		IClienteDAO dao = new ClienteDAOMock();
		service = new ClienteService(dao);
	}
	
	@Before
	public void init() {
		cliente = new Cliente();
		cliente.setNome("Douglas");
		cliente.setCpf(1234567890L);
		cliente.setTel(51999999999L);
		cliente.setRua("Bento Gonçalves");
		cliente.setNum(2L);
		cliente.setCidade("Porto Alegre");
		cliente.setEstado("RS");
	}
	
	@Test
	public void pesquisarCliente() throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException {
		Cliente clienteConsultado = service.consultar(cliente.getCpf());
		Assert.assertNotNull(clienteConsultado);
	}

	@Test
	public void salvarCliente() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		Boolean retorno = service.cadastrar(cliente);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void excluirCliente() throws MaisDeUmRegistroException, TabelaException, DAOException {
		service.excluir(cliente.getCpf());
	}
	
	@Test
	public void alterarCliente() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		cliente.setNome("Douglas Oliveira");
		service.alterar(cliente);
		
		Assert.assertEquals("Douglas Oliveira", cliente.getNome());
	}
}
