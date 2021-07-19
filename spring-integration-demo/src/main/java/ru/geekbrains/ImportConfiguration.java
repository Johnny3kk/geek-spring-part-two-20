package ru.geekbrains;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.jpa.dsl.Jpa;
import org.springframework.integration.jpa.dsl.JpaUpdatingOutboundEndpointSpec;
import org.springframework.integration.jpa.support.PersistMode;
import org.springframework.messaging.MessageHandler;
import ru.geekbrains.model.Brand;
import ru.geekbrains.model.Category;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Configuration
public class ImportConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ImportConfiguration.class);

    @Value("${source.directory.path}")
    private String sourceDirectoryPath;

    @Value("${dest.directory.path}")
    private String destDirectoryPath;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public MessageSource<File> sourceDirectory() {
        FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(sourceDirectoryPath));
        messageSource.setAutoCreateDirectory(true);
        return messageSource;
    }

    @Bean
    public MessageHandler destDirectory() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(destDirectoryPath));
        handler.setExpectReply(false);
        handler.setDeleteSourceFiles(true);
        return handler;
    }

    @Bean
    public JpaUpdatingOutboundEndpointSpec jpaPersistHandler() {
        return Jpa.outboundAdapter(this.entityManagerFactory)
                .entityClass(Brand.class)
                .persistMode(PersistMode.PERSIST);
    }

//    @Bean
    public IntegrationFlow fileMoveFlow() {
        return IntegrationFlows.from(sourceDirectory(), conf -> conf.poller(Pollers.fixedDelay(100)))
                .filter(msg -> ((File) msg).getName().endsWith(".txt"))
                .transform(new FileToStringTransformer())
                .<String, String>transform(String::toUpperCase)
                .handle(destDirectory())
                .get();
    }

    @Bean
    public IntegrationFlow updateDatabaseFlow() {
        return IntegrationFlows.from(sourceDirectory(), conf -> conf.poller(Pollers.fixedDelay(100)))
                .filter(msg -> ((File) msg).getName().endsWith(".csv"))
                .transform(new CsvToCategoryTransformer())
                .handle(jpaPersistHandler(), e -> e.transactional(true))
                .get();
    }

//    @Bean
//    public void createCsv() throws IOException {
//        String csv = sourceDirectoryPath + "/data.csv";
//        CSVWriter writer = new CSVWriter(new FileWriter(csv));
//        //Create record: Gibson Les Paul custom shop, Fender Stratocaster custom shop, Ibanez S666, Kiezel HYPERDRIVE
//        String [] record1 = "Gibson Les Paul custom shop,5500".split(",");
//        String [] record2 = "Fender Stratocaster custom shop,5900".split(",");
//        String [] record3 = "Ibanez S666,2900".split(",");
//        String [] record4 = "Kiezel HYPERDRIVE,6660".split(",");
//        //Write the record to files
//        writer.writeNext(record1);
//        writer.writeNext(record2);
//        writer.writeNext(record3);
//        writer.writeNext(record4);
//        //close the writer
//        writer.close();
//    }

}
