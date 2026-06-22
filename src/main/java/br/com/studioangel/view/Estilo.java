package br.com.studioangel.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.table.JTableHeader;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

public class Estilo {

    public static final Color AZUL_FUNDO = new Color(157, 195, 222);
    public static final Color AZUL_ESCURO = new Color(26, 85, 113);
    public static final Color AZUL_MEDIO = new Color(52, 94, 112);
    public static final Color AZUL_CLARO = new Color(178, 212, 235);
    public static final Color TEXTO = AZUL_ESCURO;
    public static final Color CINZA = new Color(78, 110, 128);
    public static final Color CINZA_CLARO = new Color(246, 249, 247);
    public static final Color BORDA = new Color(26, 85, 113);
    public static final Color BRANCO = Color.WHITE;

    private Estilo() {
    }

    public static void aplicarJanela(JFrame janela, String titulo) {
        janela.setTitle(titulo);
        janela.setSize(1366, 768);
        janela.setMinimumSize(new Dimension(1366, 768));
        janela.setResizable(false);
        janela.setLocationRelativeTo(null);
    }

    public static void aplicarPainel(JPanel painel) {
        painel.setBackground(AZUL_FUNDO);
        painel.setBorder(null);
    }

    public static JLabel titulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 42));
        label.setForeground(AZUL_ESCURO);
        return label;
    }

    public static JLabel subtitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        label.setForeground(AZUL_ESCURO);
        return label;
    }

    public static JLabel rotulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setForeground(TEXTO);
        return label;
    }

    public static JButton botao(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(AZUL_ESCURO);
        botao.setForeground(BRANCO);
        botao.setFocusPainted(false);
        botao.setBorder(new EmptyBorder(13, 22, 13, 22));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return botao;
    }

    public static JButton botaoSecundario(String texto) {
        JButton botao = botao(texto);
        botao.setBackground(BRANCO);
        botao.setForeground(AZUL_ESCURO);
        return botao;
    }

    public static JButton link(String texto) {
        JButton botao = new JButton(texto);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setFocusPainted(false);
        botao.setForeground(AZUL_ESCURO);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return botao;
    }

    public static JButton botaoPrototipo(String texto, int x, int y, int largura, int altura) {
        JButton botao = new RoundedButton(texto, AZUL_ESCURO, AZUL_CLARO, altura);
        botao.setBounds(x, y, largura, altura);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 22));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return botao;
    }

    public static JTextField campoPrototipo(int x, int y, int largura, int altura) {
        JTextField campo = new RoundedTextField(altura);
        campo.setBounds(x, y, largura, altura);
        campo.setOpaque(false);
        campo.setBackground(AZUL_ESCURO);
        campo.setBorder(new EmptyBorder(0, 18, 0, 18));
        campo.setForeground(BRANCO);
        campo.setCaretColor(BRANCO);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return campo;
    }

    public static JPasswordField senhaPrototipo(int x, int y, int largura, int altura) {
        JPasswordField campo = new RoundedPasswordField(altura);
        campo.setBounds(x, y, largura, altura);
        campo.setOpaque(false);
        campo.setBackground(AZUL_ESCURO);
        campo.setBorder(new EmptyBorder(0, 18, 0, 18));
        campo.setForeground(BRANCO);
        campo.setCaretColor(BRANCO);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return campo;
    }

    public static JTextField campoPromptPrototipo(int x, int y, int largura, int altura, String prompt) {
        JTextField campo = new PromptTextField(prompt, altura);
        campo.setBounds(x, y, largura, altura);
        campo.setOpaque(false);
        campo.setBackground(AZUL_ESCURO);
        campo.setBorder(new EmptyBorder(0, 18, 0, 18));
        campo.setForeground(BRANCO);
        campo.setCaretColor(BRANCO);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return campo;
    }

    public static JPasswordField senhaPromptPrototipo(int x, int y, int largura, int altura, String prompt) {
        JPasswordField campo = new PromptPasswordField(prompt, altura);
        campo.setBounds(x, y, largura, altura);
        campo.setOpaque(false);
        campo.setBackground(AZUL_ESCURO);
        campo.setBorder(new EmptyBorder(0, 18, 0, 18));
        campo.setForeground(BRANCO);
        campo.setCaretColor(BRANCO);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return campo;
    }

    public static JFormattedTextField campoMascaraPrototipo(int x, int y, int largura, int altura, String mascara) {
        try {
            MaskFormatter formatter = new MaskFormatter(mascara);
            formatter.setPlaceholderCharacter('_');
            JFormattedTextField campo = new RoundedFormattedTextField(formatter, altura);
            campo.setBounds(x, y, largura, altura);
            campo.setOpaque(false);
            campo.setBackground(AZUL_ESCURO);
            campo.setBorder(new EmptyBorder(0, 18, 0, 18));
            campo.setForeground(BRANCO);
            campo.setCaretColor(BRANCO);
            campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            return campo;
        } catch (java.text.ParseException ex) {
            throw new IllegalArgumentException("Mascara invalida: " + mascara, ex);
        }
    }

    public static JPanel cartao() {
        return new RoundedPanel(28, BRANCO);
    }

    public static JPanel blocoVerde() {
        return new RoundedPanel(36, AZUL_ESCURO);
    }

    public static JPanel telaBase() {
        JPanel painel = new JPanel(null);
        painel.setBackground(AZUL_FUNDO);
        return painel;
    }

    public static JLabel texto(String texto, int x, int y, int largura, int altura, int tamanho, int estilo) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, largura, altura);
        label.setFont(new Font("Segoe UI", estilo, tamanho));
        label.setForeground(AZUL_ESCURO);
        return label;
    }

    public static JComponent logo(int x, int y, int largura, int altura, boolean comTexto, int tamanhoTexto) {
        LogoMarca logo = new LogoMarca(comTexto, tamanhoTexto, false);
        logo.setBounds(x, y, largura, altura);
        return logo;
    }

    public static JComponent logoClaro(int x, int y, int largura, int altura, boolean comTexto, int tamanhoTexto) {
        LogoMarca logo = new LogoMarca(comTexto, tamanhoTexto, true);
        logo.setBounds(x, y, largura, altura);
        return logo;
    }

    public static JComponent icone(String tipo, int x, int y, int largura, int altura) {
        JLabel icone = new JLabel(iconeBiblioteca(tipo, Math.min(largura, altura), AZUL_CLARO));
        icone.setBounds(x, y, largura, altura);
        icone.setHorizontalAlignment(SwingConstants.CENTER);
        icone.setVerticalAlignment(SwingConstants.CENTER);
        return icone;
    }

    public static JComponent icone(String tipo, int x, int y, int largura, int altura, Color cor) {
        JLabel icone = new JLabel(iconeBiblioteca(tipo, Math.min(largura, altura), cor));
        icone.setBounds(x, y, largura, altura);
        icone.setHorizontalAlignment(SwingConstants.CENTER);
        icone.setVerticalAlignment(SwingConstants.CENTER);
        return icone;
    }

    private static FontIcon iconeBiblioteca(String tipo, int tamanho, Color cor) {
        FontAwesome codigo;
        if ("clientes".equalsIgnoreCase(tipo)) {
            codigo = FontAwesome.USERS;
        } else if ("servicos".equalsIgnoreCase(tipo)) {
            codigo = FontAwesome.CUT;
        } else if ("profissionais".equalsIgnoreCase(tipo)) {
            codigo = FontAwesome.USER_PLUS;
        } else if ("agendamentos".equalsIgnoreCase(tipo)) {
            codigo = FontAwesome.CALENDAR;
        } else if ("financeiro".equalsIgnoreCase(tipo)) {
            codigo = FontAwesome.USD;
        } else {
            codigo = FontAwesome.CIRCLE;
        }
        return FontIcon.of(codigo, tamanho, cor);
    }

    public static Image logoImagem(boolean claro) {
        return carregarLogoRecortada(claro);
    }

    private static Image carregarLogoRecortada(boolean claro) {
        String arquivo = claro ? "/logos/logo_clara.png" : "/logos/logo_Escura.png";
        try {
            BufferedImage original = ImageIO.read(Estilo.class.getResource(arquivo));
            int minX = original.getWidth();
            int minY = original.getHeight();
            int maxX = 0;
            int maxY = 0;
            for (int y = 0; y < original.getHeight(); y++) {
                for (int x = 0; x < original.getWidth(); x++) {
                    int alpha = (original.getRGB(x, y) >>> 24) & 0xff;
                    if (alpha > 10) {
                        minX = Math.min(minX, x);
                        minY = Math.min(minY, y);
                        maxX = Math.max(maxX, x);
                        maxY = Math.max(maxY, y);
                    }
                }
            }
            if (maxX <= minX || maxY <= minY) {
                return original;
            }
            return original.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1);
        } catch (IOException | IllegalArgumentException ex) {
            throw new IllegalStateException("Nao foi possivel carregar " + arquivo, ex);
        }
    }

    public static JButton cartaoMenu(String titulo, String descricao) {
        JButton botao = new JButton("<html><div style='text-align:left;'>"
                + "<div style='font-size:22px;font-weight:700;'>" + titulo + "</div>"
                + "<div style='font-size:13px;font-weight:400;margin-top:8px;'>" + descricao + "</div>"
                + "</div></html>");
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.setVerticalAlignment(SwingConstants.CENTER);
        botao.setBackground(BRANCO);
        botao.setForeground(TEXTO);
        botao.setFocusPainted(false);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setBorder(new EmptyBorder(26, 28, 26, 28));
        botao.setFont(new Font("Segoe UI", Font.BOLD, 18));
        return botao;
    }

    public static void aplicarCampo(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campo.setForeground(TEXTO);
        campo.setBackground(BRANCO);
        campo.setBorder(new EmptyBorder(11, 14, 11, 14));
    }

    public static void aplicarAreaTexto(JTextArea area) {
        area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        area.setForeground(TEXTO);
        area.setBackground(BRANCO);
        area.setBorder(new EmptyBorder(18, 18, 18, 18));
    }

    public static void aplicarTabela(JTable tabela) {
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.setForeground(TEXTO);
        tabela.setRowHeight(32);
        tabela.setGridColor(BORDA);
        tabela.setSelectionBackground(AZUL_CLARO);
        tabela.setSelectionForeground(TEXTO);
        JTableHeader cabecalho = tabela.getTableHeader();
        cabecalho.setBackground(AZUL_ESCURO);
        cabecalho.setForeground(BRANCO);
        cabecalho.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    public static void aplicarTabelaPrototipo(JTable tabela, int rowHeight, int headerSize) {
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tabela.setForeground(Color.BLACK);
        tabela.setRowHeight(rowHeight);
        tabela.setShowVerticalLines(false);
        tabela.setShowHorizontalLines(false);
        tabela.setGridColor(BRANCO);
        tabela.setSelectionBackground(AZUL_CLARO);
        tabela.setSelectionForeground(Color.BLACK);
        JTableHeader cabecalho = tabela.getTableHeader();
        cabecalho.setFont(new Font("Segoe UI", Font.BOLD, headerSize));
        cabecalho.setForeground(AZUL_CLARO);
        cabecalho.setBackground(AZUL_ESCURO);
        cabecalho.setOpaque(true);
    }

    public static void deixarTransparente(Component componente) {
        if (componente instanceof JPanel) {
            ((JPanel) componente).setOpaque(false);
        }
    }

    public static JPanel painelArredondado(int radius, Color color) {
        return new RoundedPanel(radius, color);
    }

    public static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color color;

        RoundedPanel(int radius, Color color) {
            this.radius = radius;
            this.color = color;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class RoundedButton extends JButton {
        private final int radius;

        RoundedButton(String text, Color background, Color foreground, int radius) {
            super(text);
            this.radius = radius;
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setBackground(background);
            setForeground(foreground);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class RoundedTextField extends JTextField {
        private final int radius;

        RoundedTextField(int radius) {
            this.radius = radius;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public boolean contains(int x, int y) {
            Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
            return shape.contains(x, y);
        }
    }

    private static class RoundedPasswordField extends JPasswordField {
        private final int radius;

        RoundedPasswordField(int radius) {
            this.radius = radius;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class PromptTextField extends RoundedTextField {
        private final String prompt;

        PromptTextField(String prompt, int radius) {
            super(radius);
            this.prompt = prompt;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BRANCO);
                g2.setFont(getFont().deriveFont(15f));
                g2.drawString(prompt, 26, getHeight() / 2 + 6);
                g2.dispose();
            }
        }
    }

    private static class PromptPasswordField extends RoundedPasswordField {
        private final String prompt;

        PromptPasswordField(String prompt, int radius) {
            super(radius);
            this.prompt = prompt;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getPassword().length == 0) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BRANCO);
                g2.setFont(getFont().deriveFont(15f));
                g2.drawString(prompt, 26, getHeight() / 2 + 6);
                g2.dispose();
            }
        }
    }

    private static class RoundedFormattedTextField extends JFormattedTextField {
        private final int radius;

        RoundedFormattedTextField(MaskFormatter formatter, int radius) {
            super(formatter);
            this.radius = radius;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class LogoMarca extends JComponent {
        private final boolean comTexto;
        private final int tamanhoTexto;
        private final boolean claro;
        private final Image imagem;

        LogoMarca(boolean comTexto, int tamanhoTexto, boolean claro) {
            this.comTexto = comTexto;
            this.tamanhoTexto = tamanhoTexto;
            this.claro = claro;
            this.imagem = carregarLogoRecortada(claro);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int size = Math.min(getHeight(), comTexto ? getWidth() / 3 : getWidth()) - 8;
            int cx = 4;
            int cy = (getHeight() - size) / 2;
            g2.drawImage(imagem, cx, cy, size, size, this);

            if (comTexto) {
                g2.setColor(claro ? AZUL_CLARO : AZUL_ESCURO);
                g2.setFont(new Font("Segoe UI", Font.BOLD, tamanhoTexto));
                g2.drawString("Studio Angel", cx + size + 28, cy + size * 2 / 3);
            }
            g2.dispose();
        }
    }

}
