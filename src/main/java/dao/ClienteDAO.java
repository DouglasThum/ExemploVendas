package dao;

import domain.Cliente;
import generics.GenericDAO;

public class ClienteDAO extends GenericDAO<Cliente> implements IClienteDAO {

	@Override
	public Class<Cliente> getClassType() {
		return Cliente.class;
	}

	@Override
	public void atualizarDados(Cliente entityNovo, Cliente entityCadastrado) {
		// TODO Auto-generated method stub
		
	}

	
}
