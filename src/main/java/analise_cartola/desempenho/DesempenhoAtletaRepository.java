package analise_cartola.desempenho;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesempenhoAtletaRepository extends JpaRepository<DesempenhoAtletaRodada, Long> {

    Optional<DesempenhoAtletaRodada> findByTemporadaAndRodadaAndAtleta_id(Integer temporada, Integer rodada, Long atleta_id0);

}
