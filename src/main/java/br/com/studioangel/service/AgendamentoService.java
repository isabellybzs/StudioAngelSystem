package br.com.studioangel.service;

import br.com.studioangel.model.Agendamento;
import br.com.studioangel.model.Cliente;
import br.com.studioangel.model.Profissional;
import br.com.studioangel.repository.AgendamentoRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public Agendamento salvar(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id).orElse(null);
    }

    public void excluir(Long id) {
        agendamentoRepository.deleteById(id);
    }

    public List<Agendamento> listarPorData(LocalDate data) {
        return agendamentoRepository.findByData(data);
    }

    public List<Agendamento> listarPorSemana(LocalDate data) {
        return agendamentoRepository.findByDataBetween(data, data.plusDays(6));
    }

    public List<Agendamento> listarPorCliente(Cliente cliente) {
        return agendamentoRepository.findByCliente(cliente);
    }

    public List<Agendamento> listarPorProfissional(Profissional profissional) {
        return agendamentoRepository.findByProfissional(profissional);
    }

    public boolean existeConflito(Profissional profissional, LocalDate data, LocalTime horario, Long ignorarId) {
        if (profissional == null || data == null || horario == null) {
            return false;
        }
        if (ignorarId == null) {
            return agendamentoRepository.existsByProfissionalAndDataAndHorario(profissional, data, horario);
        }
        return agendamentoRepository.existsByProfissionalAndDataAndHorarioAndIdNot(profissional, data, horario, ignorarId);
    }
}
