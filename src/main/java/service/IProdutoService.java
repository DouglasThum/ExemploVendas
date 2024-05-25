package service;

import domain.Produto;
import exception.TipoChaveNaoEncontradoException;

public interface IProdutoService {

	Boolean cadastrar(Produto produto) throws TipoChaveNaoEncontradoException;

	Produto buscarPorCod(String cod);

	void excluir(String cod);

	void alterar(Produto produto) throws TipoChaveNaoEncontradoException;
}
