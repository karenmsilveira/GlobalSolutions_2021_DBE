package br.com.colheita.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.colheita.model.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario,Integer>{
	Usuario findByIdUsuario (int idUsuario);
	Usuario findByCnpj (String cnpj);

}
