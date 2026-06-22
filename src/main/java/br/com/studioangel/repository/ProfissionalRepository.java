package br.com.studioangel.repository;

import br.com.studioangel.model.Profissional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    List<Profissional> findByAtivoTrue();
}
