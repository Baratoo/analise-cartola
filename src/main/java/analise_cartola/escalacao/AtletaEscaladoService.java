package analise_cartola.escalacao;

import analise_cartola.integracao.cartola.dto.CartolaAtletaResponse;
import analise_cartola.integracao.cartola.dto.CartolaEscalacaoResponse;
import org.springframework.boot.logging.log4j2.CorrelationIdConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AtletaEscaladoService {

    private final AtletaEscaladoRepository atletaEscaladoRepository;

    public AtletaEscaladoService(AtletaEscaladoRepository atletaEscaladoRepository) {
        this.atletaEscaladoRepository = atletaEscaladoRepository;
    }

    @Transactional
    public void sincronizar(Escalacao escalacao, CartolaEscalacaoResponse dados) {

        if (dados.atletas() == null){
            throw new IllegalArgumentException("A resposta não contém a lista de atletas.");
        }

        List<AtletaEscalado> existentes = atletaEscaladoRepository.findByEscalacao_Id(escalacao.getId());

        sincronizarLista(escalacao, OrigemAtletaEscalado.ATLETAS, dados.atletas(), existentes);

        if (dados.reservas() != null){
            sincronizarLista(escalacao, OrigemAtletaEscalado.RESERVAS, dados.atletas(), existentes);
        }

    }


    public void sincronizarLista(Escalacao escalacao, OrigemAtletaEscalado origem, List<CartolaAtletaResponse> recebidos,
                                 List<AtletaEscalado> existentes) {

        Map<Long, AtletaEscalado> pendentes = new HashMap<>();

        for(AtletaEscalado existente : existentes) {
            if (existente.getOrigem() == origem){
                pendentes.put(existente.getAtletaId(), existente);
            }
        }

        Set<Long> idsRecebidos = new HashSet<>();

        for (CartolaAtletaResponse recebido : recebidos){
            if (recebido == null || recebido.id() == null || !idsRecebidos.add(recebido.id())){
                throw new IllegalArgumentException("A lista contém atleta sem ID ou repetido");
            }

            AtletaEscalado atleta = pendentes.remove(recebido.id());

            if (atleta == null) {
                atleta = new AtletaEscalado(
                        escalacao,
                        origem,
                        recebido.id(),
                        recebido.nome(),
                        recebido.clubeId(),
                        recebido.posicaoId(),
                        recebido.pontos(),
                        recebido.media(),
                        recebido.preco(),
                        recebido.jogos(),
                        recebido.entrouEmCampo(),
                        recebido.scout());
            } else {
                atleta.atualizarDados(
                        recebido.nome(),
                        recebido.clubeId(),
                        recebido.posicaoId(),
                        recebido.pontos(),
                        recebido.media(),
                        recebido.preco(),
                        recebido.jogos(),
                        recebido.entrouEmCampo(),
                        recebido.scout());
            }

            atletaEscaladoRepository.save(atleta);
        }

        atletaEscaladoRepository.deleteAll(pendentes.values());
    }

}
