package analise_cartola.escalacao;

import analise_cartola.integracao.cartola.CartolaClient;
import analise_cartola.integracao.cartola.dto.CartolaEscalacaoResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImportacaoEscalacaoService {

    private final CartolaClient cartolaClient;
    private final GravacaoEscalacaoService gravacaoEscalacaoService;

    public ImportacaoEscalacaoService(CartolaClient cartolaClient, GravacaoEscalacaoService gravacaoEscalacaoService) {
        this.cartolaClient = cartolaClient;
        this.gravacaoEscalacaoService = gravacaoEscalacaoService;
    }

    public Escalacao importar(Integer temporada, Long timeId, Integer rodada)
        throws IOException, InterruptedException{

        CartolaEscalacaoResponse response = cartolaClient.buscarEscalacao(timeId, rodada);

        if (response == null || response.time() == null
                || !timeId.equals(response.time().id()) || !rodada.equals(response.rodada())) {
            throw new IOException(
                    "A resposta do Cartola não corresponde ao time e à rodada solicitados.");
        }

        Escalacao escalacao = gravacaoEscalacaoService.salvar(temporada, response);

        return escalacao;
    }
}
