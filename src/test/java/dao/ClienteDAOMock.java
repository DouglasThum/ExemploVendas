package dao;

import java.util.Collection;

import domain.Cliente;

public class ClienteDAOMock implements IClienteDAO {

	@Override
	public Boolean cadastrar(Cliente entity) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void excluir(Long codigo) {
		// TODO Auto-generated method stub
	}

	@Override
	public void alterar(Cliente entity) {
		// TODO Auto-generated method stub
	}

	@Override
	public Cliente consultar(Long codigo) {
		Cliente cliente = new Cliente();
		cliente.setCpf(codigo);
		return cliente;
	}

	@Override
	public Collection<Cliente> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}
}
