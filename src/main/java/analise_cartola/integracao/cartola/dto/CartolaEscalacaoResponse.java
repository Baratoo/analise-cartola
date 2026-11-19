package analise_cartola.integracao.cartola.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CartolaEscalacaoResponse(
        CartolaTimeResponse time,
        List<CartolaAtletaResponse> atletas,
        List<CartolaAtletaResponse> reservas,
        @JsonProperty("rodada_atual") Integer rodada,
        @JsonProperty("capitao_id") Long capitaoId,
        @JsonProperty("reserva_luxo_id") Long reservaLuxoId,
        @JsonProperty("esquema_id") Integer esquemaId,
        BigDecimal pontos,
        BigDecimal patrimonio
) {
}
