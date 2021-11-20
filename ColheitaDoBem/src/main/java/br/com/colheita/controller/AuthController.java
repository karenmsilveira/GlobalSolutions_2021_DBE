package br.com.colheita.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.colheita.bo.UsuarioBo;
import br.com.colheita.dto.LoginDto;
import br.com.colheita.dto.TokenDto;
import br.com.colheita.repository.EnderecoRepository;
import br.com.colheita.repository.UsuarioRepository;
import br.com.colheita.security.TokenService;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private TokenService tokenService; 
	
	@CrossOrigin
	@RequestMapping(value="/autenticar",method=RequestMethod.POST)
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginDto form) {
		session.invalidate();
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication, form);
			System.out.println(token);
			UsuarioBo ubo = this.getInstanceUsuarioBo();
			JSONObject json = ubo.carregaUsuario(form);
			System.out.println(session.getId());
			session.setAttribute("nome", json.getString("nome"));
			session.setAttribute("idUsuario", json.getInt("idUsuario"));
			session.setAttribute("cnpj", json.getString("cnpj"));
			
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@CrossOrigin
	@RequestMapping(value="/paginas/index",method=RequestMethod.POST)
	public String logout() {
		session.invalidate();
		return "../";
	}

	public UsuarioBo getInstanceUsuarioBo() {
		return new UsuarioBo(this.usuarioRepository, this.enderecoRepository);
	}

}
