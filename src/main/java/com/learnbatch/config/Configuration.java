package com.learnbatch.config;

import com.learnbatch.constants.SpringBatchMultiThreadingConstants;
import com.learnbatch.listener.LogProcessListener;
import com.learnbatch.listener.ProtocolListener;
import com.learnbatch.mapper.MultiThreadedFieldMapper;
import com.learnbatch.processor.MultiThreadProcessor;
import com.learnbatch.reader.MultiThreadReader;
import com.learnbatch.writer.MultiThreadWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/**
 * Created by z001qgd on 1/11/17.
 */
@org.springframework.context.annotation.Configuration
@EnableBatchProcessing
public class Configuration {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    Environment env;


    @Autowired
    DataSource dataSource;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job loadCreateJob(){
        return jobs.get(SpringBatchMultiThreadingConstants.MULTI_THREAD_JOB)
                .listener(protocolListener())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .chunk(1) //important to be one in this case to commit after every line read
                .reader(reader())
                .writer(writer())
                .processor(processor())
                .listener(logProcessListener())
                .faultTolerant()
                .build();
    }

    @Bean
    public ItemReader<String> reader(){

        FlatFileItemReader<String> reader = new FlatFileItemReader<String>();
        //reader.setLinesToSkip(1);//first line is title definition
        reader.setResource(new ClassPathResource("TestFile.txt"));
        reader.setLineMapper(lineMapper());
        return reader;
    }

    @Bean
    public LineMapper<String> lineMapper() {
        DefaultLineMapper<String> lineMapper = new DefaultLineMapper<String>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        //lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"VALUE"});

       // BeanWrapperFieldSetMapper<SuggestedPodcast> fieldSetMapper = new BeanWrapperFieldSetMapper<SuggestedPodcast>();
      //  fieldSetMapper.setTargetType(SuggestedPodcast.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(multiThreadedFieldSetMapper());

        return lineMapper;
    }

    @Bean
    public MultiThreadedFieldMapper multiThreadedFieldSetMapper() {
        return new MultiThreadedFieldMapper();
    }

    @Bean
    public ItemProcessor processor(){

        return new MultiThreadProcessor();
    }

    @Bean
    public ItemWriter<Object> writer() {
        return new MultiThreadWriter();
    }

    @Bean
    public ProtocolListener protocolListener(){
        return new ProtocolListener();
    }

    @Bean
    public LogProcessListener logProcessListener(){
        return new LogProcessListener();
    }

}
