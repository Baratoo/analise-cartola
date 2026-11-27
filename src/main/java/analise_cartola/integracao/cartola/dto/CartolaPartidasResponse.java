package analise_cartola.integracao.cartola.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//ligacao de partidas daquela rodada
@JsonIgnoreProperties(ignoreUnknown = true)
public record CartolaPartidasResponse(
        Integer rodada,
        List<CartolaPartidaResponse> partidas
) {
}
