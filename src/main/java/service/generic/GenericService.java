package service.generic;

import java.io.Serializable;
import java.sql.SQLException;

import dao.Persistente;
import exception.DAOException;
import exception.MaisDeUmRegistroException;
import exception.TabelaException;
import exception.TipoChaveNaoEncontradoException;
import generics.IGenericDAO;

public abstract class GenericService<T extends Persistente, E extends Serializable> implements IGenericService<T, E> {

	protected IGenericDAO<T, E> dao;
	
	public GenericService(IGenericDAO<T, E> dao) {
		this.dao = dao;
	}

	@Override
	public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		return this.dao.cadastrar(entity);
	}

	@Override
	public void excluir(E valor) throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException {
		this.dao.excluir(valor);		
	}

	@Override
	public void alterar(T entity) throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		this.dao.alterar(entity);		
	}

	@Override
	public T consultar(E valor) throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException, TipoChaveNaoEncontradoException {
		return this.dao.consultar(valor);
	}
}
