package br.com.studioangel.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PrototipoInterno extends JPanel {

    private final String ativo;
    private final Image logoClara;

    public PrototipoInterno(String ativo) {
        this.ativo = ativo;
        this.logoClara = Estilo.logoImagem(true);
        setLayout(null);
        setBackground(Estilo.AZUL_FUNDO);
        montarIconesLaterais();
    }

    public JButton voltar(JFrame frame) {
        JButton b = new JButton("\u2190  VOLTAR AO MENU");
        b.setBounds(205, 15, 385, 55);
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setForeground(Estilo.AZUL_ESCURO);
        b.setFont(new Font("Segoe UI", Font.BOLD, 30));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(e -> frame.setVisible(false));
        return b;
    }

    public void habilitarNavegacao(JFrame frame) {
        add(navButton(0, 242, 190, 55, () -> AppNavigation.abrirClientes(frame)));
        add(navButton(0, 302, 190, 55, () -> AppNavigation.abrirServicos(frame)));
        add(navButton(0, 362, 190, 55, () -> AppNavigation.abrirProfissionais(frame)));
        add(navButton(0, 422, 190, 62, () -> AppNavigation.abrirAgendamentos(frame)));
        add(navButton(0, 492, 190, 56, () -> AppNavigation.abrirFinanceiro(frame)));
    }

    public JLabel titulo(String texto, int x, int y, int w, int h, int tamanho) {
        JLabel l = new JLabel("<html><center>" + texto.replace("\n", "<br>") + "</center></html>", SwingConstants.CENTER);
        l.setBounds(x, y, w, h);
        l.setForeground(Estilo.AZUL_ESCURO);
        l.setFont(new Font("Segoe UI", Font.BOLD, tamanho));
        return l;
    }

    public JLabel label(String texto, int x, int y, int w, int h, int tamanho) {
        JLabel l = new JLabel(texto);
        l.setBounds(x, y, w, h);
        l.setForeground(Estilo.AZUL_ESCURO);
        l.setFont(new Font("Segoe UI", Font.BOLD, tamanho));
        return l;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Estilo.AZUL_ESCURO);
        g2.fillRoundRect(-28, -18, 214, 202, 24, 24);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(-26, 177, 224, 600, 50, 50);
        logoLateral(g2);
        item(g2, "Clientes", 245, ativo.equals("Clientes"));
        item(g2, "Servicos", 305, ativo.equals("Servicos"));
        item(g2, "Profissionais", 365, ativo.equals("Profissionais"));
        item(g2, "Agendamentos", 425, ativo.equals("Agendamentos"));
        item(g2, "Financeiro", 495, ativo.equals("Financeiro"));
        g2.dispose();
    }

    private void logoLateral(Graphics2D g2) {
        g2.drawImage(logoClara, 15, 59, 58, 58, this);
        g2.setColor(Estilo.AZUL_CLARO);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 25));
        g2.drawString("Studio", 81, 83);
        g2.drawString("Angel", 81, 109);
    }

    private void item(Graphics2D g2, String texto, int y, boolean selecionado) {
        if (selecionado) {
            g2.setColor(Estilo.AZUL_ESCURO);
            g2.fillRoundRect(-20, y - 12, 212, 55, 32, 32);
            g2.setColor(Estilo.AZUL_CLARO);
        } else {
            g2.setColor(Estilo.AZUL_ESCURO);
        }
        g2.setFont(new Font("Segoe UI", Font.BOLD, texto.length() > 11 ? 14 : 22));
        g2.drawString(texto, texto.length() > 11 ? 58 : 68, y + 22);
    }

    private void montarIconesLaterais() {
        add(iconeLateral("clientes", 12, 239, ativo.equals("Clientes")));
        add(iconeLateral("servicos", 12, 299, ativo.equals("Servicos")));
        add(iconeLateral("profissionais", 12, 359, ativo.equals("Profissionais")));
        add(iconeLateral("agendamentos", 12, 419, ativo.equals("Agendamentos")));
        add(iconeLateral("financeiro", 12, 489, ativo.equals("Financeiro")));
    }

    private JComponent iconeLateral(String tipo, int x, int y, boolean selecionado) {
        Color cor = selecionado ? Estilo.AZUL_CLARO : Estilo.AZUL_ESCURO;
        return Estilo.icone(tipo, x, y, 42, 42, cor);
    }

    private JButton navButton(int x, int y, int w, int h, Runnable action) {
        JButton b = new JButton();
        b.setBounds(x, y, w, h);
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(e -> action.run());
        return b;
    }
}
