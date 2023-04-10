package com.andy.moneywizparser;

import com.andy.moneywizparser.util.FileUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileReader;

@SpringBootApplication
public class MoneywizParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneywizParserApplication.class, args);
	}

	@Bean
	public ApplicationRunner run() {
		return args -> {
			String filePath = "/Users/andy/Documents/project/moneywiz-parser/moneywiz-parser/src/main/resources/file/export_report.csv";
			FileReader fileReader = new FileReader(filePath);
			CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
			Flux.fromStream(csvParser.stream())
					.doOnNext(record -> {
						System.out.println(record.size());
					})
					.filter(record -> {
						return record.size() > 3;
					})
					.doOnNext(System.out::println)
					.subscribe();
		};
	}

}
