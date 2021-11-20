package br.com.colheita.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.colheita.model.Usuario;
import br.com.colheita.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String cnpj) throws UsernameNotFoundException {
		Usuario usu = usuarioRepository.findByCnpj(cnpj);
		if (usu != null) {
			return usu;
		}
		
		throw new UsernameNotFoundException("Dados inválidos!");
	}
}
