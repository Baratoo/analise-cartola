package analise_cartola.escalacao;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

@Entity
@Table(name = "escalacao")
public class Escalacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer temporada;

    @Column(name = "time_id", nullable = false)
    private Long timeId;

    @Column(nullable = false)
    private Integer rodada;

    @Column(name = "capitao_id")
    private Long capitaoId;

    @Column(name = "reserva_luxo_id")
    private Long reservaLuxoId;

    @Column(name = "esquema_id")
    private Integer esquemaId;

    private BigDecimal pontos;

    private BigDecimal patrimonio;

    @Column(name = "atualizado_em", nullable = false)
    private Instant atualizadoEm;

    @Column(nullable = false)
    private boolean consolidada;

    protected Escalacao() {

    }

    //Identificacoes da rodada
    public Escalacao(Integer temporada, Long timeId, Integer rodada) {
        this.temporada = temporada;
        this.timeId = timeId;
        this.rodada = rodada;
        this.atualizadoEm = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public Long getTimeId() {
        return timeId;
    }

    public Integer getRodada() {
        return rodada;
    }

    public Long getCapitaoId() {
        return capitaoId;
    }

    public Long getReservaLuxoId() {
        return reservaLuxoId;
    }

    public Integer getEsquemaId() {
        return esquemaId;
    }

    public BigDecimal getPontos() {
        return pontos;
    }

    public BigDecimal getPatrimonio() {
        return patrimonio;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    public boolean isConsolidada() { return consolidada; }

    public void atualizarDados(Long capitaoId, Long reservaLuxoId, Integer esquemaId, BigDecimal pontos, BigDecimal patrimonio) {
        this.capitaoId = capitaoId;
        this.reservaLuxoId = reservaLuxoId;
        this.esquemaId = esquemaId;
        this.pontos = pontos;
        this.patrimonio = patrimonio;
        this.atualizadoEm = Instant.now();
    }

}

