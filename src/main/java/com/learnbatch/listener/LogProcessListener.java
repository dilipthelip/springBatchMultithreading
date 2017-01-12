package com.learnbatch.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ItemProcessListener;

public class LogProcessListener implements ItemProcessListener<Object, Object> {

	private static final Log log = LogFactory.getLog(LogProcessListener.class);

	public void afterProcess(Object input, Object output) {
		if(input!=null) log.info("Input to Processor: " + input.toString());
		if(output!=null) log.info("Output of Processor: " + output.toString());
	}

	public void beforeProcess(Object item) {
	}

	public void onProcessError(Object item, Exception e) {
	}

}
