package dao.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Cliente;

public class ClienteFactory {

	public static Cliente convert(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setId(rs.getLong("ID_CLIENTE"));
		cliente.setNome(rs.getString(("NOME")));
		cliente.setCpf(rs.getLong(("CPF")));
		cliente.setTel(rs.getLong(("TEL")));
		cliente.setEndereco(rs.getString(("ENDERECO")));
		cliente.setNum(rs.getLong(("NUM")));
		cliente.setCidade(rs.getString(("CIDADE")));
		cliente.setEstado(rs.getString(("ESTADO")));
		cliente.setIdade(rs.getLong("IDADE"));
		return cliente;
	}
}
