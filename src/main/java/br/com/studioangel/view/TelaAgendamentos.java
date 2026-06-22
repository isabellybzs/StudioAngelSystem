package br.com.studioangel.view;

import br.com.studioangel.model.Agendamento;
import br.com.studioangel.model.Cliente;
import br.com.studioangel.model.Profissional;
import br.com.studioangel.model.Servico;
import br.com.studioangel.model.StatusAgendamento;
import br.com.studioangel.service.AgendamentoService;
import br.com.studioangel.service.ClienteService;
import br.com.studioangel.service.ProfissionalService;
import br.com.studioangel.service.ServicoService;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;

@Component
public class TelaAgendamentos extends JFrame {

    private final AgendamentoService agendamentoService;
    private final ClienteService clienteService;
    private final ServicoService servicoService;
    private final ProfissionalService profissionalService;
    private JComboBox<Cliente> comboCliente;
    private JComboBox<Servico> comboServico;
    private JComboBox<Profissional> comboProfissional;
    private JComboBox<StatusAgendamento> comboStatus;
    private JComboBox<Cliente> filtroCliente;
    private JComboBox<Profissional> filtroProfissional;
    private JFormattedTextField campoData;
    private JFormattedTextField campoHorario;
    private JFormattedTextField filtroData;
    private JTable tabela;
    private DefaultTableModel modelo;
    private Long agendamentoSelecionadoId;
    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaAgendamentos(AgendamentoService agendamentoService, ClienteService clienteService,
                            ServicoService servicoService, ProfissionalService profissionalService) {
        this.agendamentoService = agendamentoService;
        this.clienteService = clienteService;
        this.servicoService = servicoService;
        this.profissionalService = profissionalService;
        configurarTela();
    }

    public void abrir() {
        carregarCombos();
        carregarTabela();
        setVisible(true);
    }

