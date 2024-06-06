package service;

import java.sql.SQLException;

import domain.Produto;
import exception.DAOException;
import exception.MaisDeUmRegistroException;
import exception.TabelaException;
import exception.TipoChaveNaoEncontradoException;

public interface IProdutoService {

	Boolean cadastrar(Produto produto) throws TipoChaveNaoEncontradoException, DAOException, SQLException;

	Produto buscarPorCod(String cod) throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException;

	void excluir(String cod) throws MaisDeUmRegistroException, TabelaException, DAOException;

	void alterar(Produto produto) throws TipoChaveNaoEncontradoException, DAOException, SQLException;
}
