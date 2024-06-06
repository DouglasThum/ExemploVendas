package domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import annotation.ColunaTabela;
import annotation.Tabela;
import annotation.TipoChave;

@Tabela("TB_VENDA")
public class Venda implements Persistente {
	
	public enum Status {
		INICIADA, CONCLUIDA, CANCELADA;
	}
	
	public static Status getByName(String value) {
		for (Status status: Status.values()) {
			if(status.name().equals(value)) {
				return status;
			}
		}
		return null;
	}
	
	@ColunaTabela(dbName = "id", setJavaName = "setId")
	private Long id;
	
	@TipoChave("getCodigo")
	@ColunaTabela(dbName = "codigo", setJavaName = "setCodigo")
	private String codigo;
	
	@ColunaTabela(dbName = "cliente", setJavaName = "setCliente")
	private Cliente cliente;
	
	private Set<ProdutoQuantidade> produtos;
	
	@ColunaTabela(dbName = "valorTotal", setJavaName = "setValorTotal")
	private BigDecimal valorTotal;
	
	@ColunaTabela(dbName = "dataVenda", setJavaName = "setDataVenda")
	private Instant dataVenda;
	
	@ColunaTabela(dbName = "status", setJavaName = "setStatus")
	private Status status;
	
	public Venda() {
		produtos = new HashSet<>();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Set<ProdutoQuantidade> getProdutos() {
		return produtos;
	}

	public void setProdutos(Set<ProdutoQuantidade> produtos) {
		this.produtos = produtos;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Instant getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Instant dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public void adicionarProduto(Produto produto, Integer quantidade) {
		validarStatus();
		Optional<ProdutoQuantidade> optional = produtos.stream()
				.filter(p -> p.getProduto().getCodigo().equals(produto.getCodigo()))
				.findAny();
		if (optional.isPresent()) {
			ProdutoQuantidade prodQuant = optional.get();
			prodQuant.adicionar(quantidade);
		} else {
			ProdutoQuantidade prod = new ProdutoQuantidade();
			prod.setProduto(produto);
			prod.adicionar(quantidade);
			produtos.add(prod);
		}
		recalcularValorTotalVenda();
	}
	
	public void validarStatus() {
		if(this.status == Status.CONCLUIDA) {
			throw new UnsupportedOperationException("IMPOSSÍVEL ALTERAR VENDA FINALIZADA");
		}
	}
	
	public void removerProduto(Produto produto, Integer quantidade) {
		validarStatus();
		Optional<ProdutoQuantidade> optional = produtos.stream()
				.filter(p -> p.getProduto().getCodigo().equals(produto.getCodigo()))
				.findAny();
		if (optional.isPresent()) {
			ProdutoQuantidade prodQuant = optional.get();
			prodQuant.remover(quantidade);
		} else {
			produtos.remove(optional.get());
		}
		recalcularValorTotalVenda();
	}
	
	public void removerTodosProdutos() {
		validarStatus();
		produtos.clear();
		valorTotal = BigDecimal.ZERO;
	}
	
	public Integer getQuantidadeTotalProdutos() {
		Integer result = produtos.stream()
				.reduce(0, (parcialResult, prod) -> parcialResult + prod.getQuantidade(), Integer::sum);
		return result;		
	}
	
	private void recalcularValorTotalVenda() {
		validarStatus();
		BigDecimal valorTotal = BigDecimal.ZERO;
		for (ProdutoQuantidade prod : this.produtos) {
			valorTotal = valorTotal.add(prod.getValorTotal());
		}
		this.valorTotal = valorTotal;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
