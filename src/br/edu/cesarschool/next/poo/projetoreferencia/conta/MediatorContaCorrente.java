package br.edu.cesarschool.next.poo.projetoreferencia.conta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.cesarschool.next.poo.projetoreferencia.produto.ComparadorProdutoNome;
import br.edu.cesarschool.next.poo.projetoreferencia.produto.Produto;
import br.edu.cesarschool.next.poo.projetoreferencia.produto.ProdutoCestaBasica;
import br.edu.cesarschool.next.poo.projetoreferencia.utils.DAOGenerico;
import br.edu.cesarschool.next.poo.projetoreferencia.utils.Registro;
import br.edu.cesarschool.next.poo.projetoreferencia.utils.StringUtils;

public class MediatorContaCorrente {
	
	private DAOGenerico dao =  new DAOGenerico(ContaCorrente.class);
		
	public MediatorContaCorrente() {
		
	}
	private String validarAgencia(int agencia) {
		if (agencia <= 0 || agencia > 999) {
			return "Código de Agência invalido";
		} 
		return null;
	}
	
	private String validarDadosCreditoDebito(double valor, int agencia, String numero) {
		if (valor < 0) {
			return "Valor inválido";
		}
		String msgAgencia = validarAgencia(agencia);
		if (msgAgencia != null) {
			return msgAgencia;
		}
		if (StringUtils.stringVazia(numero)) {
			return "Numero invalido!";
		}
		return null;
	}
	
	private String validar(ContaCorrente contaCorrente) {
		if (contaCorrente == null) {
			return "Conta não informada";
		}
		String msgAgencia = validarAgencia(contaCorrente.getAgencia());
		if (msgAgencia != null) {
			return msgAgencia;
		}
		
		if (StringUtils.stringVazia(contaCorrente.getNumero()) || 
				contaCorrente.getNumero().length()!=5 || 
				!(contaCorrente.getNumero().substring(0, 5).matches("[0-9]*"))) { 
			return "Numero da Conta invalido";
		}	
		if (contaCorrente.getSaldo() < 0.0 ) { 
			return "Saldo invalido";
		}			
		if (StringUtils.stringVazia(contaCorrente.getNomeCorrentista()) || 
				contaCorrente.getNomeCorrentista().length()>60) {  
			return "Nome invalido";
		}
		if (contaCorrente instanceof ContaPoupanca) {
			ContaPoupanca poupanca = (ContaPoupanca)contaCorrente;
			return validarPoupanca(poupanca);	
		}
		return null;
	}
	
	private String validarPoupanca(ContaPoupanca poupanca) {
		if (poupanca.getPercentualBonus()<0.0) { 
			return "Percentual bonus invalido"; 
		}
		return null;
	}
	
	public String incluir(ContaCorrente conta) {
		String mensagem = validar(conta);
		if (mensagem != null) {
			return mensagem;
		}
		if (!dao.incluir(conta)) {
			return "Codigo da conta ja existente";
		} else return null;
	}
	
	ContaCorrente buscar(int agencia, String numero) {
		return (ContaCorrente)dao.buscar(ContaCorrente.obterChave(agencia, numero));
	}
	
	public String creditar(double valor, int agencia, String numero) {
		String msg = validarDadosCreditoDebito(valor, agencia, numero);
		if (msg != null) {
			return msg;
		}
		ContaCorrente conta = buscar(agencia, numero);
		if (conta == null) {
			return "Conta inexistente";
		}
		conta.creditar(valor);
		if (!dao.alterar(conta)) {
			return "Conta inexistente";
		}		
		return null; 		
	}
	
	public String debitar(double valor, int agencia, String numero) {
		String msg = validarDadosCreditoDebito(valor, agencia, numero);
		if (msg != null) {
			return msg;
		}
		ContaCorrente conta = buscar(agencia, numero);
		if (conta == null) {
			return "Conta inexistente";
		}
		if (conta.getSaldo() < valor) {
			return "Saldo insuficiente para sacar esse valor";
		}
		conta.debitar(valor);
		if (!dao.alterar(conta)) {
			return "Conta inexistente";
		}		
		return null; 	
	}

	List<ContaCorrente> gerarRelatorioGeral(){
		List<ContaCorrente> contas = new ArrayList<ContaCorrente>();
		Registro[] arrayRegs = dao.buscarTodos();
		if (arrayRegs != null) {
			for (Registro registro : arrayRegs) {
				contas.add((ContaCorrente)registro);
			}
			Collections.sort(contas, new ComparadorContaCorrenteSaldo());
		}
		return contas;		
	}
	
	public String alterarConta(ContaCorrente conta) {
		String mensagem = validar(conta);
		if (mensagem != null) {
			return mensagem;
		}
		if (!dao.alterar(conta)) {
			return "Codigo da conta inexistente";
		}
		return null;
	}
	
	public String excluirConta(int agencia, String numero) {
		String id = ContaCorrente.obterChave(agencia, numero);
		if (!dao.excluir(id)) {
			return "Codigo da conta inexistente";
		}
		return null;		
	}	
}	

