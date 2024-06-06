package service;

import java.sql.SQLException;

import dao.IClienteDAO;
import domain.Cliente;
import exception.DAOException;
import exception.MaisDeUmRegistroException;
import exception.TabelaException;
import service.generic.GenericService;

public class ClienteService extends GenericService<Cliente, Long> implements IClienteService {
	
	public ClienteService(IClienteDAO clienteDAO) {
		super(clienteDAO);
	}

	@Override
	public Cliente buscarPorCpf(Long cpf) throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException {
		return this.dao.consultar(cpf);
	}
}