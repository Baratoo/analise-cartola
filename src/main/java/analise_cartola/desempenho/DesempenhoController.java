package analise_cartola.desempenho;

import analise_cartola.integracao.cartola.CartolaClient;
import analise_cartola.integracao.cartola.dto.CartolaPontuadosResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/desempenhos")
public class DesempenhoController {

    private final CartolaClient cartolaClient;

    public DesempenhoController(CartolaClient cartolaClient) {
        this.cartolaClient = cartolaClient;
    }

    @GetMapping("/rodadas/{rodada}")
    public CartolaPontuadosResponse buscarPontuados(@PathVariable("rodada") Integer rodada)
        throws IOException, InterruptedException {

        if (rodada <= 0){
            throw new IllegalArgumentException("rodada invalida");
        }

        return cartolaClient.buscarPontuados(rodada);
    }

}
