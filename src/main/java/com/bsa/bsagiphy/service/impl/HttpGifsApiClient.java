package com.bsa.bsagiphy.service.impl;

import com.bsa.bsagiphy.entity.Gif;
import com.bsa.bsagiphy.service.GifsApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

@Log4j2
@Service
public class HttpGifsApiClient implements GifsApiClient {

    @Value("${api.url}")
    private String gifsApiUrl;

    @Value("${api.url.download}")
    private String gifsApiDownloadUrl;

    @Value("${api.key}")
    private String gifsApiKey;

    @Value("${resources.location.cache}")
    private String pathToCache;

    private final String fileExtension = ".gif";

    private final HttpClient client;

    @Autowired
    public HttpGifsApiClient(HttpClient client) {
        this.client = client;
    }

    @Override
    public Gif getGif(String query) {
        try {
            var response = client.send(buildGetRequest(buildURI(query)), HttpResponse.BodyHandlers.ofString());
            var gif = parseResponseToGif(response);

            gif.setName(query);
            gif.setPath(Paths.get(pathToCache, query, gif.getId() + fileExtension).toString());

            downloadGif(gif);

            return gif;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

            //todo
            return null;
        }
    }

    private HttpRequest buildGetRequest(URI uri) {
        return HttpRequest.newBuilder(uri).GET().build();
    }

    private URI buildURI(String query) {
        return URI.create(String.format(
                "%s?api_key=%s&tag=%s",
                gifsApiUrl,
                gifsApiKey,
                query
        ));
    }

    @SneakyThrows
    private Gif parseResponseToGif(HttpResponse<String> response) {
        var objectMapper = new ObjectMapper();
        String id = objectMapper.readTree(response.body()).at("/data/id").toString().replace("\"", "");

        var gif = new Gif();
        gif.setId(id);

        return gif;
    }

    @SneakyThrows
    private void downloadGif(Gif gif) {
        try (InputStream source = new URL(prepareUrlForDownloading(gif.getId())).openStream()) {
            var dest = new File(gif.getPath());
            FileUtils.copyInputStreamToFile(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String prepareUrlForDownloading(String id) {
        return gifsApiDownloadUrl.replace("{id}", id);
    }

}
