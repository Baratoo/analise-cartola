package analise_cartola.partida;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name="partida")
public class Partida {

    @Id
    private Long id;

    @Column(nullable = false)
    private Integer temporada;

    @Column(nullable = false)
    private Integer rodada;

    @Column(nullable = false, name = "clube_mandante_id")
    private Long clubeMandanteId;

    @Column(nullable = false, name = "clube_visitante_id")
    private Long clubeVisitanteId;

    @Column(name="gols_mandante")
    private Integer golsMandante;

    @Column(name="gols_visitante")
    private Integer golsVisitante;

    @Column(name = "data_hora")
    private Instant dataHora;

    private Boolean valida;

    @Column(name = "atualizado_em", nullable = false)
    private Instant atualizadoEm;

    protected Partida() {}

    public Partida(Long id, Integer temporada, Integer rodada) {
        this.id = id;
        this.temporada = temporada;
        this.rodada = rodada;
        this.atualizadoEm = Instant.now();
    }

    public void atualizarDados(Long clubeMandanteId, Long clubeVisitanteId,
                               Integer golsMandante, Integer golsVisitante,
                               Instant dataHora, Boolean valida) {
        this.clubeMandanteId = clubeMandanteId;
        this.clubeVisitanteId = clubeVisitanteId;
        this.golsMandante = golsMandante;
        this.golsVisitante = golsVisitante;
        this.dataHora = dataHora;
        this.valida = valida;
        this.atualizadoEm = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public Integer getRodada() {
        return rodada;
    }

    public Long getClubeMandanteId() {
        return clubeMandanteId;
    }

    public Long getClubeVisitanteId() {
        return clubeVisitanteId;
    }

    public Integer getGolsMandante() {
        return golsMandante;
    }

    public Integer getGolsVisitante() {
        return golsVisitante;
    }

    public Instant getDataHora() {
        return dataHora;
    }

    public Boolean getValida() {
        return valida;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }
}
