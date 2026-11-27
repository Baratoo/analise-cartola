package analise_cartola.partida;

import analise_cartola.integracao.cartola.CartolaClient;
import analise_cartola.integracao.cartola.dto.CartolaPartidasResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private final CartolaClient cartolaClient;
    private final ImportacaoPartidaService importacaoPartidaService;

    public PartidaController(CartolaClient cartolaClient, ImportacaoPartidaService importacaoPartidaService) {
        this.cartolaClient = cartolaClient;
        this.importacaoPartidaService = importacaoPartidaService;
    }

    public CartolaPartidasResponse buscarPartidas(@PathVariable("rodada") Integer rodada)
        throws IOException, InterruptedException {

        if (rodada <= 0){
            throw new IllegalArgumentException("rodada invalida");
        }

        return cartolaClient.buscarPartidas(rodada);
    }

    @PostMapping("/rodadas/{rodada}/importacao")
    public int importar(@PathVariable("rodada") Integer rodada,
                        @RequestParam("temporada") Integer temporada)
        throws IOException, InterruptedException {

        return importacaoPartidaService.importar(temporada, rodada);
        }

}
