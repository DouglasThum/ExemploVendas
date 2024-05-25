package dao;

import domain.Venda;
import domain.Venda.Status;
import exception.TipoChaveNaoEncontradoException;
import generics.GenericDAO;

public class VendaDAO extends GenericDAO<Venda, String> implements IVendaDAO {

	@Override
	public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradoException {
		venda.setStatus(Status.CONCLUIDA);
		super.alterar(venda);
	}

	@Override
	public Class<Venda> getClassType() {
		return Venda.class;
	}

	@Override
	public void atualizarDados(Venda entityNovo, Venda entityCadastrado) {
		entityCadastrado.setCodigo(entityNovo.getCodigo());
		entityCadastrado.setCliente(entityNovo.getCliente());
		entityCadastrado.setDataVenda(entityNovo.getDataVenda());
		entityCadastrado.setValorTotal(entityNovo.getValorTotal());
		entityCadastrado.setStatus(entityNovo.getStatus());
	}
	
	@Override
	public void excluir(String valor) {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
	}

}
