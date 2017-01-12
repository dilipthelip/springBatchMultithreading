package com.learnbatch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * Created by z001qgd on 1/11/17.
 */
public class MultiThreadedFieldMapper implements FieldSetMapper<String> {

    @Override
    public String mapFieldSet(FieldSet fieldSet) throws BindException {

        return fieldSet.readString("VALUE");
    }
}
