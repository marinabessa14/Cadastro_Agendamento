package org.libertas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Agendamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int idagendamento;
	private String data;
	private String horario;
	private String nome;
	private String telefone;
	private double valor;
	
	public int getIdAagendamento() {
		return idagendamento;
	}
	public void setIdAgendamento(int idagendamento) {
		this.idagendamento = idagendamento;
	}
	public String getdata() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome= nome;
	}
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String toString() {
		return "Id: " + idagendamento
				+  " Data: " + data 
				+ " Horario: " + horario 
				+ " Nome: " + nome
				+ " Telefone: " + telefone
		        + " Valor: " + valor;
	}
	
}
