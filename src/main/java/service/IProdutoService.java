package service;

import domain.Produto;
import exception.TipoChaveNaoEncontradoException;

public interface IProdutoService {

	Boolean cadastrar(Produto produto) throws TipoChaveNaoEncontradoException;

	Produto buscarPorCod(Long cod);

	void excluir(Long cod);

	void alterar(Produto produto) throws TipoChaveNaoEncontradoException;
}
