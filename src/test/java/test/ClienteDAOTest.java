package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.ClienteDAOMock;
import domain.Cliente;
import exception.TipoChaveNaoEncontradoException;
import generics.IGenericDAO;

public class ClienteDAOTest {
	
	private Cliente cliente;
	
	private IGenericDAO<Cliente, Long> clienteDao;
	
	public ClienteDAOTest() {
		clienteDao = new ClienteDAOMock();
	}

	@Before
	public void init() throws TipoChaveNaoEncontradoException {
		cliente = new Cliente();
		cliente.setNome("Douglas");
		cliente.setCpf(1234567890L);
		cliente.setTel(51999999999L);
		cliente.setRua("Bento Gonçalves");
		cliente.setNum(2);
		cliente.setCidade("Porto Alegre");
		cliente.setEstado("RS");
		clienteDao.cadastrar(cliente);
	}
	
	@Test
	public void pesquisarCliente() {
		Cliente clienteConsultado = clienteDao.consultar(cliente.getCpf());
		
		Assert.assertNotNull(clienteConsultado);
	}
	
	@Test
	public void salvarCliente() throws TipoChaveNaoEncontradoException {
		Boolean retorno = clienteDao.cadastrar(cliente);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void excluirCliente() {
		clienteDao.excluir(cliente.getCpf());
	}
	
	@Test
	public void alterarCliente() throws TipoChaveNaoEncontradoException {
		clienteDao.alterar(cliente);
	}
}
