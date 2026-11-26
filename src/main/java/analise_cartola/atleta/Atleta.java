package analise_cartola.atleta;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name="atleta")
public class Atleta {

    @Id
    private Long id;

    @Column(length = 255)
    private String apelido;

    @Column(name = "atualizado_em", nullable = false)
    private Instant atualizadoEm;

    protected Atleta() {

    }

    public Atleta(Long id, String apelido) {
        this.id = id;
        atualizarDados(apelido);
    }

    public void atualizarDados(String apelido){
        this.apelido = apelido;
        this.atualizadoEm = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getApelido() {
        return apelido;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }
}
