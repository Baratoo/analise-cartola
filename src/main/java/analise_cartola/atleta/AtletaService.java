package analise_cartola.atleta;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtletaService {

    private final AtletaRepository atletaRepository;

    public AtletaService(AtletaRepository atletaRepository) {
        this.atletaRepository = atletaRepository;
    }

    @Transactional
    public Atleta salvarOuAtualizar(Long id, String apelido) {
        if (id == null || id <= 0){
            throw new IllegalArgumentException("O ID do atleta está errado");
        }

        Atleta atleta = atletaRepository.findById(id).orElseGet(() -> new Atleta(id, apelido));

        atleta.atualizarDados(apelido);

        return atletaRepository.save(atleta);

//        return atleta;
    }
}
