package br.com.senai.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.senai.view.categoria.ViewConsultaCategoria;
import br.com.senai.view.horario.ViewCadastroHorario;
import br.com.senai.view.restaurante.ViewConsultaRestaurante;

public class ViewPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ViewPrincipal() {
		setResizable(false);
		setTitle("Tela Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		
		JMenuBar barraPrincipal = new JMenuBar();
		barraPrincipal.setBounds(0, 0, 784, 22);
		contentPane.add(barraPrincipal);
		
		JMenu menuCadastros = new JMenu("Cadastros");
		barraPrincipal.add(menuCadastros);
		
		JMenuItem opcaoCategorias = new JMenuItem("Categorias");		
		opcaoCategorias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
   
				ViewConsultaCategoria view = new ViewConsultaCategoria();
				view.setVisible(true);		
			}
		});
		menuCadastros.add(opcaoCategorias);
		
		JMenuItem opcaoRestaurantes = new JMenuItem("Restaurantes");
		opcaoRestaurantes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewConsultaRestaurante view = new ViewConsultaRestaurante();
				view.setVisible(true);
			}
		});
		menuCadastros.add(opcaoRestaurantes);
		
		JMenu menuConfiguracoes = new JMenu("Configurações");
		barraPrincipal.add(menuConfiguracoes);
		
		JMenuItem opcaoHorarios = new JMenuItem("Horários");		
		opcaoHorarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewCadastroHorario view = new ViewCadastroHorario();
				view.setVisible(true);
			}
		});
		menuConfiguracoes.add(opcaoHorarios);
		
		JMenuItem opcaoSair = new JMenuItem("Sair");
		opcaoSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		barraPrincipal.add(opcaoSair);
	}
}
