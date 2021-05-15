package com.erdi.shortlink;


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public final class ShortlinkUtil {

    private ShortlinkUtil() {
    }

    private static String expandSingleLevel(String url) {

        try {
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();


            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();

            CompletableFuture<HttpResponse<String>> response =
                    client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.thenApply(HttpResponse::statusCode).get(5, TimeUnit.SECONDS);

            if (statusCode != 301 && statusCode != 302 && statusCode != 308) {
                return url;
            }
            return response.get().headers().map().get(HttpHeaders.LOCATION).get(0);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return url;
        }
    }

    public static String expand(String urlArg) throws IOException {
        String originalUrl = urlArg;
        String newUrl = expandSingleLevel(originalUrl);

        while (!originalUrl.equals(newUrl)) {
            originalUrl = newUrl;
            newUrl = expandSingleLevel(originalUrl);

        }
        return newUrl;
    }

}
