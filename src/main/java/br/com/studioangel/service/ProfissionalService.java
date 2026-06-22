package br.com.studioangel.service;

import br.com.studioangel.model.Profissional;
import br.com.studioangel.repository.ProfissionalRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;

    public ProfissionalService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    public Profissional salvar(Profissional profissional) {
        return profissionalRepository.save(profissional);
    }

    public List<Profissional> listarTodos() {
        return profissionalRepository.findAll();
    }

    public List<Profissional> listarAtivos() {
        return profissionalRepository.findByAtivoTrue();
    }

    public Profissional buscarPorId(Long id) {
        return profissionalRepository.findById(id).orElse(null);
    }

    public void excluir(Long id) {
        profissionalRepository.deleteById(id);
    }
}
