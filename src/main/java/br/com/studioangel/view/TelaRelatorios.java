package br.com.studioangel.view;

import br.com.studioangel.service.RelatorioService;
import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;

@Component
public class TelaRelatorios extends JFrame {

    private final RelatorioService relatorioService;
    private JComboBox<String> comboTipo;
    private JTextField campoDescricao;
    private JTextField campoValor;
    private JTable tabela;
    private DefaultTableModel modelo;
    private int linhaSelecionada = -1;

    public TelaRelatorios(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
        configurarTela();
    }

    public void abrir() {
        atualizarRelatorio();
        setVisible(true);
    }

    private void configurarTela() {
        Estilo.aplicarJanela(this, "Financeiro - Studio Angel");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        PrototipoInterno tela = new PrototipoInterno("Financeiro");
        tela.add(tela.voltar(this));
        tela.habilitarNavegacao(this);
        tela.add(tela.titulo("NOVA\nMOVIMENTACAO", 380, 108, 250, 62, 28));
        tela.add(tela.titulo("HISTORICO\nFINANCEIRO", 995, 60, 220, 64, 28));

        JPanel form = Estilo.painelArredondado(58, Color.WHITE);
        form.setBounds(207, 183, 536, 536);
        form.setLayout(null);
        form.add(tela.label("TIPO:", 60, 36, 190, 34, 31));
        comboTipo = new JComboBox<>(new String[]{"Receita", "Despesa", "Resumo", "Insight"});
        comboTipo.setBounds(40, 83, 365, 50);
        comboTipo.setFont(new Font("Segoe UI", Font.BOLD, 21));
        comboTipo.setBackground(Estilo.AZUL_ESCURO);
        comboTipo.setForeground(Color.WHITE);
        form.add(comboTipo);
        form.add(tela.label("DESCRICAO:", 58, 148, 250, 34, 30));
        campoDescricao = campo(38, 188, 374, 53);
        form.add(campoDescricao);
        form.add(tela.label("VALOR:", 56, 267, 190, 34, 25));
        campoValor = campo(38, 306, 374, 53);
        form.add(campoValor);
        JButton adicionar = botao("Adicionar", 58, 382, 200, 55);
        adicionar.addActionListener(e -> adicionarMovimento());
        JButton atualizar = botao("Atualizar", 274, 382, 200, 55);
        atualizar.addActionListener(e -> atualizarMovimento());
        JButton excluir = botao("Excluir", 58, 457, 200, 55);
        excluir.addActionListener(e -> excluirMovimento());
        JButton limpar = botao("Limpar", 274, 457, 200, 55);
        limpar.addActionListener(e -> limparCampos());
        form.add(adicionar);
        form.add(atualizar);
        form.add(excluir);
        form.add(limpar);
        tela.add(form);

        JPanel lista = Estilo.painelArredondado(58, Color.WHITE);
        lista.setBounds(755, 139, 585, 583);
        lista.setLayout(null);
        modelo = new DefaultTableModel(new Object[]{"TIPO", "DESCRICAO", "VALOR"}, 0);
        tabela = new JTable(modelo);
        Estilo.aplicarTabelaPrototipo(tabela, 46, 25);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherCampos();
            }
        });
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(0, 0, 585, 500);
        lista.add(scroll);
        tela.add(lista);
        setContentPane(tela);
    }

    private JTextField campo(int x, int y, int w, int h) {
        JTextField c = Estilo.campoPrototipo(x, y, w, h);
        c.setText("");
        return c;
    }

    private JButton botao(String texto, int x, int y, int w, int h) {
        JButton b = Estilo.botaoPrototipo(texto, x, y, w, h);
        b.setFont(new Font("Segoe UI", Font.BOLD, 25));
        return b;
    }

    private void atualizarRelatorio() {
        modelo.setRowCount(0);
        linhaSelecionada = -1;
        NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        modelo.addRow(new Object[]{"Resumo", "Total de agendamentos", relatorioService.totalAgendamentos()});
        modelo.addRow(new Object[]{"Resumo", "Agendados", relatorioService.totalPorStatus(br.com.studioangel.model.StatusAgendamento.AGENDADO)});
        modelo.addRow(new Object[]{"Resumo", "Confirmados", relatorioService.totalPorStatus(br.com.studioangel.model.StatusAgendamento.CONFIRMADO)});
        modelo.addRow(new Object[]{"Resumo", "Concluidos", relatorioService.totalPorStatus(br.com.studioangel.model.StatusAgendamento.CONCLUIDO)});
        modelo.addRow(new Object[]{"Resumo", "Cancelados", relatorioService.totalPorStatus(br.com.studioangel.model.StatusAgendamento.CANCELADO)});
        modelo.addRow(new Object[]{"Insight", "Servico mais agendado", relatorioService.servicoMaisAgendado()});
        modelo.addRow(new Object[]{"Insight", "Cliente mais frequente", relatorioService.clienteMaisFrequente()});
        modelo.addRow(new Object[]{"Insight", "Profissional com mais atendimentos", relatorioService.profissionalComMaisAtendimentos()});
        modelo.addRow(new Object[]{"Insight", "Horario mais ocupado", relatorioService.horarioMaisOcupado()});
        modelo.addRow(new Object[]{"Receita", "Servicos concluidos", moeda.format(relatorioService.valorTotalConcluido())});
    }

    private void adicionarMovimento() {
        modelo.addRow(new Object[]{comboTipo.getSelectedItem(), campoDescricao.getText(), campoValor.getText()});
        limparCampos();
    }

    private void atualizarMovimento() {
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma movimentacao na tabela.");
            return;
        }
        modelo.setValueAt(comboTipo.getSelectedItem(), linhaSelecionada, 0);
        modelo.setValueAt(campoDescricao.getText(), linhaSelecionada, 1);
        modelo.setValueAt(campoValor.getText(), linhaSelecionada, 2);
        limparCampos();
    }

    private void excluirMovimento() {
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma movimentacao na tabela.");
            return;
        }
        modelo.removeRow(linhaSelecionada);
        limparCampos();
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            linhaSelecionada = linha;
            comboTipo.setSelectedItem(String.valueOf(modelo.getValueAt(linha, 0)));
            campoDescricao.setText(String.valueOf(modelo.getValueAt(linha, 1)));
            campoValor.setText(String.valueOf(modelo.getValueAt(linha, 2)));
        }
    }

    private void limparCampos() {
        linhaSelecionada = -1;
        comboTipo.setSelectedIndex(0);
        campoDescricao.setText("");
        campoValor.setText("");
        if (tabela != null) {
            tabela.clearSelection();
        }
    }
}
