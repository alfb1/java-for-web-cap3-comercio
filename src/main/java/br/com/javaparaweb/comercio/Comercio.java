package br.com.javaparaweb.comercio;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import br.com.javaparaweb.comercio.entidades.Categoria;
import br.com.javaparaweb.comercio.entidades.Cliente;
import br.com.javaparaweb.comercio.entidades.Empregado;
import br.com.javaparaweb.comercio.entidades.Endereco;
import br.com.javaparaweb.comercio.entidades.Pedido;
import br.com.javaparaweb.comercio.entidades.Produto;
import br.com.javaparaweb.comercio.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Comercio {
   private Session session = null;
   
   public Comercio(Session session) {
	   this.session = session;
   }
   
   private Produto criarProdutoFilmeHobbit() {
	   Categoria categoria = new Categoria("Filmes", "Categoria de Filmes");
	   session.save(categoria);
	   
	   Produto produto = new Produto();
	   produto.setDescricao("O Hobbit");
	   produto.setPreco(29.99f);
	   produto.setCategoria(categoria);
	   session.save(produto);
	   
	   return produto;
   }
   
   private Produto criarProdutoAlimentoMistoQuente() {
	   Categoria categoria = new Categoria("Alimento", "Categoria de Alimentos");
	   session.save(categoria);
	   
	   Produto produto = new Produto();
	   produto.setDescricao("Misto Quente");
	   produto.setPreco(1.59f);
	   produto.setCategoria(categoria);
	   session.save(produto);
	   
	   return produto;
   }
   
   private Produto criarProdutoLivroPeregrino() {
	   Categoria categoria = new Categoria("Livros", "Categoria de Livros");
	   session.save(categoria);
	   
	   Produto produto = new Produto();
	   produto.setDescricao("O Peregrino");
	   produto.setPreco(15.75f);
	   produto.setCategoria(categoria);
	   session.save(produto);
	   
	   return produto;
   }   
   
   private Cliente criarClienteBeltrano() {
	   Cliente cliente = new Cliente();
	   cliente.setNome("Beltrano");
	   
	   Endereco endereco = new Endereco();
	   endereco.setRua("Street Water fall, n 60");
	   endereco.setCidade("Blumenau");
	   endereco.setCliente(cliente);
	   session.save(endereco);
	   
	   // why !?
	   cliente.setEndereco(endereco);
	   
	   //why did not save endereco object too ??
	   session.save(cliente);
	   
	   return cliente;
   }
   
   private Cliente criarClienteFulano() {
	   Cliente cliente = new Cliente();
	   cliente.setNome("Fulano");
	   
	   Endereco endereco = new Endereco();
	   endereco.setRua("Street Blue birds, n 80");
	   endereco.setCidade("Florianopolis");
	   endereco.setCliente(cliente);
	   session.save(endereco);
	   
	   // why !?
	   cliente.setEndereco(endereco);
	   
	   //why did not save endereco object too ??
	   session.save(cliente);
	   
	   return cliente;
   }
   
   public Empregado criarEmpregadoMelo() {
	   Empregado boss = new Empregado();
	   boss.setNome("Boss");
	   session.save(boss);
	   
	   Empregado worker1 = new Empregado();
	   worker1.setNome("Melo");
	   worker1.setChefe(boss);
	   session.save(worker1);
	   
	   return worker1;
   }
   
   public Empregado criarEmpregadoLuckow() {
	   Empregado worker1 = new Empregado();
	   worker1.setNome("Luckow");
	   session.save(worker1);
	   
	   return worker1;
   }
   
   public void criarPedidos() {
	   Produto filmeHobbit = criarProdutoFilmeHobbit();
	   Produto livroPeregrino = criarProdutoLivroPeregrino();
	   Produto alimentoMistoQuente = criarProdutoAlimentoMistoQuente();
	   
	   Empregado empregadoLuckow = criarEmpregadoLuckow();
	   Empregado empregadoMelo = criarEmpregadoMelo();
	   
	   Cliente clienteFulano = criarClienteFulano();
	   
	   Pedido pedido = new Pedido();
	   pedido.setCliente(clienteFulano);
	   pedido.setEmpregado(empregadoLuckow);
	   pedido.setDescricao("Pedido do sr Fulano");
	   pedido.setDataPedido(new Date(System.currentTimeMillis()));
	   pedido.setHoraPedido(new Time(System.currentTimeMillis()));
	   
	   Set<Produto> produtos = new HashSet<Produto>();
	   pedido.setProduto(produtos);
	   
	   produtos.add(filmeHobbit);
	   produtos.add(livroPeregrino);
	   
	   session.save(pedido);
	   
	   Cliente clienteBeltrano = criarClienteBeltrano();
	   
	   pedido = new Pedido();
	   pedido.setCliente(clienteBeltrano);
	   pedido.setEmpregado(empregadoMelo);
	   pedido.setDescricao("Pedido do sr Beltrano");
	   pedido.setDataPedido(new Date(System.currentTimeMillis()));
	   pedido.setHoraPedido(new Time(System.currentTimeMillis()));
	   
	   produtos = new HashSet<Produto>();
	   pedido.setProduto(produtos);
	   
	   produtos.add(filmeHobbit);
	   produtos.add(alimentoMistoQuente);
	   
	   session.save(pedido);
	   
   }
   
   public static void main(String[] args) {
	   Session session = HibernateUtil.getSessionFactory().openSession();
	   Comercio comercio = new Comercio(session);
	   
	   Transaction transaction = session.beginTransaction();
	   comercio.criarPedidos();
	   transaction.commit();
	   
	   System.out.println("Cadastrou !!");
   }
   
}
