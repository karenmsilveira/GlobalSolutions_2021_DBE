package br.com.colheita.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="perfil_navegacao_colheita")
@SequenceGenerator(sequenceName = "sq_tb_perfil_navegacao_colheita", name = "perfil_navegacao", allocationSize = 1)
public class Perfil implements GrantedAuthority{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5211530935580747857L;

	@Id
	@Column(name = "id_perfil_navegacao")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_navegacao")
	private Integer idPerfilNavegacao;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Column(name="data_insercao")
	private Date dataInsercao;
	
	

	public Integer getIdPerfilNavegacao() {
		return idPerfilNavegacao;
	}



	public void setIdPerfilNavegacao(Integer idPerfilNavegacao) {
		this.idPerfilNavegacao = idPerfilNavegacao;
	}



	public Usuario getUsuario() {
		return usuario;
	}



	public void setCliente(Usuario usuario) {
		this.usuario = usuario;
	}



	public Date getDataInsercao() {
		return dataInsercao;
	}



	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}



	@Override
	public String getAuthority() {
		return this.getUsuario().getCnpj();
	}
}
