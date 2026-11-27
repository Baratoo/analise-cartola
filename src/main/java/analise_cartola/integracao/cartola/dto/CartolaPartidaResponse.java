package analise_cartola.integracao.cartola.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CartolaPartidaResponse(
    @JsonProperty("partida_id") Long id,
    @JsonProperty("clube_casa_id") Long clubeMandanteId,
    @JsonProperty("clube_visitante_id") Long clubeVisitanteId,
    @JsonProperty("placar_oficial_mandante") Integer placarOficialMandante,
    @JsonProperty("placar_oficial_visitante") Integer placarOficialVisitante,
    Long timeStamp,
    Boolean valida
){

}
