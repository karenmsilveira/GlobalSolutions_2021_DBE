package br.com.colheita.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.colheita.model.Produto;
import br.com.colheita.model.Usuario;
import br.com.colheita.repository.EnderecoRepository;
import br.com.colheita.repository.ProdutoRepository;
import br.com.colheita.repository.UsuarioRepository;
import br.com.colheita.security.TokenService;

@Controller
public class AdminController {
	
	final String paginaDeslogado = "/paginas/index";
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	TokenService tokenService;
	
	
	public boolean validaUsuarioLogado() {
		try {
			Object usu = session.getAttribute("idUsuario");
			Integer idUsuario = Integer.parseInt(usu.toString());
			
			if(idUsuario != null) {
				return true;
			}
		}catch(NullPointerException e) {}
		return false;
		
	}
	

	@RequestMapping(value="/",method=RequestMethod.GET)
	public String inicio() {
		
			return "/paginas/index";
		
	}
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public String home(Model model) {
			
			if (this.validaUsuarioLogado()) {
				Integer idUsuario = Integer.parseInt(session.getAttribute("idUsuario").toString());
				Usuario usu = this.usuarioRepository.findByIdUsuario(idUsuario);
				model.addAttribute("usuario", usu);
				return "paginas/home";
			}else {
				return paginaDeslogado;
			}
			
	}
	@RequestMapping(value="/cadastro",method=RequestMethod.GET)
	public String cadastro() {
					
			return "paginas/cadastro";
	}
	@RequestMapping(value="/quem-somos",method=RequestMethod.GET)
	public String quemSomos() {
					
			return "paginas/quem-somos";
	}
	
	@RequestMapping(value="/produtos",method=RequestMethod.GET)
	public String produto() {
					
			return "paginas/produtos";
	}
	@RequestMapping(value="/cadastro-produto",method=RequestMethod.GET)
	public String cadastroProduto(Model model) {
			
			if (this.validaUsuarioLogado()) {
				Integer idUsuario = Integer.parseInt(session.getAttribute("idUsuario").toString());
				Usuario usu = this.usuarioRepository.findByIdUsuario(idUsuario);
				model.addAttribute("usuario", usu);
				return "paginas/cadastro-produto";
			}else {
				return paginaDeslogado;
			}
			
	}
	
	@RequestMapping(value="/listar-produtos",method=RequestMethod.GET)
	public String ListarProdutos(Model model) {
			
		if (this.validaUsuarioLogado()) {
			Integer idUsuario = Integer.parseInt(session.getAttribute("idUsuario").toString());
			Usuario usu = this.usuarioRepository.findByIdUsuario(idUsuario);
			List<Produto> lst = this.produtoRepository.findByUsuario(usu);
			model.addAttribute("lst", lst);
			
			return "paginas/listar-produtos";
			}else {
				return paginaDeslogado;
			}
			
	}
	@RequestMapping(value="/editar-produto/{idProduto}",method=RequestMethod.GET)
	public String editarProduto(@PathVariable(name = "idProduto") Integer idProduto, Model model) {
			
			if (this.validaUsuarioLogado()) {
				Produto prod = produtoRepository.findByIdProduto(idProduto);
				model.addAttribute("produto", prod);
				
				return "paginas/editar-produto";
			}else {
				return paginaDeslogado;
			}
			
	}
	@RequestMapping(value="/deletar-produto/{idProduto}",method=RequestMethod.GET)
	public String deletarProduto(@PathVariable(name = "idProduto") Integer idProduto, Model model) {
			
			if (this.validaUsuarioLogado()) {
				Produto prod = produtoRepository.findByIdProduto(idProduto);
				
				produtoRepository.delete(prod);
				
				Integer idUsuario = Integer.parseInt(session.getAttribute("idUsuario").toString());
				Usuario usu = this.usuarioRepository.findByIdUsuario(idUsuario);
				List<Produto> lst = this.produtoRepository.findByUsuario(usu);
				model.addAttribute("lst", lst);
				
				
				
				return "redirect:/listar-produtos";
			}else {
				return paginaDeslogado;
			}
			
	}
}