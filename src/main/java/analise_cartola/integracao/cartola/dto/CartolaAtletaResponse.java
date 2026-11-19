package analise_cartola.integracao.cartola.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CartolaAtletaResponse(
        @JsonProperty("atleta_id") Long id,
        String nome,
        @JsonProperty("rodada_id") Integer rodada,
        @JsonProperty("clube_id") Long clubeId,
        @JsonProperty("posicao_id") Integer posicaoId,
        @JsonProperty("pontos_num") BigDecimal pontos,
        @JsonProperty("media_num") BigDecimal media,
        @JsonProperty("preco_num") BigDecimal preco,
        @JsonProperty("jogos_num") Integer jogos,
        @JsonProperty("entrou_em_campo") Boolean entrouEmCampo,
        Map<String, Integer> scout
) {
}
