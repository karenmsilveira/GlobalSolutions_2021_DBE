package br.com.colheita.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_endereco_colheita")
@SequenceGenerator(sequenceName = "sq_tbl_endereco_colheita", name = "endereco", allocationSize = 1)
public class Endereco {
	
	@Id
	@Column(name = "id_endereco")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco")
	private int idEndereco;
	
	@Column(name = "desc_logradouro")
	private String logradouro;
	
	@Column(name = "nr_logradouro")
	private int logradouroNumero;
	
	@Column(name = "desc_bairro")
	private String bairro;
	
	@Column(name = "desc_cidade")
	private String cidade;
	
	@Column(name = "desc_estado")
	private String estado;
	
	@Column(name = "cep")
	private String cep;

	public int getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(int idEndereco) {
		this.idEndereco = idEndereco;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getLogradouroNumero() {
		return logradouroNumero;
	}

	public void setLogradouroNumero(int logradouroNumero) {
		this.logradouroNumero = logradouroNumero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	


}
