package analise_cartola.integracao.cartola;

import analise_cartola.integracao.cartola.dto.CartolaEscalacaoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class CartolaClient {
    private final HttpClient httpClient;
    private final String baseURL;
    private final JsonMapper jsonMapper;

    public CartolaClient(@Value("${cartola.api.base-url}") String baseURL,
                         JsonMapper jsonMapper) {
            this.httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10)).build();
            this.baseURL = baseURL;
            this.jsonMapper = jsonMapper;
    }

    public HttpRequest getRequest(String caminho) {
        return HttpRequest.newBuilder()
                .uri(URI.create(baseURL + caminho))
                .timeout(Duration.ofSeconds(20))
                .header("User-Agent", "AnaliseCartola/0.1")
                .header("Accept", "application/json")
                .GET()
                .build();
    }

    public CartolaEscalacaoResponse buscarEscalacao(Long idTime, int rodada)
        throws IOException, InterruptedException {

        HttpRequest request = getRequest("/time/id/" + idTime + "/" + rodada);

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            if(response.statusCode() != 200){
                throw new IOException("Erro ao consulta escalação. " + response.statusCode());
            }

            return jsonMapper.readValue(response.body(), CartolaEscalacaoResponse.class);
            //return response.body();
    }
}


