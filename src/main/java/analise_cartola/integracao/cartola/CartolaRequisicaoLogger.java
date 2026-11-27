package analise_cartola.integracao.cartola;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class CartolaRequisicaoLogger {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CartolaRequisicaoLogger.class);

    private static final DateTimeFormatter FORMATO_PASTA =
            DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private static final DateTimeFormatter FORMATO_HORARIO =
            DateTimeFormatter.ofPattern("HH-mm-ss-SSS");

    private final Path diretorio;

    public CartolaRequisicaoLogger(
            @Value("${cartola.api.logs-diretorio}") String diretorio) {
        this.diretorio = Path.of(diretorio);
    }

    public void registrarResposta(HttpRequest request,
                                  HttpResponse<String> response,
                                  ZonedDateTime inicio,
                                  long duracaoMs) {

        String resultado = "Status HTTP: " + response.statusCode()
                + "\n\nRESPOSTA:\n" + response.body();

        gravar(request, inicio, duracaoMs, resultado);
    }

    public void registrarFalha(HttpRequest request,
                               Exception erro,
                               ZonedDateTime inicio,
                               long duracaoMs) {

        StringWriter detalhes = new StringWriter();
        erro.printStackTrace(new PrintWriter(detalhes));

        String resultado = "Status HTTP: sem resposta"
                + "\n\nERRO:\n" + detalhes;

        gravar(request, inicio, duracaoMs, resultado);
    }

    private void gravar(HttpRequest request, ZonedDateTime inicio,
                        long duracaoMs, String resultado) {

        String caminho = request.uri().getPath()
                .replaceAll("[^a-zA-Z0-9_-]", "_");

        String nomeArquivo = FORMATO_HORARIO.format(inicio)
                + "_" + UUID.randomUUID()
                + "_" + request.method()
                + caminho + ".log";

        Path pasta = diretorio.resolve(FORMATO_PASTA.format(inicio));
        Path arquivo = pasta.resolve(nomeArquivo);

        String conteudo = """
                Data: %s
                Método: %s
                URL: %s
                Duração: %d ms
                %s
                """.formatted(
                inicio,
                request.method(),
                request.uri(),
                duracaoMs,
                resultado);

        try {
            Files.createDirectories(pasta);

            Files.writeString(
                    arquivo,
                    conteudo,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE_NEW,
                    StandardOpenOption.WRITE);

        } catch (IOException | SecurityException erro) {
            LOGGER.warn("Não foi possível gravar o log em {}", arquivo, erro);
        }
    }
}