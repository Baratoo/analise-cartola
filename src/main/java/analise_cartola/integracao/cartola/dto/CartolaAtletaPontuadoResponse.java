package analise_cartola.integracao.cartola.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CartolaAtletaPontuadoResponse(
        String apelido,
        BigDecimal pontuacao,
        @JsonProperty("posicao_id") Integer posicaoId,
        @JsonProperty("clube_id") Long clubeId,
        @JsonProperty("entrou_em_campo") Boolean entrouEmCampo,
        Map<String, Integer> scout
){

}
