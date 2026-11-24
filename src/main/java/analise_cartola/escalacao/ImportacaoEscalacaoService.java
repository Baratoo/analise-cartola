package analise_cartola.escalacao;

import analise_cartola.integracao.cartola.CartolaClient;
import analise_cartola.integracao.cartola.dto.CartolaEscalacaoResponse;
import analise_cartola.integracao.cartola.dto.CartolaMercadoStatusResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImportacaoEscalacaoService {

    private final CartolaClient cartolaClient;
    private final GravacaoEscalacaoService gravacaoEscalacaoService;
    private final EscalacaoRepository escalacaoRepository;

    public ImportacaoEscalacaoService(CartolaClient cartolaClient, GravacaoEscalacaoService gravacaoEscalacaoService, EscalacaoRepository escalacaoRepository) {
        this.cartolaClient = cartolaClient;
        this.gravacaoEscalacaoService = gravacaoEscalacaoService;
        this.escalacaoRepository = escalacaoRepository;
    }

    public Escalacao importar(Integer temporada, Long timeId, Integer rodada)
        throws IOException, InterruptedException{

        if (temporada == null || timeId == null || rodada == null || temporada <= 0 || timeId <= 0 || rodada <= 0) {
            throw new IllegalArgumentException("Temporada, time e rodada devem ser informados com valores positivos.");
        }

        Optional<Escalacao> existente = escalacaoRepository.findByTemporadaAndTimeIdAndRodada(temporada, timeId, rodada);

        //Rodada que ja passou e esta no banco nao altera
        if  (existente.isPresent() && existente.get().isConsolidada()) {
            return existente.get();
        }

        validaMercado(temporada, rodada);

        CartolaEscalacaoResponse response = cartolaClient.buscarEscalacao(timeId, rodada);

        if (response == null || response.time() == null || !timeId.equals(response.time().id()) || !rodada.equals(response.rodada())) {
            throw new IOException("A resposta do Cartola não corresponde ao time e à rodada solicitados.");
        }

        Escalacao escalacao = gravacaoEscalacaoService.salvar(temporada, response);

        return escalacao;
    }

    public void validaMercado(Integer temporada, Integer rodada)
            throws IOException, InterruptedException {

        CartolaMercadoStatusResponse status = cartolaClient.buscarMercadoStatus();

        if (status == null || status.temporada() == null || status.rodadaAtual() == null) {
            throw new IOException("O Cartola não informou a temporada ou a rodada atual.");
        }

        if (!temporada.equals(status.temporada())) {
            throw new IllegalArgumentException("A temporada solicitada difere da temporada atual da API.");
        }

        if (rodada > status.rodadaAtual()) {
            throw new IllegalArgumentException("Não é permitido importar uma rodada futura.");
        }
    }
}
