package analise_cartola.escalacao;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.mapping.Join;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "atleta_escalado")
public class AtletaEscalado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "escalacao_id", nullable = false)
//    private Long escalacaoId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "escalacao_id", nullable = false)
    private Escalacao escalacao;

    @Column(name = "atleta_id", nullable = false)
    private Long atletaId;

    private String apelido;

    @Column(name = "clube_id")
    private Long clubeId;

    @Column(name = "posicao_id")
    private Integer posicaoId;

    private BigDecimal pontos;

    private BigDecimal media;

    private BigDecimal preco;

    private Integer jogos;

    @Column(name = "entrou_em_campo")
    private Boolean entrouEmCampo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrigemAtletaEscalado origem;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Integer> scout;

    protected AtletaEscalado() {

    }

    public AtletaEscalado(
            Escalacao escalacao,
            OrigemAtletaEscalado origem,
            Long atletaId,
            String apelido,
            Long clubeId,
            Integer posicaoId,
            BigDecimal pontos,
            BigDecimal media,
            BigDecimal preco,
            Integer jogos,
            Boolean entrouEmCampo,
            Map<String, Integer> scout) {

        this.escalacao = escalacao;
        this.origem = origem;
        this.atletaId = atletaId;

        atualizarDados(
                apelido,
                clubeId,
                posicaoId,
                pontos,
                media,
                preco,
                jogos,
                entrouEmCampo,
                scout);
    }

    public void atualizarDados(
            String apelido,
            Long clubeId,
            Integer posicaoId,
            BigDecimal pontos,
            BigDecimal media,
            BigDecimal preco,
            Integer jogos,
            Boolean entrouEmCampo,
            Map<String, Integer> scout) {

        this.apelido = apelido;
        this.clubeId = clubeId;
        this.posicaoId = posicaoId;
        this.pontos = pontos;
        this.media = media;
        this.preco = preco;
        this.jogos = jogos;
        this.entrouEmCampo = entrouEmCampo;
        this.scout = scout == null ? null : Map.copyOf(scout);
    }

    public Long getId() {
        return id;
    }

    public Escalacao getEscalacao() {
        return escalacao;
    }

    public Long getAtletaId() {
        return atletaId;
    }

    public String getApelido() {
        return apelido;
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

    public BigDecimal getMedia() {
        return media;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getJogos() {
        return jogos;
    }

    public Boolean getEntrouEmCampo() {
        return entrouEmCampo;
    }

    public OrigemAtletaEscalado getOrigem() {
        return origem;
    }

    public Map<String, Integer> getScout() {
        return scout;
    }
}
