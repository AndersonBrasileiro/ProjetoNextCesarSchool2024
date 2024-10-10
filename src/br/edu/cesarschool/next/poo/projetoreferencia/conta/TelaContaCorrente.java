package br.edu.cesarschool.next.poo.projetoreferencia.conta;

import java.util.List;
import java.util.Scanner;

import br.edu.cesarschool.next.poo.projetoreferencia.produto.Produto;
import br.edu.cesarschool.next.poo.projetoreferencia.produto.ProdutoCestaBasica;
import br.edu.cesarschool.next.poo.projetoreferencia.produto.ProdutoMediator;
import br.edu.cesarschool.next.poo.projetoreferencia.utils.DateUtils;

public class TelaContaCorrente {
	
	public TelaContaCorrente() {
	}
	
	private static final String SIM = "S";
	private Scanner entrada = new Scanner(System.in);
	private MediatorContaCorrente mediator = new MediatorContaCorrente();
	
	
	
	public void iniciarTela() {
		while(true) {
			System.out.println("1- Incluir");
			System.out.println("2- Creditar conta");
			System.out.println("3- Debitar conta");
			System.out.println("4- Buscar conta");
			System.out.println("5- Relatório geral");
			System.out.println("6- Sair");
			System.out.println("Selecione a operação");
			int operacao = entrada.nextInt();
			if (operacao == 1) {
				rodarIncluir();
			} else if (operacao == 2) {
				creditar();
			} else if (operacao == 3) {
				debitar();
			} else if (operacao == 4) {
				buscar();
			} else if (operacao == 5) {
				gerarRelatorioGeral();
			} else if (operacao == 6) {
				rodarSair();
			} else {
				System.out.println("Operação inválida");
			}
		} 
	}
	
	private void rodarIncluir() {
		ContaCorrente conta = null;
		System.out.println("Digite a agência: ");
		int agencia = entrada.nextInt();
		System.out.println("Digite o numero da conta: ");
		String numero = entrada.next();
		System.out.println("Digite saldo da conta: ");
		double saldo = entrada.nextDouble();
		System.out.println("Digite o nome do correntista: ");
		String nome = entrada.next();		
		System.out.println("A conta é Poupança (S/N)?");
		String ePoupanca = entrada.next();		
		if (ePoupanca.equals(SIM)) {
			System.out.println("Digite o percentual de bonus:");
			double pBonus = entrada.nextDouble();
			conta = new ContaPoupanca(agencia, numero, saldo, nome, pBonus);
		} else {
			conta = new ContaCorrente(agencia, numero, saldo, nome);
		}
		String mensagem = mediator.incluir(conta);
		if (mensagem == null) {
			System.out.println("Conta incluida com sucesso!");
		} else {
			System.out.println(mensagem);
		}
	}
	
	private void creditar() {
		System.out.println("Digite a agencia: ");
		int agencia = entrada.nextInt();
		System.out.println("Digite o numero da conta: ");
		String conta = entrada.next();
		System.out.println("Digite o valor a creditar: ");
		int valor = entrada.nextInt();
		String mensagem = mediator.creditar(valor, agencia, conta); 
		if (mensagem == null) {
			System.out.println("Crédito realizado sucesso!");
		} else {
			System.out.println(mensagem);
		}	
	}
	
	private void debitar() {
		System.out.println("Digite a agencia: ");
		int agencia = entrada.nextInt();
		System.out.println("Digite o numero da conta: ");
		String conta = entrada.next();
		System.out.println("Digite o valor a debitar: ");
		int valor = entrada.nextInt();
		String mensagem = mediator.debitar(valor, agencia, conta); 
		if (mensagem == null) {
			System.out.println("Débito realizado sucesso!");
		} else {
			System.out.println(mensagem);
		}	
	}
	
	private void buscar() {
		System.out.println("Digite a agencia: ");
		int agencia = entrada.nextInt();
		System.out.println("Digite o numero da conta: ");
		String numero = entrada.next();
//		System.out.println("Digite o valor a debitar: ");
//		int valor = entrada.nextInt();
		ContaCorrente conta = mediator.buscar(agencia, numero);
		if (conta == null) {
			System.out.println("Conta não encontrada");
		} else {
			imprimirAtributosConta(conta);
		}	
	}
	
	
	
	private void imprimirAtributosConta(ContaCorrente conta) {
		System.out.println("Agencia            : " + conta.getAgencia());
		System.out.println("Numero             : " + conta.getNumero());
		System.out.println("Titular            : " + conta.getNomeCorrentista());
		System.out.println("Saldo              : " + conta.getSaldo());
		if (conta instanceof ContaPoupanca) {
			ContaPoupanca cp = (ContaPoupanca)conta;
			System.out.println("Percentual Bonus   : " + cp.getPercentualBonus());
		}
		System.out.println("Incluído em      : " + 
				DateUtils.formatar(conta.getDhInclusao()));					
		System.out.println("Alterada em      : " + 
				DateUtils.formatar(conta.getDhUltimaAtualizacao()));
	}
	
	private void gerarRelatorioGeral() {
		List<ContaCorrente> contas = mediator.gerarRelatorioGeral();
		System.out.println("######## IN�CIO RELAT�RIO ##########");
		for (ContaCorrente conta : contas) {
			System.out.println("##### CONTA #####");
			imprimirAtributosConta(conta);
		}
		System.out.println("######## FIM RELAT�RIO ##########");
	}
	
	private void rodarSair() {
		System.out.println("Bye bye!");
		System.exit(0);
	}


}
