package dao;

import java.util.Collection;

import domain.Produto;
import exception.TipoChaveNaoEncontradoException;

public class ProdutoDAOMock implements IProdutoDAO {

	@Override
	public Boolean cadastrar(Produto entity) throws TipoChaveNaoEncontradoException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void excluir(Long codigo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(Produto entity) throws TipoChaveNaoEncontradoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Produto consultar(Long codigo) {
		Produto produto = new Produto();
		produto.setCod(codigo);
		return produto;
	}

	@Override
	public Collection<Produto> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
