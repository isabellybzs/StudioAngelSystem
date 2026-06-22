package br.com.studioangel.service;

import br.com.studioangel.model.Servico;
import br.com.studioangel.repository.ServicoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public Servico salvar(Servico servico) {
        return servicoRepository.save(servico);
    }

    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }

    public Servico buscarPorId(Long id) {
        return servicoRepository.findById(id).orElse(null);
    }

    public void excluir(Long id) {
        servicoRepository.deleteById(id);
    }
}
