package service;

import domain.Produto;
import exception.DAOException;
import exception.MaisDeUmRegistroException;
import exception.TabelaException;

import java.sql.SQLException;

import dao.IProdutoDAO;
import service.generic.GenericService;

public class ProdutoService extends GenericService<Produto, String> implements IProdutoService {

	public ProdutoService(IProdutoDAO produtoDAO) {
		super(produtoDAO);
	}

	@Override
	public Produto buscarPorCod(String cod) throws MaisDeUmRegistroException, TabelaException, DAOException, SQLException {
		return this.dao.consultar(cod);
	}

}
