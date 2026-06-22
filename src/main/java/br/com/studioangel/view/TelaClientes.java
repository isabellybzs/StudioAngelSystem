package br.com.studioangel.view;

import br.com.studioangel.model.Cliente;
import br.com.studioangel.service.ClienteService;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;

@Component
public class TelaClientes extends JFrame {

    private final ClienteService clienteService;
    private JTextField campoNome;
    private JTextField campoTelefone;
    private JTextField campoEmail;
    private JTable tabela;
    private DefaultTableModel modelo;
    private Long clienteSelecionadoId;

    public TelaClientes(ClienteService clienteService) {
        this.clienteService = clienteService;
        configurarTela();
    }

    public void abrir() {
        carregarTabela();
        setVisible(true);
    }

    private void configurarTela() {
        Estilo.aplicarJanela(this, "Clientes - Studio Angel");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        PrototipoInterno tela = new PrototipoInterno("Clientes");
        tela.add(tela.voltar(this));
        tela.habilitarNavegacao(this);
        tela.add(tela.titulo("Cadastrar\nCliente", 394, 85, 210, 62, 28));
        tela.add(tela.titulo("Lista de\nClientes", 987, 45, 210, 66, 30));

        JPanel form = Estilo.painelArredondado(58, Color.WHITE);
        form.setBounds(205, 170, 535, 535);
        form.setLayout(null);
        campoNome = campo(286, 231, 374, 53);
        campoTelefone = campo(286, 331, 374, 52);
        campoEmail = campo(286, 441, 374, 53);
        form.add(tela.label("Nome completo:", 97, 20, 310, 34, 25));
        form.add(campoLocal(campoNome, 80, 61));
        form.add(tela.label("Telefone| Celular:", 107, 119, 320, 34, 25));
        form.add(campoLocal(campoTelefone, 80, 161));
        form.add(tela.label("Email :", 107, 228, 200, 34, 25));
        form.add(campoLocal(campoEmail, 80, 271));
        JButton cadastrar = botaoLocal("Cadastrar", 58, 342, 200, 55);
        cadastrar.addActionListener(e -> salvar());
        JButton atualizar = botaoLocal("Atualizar", 274, 342, 200, 55);
        atualizar.addActionListener(e -> atualizar());
        JButton excluir = botaoLocal("Excluir", 58, 417, 200, 55);
        excluir.addActionListener(e -> excluir());
        JButton limpar = botaoLocal("Limpar", 274, 417, 200, 55);
        limpar.addActionListener(e -> limparCampos());
        form.add(cadastrar);
        form.add(atualizar);
        form.add(excluir);
        form.add(limpar);
        tela.add(form);

        JPanel lista = Estilo.painelArredondado(58, Color.WHITE);
        lista.setBounds(755, 140, 585, 583);
        lista.setLayout(null);
        modelo = new DefaultTableModel(new Object[]{"ID", "NOME", "TELEFONE", "E-MAIL"}, 0);
        tabela = new JTable(modelo);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tabela.setRowHeight(46);
        tabela.setShowVerticalLines(false);
        tabela.setShowHorizontalLines(false);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 25));
        tabela.getTableHeader().setForeground(Estilo.AZUL_CLARO);
        tabela.getTableHeader().setBackground(Estilo.AZUL_ESCURO);
        tabela.getSelectionModel().addListSelectionListener(e -> preencherCampos());
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

    private JTextField campoLocal(JTextField campo, int x, int y) {
        campo.setBounds(x, y, 374, 53);
        return campo;
    }

    private JButton botaoLocal(String texto, int x, int y, int w, int h) {
        JButton b = Estilo.botaoPrototipo(texto, x, y, w, h);
        b.setFont(new Font("Segoe UI", Font.BOLD, 25));
        return b;
    }

    private void salvar() {
        try {
            Cliente cliente = new Cliente(campoNome.getText(), campoTelefone.getText(), campoEmail.getText(), "");
            clienteService.salvar(cliente);
            carregarTabela();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente: " + ex.getMessage());
        }
    }

    private void atualizar() {
        if (clienteSelecionadoId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente na lista.");
            return;
        }
        try {
            Cliente cliente = clienteService.buscarPorId(clienteSelecionadoId);
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente nao encontrado.");
                return;
            }
            cliente.setNome(campoNome.getText());
            cliente.setTelefone(campoTelefone.getText());
            cliente.setEmail(campoEmail.getText());
            clienteService.salvar(cliente);
            carregarTabela();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente: " + ex.getMessage());
        }
    }

    private void excluir() {
        if (clienteSelecionadoId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente na lista.");
            return;
        }
        try {
            clienteService.excluir(clienteSelecionadoId);
            carregarTabela();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nao foi possivel excluir o cliente.");
        }
    }

    private void carregarTabela() {
        modelo.setRowCount(0);
        List<Cliente> clientes = clienteService.listarTodos();
        for (Cliente cliente : clientes) {
            modelo.addRow(new Object[]{cliente.getId(), cliente.getNome(), cliente.getTelefone(), cliente.getEmail()});
        }
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            clienteSelecionadoId = Long.valueOf(modelo.getValueAt(linha, 0).toString());
            campoNome.setText(String.valueOf(modelo.getValueAt(linha, 1)));
            campoTelefone.setText(String.valueOf(modelo.getValueAt(linha, 2)));
            campoEmail.setText(String.valueOf(modelo.getValueAt(linha, 3)));
        }
    }

    private void limparCampos() {
        clienteSelecionadoId = null;
        campoNome.setText("");
        campoTelefone.setText("");
        campoEmail.setText("");
        tabela.clearSelection();
    }
}
