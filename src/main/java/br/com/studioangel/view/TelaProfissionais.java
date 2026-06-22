package br.com.studioangel.view;

import br.com.studioangel.model.Profissional;
import br.com.studioangel.service.ProfissionalService;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
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
public class TelaProfissionais extends JFrame {

    private final ProfissionalService profissionalService;
    private JTextField campoNome;
    private JTextField campoEspecialidade;
    private JComboBox<String> comboStatus;
    private JTable tabela;
    private DefaultTableModel modelo;
    private Long profissionalSelecionadoId;

    public TelaProfissionais(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
        configurarTela();
    }

    public void abrir() {
        carregarTabela();
        setVisible(true);
    }

    private void configurarTela() {
        Estilo.aplicarJanela(this, "Profissionais - Studio Angel");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        PrototipoInterno tela = new PrototipoInterno("Profissionais");
        tela.add(tela.voltar(this));
        tela.habilitarNavegacao(this);
        tela.add(tela.titulo("CADASTRAR\nPROFISSIONAL", 360, 114, 280, 65, 29));
        tela.add(tela.titulo("LISTA DE\nPROFISSIONAIS", 960, 55, 255, 62, 29));

        JPanel form = Estilo.painelArredondado(58, Color.WHITE);
        form.setBounds(205, 190, 535, 536);
        form.setLayout(null);
        form.add(tela.label("NOME:", 72, 40, 310, 34, 25));
        campoNome = campo(52, 77, 376, 53);
        form.add(campoNome);
        form.add(tela.label("ESPECIALIDADE:", 72, 137, 310, 34, 25));
        campoEspecialidade = campo(52, 178, 376, 53);
        form.add(campoEspecialidade);
        form.add(tela.label("STATUS:", 72, 242, 240, 34, 25));
        comboStatus = new JComboBox<>(new String[]{"ATIVO", "INATIVO"});
        comboStatus.setBounds(52, 286, 376, 53);
        comboStatus.setFont(new Font("Segoe UI", Font.BOLD, 18));
        comboStatus.setBackground(Estilo.AZUL_ESCURO);
        comboStatus.setForeground(Color.WHITE);
        form.add(comboStatus);

        JButton cadastrar = botao("Cadastrar", 58, 342, 200, 55);
        cadastrar.addActionListener(e -> salvar());
        JButton atualizar = botao("Atualizar", 274, 342, 200, 55);
        atualizar.addActionListener(e -> atualizar());
        JButton excluir = botao("Excluir", 58, 417, 200, 55);
        excluir.addActionListener(e -> excluir());
        JButton limpar = botao("Limpar", 274, 417, 200, 55);
        limpar.addActionListener(e -> limparCampos());
        form.add(cadastrar);
        form.add(atualizar);
        form.add(excluir);
        form.add(limpar);
        tela.add(form);

        JPanel lista = Estilo.painelArredondado(58, Color.WHITE);
        lista.setBounds(755, 139, 585, 583);
        lista.setLayout(null);
        modelo = new DefaultTableModel(new Object[]{"ID", "NOME", "ESPECIALIDADE", "STATUS"}, 0);
        tabela = new JTable(modelo);
        Estilo.aplicarTabelaPrototipo(tabela, 46, 22);
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

    private void salvar() {
        if (campoNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome do profissional.");
            return;
        }
        Profissional profissional = new Profissional(campoNome.getText(), campoEspecialidade.getText(),
                comboStatus.getSelectedIndex() == 0);
        profissionalService.salvar(profissional);
        carregarTabela();
        limparCampos();
    }

    private void atualizar() {
        if (profissionalSelecionadoId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um profissional na lista.");
            return;
        }
        Profissional profissional = profissionalService.buscarPorId(profissionalSelecionadoId);
        if (profissional == null) {
            JOptionPane.showMessageDialog(this, "Profissional nao encontrado.");
            return;
        }
        profissional.setNome(campoNome.getText());
        profissional.setEspecialidade(campoEspecialidade.getText());
        profissional.setAtivo(comboStatus.getSelectedIndex() == 0);
        profissionalService.salvar(profissional);
        carregarTabela();
        limparCampos();
    }

    private void excluir() {
        if (profissionalSelecionadoId == null) {
            JOptionPane.showMessageDialog(this, "Selecione um profissional na lista.");
            return;
        }
        try {
            profissionalService.excluir(profissionalSelecionadoId);
            carregarTabela();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nao foi possivel excluir o profissional.");
        }
    }

    private void carregarTabela() {
        modelo.setRowCount(0);
        List<Profissional> profissionais = profissionalService.listarTodos();
        for (Profissional profissional : profissionais) {
            modelo.addRow(new Object[]{
                    profissional.getId(),
                    profissional.getNome(),
                    profissional.getEspecialidade(),
                    Boolean.TRUE.equals(profissional.getAtivo()) ? "ATIVO" : "INATIVO"
            });
        }
    }

    private void limparCampos() {
        profissionalSelecionadoId = null;
        campoNome.setText("");
        campoEspecialidade.setText("");
        comboStatus.setSelectedIndex(0);
        tabela.clearSelection();
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            profissionalSelecionadoId = Long.valueOf(modelo.getValueAt(linha, 0).toString());
            campoNome.setText(String.valueOf(modelo.getValueAt(linha, 1)));
            campoEspecialidade.setText(String.valueOf(modelo.getValueAt(linha, 2)));
            comboStatus.setSelectedItem(String.valueOf(modelo.getValueAt(linha, 3)));
        }
    }
}
