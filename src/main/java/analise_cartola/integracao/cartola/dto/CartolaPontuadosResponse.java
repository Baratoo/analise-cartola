package analise_cartola.integracao.cartola.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CartolaPontuadosResponse(
        Map<String, CartolaAtletaPontuadoResponse> atletas,
        Integer rodada,
        @JsonProperty("total_atletas") Integer totalAtletas
) {

}
