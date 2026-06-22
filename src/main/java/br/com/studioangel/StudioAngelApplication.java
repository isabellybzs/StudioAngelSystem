package br.com.studioangel;

import br.com.studioangel.model.Agendamento;
import br.com.studioangel.model.Cliente;
import br.com.studioangel.model.Profissional;
import br.com.studioangel.model.Servico;
import br.com.studioangel.model.StatusAgendamento;
import br.com.studioangel.repository.AgendamentoRepository;
import br.com.studioangel.repository.ClienteRepository;
import br.com.studioangel.repository.ProfissionalRepository;
import br.com.studioangel.repository.ServicoRepository;
import br.com.studioangel.view.TelaLogin;
import java.awt.EventQueue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudioAngelApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(StudioAngelApplication.class)
                .headless(false)
                .run(args);
        EventQueue.invokeLater(() -> context.getBean(TelaLogin.class).setVisible(true));
    }

    @Bean
    CommandLineRunner carregarDadosIniciais(ClienteRepository clienteRepository,
                                            ServicoRepository servicoRepository,
                                            ProfissionalRepository profissionalRepository,
                                            AgendamentoRepository agendamentoRepository) {
        return args -> {
            Cliente ana = clienteRepository.findAll().stream().findFirst().orElse(null);
            Cliente mariana = null;
            Cliente beatriz = null;
            if (clienteRepository.count() == 0) {
                ana = clienteRepository.save(new Cliente("Ana Souza", "(11) 99999-1111", "ana@email.com", "Prefere horario da manha"));
                mariana = clienteRepository.save(new Cliente("Mariana Lima", "(11) 98888-2222", "mariana@email.com", "Cliente mensal"));
                beatriz = clienteRepository.save(new Cliente("Beatriz Santos", "(11) 97777-3333", "beatriz@email.com", "Gosta de esmaltes claros"));
            } else {
                for (Cliente cliente : clienteRepository.findAll()) {
                    if (mariana == null) {
                        mariana = cliente;
                    } else if (beatriz == null) {
                        beatriz = cliente;
                    }
                }
            }

            if (mariana == null) {
                mariana = ana;
            }
            if (beatriz == null) {
                beatriz = ana;
            }

            Servico corte;
            Servico escova;
            Servico manicure;
            Servico sobrancelha;
            Servico coloracao;
            if (servicoRepository.count() == 0) {
                corte = servicoRepository.save(new Servico("Corte feminino", "Corte, lavagem e finalizacao", new BigDecimal("85.00"), 60));
                escova = servicoRepository.save(new Servico("Escova", "Escova modelada", new BigDecimal("55.00"), 45));
                manicure = servicoRepository.save(new Servico("Manicure", "Cuidado completo das unhas", new BigDecimal("35.00"), 40));
                sobrancelha = servicoRepository.save(new Servico("Design de sobrancelha", "Design com acabamento", new BigDecimal("45.00"), 30));
                coloracao = servicoRepository.save(new Servico("Coloracao", "Coloracao completa dos fios", new BigDecimal("180.00"), 120));
            } else {
                java.util.List<Servico> servicos = servicoRepository.findAll();
                corte = servicos.get(0);
                escova = servicos.size() > 1 ? servicos.get(1) : corte;
                manicure = servicos.size() > 2 ? servicos.get(2) : corte;
                sobrancelha = servicos.size() > 3 ? servicos.get(3) : corte;
                coloracao = servicos.size() > 4 ? servicos.get(4) : corte;
            }

            Profissional camila;
            Profissional carolina;
            Profissional vania;
            if (profissionalRepository.count() == 0) {
                camila = profissionalRepository.save(new Profissional("Camila", "Cabelos e escova", true));
                carolina = profissionalRepository.save(new Profissional("Carolina", "Corte e coloracao", true));
                vania = profissionalRepository.save(new Profissional("Vania", "Manicure e pedicure", true));
            } else {
                java.util.List<Profissional> profissionais = profissionalRepository.findAll();
                camila = profissionais.get(0);
                carolina = profissionais.size() > 1 ? profissionais.get(1) : camila;
                vania = profissionais.size() > 2 ? profissionais.get(2) : camila;
            }

            for (Agendamento agendamento : agendamentoRepository.findAll()) {
                if (agendamento.getProfissional() == null) {
                    agendamento.setProfissional(camila);
                    agendamentoRepository.save(agendamento);
                }
            }

            if (agendamentoRepository.count() == 0) {
                agendamentoRepository.saveAll(Arrays.asList(
                        new Agendamento(ana, escova, camila, LocalDate.now().minusDays(3), LocalTime.of(9, 0), StatusAgendamento.CONCLUIDO, "Finalizado sem atraso"),
                        new Agendamento(ana, manicure, vania, LocalDate.now().plusDays(2), LocalTime.of(14, 0), StatusAgendamento.AGENDADO, "Confirmar no dia anterior"),
                        new Agendamento(mariana, corte, carolina, LocalDate.now().minusDays(1), LocalTime.of(10, 30), StatusAgendamento.CONCLUIDO, "Cliente satisfeita"),
                        new Agendamento(mariana, escova, camila, LocalDate.now().plusDays(1), LocalTime.of(16, 0), StatusAgendamento.CONFIRMADO, ""),
                        new Agendamento(beatriz, sobrancelha, carolina, LocalDate.now().minusDays(2), LocalTime.of(11, 0), StatusAgendamento.CANCELADO, "Cancelado pela cliente"),
                        new Agendamento(beatriz, coloracao, carolina, LocalDate.now().minusDays(7), LocalTime.of(13, 0), StatusAgendamento.CONCLUIDO, "Usar mesma cor na proxima vez"),
                        new Agendamento(ana, escova, camila, LocalDate.now().minusDays(5), LocalTime.of(15, 0), StatusAgendamento.CONCLUIDO, "Escova para evento")
                ));
            }
        };
    }
}