    private void configurarTela() {
        Estilo.aplicarJanela(this, "Agendamentos - Studio Angel");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        PrototipoInterno tela = new PrototipoInterno("Agendamentos");
        tela.add(tela.voltar(this));
        tela.habilitarNavegacao(this);
        tela.add(tela.titulo("Agendar\nnovo horario", 410, 112, 170, 44, 20));
        tela.add(tela.titulo("Agenda e\nconsultas", 965, 47, 250, 65, 28));

        JPanel form = Estilo.painelArredondado(58, Color.WHITE);
        form.setBounds(205, 170, 535, 535);
        form.setLayout(null);
        comboCliente = new JComboBox<>();
        comboServico = new JComboBox<>();
        comboProfissional = new JComboBox<>();
        comboStatus = new JComboBox<>(StatusAgendamento.values());
        campoData = Estilo.campoMascaraPrototipo(62, 222, 376, 44, "##/##/####");
        campoHorario = Estilo.campoMascaraPrototipo(62, 287, 376, 44, "##:##");

        form.add(tela.label("Cliente:", 64, 0, 250, 30, 23));
        form.add(combo(62, 29, comboCliente));
        form.add(tela.label("Servico:", 72, 65, 250, 30, 23));
        form.add(combo(62, 94, comboServico));
        form.add(tela.label("Profissional:", 78, 130, 250, 30, 23));
        form.add(combo(62, 159, comboProfissional));
        form.add(tela.label("Data:", 78, 195, 250, 30, 23));
        form.add(campoData);
        form.add(tela.label("Horario:", 78, 260, 250, 30, 23));
        form.add(campoHorario);
        form.add(tela.label("Status:", 78, 325, 250, 30, 23));
        form.add(combo(62, 354, comboStatus));

        JButton cadastrar = botao("Cadastrar", 58, 420, 200, 48);
        cadastrar.addActionListener(e -> salvar());
        JButton atualizar = botao("Atualizar", 274, 420, 200, 48);
        atualizar.addActionListener(e -> atualizar());
        JButton excluir = botao("Excluir", 58, 477, 200, 48);
        excluir.addActionListener(e -> excluir());
        JButton limpar = botao("Limpar", 274, 477, 200, 48);
        limpar.addActionListener(e -> limparCampos());
        form.add(cadastrar);
        form.add(atualizar);
        form.add(excluir);
        form.add(limpar);
        tela.add(form);

        JPanel lista = Estilo.painelArredondado(58, Color.WHITE);
        lista.setBounds(755, 140, 585, 583);
        lista.setLayout(null);
        filtroData = Estilo.campoMascaraPrototipo(36, 28, 150, 42, "##/##/####");
        filtroCliente = new JComboBox<>();
        filtroProfissional = new JComboBox<>();
        lista.add(tela.label("Data:", 36, 0, 90, 26, 18));
        lista.add(filtroData);
        lista.add(tela.label("Cliente:", 205, 0, 110, 26, 18));
        lista.add(comboFiltro(205, 28, filtroCliente, 150));
        lista.add(tela.label("Profissional:", 374, 0, 145, 26, 18));
        lista.add(comboFiltro(374, 28, filtroProfissional, 165));

        JButton porData = botaoFiltro("Data", 30, 85);
        porData.addActionListener(e -> filtrarData(false));
        JButton porSemana = botaoFiltro("Semana", 112, 85);
        porSemana.addActionListener(e -> filtrarData(true));
        JButton porCliente = botaoFiltro("Cliente", 212, 85);
        porCliente.addActionListener(e -> carregarTabela(agendamentoService.listarPorCliente((Cliente) filtroCliente.getSelectedItem())));
        JButton porProfissional = botaoFiltro("Prof.", 312, 85);
        porProfissional.addActionListener(e -> carregarTabela(agendamentoService.listarPorProfissional((Profissional) filtroProfissional.getSelectedItem())));
        JButton todos = botaoFiltro("Todos", 412, 85);
        todos.addActionListener(e -> carregarTabela());
        lista.add(porData);
        lista.add(porSemana);
        lista.add(porCliente);
        lista.add(porProfissional);
        lista.add(todos);

        modelo = new DefaultTableModel(new Object[]{"ID", "DATA", "HORA", "CLIENTE", "PROF.", "STATUS"}, 0);
        tabela = new JTable(modelo);
        Estilo.aplicarTabelaPrototipo(tabela, 42, 18);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherCampos();
            }
        });
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(0, 142, 585, 370);
        lista.add(scroll);
        tela.add(lista);
        setContentPane(tela);
    }

    private JComboBox<?> combo(int x, int y, JComboBox<?> combo) {
        combo.setBounds(x, y, 376, 44);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        combo.setBackground(Estilo.AZUL_ESCURO);
        combo.setForeground(Color.WHITE);
        return combo;
    }

    private JComboBox<?> comboFiltro(int x, int y, JComboBox<?> combo, int largura) {
        combo.setBounds(x, y, largura, 42);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(Estilo.AZUL_ESCURO);
        combo.setForeground(Color.WHITE);
        return combo;
    }

    private JButton botao(String texto, int x, int y, int w, int h) {
        JButton b = Estilo.botaoPrototipo(texto, x, y, w, h);
        b.setFont(new Font("Segoe UI", Font.BOLD, 24));
        return b;
    }

    private JButton botaoFiltro(String texto, int x, int y) {
        JButton b = Estilo.botaoPrototipo(texto, x, y, 85, 38);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return b;
    }

    private void salvar() {
        persistir(null);
    }

    private void atualizar() {
        if (agendamentoSelecionadoId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento na lista.");
            return;
        }
        persistir(agendamentoSelecionadoId);
    }

    private void persistir(Long id) {
        try {
            Cliente cliente = (Cliente) comboCliente.getSelectedItem();
            Servico servico = (Servico) comboServico.getSelectedItem();
            Profissional profissional = (Profissional) comboProfissional.getSelectedItem();
            LocalDate data = LocalDate.parse(campoData.getText(), formatoData);
            LocalTime horario = LocalTime.parse(campoHorario.getText());

            if (agendamentoService.existeConflito(profissional, data, horario, id)) {
                JOptionPane.showMessageDialog(this, "Conflito de horario: este profissional ja tem agendamento nesse horario.");
                return;
            }

            Agendamento agendamento = id == null ? new Agendamento() : agendamentoService.buscarPorId(id);
            if (agendamento == null) {
                JOptionPane.showMessageDialog(this, "Agendamento nao encontrado.");
                return;
            }
            agendamento.setCliente(cliente);
            agendamento.setServico(servico);
            agendamento.setProfissional(profissional);
            agendamento.setData(data);
            agendamento.setHorario(horario);
            agendamento.setStatus((StatusAgendamento) comboStatus.getSelectedItem());
            agendamento.setObservacao("");
            agendamentoService.salvar(agendamento);
            carregarTabela();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar agendamento. Confira data, horario e campos selecionados.");
        }
    }

    private void excluir() {
        if (agendamentoSelecionadoId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento na lista.");
            return;
        }
        try {
            agendamentoService.excluir(agendamentoSelecionadoId);
            carregarTabela();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nao foi possivel excluir o agendamento.");
        }
    }

    private void filtrarData(boolean semana) {
        try {
            LocalDate data = LocalDate.parse(filtroData.getText(), formatoData);
            carregarTabela(semana ? agendamentoService.listarPorSemana(data) : agendamentoService.listarPorData(data));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Informe a data no formato dd/mm/aaaa.");
        }
    }

    private void carregarCombos() {
        comboCliente.removeAllItems();
        comboServico.removeAllItems();
        comboProfissional.removeAllItems();
        filtroCliente.removeAllItems();
        filtroProfissional.removeAllItems();
        for (Cliente cliente : clienteService.listarTodos()) {
            comboCliente.addItem(cliente);
            filtroCliente.addItem(cliente);
        }
        for (Servico servico : servicoService.listarTodos()) {
            comboServico.addItem(servico);
        }
        for (Profissional profissional : profissionalService.listarTodos()) {
            comboProfissional.addItem(profissional);
            filtroProfissional.addItem(profissional);
        }
    }

    private void carregarTabela() {
        carregarTabela(agendamentoService.listarTodos());
    }

    private void carregarTabela(List<Agendamento> agendamentos) {
        modelo.setRowCount(0);
        for (Agendamento a : agendamentos) {
            modelo.addRow(new Object[]{
                    a.getId(),
                    a.getData() != null ? a.getData().format(formatoData) : "",
                    a.getHorario(),
                    a.getCliente() != null ? a.getCliente().getNome() : "",
                    a.getProfissional() != null ? a.getProfissional().getNome() : "",
                    a.getStatus()
            });
        }
    }

    private void limparCampos() {
        agendamentoSelecionadoId = null;
        campoData.setText("");
        campoHorario.setText("");
        comboStatus.setSelectedItem(StatusAgendamento.AGENDADO);
        tabela.clearSelection();
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            agendamentoSelecionadoId = Long.valueOf(modelo.getValueAt(linha, 0).toString());
            Agendamento agendamento = agendamentoService.buscarPorId(agendamentoSelecionadoId);
            if (agendamento == null) {
                return;
            }
            selecionarCliente(agendamento.getCliente());
            selecionarServico(agendamento.getServico());
            selecionarProfissional(agendamento.getProfissional());
            campoData.setText(agendamento.getData() != null ? agendamento.getData().format(formatoData) : "");
            campoHorario.setText(agendamento.getHorario() != null ? String.valueOf(agendamento.getHorario()) : "");
            comboStatus.setSelectedItem(agendamento.getStatus());
        }
    }

    private void selecionarCliente(Cliente cliente) {
        if (cliente == null) {
            return;
        }
        for (int i = 0; i < comboCliente.getItemCount(); i++) {
            Cliente item = comboCliente.getItemAt(i);
            if (item != null && item.getId().equals(cliente.getId())) {
                comboCliente.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selecionarServico(Servico servico) {
        if (servico == null) {
            return;
        }
        for (int i = 0; i < comboServico.getItemCount(); i++) {
            Servico item = comboServico.getItemAt(i);
            if (item != null && item.getId().equals(servico.getId())) {
                comboServico.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selecionarProfissional(Profissional profissional) {
        if (profissional == null) {
            return;
        }
        for (int i = 0; i < comboProfissional.getItemCount(); i++) {
            Profissional item = comboProfissional.getItemAt(i);
            if (item != null && item.getId().equals(profissional.getId())) {
                comboProfissional.setSelectedIndex(i);
                return;
            }
        }
    }
}
