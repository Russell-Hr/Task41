package com.example.Task41.cfg;

import com.example.Task41.pojo.Order;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import java.util.concurrent.Future;

@Configuration
@EnableBatchProcessing
public class FileReader {

    @Bean
    public FlatFileItemReader<Order> reader()
    {
        //Create reader instance
        FlatFileItemReader<Order> reader = new FlatFileItemReader<Order>();
        //Set input file location
        reader.setResource(new FileSystemResource("/input/inputData.csv"));
        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(1);
        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper() {
            {
                //3 columns in each row
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] { "id", "orderState", "price" });
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Order>() {
                    {
                        setTargetType(Order.class);
                    }
                });
            }
        });
        return reader;
    }
}
