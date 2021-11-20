package br.com.colheita.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.sf.json.JSONObject;

@Entity
@Table(name = "tb_produto_colheita")
@SequenceGenerator(sequenceName = "sq_tb_produto_colheita", name = "produto", allocationSize = 1)
public class Produto {
	
	@Id
	@Column(name = "id_produto")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto")
	private int idProduto;
	
	@Column(name = "nome")
	private String nome;
	@Column(name = "dt_validade")
	private Date dataValidade;
	@Column(name = "preco")
	private double precoProduto;
	@Column(name = "categoria")
	private String categoria;
	@OneToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	public int getIdProduto() {
		return idProduto;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataValidade() {
		return dataValidade;
	}
	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}
	public double getPrecoProduto() {
		return precoProduto;
	}
	public void setPrecoProduto(double precoProduto) {
		this.precoProduto = precoProduto;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public JSONObject toJsonSimple() {
		JSONObject json = new JSONObject();
		json.put("idProduto", this.idProduto);
		json.put("nome", this.nome);
		json.put("dataValidade", this.dataValidade);
		json.put("categoria", this.categoria);
		
		return json;
	}
	
	

}
