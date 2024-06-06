package dao;

import domain.Venda;
import exception.DAOException;
import exception.TipoChaveNaoEncontradoException;
import generics.IGenericDAO;

public interface IVendaDAO extends IGenericDAO<Venda, String> {

	public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradoException, DAOException;
}
