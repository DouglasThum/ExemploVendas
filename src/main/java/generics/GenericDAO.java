package generics;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import annotation.TipoChave;
import domain.Persistente;


public abstract class GenericDAO<T extends Persistente> implements IGenericDAO<T> {
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Map<Long, T>> map;
	
	public abstract Class<T> getClassType();
	
	public abstract void atualizarDados(T entityNovo, T entityCadastrado);
	
	public GenericDAO() {
		if (this.map == null) {
			this.map = new HashMap<>();
		}
	}
	
	public Long getChave(T entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		for(Field field : fields) {
			if(field.isAnnotationPresent(TipoChave.class)) {
				TipoChave tipoChave = field.getAnnotation(TipoChave.class);
				String nomeMetodo = tipoChave.value();
				try {
					Method method = entity.getClass().getMethod(nomeMetodo);
					Long value = (Long) method.invoke(entity);
					return value;
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public Boolean cadastrar(T entity) {
		Map<Long, T> mapaInterno =  this.map.get(getClassType());
		Long chave = getChave(entity);
		if (mapaInterno.containsKey(chave)) {
			return true;
		}
		mapaInterno.put(chave, entity);
		return false;
	}

	@Override
	public void excluir(Long codigo) {
		Map<Long, T> mapaInterno = this.map.get(getClassType());
		T objCadastrado = mapaInterno.get(codigo);
		
		if (objCadastrado != null) {
			this.map.remove(codigo, objCadastrado);
		}
	}

	@Override
	public void alterar(T entity) {
		Map<Long, T> mapaInterno = this.map.get(getClassType());
		Long chave = getChave(entity);
		T objCadastrado = mapaInterno.get(chave);
		if (objCadastrado != null) {
			atualizarDados(entity, objCadastrado);
		}
	}

	@Override
	public T consultar(Long codigo) {
		Map<Long, T> mapaInterno =  this.map.get(getClassType());
		return mapaInterno.get(codigo);
	}

	@Override
	public Collection<T> buscarTodos() {
		Map<Long, T> mapaInterno =  this.map.get(getClassType());
		return mapaInterno.values();
	}
}
