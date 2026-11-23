package analise_cartola.escalacao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EscalacaoRepository extends JpaRepository<Escalacao, Long> {

    Optional<Escalacao> findByTemporadaAndTimeIdAndRodada(Integer temporada, Long idTime, Integer rodada);
}
