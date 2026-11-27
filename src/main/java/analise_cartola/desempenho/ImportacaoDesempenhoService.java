package analise_cartola.desempenho;

import analise_cartola.escalacao.Escalacao;
import analise_cartola.integracao.cartola.CartolaClient;
import analise_cartola.integracao.cartola.dto.CartolaEscalacaoResponse;
import analise_cartola.integracao.cartola.dto.CartolaMercadoStatusResponse;
import analise_cartola.integracao.cartola.dto.CartolaPontuadosResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImportacaoDesempenhoService{

    private final CartolaClient cartolaClient;
    private final GravacaoDesempenhoService gravacaoDesempenhoService;
    private final ImportacaoDesempenhoRepository importacaoDesempenhoRepository;

    public ImportacaoDesempenhoService(CartolaClient cartolaClient, GravacaoDesempenhoService gravacaoDesempenhoService, ImportacaoDesempenhoRepository importacaoDesempenhoRepository) {
        this.cartolaClient = cartolaClient;
        this.gravacaoDesempenhoService = gravacaoDesempenhoService;
        this.importacaoDesempenhoRepository = importacaoDesempenhoRepository;
    }

    public int importarIntervalo(Integer temporada, Integer rodadaInicial, Integer rodadaFinal)
            throws IOException, InterruptedException{
        if (rodadaInicial == null || rodadaFinal == null || rodadaFinal <= 0 || rodadaFinal < rodadaInicial) {
            throw new IllegalArgumentException("Informe um intervalo válido");
        }

        Integer qtdTotal = 0;
        for (int rodada = rodadaInicial; rodada <= rodadaFinal; rodada++) {
            qtdTotal += importar(temporada, rodada);
        }

        return qtdTotal;
    }

    public int importar(Integer temporada, Integer rodada)
        throws IOException, InterruptedException{

        if (temporada == null || temporada <= 0 || rodada == null || rodada <= 0) {
            throw new IllegalArgumentException("Temporada e rodada invalidos.");
        }

        Optional<ImportacaoDesempenho> existente = importacaoDesempenhoRepository.findByTemporadaAndRodada(temporada, rodada);

        if (existente.isPresent() && existente.get().isConsolidada()) {
            return existente.get().getQuantidadeAtletas();
        }

        CartolaMercadoStatusResponse statusMercado = cartolaClient.buscarMercadoStatus();

        if (statusMercado == null || statusMercado.temporada() == null || statusMercado.rodadaAtual() == null) {
            throw new IOException("O Cartola não informou a temporada ou a rodada atual.");
        }

        if (!temporada.equals(statusMercado.temporada())) {
            throw new IllegalArgumentException("A temporada solicitada difere da temporada atual da API.");
        }

        if (rodada > statusMercado.rodadaAtual()) {
            throw new IllegalArgumentException("Não é permitido importar uma rodada futura.");
        }

        CartolaPontuadosResponse dados = cartolaClient.buscarPontuados(rodada);

        return gravacaoDesempenhoService.salvar(temporada, dados, podeConsolidar(rodada, statusMercado));
    }

    private boolean podeConsolidar(Integer rodada, CartolaMercadoStatusResponse status){
        return rodada < status.rodadaAtual() && Integer.valueOf(1).equals(status.statusMercado())
                && Boolean.TRUE.equals(status.mercadoPosRodada()) && Boolean.FALSE.equals(status.bolaRolando())
                && Boolean.FALSE.equals(status.gameOver());
    }

}
