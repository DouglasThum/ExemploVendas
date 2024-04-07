package service.generic;

import java.io.Serializable;

import domain.Persistente;
import exception.TipoChaveNaoEncontradoException;

public interface IGenericService<T extends Persistente, E extends Serializable> {
	
	public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradoException;
	public void excluir(E valor);
	public void alterar(T entity) throws TipoChaveNaoEncontradoException;
	public T consultar(E valor);
}
