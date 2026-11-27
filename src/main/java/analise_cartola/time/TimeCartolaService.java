package analise_cartola.time;

import analise_cartola.escalacao.EscalacaoController;
import analise_cartola.integracao.cartola.dto.CartolaEscalacaoResponse;
import analise_cartola.integracao.cartola.dto.CartolaTimeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TimeCartolaService {

    private final TimeCartolaRepository timeCartolaRepository;

    public TimeCartolaService(TimeCartolaRepository timeCartolaRepository) {
        this.timeCartolaRepository = timeCartolaRepository;
    }

    @Transactional
    public TimeCartola salvarOuAtualizar(CartolaTimeResponse dados){

        TimeCartola time = timeCartolaRepository.findById(dados.id()).
                orElseGet(() -> new TimeCartola(dados.id(), dados.nome(), dados.nomeCartola(), dados.slug(), dados.urlEscudo()));

        time.atualizarDados(dados.nome(), dados.nomeCartola(), dados.slug(), dados.urlEscudo());

        return timeCartolaRepository.save(time);

//        return time;

    }
}
