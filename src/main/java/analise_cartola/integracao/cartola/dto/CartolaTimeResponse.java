package analise_cartola.integracao.cartola.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CartolaTimeResponse(
        @JsonProperty("time_id") Long id,
        String nome,
        @JsonProperty("nome_cartola") String nomeCartola,
        String slug,
        @JsonProperty("url_escudo_png")String urlEscudo
) {
}
