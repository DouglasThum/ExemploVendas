package generics;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;

import dao.Persistente;
import exception.DAOException;
import exception.MaisDeUmRegistroException;
import exception.TabelaException;
import exception.TipoChaveNaoEncontradoException;


public interface IGenericDAO <T extends Persistente, E extends Serializable> {
	
	public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradoException, DAOException, SQLException;
	
	public void excluir(E valor) throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException;
	
	public void alterar(T entity) throws TipoChaveNaoEncontradoException, DAOException, SQLException;
	
	public T consultar(E valor) throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException, TipoChaveNaoEncontradoException;
	
	public Collection<T> buscarTodos() throws DAOException;

}
