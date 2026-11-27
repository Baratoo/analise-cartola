package analise_cartola.desempenho;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImportacaoDesempenhoRepository extends JpaRepository<ImportacaoDesempenho, Long> {

    Optional<ImportacaoDesempenho> findByTemporadaAndRodada(Integer temporada, Integer rodada);

}
