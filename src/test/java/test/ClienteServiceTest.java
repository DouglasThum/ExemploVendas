package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.ClienteDAOMock;
import dao.IClienteDAO;
import domain.Cliente;
import exception.TipoChaveNaoEncontradoException;
import service.ClienteService;
import service.IClienteService;

public class ClienteServiceTest {
	
	private IClienteService clienteService;
	private Cliente cliente;
	
	public ClienteServiceTest() {
		IClienteDAO dao = new ClienteDAOMock();
		clienteService = new ClienteService(dao);
	}
	
	@Before
	public void init() {
		cliente = new Cliente();
		cliente.setNome("Douglas");
		cliente.setCpf(1234567890L);
		cliente.setTel(51999999999L);
		cliente.setRua("Bento Gonçalves");
		cliente.setNum(2);
		cliente.setCidade("Porto Alegre");
		cliente.setEstado("RS");
	}
	
	@Test
	public void pesquisarCliente() {
		Cliente clienteConsultado = clienteService.buscarPorCpf(cliente.getCpf());
		Assert.assertNotNull(clienteConsultado);
	}

	@Test
	public void salvarCliente() throws TipoChaveNaoEncontradoException {
		Boolean retorno = clienteService.salvar(cliente);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void excluirCliente() {
		clienteService.excluir(cliente.getCpf());
	}
	
	@Test
	public void alterarCliente() throws TipoChaveNaoEncontradoException {
		cliente.setNome("Douglas Oliveira");
		clienteService.alterar(cliente);
		
		Assert.assertEquals("Douglas Oliveira", cliente.getNome());
	}
}
