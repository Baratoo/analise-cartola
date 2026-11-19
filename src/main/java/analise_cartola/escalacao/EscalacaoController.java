package analise_cartola.escalacao;

import analise_cartola.integracao.cartola.CartolaClient;
import analise_cartola.integracao.cartola.dto.CartolaEscalacaoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/times")
public class EscalacaoController {
    private CartolaClient cartolaClient;

    public EscalacaoController(CartolaClient cartolaClient) {
        this.cartolaClient = cartolaClient;
    }

    @GetMapping(value="/{timeId}/escalacoes/{rodada}",
                produces = "application/json")
    public CartolaEscalacaoResponse buscarEscalacao(@PathVariable("timeId") long timeId,
                                                    @PathVariable("rodada") int rodada)
            throws IOException, InterruptedException{

        return cartolaClient.buscarEscalacao(timeId, rodada);
    }
}
