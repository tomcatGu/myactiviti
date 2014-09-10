package org.crusoe.command;

import java.io.InputStream;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

public class GenFlowImageCmd implements Command<InputStream> {

	private String bpmnDefId = "";
	private RepositoryService repositoryService;

	public GenFlowImageCmd(RepositoryService repositoryService, String bpmnDefId) {
		this.bpmnDefId = bpmnDefId;
		this.repositoryService = repositoryService;
	}

	@Override
	public InputStream execute(CommandContext context) {

		// RepositoryService repositoryService=(RepositoryService)
		// AppUtil.getBean("repositoryService");

		BpmnModel bpmnModel = repositoryService.getBpmnModel(bpmnDefId);
		// bpmnModel.

		InputStream inputStream = new DefaultProcessDiagramGenerator()
				.generatePngDiagram(bpmnModel);
		// ProcessDiagramGenerator.g
		return inputStream;
	}

}