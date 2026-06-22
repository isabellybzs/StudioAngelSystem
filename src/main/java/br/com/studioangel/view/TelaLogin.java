package br.com.studioangel.view;

import br.com.studioangel.controller.SistemaController;
import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.springframework.stereotype.Component;

@Component
public class TelaLogin extends JFrame {

    private final SistemaController sistemaController;
    private final TelaMenu telaMenu;
    private CardLayout cards;
    private JPanel telas;
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JTextField campoRecuperacao;

    public TelaLogin(SistemaController sistemaController, TelaMenu telaMenu) {
        this.sistemaController = sistemaController;
        this.telaMenu = telaMenu;
        configurarTela();
    }

    private void configurarTela() {
        Estilo.aplicarJanela(this, "Login - Studio Angel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cards = new CardLayout();
        telas = new JPanel(cards);
        telas.add(login(), "login");
        telas.add(cadastro(), "cadastro");
        telas.add(recuperacao(), "recuperacao");
        setContentPane(telas);
    }

    private JPanel login() {
        JPanel p = Estilo.telaBase();
        p.add(Estilo.logo(344, 31, 690, 210, true, 72));

        JLabel titulo = labelCentro("Bem vindo de volta!", 0, 274, 1366, 44, 34, Font.BOLD);
        JLabel sub = labelCentro("Faca login para continuar", 0, 318, 1366, 22, 17, Font.PLAIN);
        p.add(titulo);
        p.add(sub);

        campoUsuario = Estilo.campoPromptPrototipo(545, 368, 284, 51, "Email :");
        campoSenha = Estilo.senhaPromptPrototipo(545, 428, 284, 51, "Senha:");
        p.add(campoUsuario);
        p.add(campoSenha);

        JButton esqueci = transparente("Esqueci minha senha", 673, 489, 152, 24, 14);
        esqueci.addActionListener(e -> cards.show(telas, "recuperacao"));
        p.add(esqueci);

        JButton entrar = Estilo.botaoPrototipo("ENTRAR", 548, 554, 284, 51);
        entrar.addActionListener(e -> autenticar());
        p.add(entrar);

        JLabel ou = labelCentro("ou", 0, 626, 1366, 18, 14, Font.BOLD);
        p.add(ou);

        JButton cadastrar = Estilo.botaoPrototipo("Nao possui conta? Cadastra-se", 541, 655, 284, 51);
        cadastrar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cadastrar.setBackground(new java.awt.Color(111, 161, 194));
        cadastrar.setForeground(java.awt.Color.WHITE);
        cadastrar.addActionListener(e -> cards.show(telas, "cadastro"));
        p.add(cadastrar);

        return p;
    }

    private JPanel cadastro() {
        JPanel p = Estilo.telaBase();
        p.add(Estilo.logo(371, 31, 660, 170, true, 72));
        p.add(labelCentro("CADASTRAR USUARIO", 0, 219, 1366, 39, 34, Font.BOLD));

        p.add(campoTexto("NOME COMPLETO:", 524, 271));
        p.add(campoTexto("E-MAIL:", 524, 332));
        p.add(campoTexto("TELEFONE:", 524, 391));
        p.add(campoSenha("SENHA:", 524, 449));
        p.add(campoSenha("CONFIRMAR SENHA:", 524, 509));

        JButton cadastrar = Estilo.botaoPrototipo("CADASTRAR", 624, 595, 160, 49);
        cadastrar.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        cadastrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Usuario cadastrado no prototipo.");
            cards.show(telas, "login");
        });
        p.add(cadastrar);

        JButton voltar = Estilo.botaoPrototipo("JA TENHO CONTA", 629, 666, 160, 51);
        voltar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        voltar.setBackground(Estilo.AZUL_MEDIO);
        voltar.addActionListener(e -> cards.show(telas, "login"));
        p.add(voltar);
        return p;
    }

    private JPanel recuperacao() {
        JPanel p = Estilo.telaBase();
        p.add(Estilo.logo(344, 31, 690, 205, true, 72));
        p.add(labelCentro("ESQUECI MINHA SENHA", 0, 270, 1366, 34, 32, Font.BOLD));
        p.add(labelCentro("Digite email ou cpf para recuperar sua senha", 0, 313, 1366, 22, 17, Font.PLAIN));

        campoRecuperacao = Estilo.campoPromptPrototipo(545, 368, 284, 51, "Email ou cpf:");
        p.add(campoRecuperacao);

        JButton enviar = Estilo.botaoPrototipo("ENVIAR LINK PARA RECUPERACAO", 545, 428, 284, 51);
        enviar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        enviar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Link de recuperacao enviado."));
        p.add(enviar);

        JButton voltar = Estilo.botaoPrototipo("Voltar ao login", 548, 554, 284, 51);
        voltar.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        voltar.addActionListener(e -> cards.show(telas, "login"));
        p.add(voltar);
        return p;
    }

    private JTextField campoTexto(String texto, int x, int y) {
        return Estilo.campoPromptPrototipo(x, y, 385, 45, texto);
    }

    private JPasswordField campoSenha(String texto, int x, int y) {
        return Estilo.senhaPromptPrototipo(x, y, 385, 45, texto);
    }

    private JLabel labelCentro(String texto, int x, int y, int w, int h, int tamanho, int estilo) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setBounds(x, y, w, h);
        label.setFont(new Font("Segoe UI", estilo, tamanho));
        label.setForeground(Estilo.AZUL_ESCURO);
        return label;
    }

    private JButton transparente(String texto, int x, int y, int w, int h, int tamanho) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, w, h);
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setForeground(Estilo.AZUL_ESCURO);
        botao.setFont(new Font("Segoe UI", Font.PLAIN, tamanho));
        return botao;
    }

    private void autenticar() {
        String usuario = campoUsuario.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();

        if (sistemaController.autenticar(usuario, senha)) {
            campoSenha.setText("");
            setVisible(false);
            telaMenu.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario ou senha invalidos.");
        }
    }
}
