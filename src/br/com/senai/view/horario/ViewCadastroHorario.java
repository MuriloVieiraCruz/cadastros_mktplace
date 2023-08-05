package br.com.senai.view.horario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import br.com.senai.core.domain.Horario;
import br.com.senai.core.domain.Restaurante;
import br.com.senai.core.service.HorarioService;
import br.com.senai.core.service.RestauranteService;
import br.com.senai.view.componentes.table.HorarioTableModel;

public class ViewCadastroHorario extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableHorario;
	private RestauranteService retauranteService;
	private HorarioService horarioService;
	private JComboBox<String> cbDiaDaSemana;
	private JComboBox<Restaurante> cbRestaurante;
	private Horario horario;
	private JFormattedTextField txtAbertura;
	private JFormattedTextField txtFechamento;

	public void carregarComboRestaurante() {
		cbRestaurante = new JComboBox<Restaurante>();
		cbRestaurante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Restaurante restauranteInformado = (Restaurante) cbRestaurante.getSelectedItem();
				try {
					if (restauranteInformado != null) {

						mostrarLista(restauranteInformado);
						limparCampos();

					} else {

						HorarioTableModel model = new HorarioTableModel(new ArrayList<Horario>());
						tableHorario.setModel(model);
					}
				} catch (Exception ex) {
					throw new IllegalArgumentException(
							"Ocorreu um erro na listagem dos horarios. Motivo:" + ex.getMessage());
				}
			}
		});
		cbRestaurante.addItem(null);
		List<Restaurante> restaurantes = retauranteService.listarTodas();
		for (Restaurante ca : restaurantes) {
			cbRestaurante.addItem(ca);
		}
	}

	private void setHorario(Horario horario) {
		this.horario = horario;
		this.txtAbertura.setText(horario.getHoraAbertura().toString());
		this.txtFechamento.setText(horario.getHoraFechamento().toString());
		this.cbDiaDaSemana.setSelectedItem(horario.getDiaDaSemana());
	}

	/**
	 * Create the frame.
	 */
	public ViewCadastroHorario() {
		this.horarioService = new HorarioService();
		this.retauranteService = new RestauranteService();
		HorarioTableModel model = new HorarioTableModel(new ArrayList<Horario>());
		this.tableHorario = new JTable(model);
		this.tableHorario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 673, 391);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRest = new JLabel("Restaurante");
		lblRest.setBounds(10, 27, 80, 14);
		contentPane.add(lblRest);
		
		carregarComboRestaurante();
		cbRestaurante.setBounds(100, 23, 547, 22);
		contentPane.add(cbRestaurante);
		
		JLabel lblDiaSemana = new JLabel("Dia da Semana");
		lblDiaSemana.setHorizontalAlignment(SwingConstants.LEFT);
		lblDiaSemana.setBounds(10, 80, 90, 14);
		contentPane.add(lblDiaSemana);
		
		carregarComboDiaSemana();
		cbDiaDaSemana.setBounds(100, 76, 111, 22);
		contentPane.add(cbDiaDaSemana);
		
		JLabel lblAbertura = new JLabel("Abertura");
		lblAbertura.setHorizontalAlignment(SwingConstants.LEFT);
		lblAbertura.setBounds(221, 80, 52, 14);
		contentPane.add(lblAbertura);
		
		JLabel lblFechamento = new JLabel("Fechamento");
		lblFechamento.setHorizontalAlignment(SwingConstants.LEFT);
		lblFechamento.setBounds(372, 80, 72, 14);
		contentPane.add(lblFechamento);
		
		try {
			MaskFormatter maskFormatterAbre = new MaskFormatter("##:##");
			txtAbertura = new JFormattedTextField(maskFormatterAbre);
			txtAbertura.setBounds(282, 77, 80, 20);
			contentPane.add(txtAbertura);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			MaskFormatter maskFormatterFecha = new MaskFormatter("##:##");
			txtFechamento = new JFormattedTextField(maskFormatterFecha);
			txtFechamento.setBounds(454, 77, 83, 20);
			contentPane.add(txtFechamento);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JButton btnAdic = new JButton("Adicionar");
		btnAdic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {	
					
					DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
					
					LocalTime horaInicial = LocalTime.parse(txtAbertura.getText().formatted(horaFormatter)); 
					LocalTime horaFinal = LocalTime.parse(txtFechamento.getText().formatted(horaFormatter));
					String diaDaSemana = (String) cbDiaDaSemana.getSelectedItem();
					Restaurante restaurante = (Restaurante) cbRestaurante.getSelectedItem();
	
					if (horario == null) {
						horario = new Horario(diaDaSemana, horaInicial, horaFinal, restaurante);
						horarioService.salvar(horario);
						JOptionPane.showMessageDialog(contentPane, "Horário inserido com sucesso!");
						limparCampos();
						mostrarLista(restaurante);
						horario = null;

					} else {
						horario.setDiaDaSemana(diaDaSemana);
						horario.setHoraAbertura(horaInicial);
						horario.setHoraFechamento(horaFinal);
						horario.setRestaurante(restaurante);
						horarioService.salvar(horario);
						mostrarLista(restaurante);
						
						JOptionPane.showMessageDialog(contentPane, "Horário alterado com sucesso!");
					}
				} catch (DateTimeParseException e2) {
					JOptionPane.showMessageDialog(contentPane, "O horário precisa estar dentro do formato 24h");
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(contentPane, e3.getMessage());
				}
			}
		});
		btnAdic.setBounds(558, 76, 89, 23);
		contentPane.add(btnAdic);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {
		limparCampos();
			}
		});
		btnCancelar.setBounds(499, 319, 101, 22);
		contentPane.add(btnCancelar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Ações", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(454, 145, 179, 110);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int linhaSelecionada = tableHorario.getSelectedRow();
				
				if (linhaSelecionada >= 0) {
					
					HorarioTableModel model = (HorarioTableModel) tableHorario.getModel();
					Horario horarioSelecionado = model.getPorLinha(linhaSelecionada);
					setHorario(horarioSelecionado);
					
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para edição");
				}
			}
		});
		btnEditar.setBounds(10, 20, 159, 34);
		panel.add(btnEditar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableHorario.getSelectedRow();
				HorarioTableModel model = (HorarioTableModel) tableHorario.getModel();
				if (linhaSelecionada >= 0 && !model.isVazio()) {
					int opcao = JOptionPane.showConfirmDialog(contentPane, "Deseja realmente remover!?", "Remoção",
							JOptionPane.YES_NO_OPTION);
					if (opcao == 0) {

						Horario horarioSelecionado = model.getPorLinha(linhaSelecionada);
						try {
							horarioService.removerPor(horarioSelecionado.getId());
							model.removerPor(linhaSelecionada);
							mostrarLista(horarioSelecionado.getRestaurante());
							JOptionPane.showMessageDialog(contentPane, "Horário removido com sucesso!");
						} catch (IndexOutOfBoundsException iobe) {
							JOptionPane.showMessageDialog(contentPane, iobe.getMessage());
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(contentPane, ex.getMessage());
						}
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para remoção.");
				}
			}
		});
		btnExcluir.setBounds(10, 65, 159, 34);
		panel.add(btnExcluir);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 138, 415, 182);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(tableHorario);
		
		JLabel lblHor = new JLabel("Horários");
		lblHor.setBounds(10, 117, 52, 14);
		contentPane.add(lblHor);
		
	}

	public void carregarComboDiaSemana() {
		cbDiaDaSemana = new JComboBox<>();
		cbDiaDaSemana.addItem(null);

		String[] diasDaSemana = { "DOMINGO", "SEGUNDA", "TERCA", "QUARTA", "QUINTA", "SEXTA", "SABADO" };
		for (String dia : diasDaSemana) {
			cbDiaDaSemana.addItem(dia);
		}
	}

	private void limparCampos() {
		txtAbertura.setText("");
		txtFechamento.setText("");
		cbDiaDaSemana.setSelectedIndex(0);
	}

	private void mostrarLista(Restaurante restauranteInformado) {
		List<Horario> horariosEncontrados = horarioService.listarPorId(restauranteInformado.getId());

		HorarioTableModel model = new HorarioTableModel(horariosEncontrados);
		tableHorario.setModel(model);
	}
}
