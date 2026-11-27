package analise_cartola.desempenho;

import analise_cartola.escalacao.Escalacao;
import analise_cartola.integracao.cartola.CartolaClient;
import analise_cartola.integracao.cartola.dto.CartolaPontuadosResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/desempenhos")
public class DesempenhoController {

    private final CartolaClient cartolaClient;
    ImportacaoDesempenhoService importacaoDesempenhoService;

    public DesempenhoController(CartolaClient cartolaClient, ImportacaoDesempenhoService importacaoDesempenhoService) {
        this.cartolaClient = cartolaClient;
        this.importacaoDesempenhoService = importacaoDesempenhoService;
    }

    @GetMapping("/rodadas/{rodada}")
    public CartolaPontuadosResponse buscarPontuados(@PathVariable("rodada") Integer rodada)
        throws IOException, InterruptedException {

        if (rodada <= 0){
            throw new IllegalArgumentException("rodada invalida");
        }

        return cartolaClient.buscarPontuados(rodada);
    }

    @PostMapping("/rodadas/{rodada}/importacao")
    public int importar(@PathVariable("rodada") Integer rodada,
                        @RequestParam("temporada") Integer temporada)
            throws IOException, InterruptedException {

        return importacaoDesempenhoService.importar(temporada, rodada);
    }

    @PostMapping(value = "/rodadas/importacao",
            produces = "application/json")
    public Integer importarIntervalo(@RequestParam("temporada") Integer temporada,
                                     @RequestParam("rodadaInicial") Integer rodadaInicial,
                                     @RequestParam("rodadaFinal") Integer rodadaFinal)
            throws IOException, InterruptedException {

        return importacaoDesempenhoService.importarIntervalo(temporada, rodadaInicial, rodadaFinal);
    }

}
