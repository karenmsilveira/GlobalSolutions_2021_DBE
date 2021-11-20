package br.com.colheita.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.colheita.model.Produto;
import br.com.colheita.model.Usuario;

public interface ProdutoRepository extends JpaRepository <Produto, Integer>{
	Produto findByIdProduto (int idProduto);

	List<Produto> findByUsuario(Usuario usu);

}
