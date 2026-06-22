package br.com.studioangel.view;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.springframework.stereotype.Component;

@Component
public class TelaMenu extends JFrame {

    public TelaMenu(TelaClientes telaClientes, TelaServicos telaServicos, TelaProfissionais telaProfissionais,
                    TelaAgendamentos telaAgendamentos, TelaRelatorios telaRelatorios, TelaSobre telaSobre) {
        AppNavigation.registrar(telaClientes, telaServicos, telaProfissionais, telaAgendamentos, telaRelatorios);
        Estilo.aplicarJanela(this, "Studio Angel - Sistema de Gerenciamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = Estilo.telaBase();
        painel.add(Estilo.logo(358, 31, 674, 170, true, 72));
        painel.add(textoCentro("Sistema de Gerenciamento", 0, 160, 1366, 30, 23, Font.PLAIN));

        JButton clientes = card("CLIENTES", "Cadastrar, consultar e\ngerenciar clientes", "clientes", 215, 245);
        JButton servicos = card("SERVICOS", "Cadastrar e gerenciar\nservicos oferecidos", "servicos", 585, 245);
        JButton profissionais = card("PROFISSIONAIS", "Cadastrar, consultar e\nadministrar equipe", "profissionais", 955, 245);
        JButton agendamentos = card("AGENDAMENTOS", "Agendar, consultar e\nadministrar horarios", "agendamentos", 400, 433);
        JButton financeiro = card("FINANCEIRO", "Resumo gerencial e\ncontrole de valores", "financeiro", 770, 433);

        clientes.addActionListener(e -> telaClientes.abrir());
        servicos.addActionListener(e -> telaServicos.abrir());
        profissionais.addActionListener(e -> telaProfissionais.abrir());
        agendamentos.addActionListener(e -> telaAgendamentos.abrir());
        financeiro.addActionListener(e -> telaRelatorios.abrir());

        painel.add(clientes);
        painel.add(servicos);
        painel.add(profissionais);
        painel.add(agendamentos);
        painel.add(financeiro);

        JButton sair = invisivel(1190, 20, 130, 60);
        sair.addActionListener(e -> {
            int opcao = JOptionPane.showConfirmDialog(this, "Deseja sair do sistema?");
            if (opcao == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        painel.add(sair);
        setContentPane(painel);
    }

    private JButton card(String titulo, String descricao, String iconeTipo, int x, int y) {
        JButton botao = Estilo.botaoPrototipo("", x, y, 330, 166);
        botao.setLayout(null);
        botao.add(Estilo.icone(iconeTipo, 20, 34, 94, 92));

        JLabel t = new JLabel(titulo);
        t.setBounds(125, 32, 190, 38);
        t.setFont(new Font("Segoe UI", Font.BOLD, titulo.length() > 11 ? 21 : 25));
        t.setForeground(Estilo.AZUL_CLARO);
        botao.add(t);

        JLabel d = new JLabel("<html>" + descricao.replace("\n", "<br>") + "</html>");
        d.setBounds(125, 72, 190, 70);
        d.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        d.setForeground(Estilo.AZUL_CLARO);
        botao.add(d);
        return botao;
    }

    private JLabel textoCentro(String texto, int x, int y, int w, int h, int tamanho, int estilo) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setBounds(x, y, w, h);
        label.setForeground(Estilo.AZUL_ESCURO);
        label.setFont(new Font("Segoe UI", estilo, tamanho));
        return label;
    }

    private JButton invisivel(int x, int y, int w, int h) {
        JButton b = new JButton();
        b.setBounds(x, y, w, h);
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        return b;
    }
}
