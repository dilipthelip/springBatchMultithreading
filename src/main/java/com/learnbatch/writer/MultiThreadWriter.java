package com.learnbatch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Created by z001qgd on 1/11/17.
 */
public class MultiThreadWriter implements ItemWriter<Object> {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public void write(List<?> list) throws Exception {


        logger.info("In Writer :  "+ list);

    }
}
