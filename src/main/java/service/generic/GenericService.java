package service.generic;

import java.io.Serializable;

import domain.Persistente;
import exception.TipoChaveNaoEncontradoException;
import generics.IGenericDAO;

public abstract class GenericService<T extends Persistente, E extends Serializable> implements IGenericService<T, E> {

	protected IGenericDAO<T, E> dao;
	
	public GenericService(IGenericDAO<T, E> dao) {
		this.dao = dao;
	}

	@Override
	public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradoException {
		return this.dao.cadastrar(entity);
	}

	@Override
	public void excluir(E valor) {
		this.dao.excluir(valor);		
	}

	@Override
	public void alterar(T entity) throws TipoChaveNaoEncontradoException {
		this.dao.alterar(entity);		
	}

	@Override
	public T consultar(E valor) {
		return this.dao.consultar(valor);
	}
}
