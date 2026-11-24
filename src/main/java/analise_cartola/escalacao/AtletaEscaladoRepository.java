package analise_cartola.escalacao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtletaEscaladoRepository extends JpaRepository<Escalacao, Long> {

    List<AtletaEscalado> findByEscalacao_Id(Long escalacaoId);
}
