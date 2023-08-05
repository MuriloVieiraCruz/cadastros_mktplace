package br.com.senai.view.config;

import javax.swing.SwingWorker;

import br.com.senai.view.categoria.ViewConsultaCategoria;
import br.com.senai.view.horario.ViewCadastroHorario;
import br.com.senai.view.restaurante.ViewConsultaRestaurante;

public class Worker extends SwingWorker<Void, Void>{
	
	private ViewCadastroHorario viewHorario;
	private ViewConsultaCategoria viewCategoria;
	private ViewConsultaRestaurante viewRestaurante;
	
	public Worker() {
		this.viewHorario = new ViewCadastroHorario();
		this.viewCategoria = new ViewConsultaCategoria();
		this.viewRestaurante = new ViewConsultaRestaurante();
	}

	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ViewCadastroHorario getViewCadastroHorario() {
		return this.viewHorario;
	}
	
	public ViewConsultaCategoria getViewConsultaCategoria() {
		return this.viewCategoria;
	}
	
	public ViewConsultaRestaurante getViewConsultaRestaurante() {
		return this.viewRestaurante;
	}
}
