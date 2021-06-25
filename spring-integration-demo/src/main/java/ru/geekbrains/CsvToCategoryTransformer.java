package ru.geekbrains;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.file.transformer.AbstractFilePayloadTransformer;
import org.springframework.stereotype.Service;
import ru.geekbrains.model.Category;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvToCategoryTransformer extends AbstractFilePayloadTransformer<List<Category>> {

    private static final Logger logger = LoggerFactory.getLogger(CsvToCategoryTransformer.class);

    private CSVParser parser;

    @PostConstruct
    public void init() {

        parser = new CSVParserBuilder()
                .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
    }



    @Override
    protected List<Category> transformFile(File file) throws IOException {
        List<Category> categories = new ArrayList<>();
        try (Reader reader = new FileReader(file, StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(parser)
                .build()) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                categories.add(new Category(line[0]));
            }
            logger.info("File: {} is converted, total {} lines", file.getName(), categories.size());

        } catch (Exception e) {
            logger.error("Exception throw when converting the file {}, message: {}, root cause: {}",
                    file.getName(), e.getMessage(), e.getCause().getMessage());
        }

        return categories;
    }
}
