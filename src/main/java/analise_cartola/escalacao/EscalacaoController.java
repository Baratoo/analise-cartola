package analise_cartola.escalacao;

import analise_cartola.integracao.cartola.CartolaClient;
import analise_cartola.integracao.cartola.dto.CartolaEscalacaoResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/times")
public class EscalacaoController {
    private CartolaClient cartolaClient;
    private final ImportacaoEscalacaoService importacaoEscalacaoService;

    public EscalacaoController(CartolaClient cartolaClient, ImportacaoEscalacaoService importacaoEscalacaoService) {
        this.cartolaClient = cartolaClient;
        this.importacaoEscalacaoService = importacaoEscalacaoService;
    }

    @GetMapping(value="/{timeId}/escalacoes/{rodada}",
                produces = "application/json")
    public CartolaEscalacaoResponse buscarEscalacao(@PathVariable("timeId") long timeId,
                                                    @PathVariable("rodada") int rodada)
            throws IOException, InterruptedException{

        return cartolaClient.buscarEscalacao(timeId, rodada);
    }

    @PostMapping(value = "/{timeId}/escalacoes/{rodada}/importacao",
                produces = "application/json")
    public Escalacao importarEscalacao(@PathVariable("timeId") Long timeId,
                                       @PathVariable("rodada") Integer rodada)
            throws IOException, InterruptedException{
        return importacaoEscalacaoService.importar(2026, timeId, rodada);
    }
}
