package test;

import java.sql.SQLException;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import dao.ClienteDAO;
import domain.Cliente;
import exception.DAOException;
import exception.MaisDeUmRegistroException;
import exception.TabelaException;
import exception.TipoChaveNaoEncontradoException;
import generics.IGenericDAO;

public class ClienteDAOTest {
	
	private Cliente cliente;
	
	private IGenericDAO<Cliente, Long> clienteDao;
	
	public ClienteDAOTest() {
		clienteDao = new ClienteDAO();
	}

	@After
	public void end() throws DAOException {
		Collection<Cliente> list = clienteDao.buscarTodos();
		list.forEach(cli -> {
			try {
				clienteDao.excluir(cli.getCpf());
			} catch (DAOException | MaisDeUmRegistroException | TabelaException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Test
	public void pesquisarCliente() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		
		cliente = new Cliente();
		
		cliente.setNome("Douglas");
		cliente.setCpf(1234567890L);
		cliente.setTel(51999999999L);
		cliente.setRua("Bento Gonçalves");
		cliente.setNum(2L);
		cliente.setCidade("Porto Alegre");
		cliente.setEstado("RS");
		clienteDao.cadastrar(cliente);
		
		Cliente clienteConsultado = clienteDao.consultar(cliente.getCpf());
		
		Assert.assertNotNull(clienteConsultado);
		clienteDao.excluir(cliente.getCpf());
	}
	
	@Test
	public void salvarCliente() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		Boolean retorno = clienteDao.cadastrar(cliente);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void excluirCliente() throws MaisDeUmRegistroException, TabelaException, DAOException {
		clienteDao.excluir(cliente.getCpf());
	}
	
	@Test
	public void alterarCliente() throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		clienteDao.alterar(cliente);
	}
}
