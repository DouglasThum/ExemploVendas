package generics;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import annotation.TipoChave;
import domain.Persistente;
import exception.TipoChaveNaoEncontradoException;


public abstract class GenericDAO<T extends Persistente, E extends Serializable> implements IGenericDAO<T, E> {
	
	// protected Map<Class, Map<Long, T>> map;
	
	private SingletonMap singletonMap;
	
	public abstract Class<T> getClassType();
	
	public abstract void atualizarDados(T entityNovo, T entityCadastrado);
	
	public GenericDAO() {
		this.singletonMap = SingletonMap.getInstance();
	}
	
    @SuppressWarnings("unchecked")
	public E getChave(T entity) throws TipoChaveNaoEncontradoException {
        Field[] fields = entity.getClass().getDeclaredFields();
        E returnValue = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(TipoChave.class)) {
                TipoChave tipoChave = field.getAnnotation(TipoChave.class);
                String nomeMetodo = tipoChave.value();
                try {
                    Method method = entity.getClass().getMethod(nomeMetodo);
                    returnValue = (E)method.invoke(entity);
                    return returnValue;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    throw new TipoChaveNaoEncontradoException("Chave principal do objeto " + entity.getClass() + " não encontrada", e);
                }
            }
        }
        String msg = "Chave principal do objeto " + entity.getClass() + " não encontrada";
		System.out.println("**** ERRO ****" + msg);
		throw new TipoChaveNaoEncontradoException(msg);
    }

	@Override
	public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradoException {
		Map<E, T> mapaInterno =  getMapa();
		E chave = getChave(entity);
		if (mapaInterno.containsKey(chave)) {
			return false;
		}
		mapaInterno.put(chave, entity);
		return true;
	}
	
	private Map<E, T> getMapa() {
		@SuppressWarnings("unchecked")
		Map<E, T> mapaInterno = (Map<E, T>) this.singletonMap.getMap().get(getClassType());
		if (mapaInterno == null) {
			mapaInterno = new HashMap<>();
			this.singletonMap.getMap().put(getClassType(), mapaInterno);
		}
		return mapaInterno;
	}

	@Override
	public void excluir(E codigo) {
		Map<E, T> mapaInterno = getMapa();
		T objCadastrado = mapaInterno.get(codigo);
		
		if (objCadastrado != null) {
			mapaInterno.remove(codigo, objCadastrado);
		}
	}

	@Override
	public void alterar(T entity) throws TipoChaveNaoEncontradoException {
		Map<E, T> mapaInterno = getMapa();
		E chave = getChave(entity);
		T objCadastrado = mapaInterno.get(chave);
		if (objCadastrado != null) {
			atualizarDados(entity, objCadastrado);
		}
	}

	@Override
	public T consultar(E codigo) {
		Map<E, T> mapaInterno = getMapa();
		return mapaInterno.get(codigo);
	}

	@Override
	public Collection<T> buscarTodos() {
		Map<E, T> mapaInterno = getMapa();
		return mapaInterno.values();
	}
}
