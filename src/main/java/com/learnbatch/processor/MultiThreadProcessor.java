package com.learnbatch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by z001qgd on 1/11/17.
 */
public class MultiThreadProcessor implements ItemProcessor<String,String> {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public String process(String str) throws Exception {
        logger.info("In Process : "+ str);
        return str;
    }
}
