package analise_cartola.desempenho;

import jakarta.persistence.*;

import java.time.Instant;

//Usada para consolidar a temporada, identificar se ainda precisa importar ou nao......
@Entity
@Table(name = "importacao_desempenho")
public class ImportacaoDesempenho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer temporada;

    @Column(nullable = false)
    private Integer rodada;

    @Column(name = "quantidade_atletas", nullable = false)
    private int quantidadeAtletas;

    @Column(nullable = false)
    private boolean consolidada;

    @Column(name = "atualizado_em", nullable = false)
    private Instant atualizadoEm;

    protected ImportacaoDesempenho() {

    }

    public ImportacaoDesempenho(Integer temporada, Integer rodada) {
        this.temporada = temporada;
        this.rodada = rodada;
        this.atualizadoEm = Instant.now();
    }

    public void atualizarDados(int quantidadeAtletas, boolean consolidada) {
        if (quantidadeAtletas < 0) {
            throw new IllegalArgumentException(
                    "A quantidade de atletas não pode ser negativa.");
        }

        this.quantidadeAtletas = quantidadeAtletas;
        this.consolidada = consolidada;
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

    public int getQuantidadeAtletas() {
        return quantidadeAtletas;
    }

    public boolean isConsolidada() {
        return consolidada;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }
}