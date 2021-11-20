package br.com.colheita.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.colheita.dto.LoginDto;
import br.com.colheita.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.sf.json.JSONObject;

@Service
public class TokenService {
	@Value("${colheita.jwt.expiration}")
	private String expiration;
	
	@Value("${colheita.jwt.secret}")
	private String secret;

	@Value("${colheita.jwt.secretNavigation}")
	private String secretNavigation;
	
	@Value("${colheita.jwt.expirationNavigation}")
	private String expirationNavigation;
	
	
	public String gerarToken(Authentication authentication, LoginDto form) {
		Usuario logado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + (Long.parseLong(expiration)-(form.isConectado() ? 0 : 43200000)));
		
		return Jwts.builder()
				.setIssuer("Colheita System")
				.setSubject(logado.toJsonSimple().toString())
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public String gerarTokenNavegacao(String valor) {
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + (Long.parseLong(expirationNavigation)));
		
		return Jwts.builder()
				.setIssuer("Colheita System Navigation")
				.setSubject(valor)
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secretNavigation)
				.compact();
	}
	
	public String gerarTokenNavegacao(Integer valor) {
		String vl = String.valueOf(valor);
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + (Long.parseLong(expirationNavigation)));
		
		return Jwts.builder()
				.setIssuer("Colheita System Navigation")
				.setSubject(vl)
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secretNavigation)
				.compact();
	}
	
	public String getValueNavegacao(String token) throws Exception {
		Claims claims = Jwts.parser().setSigningKey(this.secretNavigation).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public JSONObject getIdDono(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		token = token.substring(7, token.length());
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return JSONObject.fromObject(claims.getSubject());
	}
	
	public Integer getIdDono(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		JSONObject json = JSONObject.fromObject(claims.getSubject());
		System.out.println(json);
		return json.getInt("id");
	}

}
