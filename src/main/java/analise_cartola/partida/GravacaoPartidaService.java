package analise_cartola.partida;

import analise_cartola.integracao.cartola.dto.CartolaPartidaResponse;
import analise_cartola.integracao.cartola.dto.CartolaPartidasResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class GravacaoPartidaService {

    private final PartidaService partidaService;

    public GravacaoPartidaService(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @Transactional
    public int salvar(Integer temporada, CartolaPartidasResponse dados) {

        if (temporada == null || temporada <= 0) {
            throw new IllegalArgumentException("A temporada deve ser positiva.");
        }

        if (dados == null || dados.rodada() == null || dados.rodada() <= 0|| dados.partidas() == null || dados.partidas().isEmpty()) {
            throw new IllegalArgumentException("A resposta não contém uma rodada válida com partidas.");
        }

        Set<Long> idsRecebidos = new HashSet<>();
        int quantidade = 0;

        for (CartolaPartidaResponse partida : dados.partidas()) {

            if (partida == null || partida.id() == null || !idsRecebidos.add(partida.id())) {
                throw new IllegalArgumentException("A lista contém partida sem ID ou repetida.");
            }

            partidaService.salvarOuAtualizar(temporada, dados.rodada(), partida);

            quantidade++;

        }

        return quantidade;
    }

}
