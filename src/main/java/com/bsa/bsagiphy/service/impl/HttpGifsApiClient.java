package com.bsa.bsagiphy.service.impl;

import com.bsa.bsagiphy.entity.Gif;
import com.bsa.bsagiphy.service.GifsApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    @Value("${resources.file-extension}")
    private String fileExtension;

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
            var id = objectMapper.readTree(response.body()).at("/data/id").toString().replace("\"", "");

            var gif = new Gif(
                    id, query, Paths.get(pathToCache, query, id + fileExtension).toString());

            log.error(gif.getPath());
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
                "%s?api_key=%s&tag=%s",
                gifsApiUrl,
                gifsApiKey,
                query
        ));
    }

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
