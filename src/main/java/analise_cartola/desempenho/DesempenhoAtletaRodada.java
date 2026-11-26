package analise_cartola.desempenho;

import analise_cartola.atleta.Atleta;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Entity
@Table(name="desempenho_atleta_rodada")
public class DesempenhoAtletaRodada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="atleta_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Atleta atleta;

    @Column(nullable = false)
    private Integer temporada;

    @Column(nullable = false)
    private Integer rodada;

    @Column(name = "clube_id")
    private Long clubeId;

    @Column(name = "posicao_id")
    private Integer posicaoId;

    private BigDecimal pontos;

    @Column(name = "entrou_em_campo")
    private Boolean entrouEmCampo;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Integer> scout;

    @Column(name = "atualizado_em", nullable = false)
    private Instant atualizadoEm;

    protected DesempenhoAtletaRodada() {

    }

    public DesempenhoAtletaRodada(Atleta atleta, Integer temporada, Integer rodada) {
        this.atleta = atleta;
        this.temporada = temporada;
        this.rodada = rodada;
        this.atualizadoEm = Instant.now();
    }

    public void atualizarDados(Long clubeId, Integer posicaoId,
                               BigDecimal pontos, Boolean entrouEmCampo,
                               Map<String, Integer> scout) {
        this.clubeId = clubeId;
        this.posicaoId = posicaoId;
        this.pontos = pontos;
        this.entrouEmCampo = entrouEmCampo;
        this.scout = scout == null ? null : Map.copyOf(scout);
        this.atualizadoEm = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Atleta getAtleta() {
        return atleta;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public Integer getRodada() {
        return rodada;
    }

    public Long getClubeId() {
        return clubeId;
    }

    public Integer getPosicaoId() {
        return posicaoId;
    }

    public BigDecimal getPontos() {
        return pontos;
    }

    public Boolean getEntrouEmCampo() {
        return entrouEmCampo;
    }

    public Map<String, Integer> getScout() {
        return scout;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }
}
