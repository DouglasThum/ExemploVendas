package service;

import domain.Produto;
import dao.IProdutoDAO;
import service.generic.GenericService;

public class ProdutoService extends GenericService<Produto, Long> implements IProdutoService {

	public ProdutoService(IProdutoDAO produtoDAO) {
		super(produtoDAO);
	}

	@Override
	public Produto buscarPorCod(Long cod) {
		return this.dao.consultar(cod);
	}

}
