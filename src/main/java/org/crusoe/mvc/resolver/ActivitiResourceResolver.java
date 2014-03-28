package org.crusoe.mvc.resolver;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;

public class ActivitiResourceResolver implements IResourceResolver {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ProcessEngine processEngine;

	private static final Logger logger = LoggerFactory
			.getLogger(ActivitiResourceResolver.class);
	public static final String NAME = "BAR";

	public String getName() {
		// TODO Auto-generated method stub
		return NAME;
	}

	public InputStream getResourceAsStream(
			TemplateProcessingParameters templateProcessingParameters,
			String resourceName) {
		// TODO Auto-generated method stub
		String[] strs = StringUtils.split(
				templateProcessingParameters.getTemplateName(), "/");

		int version;
		ProcessDefinition processDefinition;
		try {
			version = Integer.parseInt(templateProcessingParameters
					.getContext().getVariables().get("version").toString());
		} catch (Exception e) {

			version = -1;

		}
		try {
			if (version != -1) {
				processDefinition = repositoryService
						.createProcessDefinitionQuery()
						.processDefinitionKey(strs[0])
						.processDefinitionVersion(version).singleResult();
			} else {
				processDefinition = repositoryService
						.createProcessDefinitionQuery()
						.processDefinitionKey(strs[0]).latestVersion()
						.singleResult();
			}

			return repositoryService.getResourceAsStream(
					processDefinition.getDeploymentId(), resourceName);
		} catch (final Exception e) {
			if (logger.isDebugEnabled()) {
				if (logger.isTraceEnabled()) {
					logger.trace(
							String.format(
									"[THYMELEAF][%s][%s] Resource \"%s\" could not be resolved. This can be normal as "
											+ "maybe this resource is not intended to be resolved by this resolver. "
											+ "Exception is provided for tracing purposes: ",
									TemplateEngine.threadIndex(),
									templateProcessingParameters
											.getTemplateName(), resourceName),
							e);
				} else {
					logger.debug(String
							.format("[THYMELEAF][%s][%s] Resource \"%s\" could not be resolved. This can be normal as "
									+ "maybe this resource is not intended to be resolved by this resolver. "
									+ "Exception message is provided: %s: %s",
									TemplateEngine.threadIndex(),
									templateProcessingParameters
											.getTemplateName(), resourceName, e
											.getClass().getName(), e
											.getMessage()));
				}
			}
			return null;
		}

	}
}
