package analise_cartola.partida;

import analise_cartola.integracao.cartola.CartolaClient;
import analise_cartola.integracao.cartola.dto.CartolaMercadoStatusResponse;
import analise_cartola.integracao.cartola.dto.CartolaPartidasResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImportacaoPartidaService {

    private final CartolaClient cartolaClient;
    private final GravacaoPartidaService gravacaoPartidaService;

    public ImportacaoPartidaService(CartolaClient cartolaClient, GravacaoPartidaService gravacaoPartidaService) {
        this.cartolaClient = cartolaClient;
        this.gravacaoPartidaService = gravacaoPartidaService;
    }

    public int importar(Integer temporada, Integer rodada)
        throws IOException, InterruptedException {

        if (temporada == null || temporada <= 0 || rodada == null || rodada <= 0) {
            throw new IllegalArgumentException("Temporada e rodada devem ser positivas.");
        }

        CartolaMercadoStatusResponse status = cartolaClient.buscarMercadoStatus();

        if (status == null || status.temporada() == null || status.rodadaAtual() == null) {
            throw new IOException("O Cartola não informou a temporada ou a rodada atual.");
        }

        if (!temporada.equals(status.temporada())) {
            throw new IllegalArgumentException("A temporada solicitada difere da temporada atual da API.");
        }

        if (rodada > status.rodadaAtual()) {
            throw new IllegalArgumentException("Esta importação permite somente rodadas até a atual.");
        }

        CartolaPartidasResponse dados = cartolaClient.buscarPartidas(rodada);

        if (dados == null || !rodada.equals(dados.rodada())) {
            throw new IOException("A resposta do Cartola não corresponde à rodada solicitada.");
        }

        return gravacaoPartidaService.salvar(temporada, dados);


    }


}
