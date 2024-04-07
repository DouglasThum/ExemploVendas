package dao;

import domain.Cliente;
import generics.GenericDAO;

public class ClienteDAO extends GenericDAO<Cliente, Long> implements IClienteDAO {

	public ClienteDAO() {
		super();
	}
	
	@Override
	public Class<Cliente> getClassType() {
		return Cliente.class;
	}

	@Override
	public void atualizarDados(Cliente entityNovo, Cliente entityCadastrado) {
		entityCadastrado.setCpf(entityNovo.getCpf());
		entityCadastrado.setNome(entityNovo.getNome());
		entityCadastrado.setTel(entityNovo.getTel());
		entityCadastrado.setRua(entityNovo.getRua());
		entityCadastrado.setNum(entityNovo.getNum());
		entityCadastrado.setCidade(entityNovo.getCidade());
		entityCadastrado.setEstado(entityNovo.getEstado());
	}
}
