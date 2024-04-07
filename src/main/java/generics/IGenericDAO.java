package generics;

import java.io.Serializable;
import java.util.Collection;

import domain.Persistente;
import exception.TipoChaveNaoEncontradoException;


public interface IGenericDAO <T extends Persistente, E extends Serializable> {
	
	public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradoException;
	
	public void excluir(E codigo);
	
	public void alterar(T entity) throws TipoChaveNaoEncontradoException;
	
	public T consultar(E codigo);
	
	public Collection<T> buscarTodos();

}
