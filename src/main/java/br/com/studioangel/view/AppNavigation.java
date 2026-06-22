package br.com.studioangel.view;

import javax.swing.JFrame;

public final class AppNavigation {

    private static TelaClientes clientes;
    private static TelaServicos servicos;
    private static TelaProfissionais profissionais;
    private static TelaAgendamentos agendamentos;
    private static TelaRelatorios financeiro;

    private AppNavigation() {
    }

    public static void registrar(TelaClientes telaClientes, TelaServicos telaServicos,
                                 TelaProfissionais telaProfissionais, TelaAgendamentos telaAgendamentos,
                                 TelaRelatorios telaFinanceiro) {
        clientes = telaClientes;
        servicos = telaServicos;
        profissionais = telaProfissionais;
        agendamentos = telaAgendamentos;
        financeiro = telaFinanceiro;
    }

    public static void abrirClientes(JFrame atual) {
        trocar(atual);
        if (clientes != null) {
            clientes.abrir();
        }
    }

    public static void abrirServicos(JFrame atual) {
        trocar(atual);
        if (servicos != null) {
            servicos.abrir();
        }
    }

    public static void abrirAgendamentos(JFrame atual) {
        trocar(atual);
        if (agendamentos != null) {
            agendamentos.abrir();
        }
    }

    public static void abrirProfissionais(JFrame atual) {
        trocar(atual);
        if (profissionais != null) {
            profissionais.abrir();
        }
    }

    public static void abrirFinanceiro(JFrame atual) {
        trocar(atual);
        if (financeiro != null) {
            financeiro.abrir();
        }
    }

    private static void trocar(JFrame atual) {
        if (atual != null) {
            atual.setVisible(false);
        }
    }
}
