package service;

import dao.IClienteDAO;
import domain.Cliente;
import service.generic.GenericService;

public class ClienteService extends GenericService<Cliente, Long> implements IClienteService {
	
	public ClienteService(IClienteDAO clienteDAO) {
		super(clienteDAO);
	}

	@Override
	public Cliente buscarPorCpf(Long cpf) {
		return this.dao.consultar(cpf);
	}
}
