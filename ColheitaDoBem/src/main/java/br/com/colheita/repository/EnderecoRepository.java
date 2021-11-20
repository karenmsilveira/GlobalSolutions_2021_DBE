package br.com.colheita.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.colheita.model.Endereco;

public interface EnderecoRepository extends JpaRepository <Endereco, Integer>{
	
	Endereco findByIdEndereco (int idEndereco);

}
