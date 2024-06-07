package generics;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import annotation.ColunaTabela;
import annotation.Tabela;
import annotation.TipoChave;
import connection.ConnectionFactory;
import domain.Persistente;
import exception.DAOException;
import exception.MaisDeUmRegistroException;
import exception.TabelaException;
import exception.TipoChaveNaoEncontradoException;
import exception.TipoElementoNaoEncontradoException;


public abstract class GenericDAO<T extends Persistente, E extends Serializable> implements IGenericDAO<T, E> {
	
	public abstract Class<T> getClassType();
	
	public abstract String getQueryInsercao();
	
	public abstract String getQueryExclusao();
	
	public abstract String getQueryAtualizacao();
	
	protected abstract void setParametrosQueryInsercao(PreparedStatement stmInsert, T entity) throws SQLException;
	
	protected abstract void setParametrosQueryExclusao(PreparedStatement stmDelete, E valor) throws SQLException;
	
	protected abstract void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, T entity) throws SQLException;
	
	protected abstract void setParametrosQuerySelect(PreparedStatement stmSelect, E valor) throws SQLException;
	
	public GenericDAO() {
		
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
	public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradoException, DAOException, SQLException {
		Connection connection = null;
		PreparedStatement stm = null;
		try {
			connection = getConnection();
			stm = connection.prepareStatement(getQueryInsercao(), Statement.RETURN_GENERATED_KEYS);
			setParametrosQueryInsercao(stm, entity);
			Integer rowsAffected = stm.executeUpdate();
			
			if(rowsAffected > 0) {
				try (ResultSet rs = stm.getGeneratedKeys()) {
					if (rs.next()) {
						Persistente per = (Persistente) entity;
						per.setId(rs.getLong(1));
					}
					return true;
				}
			}
		} catch (SQLException e) {
			throw new DAOException("ERRO AO CADASTRAR OBJETO", e);
		} finally {
			closeConnection(connection, stm, null);
		}
		return false;
	}
	
	@Override
	public T consultar(E valor) throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException {
		try {
			validarMaisDeUmRegistro(valor);
			Connection connection = getConnection();
			PreparedStatement stm = connection.prepareStatement("SELECT * FROM " + getTableName() + " WHERE " + getNomeCampoChave(getClassType()) + " = ?");
			setParametrosQuerySelect(stm, valor);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				T entity = getClassType().getConstructor(null).newInstance(null);
				Field[] fields = entity.getClass().getDeclaredFields();
				for (Field field : fields) {
					if (field.isAnnotationPresent(ColunaTabela.class)) {
						ColunaTabela coluna = field.getAnnotation(ColunaTabela.class);
						String dbName = coluna.dbName();
						String setJavaName = coluna.setJavaName();
						Class<?> classField = field.getType();
						try {
							Method method = entity.getClass().getMethod(setJavaName, classField);
							setValueByType(entity, method, classField, rs, dbName);
						} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
							throw new DAOException("ERRO CONSULTANDO OBJETO ", e);
						} catch (TipoElementoNaoEncontradoException e) {
							throw new DAOException("ERRO CONSULTANDO OBJETO ", e);
						}
					}
				}
				return entity;
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | TipoChaveNaoEncontradoException e){
			throw new DAOException("ERRO CONSULTANDO OBJETO ", e);
		}
		return null;
	}

	@Override
	public void excluir(E valor) throws DAOException, SQLException {
		Connection connection = getConnection();
		PreparedStatement stm = null; 
		try {
			stm = connection.prepareStatement(getQueryExclusao());
			setParametrosQueryExclusao(stm, valor);
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("ERRO EXCLUINDO OBJETO ", e);
		} finally {
			closeConnection(connection, stm, null);
		}
	}

	@Override
	public void alterar(T entity) throws TipoChaveNaoEncontradoException, DAOException {
		Connection connection = getConnection();
		PreparedStatement stm = null; 
		try {
			stm = connection.prepareStatement(getQueryAtualizacao());
			setParametrosQueryAtualizacao(stm, entity);
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("ERRO ALTERANDO OBJETO ", e);
		}
	}

	@Override
	public Collection<T> buscarTodos() throws DAOException {
		List<T> list = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement stm = connection.prepareStatement("SELECT * FROM " + getTableName());
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				T entity = getClassType().getConstructor(null).newInstance(null);
				Field[] fields = entity.getClass().getDeclaredFields();
				for (Field field : fields) {
					if (field.isAnnotationPresent(ColunaTabela.class)) {
						ColunaTabela coluna = field.getAnnotation(ColunaTabela.class);
						String dbName = coluna.dbName();
						String setJavaName = coluna.setJavaName();
						Class<?> classField = field.getType();
						try {
							Method method = entity.getClass().getMethod(setJavaName, classField);
							setValueByType(entity, method, classField, rs, dbName);
						} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
							throw new DAOException("ERRO CONSULTANDO OBJETO ", e);
						} catch (TipoElementoNaoEncontradoException e) {
							throw new DAOException("ERRO CONSULTANDO OBJETO ", e);
						}
					}
				}
				list.add(entity);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e){
			throw new DAOException("ERRO CONSULTANDO OBJETO ", e);
		}
		return list;
		
	}
	
	private Long validarMaisDeUmRegistro(E valor) throws DAOException, MaisDeUmRegistroException, SQLException, TipoChaveNaoEncontradoException {
		Connection connection = getConnection();
		PreparedStatement stm = null;
		ResultSet rs = null;
		Long count = null;
		try {
			stm = connection.prepareStatement("SELECT count(*) FROM " + getTableName() + " WHERE " + getNomeCampoChave(getClassType()) + " = ?");
			setParametrosQuerySelect(stm, valor);
			rs = stm.executeQuery();
			if (rs.next()) {
				count = rs.getLong(1);
				if (count > 1) {
					throw new MaisDeUmRegistroException("ENCONTRADO MAIS DE UM REGISTRO DE " + getTableName());
				}
			}
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return count;
	}
	
	private String getTableName() {
		if (getClassType().isAnnotationPresent(Tabela.class)) {
			Tabela tabela = getClassType().getAnnotation(Tabela.class);
			return tabela.value();
		}
		return null;
	}
	
	private String getNomeCampoChave(Class<T> cla) throws TipoChaveNaoEncontradoException {
		Field[] fields = cla.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(TipoChave.class) && field.isAnnotationPresent(ColunaTabela.class)) {
				ColunaTabela coluna = field.getAnnotation(ColunaTabela.class);
				return coluna.dbName();
			}
		}
		return null;
	}
	
	private void setValueByType(T entity, Method method, Class<?> classField, ResultSet rs, String fieldName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, TipoElementoNaoEncontradoException {

    	if (classField.equals(Integer.class)) {
			Integer val = rs.getInt(fieldName);
			method.invoke(entity, val);
		} else if (classField.equals(Long.class)) {
			Long val = rs.getLong(fieldName);
			method.invoke(entity, val);
		} else if (classField.equals(Double.class)) {
			Double val =  rs.getDouble(fieldName);
			method.invoke(entity, val);
		} else if (classField.equals(Short.class)) {
			Short val =  rs.getShort(fieldName);
			method.invoke(entity, val);
		} else if (classField.equals(BigDecimal.class)) {
			BigDecimal val =  rs.getBigDecimal(fieldName);
			method.invoke(entity, val);
		} else if (classField.equals(String.class)) {
			String val =  rs.getString(fieldName);
			method.invoke(entity, val);
		} else {
			throw new TipoElementoNaoEncontradoException("TIPO DE CLASSE NÃO CONHECIDO: " + classField);
		}
	}
	
	private Object getValueByType(Class<?> typeField, ResultSet rs, String fieldName) throws SQLException, TipoElementoNaoEncontradoException {
		if (typeField.equals(Integer.TYPE)) {
			return rs.getInt(fieldName);
		} else if (typeField.equals(Long.TYPE)) {
			return rs.getLong(fieldName);
		} else if (typeField.equals(Double.TYPE)) {
			return rs.getDouble(fieldName);
		} else if (typeField.equals(Short.TYPE)) {
			return rs.getShort(fieldName);
		} else if (typeField.equals(BigDecimal.class)) {
			return rs.getBigDecimal(fieldName);
		} else if (typeField.equals(String.class)) {
			return rs.getString(fieldName);
		} else {
			throw new TipoElementoNaoEncontradoException("TIPO DE CLASSE NÃO CONHECIDO: " + typeField);
		}
		
		
	}
	
	private Connection getConnection() throws DAOException {
		try {
			return ConnectionFactory.getConnection();
		} catch (SQLException e) {
			throw new DAOException("ERRO ABRINDO CONEXAO COM O BANCO DE DADOS ", e);
		}
	}
	
	private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) throws SQLException {
		if(connection != null && !connection.isClosed()) {
			connection.close();
		}
		
		if(stm != null && !stm.isClosed()) {
			stm.close();
		}
		
		if(rs != null && !rs.isClosed()) {
			rs.close();
		}
	};
}
