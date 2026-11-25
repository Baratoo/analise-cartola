package analise_cartola.escalacao;

import analise_cartola.integracao.cartola.dto.CartolaEscalacaoResponse;
import analise_cartola.time.TimeCartola;
import analise_cartola.time.TimeCartolaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GravacaoEscalacaoService {

    private final TimeCartolaService timeCartolaService;
    private final EscalacaoRepository escalacaoRepository;
    private final AtletaEscaladoService atletaEscaladoService;

    public GravacaoEscalacaoService(TimeCartolaService timeCartolaService, EscalacaoRepository escalacaoRepository,
                                    AtletaEscaladoService atletaEscaladoService) {
        this.timeCartolaService = timeCartolaService;
        this.escalacaoRepository = escalacaoRepository;
        this.atletaEscaladoService = atletaEscaladoService;
    }

    @Transactional
    public Escalacao salvar(Integer temporada, CartolaEscalacaoResponse dados, boolean podeConsolidar){

        TimeCartola time = timeCartolaService.salvarOuAtualizar(dados.time());

        Escalacao escalacao = escalacaoRepository.findByTemporadaAndTimeIdAndRodada(temporada, time.getId(), dados.rodada()).
                orElseGet(() -> new Escalacao(temporada, time.getId(), dados.rodada()));

        escalacao.atualizarDados(dados.capitaoId(), dados.reservaLuxoId(), dados.esquemaId(), dados.pontos(), dados.patrimonio());

        Escalacao escalacaoSalva = escalacaoRepository.save(escalacao);

        atletaEscaladoService.sincronizar(escalacaoSalva,  dados);

        if (podeConsolidar && dados.atletas() != null && !dados.atletas().isEmpty()) {
            escalacaoSalva.consolidar();
        }

        return escalacaoSalva;
    }

}
