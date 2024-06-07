package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Collection;

import org.junit.After;
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Test
	public void pesquisarCliente() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		cliente = instanciarCliente();
		clienteDao.cadastrar(cliente);
		
		Cliente clienteConsultado = clienteDao.consultar(cliente.getCpf());
		assertNotNull(clienteConsultado);
		
		clienteDao.excluir(cliente.getCpf());
	}
	
	@Test
	public void salvarCliente() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		cliente = instanciarCliente();
		Boolean retorno = clienteDao.cadastrar(cliente);
		assertTrue(retorno);
		
		Cliente clienteConsultado = clienteDao.consultar(cliente.getCpf());
		assertNotNull(clienteConsultado);
		
		clienteDao.excluir(cliente.getCpf());
	}
	
	@Test
	public void alterarCliente() throws TipoChaveNaoEncontradoException, DAOException, SQLException, MaisDeUmRegistroException, TabelaException {
		cliente = instanciarCliente();
		Boolean retorno = clienteDao.cadastrar(cliente);
		assertTrue(retorno);
		
		Cliente clienteConsultado = clienteDao.consultar(cliente.getCpf());
		assertNotNull(clienteConsultado);
		
		clienteConsultado.setNome("Douglas Oliveira");
		
		clienteDao.alterar(clienteConsultado);
		
		Cliente clienteAlterado = clienteDao.consultar(clienteConsultado.getCpf());
		assertNotNull(clienteAlterado);
		
		assertEquals(clienteAlterado.getNome(), "Douglas Oliveira");
		clienteDao.excluir(cliente.getCpf());
		clienteConsultado = clienteDao.consultar(cliente.getCpf());
		assertNull(clienteConsultado);
	}
	
	@Test
	public void excluirCliente() throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException, TipoChaveNaoEncontradoException {
		cliente = instanciarCliente();
		Boolean retorno = clienteDao.cadastrar(cliente);
		assertTrue(retorno);
				
		Cliente clienteConsultado = clienteDao.consultar(cliente.getCpf());
		assertNotNull(clienteConsultado);
		
		clienteDao.excluir(cliente.getCpf());
		clienteConsultado = clienteDao.consultar(cliente.getCpf());
		assertNull(clienteConsultado);
	}
	
	public Cliente instanciarCliente() {
		cliente = new Cliente();
		
		cliente.setNome("Douglas");
		cliente.setCpf(1234567890L);
		cliente.setTel(51999999999L);
		cliente.setEndereco("Bento Gonçalves");
		cliente.setNum(2L);
		cliente.setCidade("Porto Alegre");
		cliente.setEstado("RS");
		
		return cliente;
	}
}
