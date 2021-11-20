package br.com.colheita.bo;

import javax.validation.Valid;

import br.com.colheita.dto.LoginDto;
import br.com.colheita.model.Endereco;
import br.com.colheita.model.Usuario;
import br.com.colheita.repository.EnderecoRepository;
import br.com.colheita.repository.UsuarioRepository;
import net.sf.json.JSONObject;

public class UsuarioBo {
	
	private UsuarioRepository usuarioRepository;
	private EnderecoRepository enderecoRepository;
	
	public UsuarioBo(UsuarioRepository usuarioRepository, EnderecoRepository enderecoRepository) {
		this.usuarioRepository = usuarioRepository;
		this.enderecoRepository = enderecoRepository;
	}
	
	public Usuario salvaUsuario (Usuario u, Endereco e) {
		e = this.enderecoRepository.save(e);
		u.setEndereco(e);
		return this.usuarioRepository.save(u);
	}
	
	public Usuario alteraUsuario(JSONObject novoUsuario) {
		Integer idUsuario = novoUsuario.getInt("idUsuario");
		
		Usuario usuarioAlterar = this.usuarioRepository.findByIdUsuario(idUsuario);
		usuarioAlterar.setCnpj(novoUsuario.getString("cnpj"));
		usuarioAlterar.setEmail(novoUsuario.getString("email"));
		usuarioAlterar.setNome(novoUsuario.getString("nome"));
		usuarioAlterar.setSenha(novoUsuario.getString("senha"));
		
		Endereco e = new Endereco();
		e.setBairro(novoUsuario.getString("bairro"));
		e.setCep(novoUsuario.getString("cep"));
		e.setCidade(novoUsuario.getString("cidade"));
		e.setEstado(novoUsuario.getString("estado"));
		e.setLogradouro(novoUsuario.getString("logradouro"));
		e.setLogradouroNumero(novoUsuario.getInt("numeroLog"));
		usuarioAlterar.setEndereco(e);
		
		return this.usuarioRepository.save(usuarioAlterar);
	}
	
	public JSONObject carregaUsuario(@Valid LoginDto form) {

		Usuario usu = this.usuarioRepository.findByCnpj(form.getCnpj());
		JSONObject json = usu.toJsonSimple();

		return json;
	}

}
