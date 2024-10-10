package br.edu.cesarschool.next.poo.projetoreferencia.conta;

public class ContaPoupanca extends ContaCorrente {
	
	private double percentualBonus;

	public ContaPoupanca() {
		//super(); // confirnar na aula, esse super não precisa pq o outro já chama ele, e é obrigatório que
		//            que pelo menos um do construtor do filho chame o do pai.
	}

	public ContaPoupanca(int agencia, String numero, double saldo, String nomeCorrentista, double percentualBonus) {
		super(agencia, numero, saldo, nomeCorrentista);
		this.percentualBonus = percentualBonus;
	}

	public double getPercentualBonus() {
		return percentualBonus;
	}

	public void setPercentualBonus(double percentualBonus) {
		this.percentualBonus = percentualBonus;
	}

@Override
void creditar(double valor) {
	valor *=(1+this.percentualBonus/100);
	super.creditar(valor);
}
	

}
