package com.bsa.bsagiphy.service;

import com.bsa.bsagiphy.entity.Gif;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;

@Component
public class HttpGifsApiClient implements GifsApiClient {

    @Value("${api.url}")
    private String gifsApiUrl;

    @Value("${api.url.download}")
    private String gifsApiDownloadUrl;

    @Value("${api.key}")
    private String gifsApiKey;

    @Value("${resources.location.cache}")
    private String pathToCache;

    private final HttpClient client;

    @Autowired
    public HttpGifsApiClient(HttpClient client) {
        this.client = client;
    }

    @Override
    public Gif getGif(String query) {
        try {
            var response = client.send(buildGetRequest(buildURI(query)), HttpResponse.BodyHandlers.ofString());

            var objectMapper = new ObjectMapper();
            var type = objectMapper.readTree(response.body()).at("/data/type").toString().replace("\"", "");
            var id = objectMapper.readTree(response.body()).at("/data/id").toString().replace("\"", "");

            var gif = new Gif(
                    id, query, id + "." + type);

            downloadGif(gif);

            return gif;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

            return null;
        }
    }

    private HttpRequest buildGetRequest(URI uri) {
        return HttpRequest.newBuilder(uri).GET().build();
    }

    private URI buildURI(String query) {
        return URI.create(String.format(
                "%s?api_key=%s&query=%s",
                gifsApiUrl,
                gifsApiKey,
                query
        ));
    }

    private void downloadGif(Gif gif) {
        try (InputStream in = new URL(prepareUrl(gif.getId())).openStream()) {
            Files.copy(in, new File(pathToCache + gif.getPath()).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String prepareUrl(String id) {
        return gifsApiDownloadUrl.replace("{id}", id);
    }

}