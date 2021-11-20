package br.com.colheita.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.com.colheita.model.Produto;
import br.com.colheita.repository.ProdutoRepository;
import net.sf.json.JSONObject;

public class ProdutoBo {
	
	private ProdutoRepository produtoRepository;
	
	public ProdutoBo(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	public Produto salvaProduto (Produto p) {
		return this.produtoRepository.save(p);
	}
	
	public Produto alteraProduto(JSONObject novoProduto) throws ParseException{
		Integer idProduto = novoProduto.getInt("idProduto");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Produto produtoAlterar = this.produtoRepository.findByIdProduto(idProduto);
		produtoAlterar.setCategoria(novoProduto.getString("categoria"));
		produtoAlterar.setDataValidade(sdf.parse(novoProduto.getString("dataValidade")));
		produtoAlterar.setNome(novoProduto.getString("nomeProduto"));
		produtoAlterar.setPrecoProduto(novoProduto.getDouble("precoProduto"));
		
		return this.produtoRepository.save(produtoAlterar);
	}

}
