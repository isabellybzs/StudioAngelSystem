package br.com.studioangel.repository;

import br.com.studioangel.model.Agendamento;
import br.com.studioangel.model.Cliente;
import br.com.studioangel.model.Profissional;
import br.com.studioangel.model.StatusAgendamento;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    long countByStatus(StatusAgendamento status);

    List<Agendamento> findByStatus(StatusAgendamento status);

    List<Agendamento> findByData(LocalDate data);

    List<Agendamento> findByDataBetween(LocalDate inicio, LocalDate fim);

    List<Agendamento> findByCliente(Cliente cliente);

    List<Agendamento> findByProfissional(Profissional profissional);

    boolean existsByProfissionalAndDataAndHorario(Profissional profissional, LocalDate data, LocalTime horario);

    boolean existsByProfissionalAndDataAndHorarioAndIdNot(Profissional profissional, LocalDate data,
                                                          LocalTime horario, Long id);
}
