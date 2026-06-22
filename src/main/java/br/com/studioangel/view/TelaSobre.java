package br.com.studioangel.view;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.springframework.stereotype.Component;

@Component
public class TelaSobre extends JFrame {

    public TelaSobre() {
        Estilo.aplicarJanela(this, "Sobre - Studio Angel");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        Estilo.aplicarPainel(painel);

        JTextArea texto = new JTextArea();
        texto.setEditable(false);
        Estilo.aplicarAreaTexto(texto);
        texto.setText("Startup ficticia: AngelTech Solutions\n\n"
                + "Comercio atendido: Studio Angel\n\n"
                + "Integrantes: Isabelly e Larah\n\n"
                + "Objetivo do sistema:\n"
                + "Organizar clientes, servicos, profissionais e agendamentos do salao, facilitando o controle de horarios marcados e a consulta de informacoes gerenciais.\n\n"
                + "Tecnologias utilizadas:\n"
                + "Java, Spring Boot, Spring Data JPA, Banco H2, Java Swing e NetBeans.\n\n"
                + "Login padrao:\n"
                + "Usuario: admin\n"
                + "Senha: 1234\n");

        JPanel conteudo = Estilo.cartao();
        conteudo.setLayout(new BorderLayout(18, 18));
        conteudo.setBorder(javax.swing.BorderFactory.createEmptyBorder(26, 26, 26, 26));
        conteudo.add(new JScrollPane(texto), BorderLayout.CENTER);

        JButton voltar = Estilo.botaoSecundario("VOLTAR AO MENU");
        voltar.addActionListener(e -> setVisible(false));

        painel.add(Estilo.titulo("SOBRE"), BorderLayout.NORTH);
        painel.add(conteudo, BorderLayout.CENTER);
        painel.add(voltar, BorderLayout.SOUTH);
        add(painel);
    }
}
