package com.andy.moneywizparser.util;

import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileUtil {

    public static Flux<String> readLines(String fileName) {
        return Flux.from(subscriber -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    subscriber.onNext(line);
                }
                subscriber.onComplete();
            } catch (IOException e) {
                subscriber.onError(e);
            }
        });
    }
}
