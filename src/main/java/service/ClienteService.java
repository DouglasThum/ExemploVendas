package service;

import dao.IClienteDAO;
import domain.Cliente;
import exception.TipoChaveNaoEncontradoException;

public class ClienteService implements IClienteService {
	
	private IClienteDAO clienteDAO;
	
	public ClienteService(IClienteDAO clineteDAO) {
		this.clienteDAO = clineteDAO;
	}

	@Override
	public Boolean salvar(Cliente cliente) throws TipoChaveNaoEncontradoException{
		return clienteDAO.cadastrar(cliente);
	}

	@Override
	public Cliente buscarPorCpf(Long cpf) {
		return clienteDAO.consultar(cpf);
	}

	@Override
	public void excluir(Long cpf) {
		clienteDAO.excluir(cpf);
	}

	@Override
	public void alterar(Cliente cliente) throws TipoChaveNaoEncontradoException {
		clienteDAO.alterar(cliente);
	}
}
