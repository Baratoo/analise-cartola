package analise_cartola.desempenho;

import analise_cartola.atleta.Atleta;
import analise_cartola.integracao.cartola.dto.CartolaAtletaPontuadoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DesempenhoAtletaRodadaService {

    private final DesempenhoAtletaRepository desempenhoAtletaRepository;

    public DesempenhoAtletaRodadaService(DesempenhoAtletaRepository desempenhoAtletaRepository) {
        this.desempenhoAtletaRepository = desempenhoAtletaRepository;
    }

    @Transactional
    public DesempenhoAtletaRodada salvarOuAtualizar(Integer temporada, Integer rodada, Atleta atleta, CartolaAtletaPontuadoResponse dados){

        DesempenhoAtletaRodada desempenho = desempenhoAtletaRepository.findByTemporadaAndRodadaAndAtleta_id(temporada, rodada, atleta.getId()).
                    orElseGet(() -> new DesempenhoAtletaRodada(atleta, temporada, rodada));

        desempenho.atualizarDados(dados.clubeId(), dados.posicaoId(), dados.pontuacao(), dados.entrouEmCampo(), dados.scout());

        desempenhoAtletaRepository.save(desempenho);

        return desempenho;
    }

}
