package analise_cartola.partida;

import analise_cartola.integracao.cartola.dto.CartolaPartidaResponse;
import analise_cartola.integracao.cartola.dto.CartolaPartidasResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;

    public PartidaService(PartidaRepository partidaRepository) {
        this.partidaRepository = partidaRepository;
    }

    @Transactional
    public Partida salvarOuAtualizar(Integer temporada, Integer rodada, CartolaPartidaResponse dados){

        if (dados == null || dados.id() == null || dados.id() <= 0) {
            throw new IllegalArgumentException("A partida deve possuir um ID positivo.");
        }

        Partida partida = partidaRepository.findById(dados.id()).orElseGet(() -> new  Partida(dados.id(), temporada, rodada));

        Instant dataHora = dados.timeStamp() == null ? null : Instant.ofEpochSecond(dados.timeStamp());

        partida.atualizarDados(dados.clubeMandanteId(), dados.clubeVisitanteId(), dados.placarOficialMandante(), dados.placarOficialVisitante(), dataHora, dados.valida());

        return partidaRepository.save(partida);

//        return partida;
    }

}
