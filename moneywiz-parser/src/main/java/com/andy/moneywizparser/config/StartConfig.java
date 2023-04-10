package com.andy.moneywizparser.config;

import com.andy.moneywizparser.model.Csv;
import com.andy.moneywizparser.model.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

@Configuration
public class StartConfig {

    @Bean
    public ApplicationRunner run() {
        return args -> {
            String filePath = "/Users/andy/Documents/project/moneywiz-parser/moneywiz-parser/src/main/resources/file/export_report.csv";
            FileReader fileReader = new FileReader(filePath);
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
            ConnectableFlux<Csv> csvConnectableFlux = Flux.fromStream(csvParser.stream())
                    .filter(record -> {
                        String first = record.get(0);
                        return !StringUtils.hasText(first);
                    })
                    .filter(record -> "TWD".equals(record.get(11)))
                    .map(record -> {
                        String source = record.get(2).trim();
                        String transfers = record.get(3).trim();
                        String description = record.get(4).trim();
                        String originCategory = record.get(6).trim();
                        String date = record.get(7).trim();
                        String time = record.get(8).trim();
                        String note = record.get(9).trim();
                        Long amount = Long.parseLong(record.get(10).replaceAll(",", ""));

                        return Csv.builder()
                                .source(source)
                                .transfers(transfers)
                                .description(description)
                                .category(originCategory)
                                .date(date)
                                .time(time)
                                .note(note)
                                .amount(amount).build();
                    }).publish();

            Flux<Transaction> transferFlux = csvConnectableFlux.filter(c -> StringUtils.hasText(c.getTransfers()))
                    .map(csv -> {
                        Transaction.TransactionBuilder builder = Transaction.builder();
                        boolean isTransferToMe = csv.getAmount() > 0;
                        if (isTransferToMe) {
                            builder.source(csv.getTransfers())
                                    .destination(csv.getSource());
                        } else {
                            builder.source(csv.getSource())
                                    .destination(csv.getTransfers());
                        }
                        builder.amount(Math.abs(csv.getAmount()));
                        setCommonInfo(csv, builder);
                        return builder.build();
                    });

            Flux<Transaction> consumeFlux = csvConnectableFlux.filter(c -> !StringUtils.hasText(c.getTransfers()))
                    .map(csv -> {
                        Transaction.TransactionBuilder builder = Transaction.builder();
                        boolean isTransferToMe = csv.getAmount() > 0;
                        String originCategory = csv.getCategory();
                        String[] split = originCategory.split(">");
                        if (isTransferToMe) {
                            builder.source(split[0].trim())
                                    .destination(csv.getSource());
                        } else {
                            builder.source(csv.getSource());
                            if (split.length > 1) {
                                builder.category(split[0].trim());
                                builder.destination(split[1].trim());
                            } else {
                                builder.destination(split[0].trim());
                            }
                        }
                        builder.amount(Math.abs(csv.getAmount()));
                        setCommonInfo(csv, builder);
                        return builder.build();
                    });


            Flux<Transaction> transactionFlux = Flux.merge(transferFlux, consumeFlux)
                    .filter(t -> !"借貸".equals(t.getSource()) && !"借貸".equals(t.getDestination()))
                    .filter(t -> (!"股票張戶".equals(t.getSource()) && !"股票張戶".equals(t.getDestination())) && (!"股票".equals(t.getSource()) && !"股票".equals(t.getDestination())));




//            transactionFlux.doOnNext(System.out::println).subscribe();

            transactionFlux.distinct(Transaction::getSource)
                    .map(Transaction::getSource)
                    .doOnNext(System.out::println)
                    .subscribe();

//            transactionFlux.distinct(Transaction::getDestination)
//                    .map(Transaction::getDestination)
//                    .doOnNext(System.out::println)
//                    .subscribe();
            csvConnectableFlux.connect();
        };
    }

    private void setCommonInfo(Csv record, Transaction.TransactionBuilder builder) {
        String date = record.getDate();
        String time = record.getTime();
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy/dd/MM"));
        LocalDateTime localDateTime = setTime(localDate, time);
        String dateFormat = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm"));
        builder.description(record.getDescription())
                .note(record.getNote()).date(dateFormat);
    }

    private LocalDateTime setTime(LocalDate localDate, String time) {
        LocalDateTime from = LocalDateTime.from(localDate.atStartOfDay());
        if (StringUtils.hasText(time)) {
            boolean isAm = time.contains("上午");
            if (isAm) {
                time = time.replace("上午", "");
            } else {
                time = time.replace("下午", "");
            }
            time = Arrays.stream(time.split(":")).map(s -> {
                //fill 0 for 1 digit
                if (s.length() == 1) {
                    return "0" + s;
                }
                return s;
            }).collect(Collectors.joining(":"));
            if (isAm) {
                time = time + ":00AM";
            } else {
                time = time + ":00PM";
            }

            DateTimeFormatter dtfInput = DateTimeFormatter.ofPattern("h:m:sa", Locale.ENGLISH);
            LocalTime localTime = LocalTime.parse(time, dtfInput);
            return from.withHour(localTime.getHour()).withMinute(localTime.getMinute());
        }
        return from;
    }
}
