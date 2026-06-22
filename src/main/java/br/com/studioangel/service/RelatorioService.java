package br.com.studioangel.service;

import br.com.studioangel.model.Agendamento;
import br.com.studioangel.model.StatusAgendamento;
import br.com.studioangel.repository.AgendamentoRepository;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RelatorioService {

    private final AgendamentoRepository agendamentoRepository;

    public RelatorioService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public long totalAgendamentos() {
        return agendamentoRepository.count();
    }

    public long totalPorStatus(StatusAgendamento status) {
        return agendamentoRepository.countByStatus(status);
    }

    public String servicoMaisAgendado() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();
        Optional<Map.Entry<String, Long>> resultado = agendamentos.stream()
                .filter(a -> a.getServico() != null)
                .collect(Collectors.groupingBy(a -> a.getServico().getNome(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        return resultado.map(e -> e.getKey() + " - " + e.getValue() + " agendamento(s)")
                .orElse("Nenhum servico agendado");
    }

    public String clienteMaisFrequente() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();
        Optional<Map.Entry<String, Long>> resultado = agendamentos.stream()
                .filter(a -> a.getCliente() != null)
                .collect(Collectors.groupingBy(a -> a.getCliente().getNome(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue));

        return resultado.map(e -> e.getKey() + " - " + e.getValue() + " agendamento(s)")
                .orElse("Nenhum cliente encontrado");
    }

    public String profissionalComMaisAtendimentos() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();
        Optional<Map.Entry<String, Long>> resultado = agendamentos.stream()
                .filter(a -> a.getProfissional() != null)
                .collect(Collectors.groupingBy(a -> a.getProfissional().getNome(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue));

        return resultado.map(e -> e.getKey() + " - " + e.getValue() + " atendimento(s)")
                .orElse("Nenhum profissional encontrado");
    }

    public String horarioMaisOcupado() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();
        Optional<Map.Entry<String, Long>> resultado = agendamentos.stream()
                .filter(a -> a.getHorario() != null)
                .collect(Collectors.groupingBy(a -> a.getHorario().toString(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue));

        return resultado.map(e -> e.getKey() + " - " + e.getValue() + " agendamento(s)")
                .orElse("Nenhum horario encontrado");
    }

    public BigDecimal valorTotalConcluido() {
        return agendamentoRepository.findByStatus(StatusAgendamento.CONCLUIDO)
                .stream()
                .filter(a -> a.getServico() != null && a.getServico().getPreco() != null)
                .map(a -> a.getServico().getPreco())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
