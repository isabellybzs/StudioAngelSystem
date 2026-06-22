package br.com.studioangel.view;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.springframework.stereotype.Component;

@Component
public class TelaInicial extends JFrame {

    private final TelaLogin telaLogin;

    public TelaInicial(TelaLogin telaLogin) {
        this.telaLogin = telaLogin;
        configurarTela();
    }

    private void configurarTela() {
        Estilo.aplicarJanela(this, "Studio Angel - Sistema de Gerenciamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new BorderLayout(10, 20));
        Estilo.aplicarPainel(painel);

        JLabel titulo = Estilo.titulo("Studio Angel");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel descricao = new JLabel("Sistema de Gerenciamento");
        descricao.setHorizontalAlignment(SwingConstants.CENTER);
        descricao.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        descricao.setForeground(Estilo.CINZA);

        JButton entrar = Estilo.botao("ENTRAR");
        entrar.addActionListener(e -> {
            setVisible(false);
            telaLogin.setVisible(true);
        });

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(descricao, BorderLayout.CENTER);
        painel.add(entrar, BorderLayout.SOUTH);
        add(painel);
    }
}
