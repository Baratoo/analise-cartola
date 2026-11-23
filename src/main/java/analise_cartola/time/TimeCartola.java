package analise_cartola.time;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "time_cartola")
public class TimeCartola {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "nome_cartola")
    private String nomeCartola;

    private String slug;

    @Column(name = "url_escudo", columnDefinition = "TEXT")
    private String urlEscudo;

    @Column(name = "atualizado_em", nullable = false)
    private Instant atualizadoEm;

    protected TimeCartola() {

    }

    public TimeCartola(Long id, String nome, String nomeCartola, String slug, String urlEscudo) {
        this.id = id;
        atualizarDados(nome, nomeCartola, slug, urlEscudo);
    }

    public void atualizarDados(String nome, String nomeCartola, String slug, String urlEscudo){
        this.nome = nome;
        this.nomeCartola = nomeCartola;
        this.slug = slug;
        this.urlEscudo = urlEscudo;
        this.atualizadoEm = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeCartola() {
        return nomeCartola;
    }

    public String getSlug() {
        return slug;
    }

    public String getUrlEscudo() {
        return urlEscudo;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }
}
