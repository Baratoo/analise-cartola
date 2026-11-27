package analise_cartola.desempenho;

import analise_cartola.atleta.Atleta;
import analise_cartola.atleta.AtletaService;
import analise_cartola.integracao.cartola.dto.CartolaAtletaPontuadoResponse;
import analise_cartola.integracao.cartola.dto.CartolaPontuadosResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GravacaoDesempenhoService {

    private final AtletaService atletaService;
    private final DesempenhoAtletaRodadaService desempenhoService;
    private final ImportacaoDesempenhoRepository importacaoDesempenhoRepository;

    public GravacaoDesempenhoService(AtletaService atletaService, DesempenhoAtletaRodadaService desempenhoService, ImportacaoDesempenhoRepository importacaoDesempenhoRepository) {
        this.atletaService = atletaService;
        this.desempenhoService = desempenhoService;
        this.importacaoDesempenhoRepository = importacaoDesempenhoRepository;
    }

    public int salvar(Integer temporada, CartolaPontuadosResponse dados, boolean consolidada){ //retorna quantidade salvos

        if (temporada == null || temporada <= 0) {
            throw new IllegalArgumentException("A temporada deve ser positiva.");
        }

        if (dados == null || dados.rodada() == null || dados.rodada() <= 0
                || dados.atletas() == null || dados.atletas().isEmpty()) {
            throw new IllegalArgumentException("A resposta não contém uma rodada válida com atletas.");
        }

        int quantidade = 0;

        ///{
        ///   "atletas": {
        ///     "100084": {
        ///       "apelido": "Igor Gomes",
        ///       "pontuacao": 0
        ///     }
        ///   }
        /// }
        ///Nesse caso o ID vem como a CHAVE

        for (Map.Entry<String, CartolaAtletaPontuadoResponse> entrada : dados.atletas().entrySet()) {
            Long atletaId = Long.valueOf(entrada.getKey());
            CartolaAtletaPontuadoResponse pontuado = entrada.getValue();

            Atleta atleta = atletaService.salvarOuAtualizar(atletaId, pontuado.apelido());

            desempenhoService.salvarOuAtualizar(temporada, dados.rodada(), atleta, pontuado);

            quantidade++;
        }

        ImportacaoDesempenho importacaoDesempenho = importacaoDesempenhoRepository.findByTemporadaAndRodada(temporada, dados.rodada()).
                orElseGet(() -> new ImportacaoDesempenho(temporada, dados.rodada()));

        importacaoDesempenho.atualizarDados(quantidade, consolidada);

        importacaoDesempenhoRepository.save(importacaoDesempenho);

        return quantidade;
    }



}
