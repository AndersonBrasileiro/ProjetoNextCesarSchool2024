package br.edu.cesarschool.next.poo.projetoreferencia.conta;

import br.edu.cesarschool.next.poo.projetoreferencia.utils.Registro;

public class ContaCorrente extends Registro{

	private int agencia;
	private String numero;
	private double saldo;
	private String nomeCorrentista;
	
	public ContaCorrente() {
		//super(); // confirnar na aula, esse super não precisa pq o outro já chama ele, e é obrigatório que
		//            que pelo menos um do construtor do filho chame o do pai.
	}

	public ContaCorrente(int agencia, String numero, double saldo, String nomeCorrentista) {
		super();
		this.agencia = agencia;
		this.numero = numero;
		this.saldo = saldo;
		this.nomeCorrentista = nomeCorrentista;
	}
	
	

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public double getSaldo() {
		return saldo;
	}

//	public void setSaldo(double saldo) {
//		this.saldo = saldo;
//	}

	public String getNomeCorrentista() {
		return nomeCorrentista;
	}

	public void setNomeCorrentista(String nomeCorrentista) {
		this.nomeCorrentista = nomeCorrentista;
	}
	
	void creditar(double valor) {
		this.saldo+=valor;
	}
	
	void debitar(double valor) {
		this.saldo-=valor;
	}
	
	public static String obterChave(int agencia, String numero) {
		return String.format("%03d",agencia)+numero;
	}

	@Override
	public String getIdUnico() {
		return obterChave(agencia, numero);	
	}
	
}
