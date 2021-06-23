package ru.geekbrains;

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

import javax.persistence.EntityManagerFactory;
import java.io.File;

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

    @Bean
    public IntegrationFlow fileMoveFlow() {
        return IntegrationFlows.from(sourceDirectory(), conf -> conf.poller(Pollers.fixedDelay(100)))
                .filter(msg -> ((File) msg).getName().endsWith(".txt"))
                .transform(new FileToStringTransformer())
                .<String, String>transform(String::toUpperCase)
                .handle(destDirectory())
                .get();
    }

}
