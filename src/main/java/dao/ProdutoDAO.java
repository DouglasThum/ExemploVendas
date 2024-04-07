package dao;

import generics.GenericDAO;
import domain.Produto;

public class ProdutoDAO extends GenericDAO<Produto, Long> implements IProdutoDAO {

	public ProdutoDAO() {
		super();
	}
	
	@Override
	public Class<Produto> getClassType() {
		return Produto.class;
	}

	@Override
	public void atualizarDados(Produto entityNovo, Produto entityCadastrado) {
		entityCadastrado.setCod(entityNovo.getCod());
		entityCadastrado.setNome(entityNovo.getNome());
		entityCadastrado.setDescricao(entityNovo.getDescricao());
		entityCadastrado.setValor(entityNovo.getValor());
	}
}
