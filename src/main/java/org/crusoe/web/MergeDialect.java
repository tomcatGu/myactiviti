package org.crusoe.web;

import java.util.HashSet;
import java.util.Set;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.dialect.AbstractDialect;

public class MergeDialect extends AbstractDialect {
	static final String MERGE_PREFIX = "thy";

	public String getPrefix() {
		// TODO Auto-generated method stub
		return MERGE_PREFIX;
	}

	public boolean isLenient() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<IProcessor> getProcessors() {
		final Set<IProcessor> processors = new HashSet<IProcessor>();
		processors.add(new MergeProcessor());

		return processors;
	}

}
