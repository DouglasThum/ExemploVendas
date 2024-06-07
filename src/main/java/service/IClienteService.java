package service;

import java.sql.SQLException;

import domain.Cliente;
import exception.DAOException;
import exception.MaisDeUmRegistroException;
import exception.TabelaException;
import exception.TipoChaveNaoEncontradoException;

public interface IClienteService {

	Boolean cadastrar(Cliente cliente) throws TipoChaveNaoEncontradoException, DAOException, SQLException;

	Cliente buscarPorCpf(Long cpf) throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException;

	void excluir(Long cpf) throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException;

	void alterar(Cliente cliente) throws TipoChaveNaoEncontradoException, DAOException, SQLException;

}
