package generics;

import java.util.Collection;

import domain.Persistente;


public interface IGenericDAO <T extends Persistente> {
	
	public Boolean cadastrar(T entity);
	
	public void excluir(Long codigo);
	
	public void alterar(T entity);
	
	public T consultar(Long codigo);
	
	public Collection<T> buscarTodos();

}
