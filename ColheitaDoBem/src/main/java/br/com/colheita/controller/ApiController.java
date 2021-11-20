package br.com.colheita.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.colheita.bo.ProdutoBo;
import br.com.colheita.bo.UsuarioBo;
import br.com.colheita.model.Endereco;
import br.com.colheita.model.Produto;
import br.com.colheita.model.Usuario;
import br.com.colheita.repository.EnderecoRepository;
import br.com.colheita.repository.ProdutoRepository;
import br.com.colheita.repository.UsuarioRepository;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/api")
public class ApiController {
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	HttpSession session;

	public boolean validaUsuarioLogado() {
		try {
			Object usu = session.getAttribute("idUsuario");
			Integer idUsuario = Integer.parseInt(usu.toString());

			if (idUsuario != null) {
				return true;
			}
		} catch (NullPointerException e) {
		}
		return false;

	}

	@CrossOrigin
	@RequestMapping(value = "/cadastro", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<?> salvarUsuario(@RequestBody(required = true) JSONObject estruturaJson) throws Exception {
		JSONObject json = new JSONObject();
		Usuario usu = new Usuario();

		usu.setNome(estruturaJson.getString("nome"));
		usu.setEmail(estruturaJson.getString("email"));
		usu.setCnpj(estruturaJson.getString("cnpj"));

		Endereco end = new Endereco();
		end.setLogradouro(estruturaJson.getString("logradouro"));
		end.setLogradouroNumero(estruturaJson.getInt("numeroLog"));
		end.setBairro(estruturaJson.getString("bairro"));
		end.setCidade(estruturaJson.getString("cidade"));
		end.setCep(estruturaJson.getString("cep"));
		end.setEstado(estruturaJson.getString("estado"));
		usu.setEndereco(end);

		String senha = new BCryptPasswordEncoder().encode(estruturaJson.getString("senha"));
		usu.setSenha(senha);

		try {
			UsuarioBo uBo = this.getInstanceUsuarioBo();
			Usuario u = uBo.salvaUsuario(usu, end);
			json.put("erro", false);
		} catch (Exception e) {
			json.put("erro", true);
		}

		if (json.getBoolean("erro")) {
			return ResponseEntity.badRequest().body(json);
		} else {
			return ResponseEntity.ok(json);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/cadastro-produto", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<?> salvarProduto(@RequestBody(required = true) JSONObject estruturaJson) throws Exception {
		JSONObject json = new JSONObject();
		Produto prod = new Produto();
		Usuario usu = this.usuarioRepository
				.findByIdUsuario(Integer.parseInt(session.getAttribute("idUsuario").toString()));
		prod.setUsuario(usu);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		prod.setCategoria(estruturaJson.getString("categoria"));
		prod.setDataValidade(sdf.parse(estruturaJson.getString("dataValidade")));
		prod.setNome(estruturaJson.getString("nome"));
		prod.setPrecoProduto(estruturaJson.getDouble("precoProduto"));

		try {
			ProdutoBo pBo = this.getInstanceProdutoBo();
			Produto p = pBo.salvaProduto(prod);
			json.put("erro", false);
		} catch (Exception e) {
			json.put("erro", true);
		}

		if (json.getBoolean("erro")) {
			return ResponseEntity.badRequest().body(json);
		} else {
			return ResponseEntity.ok(json);
		}

	}

	@CrossOrigin
	@RequestMapping(value = "/editar-produto", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<?> editarProduto(@RequestBody(required = true) JSONObject estruturaJson) throws Exception {
		JSONObject json = new JSONObject();
		Produto prod = produtoRepository.findByIdProduto(estruturaJson.getInt("idProduto"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		prod.setCategoria(estruturaJson.getString("categoria"));
		prod.setDataValidade(sdf.parse(estruturaJson.getString("dataValidade")));
		prod.setNome(estruturaJson.getString("nome"));
		prod.setPrecoProduto(estruturaJson.getDouble("precoProduto"));

		try {
			ProdutoBo pBo = this.getInstanceProdutoBo();
			Produto p = pBo.salvaProduto(prod);
			json.put("erro", false);
		} catch (Exception e) {
			json.put("erro", true);
		}

		if (json.getBoolean("erro")) {
			return ResponseEntity.badRequest().body(json);
		} else {
			return ResponseEntity.ok(json);
		}

	}

	public UsuarioBo getInstanceUsuarioBo() {
		return new UsuarioBo(this.usuarioRepository, this.enderecoRepository);
	}

	public ProdutoBo getInstanceProdutoBo() {
		return new ProdutoBo(this.produtoRepository);
	}

}
