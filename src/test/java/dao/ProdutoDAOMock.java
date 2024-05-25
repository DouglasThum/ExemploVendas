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
	public void excluir(String codigo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(Produto entity) throws TipoChaveNaoEncontradoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Produto consultar(String codigo) {
		Produto produto = new Produto();
		produto.setCodigo(codigo);
		return produto;
	}

	@Override
	public Collection<Produto> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
