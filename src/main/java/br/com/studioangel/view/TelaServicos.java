package br.com.studioangel.view;

import br.com.studioangel.model.Servico;
import br.com.studioangel.service.ServicoService;
import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;

@Component
public class TelaServicos extends JFrame {

    private final ServicoService servicoService;
    private JTextField campoNome;
    private JTextField campoPreco;
    private JTextField campoDuracao;
    private JTable tabela;
    private DefaultTableModel modelo;
    private Long servicoSelecionadoId;

    public TelaServicos(ServicoService servicoService) {
        this.servicoService = servicoService;
        configurarTela();
    }

    public void abrir() {
        carregarTabela();
        setVisible(true);
    }

    private void configurarTela() {
        Estilo.aplicarJanela(this, "Servicos - Studio Angel");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        PrototipoInterno tela = new PrototipoInterno("Servicos");
        tela.add(tela.voltar(this));
        tela.habilitarNavegacao(this);
        tela.add(tela.titulo("CADASTRAR\nSERVICO", 386, 116, 200, 65, 29));
        tela.add(tela.titulo("LISTA DE\nSERVICOS", 1000, 55, 175, 62, 29));

        JPanel form = Estilo.painelArredondado(58, Color.WHITE);
        form.setBounds(205, 190, 535, 536);
        form.setLayout(null);
        form.add(tela.label("NOME DO SERVICO:", 72, 40, 310, 34, 25));
        campoNome = campo(52, 77, 376, 53);
        form.add(campoNome);
        form.add(tela.label("PRECO:", 79, 137, 220, 34, 25));
        campoPreco = campo(52, 178, 376, 53);
        form.add(campoPreco);
        form.add(tela.label("DURACAO:", 84, 242, 240, 34, 25));
        campoDuracao = campo(52, 286, 376, 53);
        form.add(campoDuracao);
        JButton limpar = botao("LIMPAR", 50, 415, 110, 55);
        limpar.addActionListener(e -> limparCampos());
        JButton cadastrar = botao("CADASTRAR", 166, 415, 120, 55);
        cadastrar.addActionListener(e -> salvar());
        JButton atualizar = botao("ATUALIZAR", 292, 415, 120, 55);
        atualizar.addActionListener(e -> atualizar());
        JButton excluir = botao("EXCLUIR", 418, 415, 100, 55);
        excluir.addActionListener(e -> excluir());
        form.add(limpar);
        form.add(cadastrar);
        form.add(atualizar);
        form.add(excluir);
        tela.add(form);

        JPanel lista = Estilo.painelArredondado(58, Color.WHITE);
        lista.setBounds(755, 139, 585, 583);
        lista.setLayout(null);
        modelo = new DefaultTableModel(new Object[]{"ID", "SERVICOS", "PRECO", "DURACAO"}, 0);
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
        b.setFont(new Font("Segoe UI", Font.BOLD, 23));
        return b;
    }

    private void salvar() {
        try {
            Servico servico = new Servico(campoNome.getText(), campoNome.getText(), lerPreco(), Integer.valueOf(campoDuracao.getText()));
            servicoService.salvar(servico);
            carregarTabela();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar servico. Confira preco e duracao.");
        }
    }

    private void atualizar() {
        if (servicoSelecionadoId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um servico na lista.");
            return;
        }
        try {
            Servico servico = servicoService.buscarPorId(servicoSelecionadoId);
            if (servico == null) {
                JOptionPane.showMessageDialog(this, "Servico nao encontrado.");
                return;
            }
            servico.setNome(campoNome.getText());
            servico.setDescricao(campoNome.getText());
            servico.setPreco(lerPreco());
            servico.setDuracaoMinutos(Integer.valueOf(campoDuracao.getText()));
            servicoService.salvar(servico);
            carregarTabela();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar servico. Confira preco e duracao.");
        }
    }

    private void excluir() {
        if (servicoSelecionadoId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um servico na lista.");
            return;
        }
        try {
            servicoService.excluir(servicoSelecionadoId);
            carregarTabela();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nao foi possivel excluir o servico.");
        }
    }

    private BigDecimal lerPreco() {
        return new BigDecimal(campoPreco.getText().replace(",", "."));
    }

    private void carregarTabela() {
        modelo.setRowCount(0);
        List<Servico> servicos = servicoService.listarTodos();
        for (Servico servico : servicos) {
            modelo.addRow(new Object[]{servico.getId(), servico.getNome(), servico.getPreco(), servico.getDuracaoMinutos() + " min"});
        }
    }

    private void limparCampos() {
        servicoSelecionadoId = null;
        campoNome.setText("");
        campoPreco.setText("");
        campoDuracao.setText("");
        tabela.clearSelection();
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            servicoSelecionadoId = Long.valueOf(modelo.getValueAt(linha, 0).toString());
            campoNome.setText(String.valueOf(modelo.getValueAt(linha, 1)));
            campoPreco.setText(String.valueOf(modelo.getValueAt(linha, 2)));
            String duracao = String.valueOf(modelo.getValueAt(linha, 3)).replace(" min", "");
            campoDuracao.setText(duracao);
        }
    }
}
