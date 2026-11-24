package analise_cartola.integracao.cartola.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CartolaMercadoStatusResponse(
        Integer temporada,
        @JsonProperty("rodada_atual") Integer rodadaAtual,
        @JsonProperty("status_mercado") Integer statusMercado,
        @JsonProperty("mercado_pos_rodada") Boolean mercadoPosRodada,
        @JsonProperty("bola_rolando") Boolean bolaRolando,
        @JsonProperty("game_over") Boolean gameOver
){
}
