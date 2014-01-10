package com.sillycat.easygroovyplugin.parsers;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scripting.config.LangNamespaceUtils;
import org.springframework.scripting.groovy.GroovyScriptFactory;
import org.w3c.dom.Element;

import com.sillycat.easygroovyplugin.utils.SystemConfiguration;

public class GroovyScanBeanDefinitionParser implements BeanDefinitionParser {

	private static final Log logger = LogFactory
			.getLog(GroovyScanBeanDefinitionParser.class);

	/**
	 * 
	 * @param element
	 * @param parserContext
	 * @return
	 */
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String pattern = element.getAttribute("source-pattern");
		String key = element.getAttribute("source-key");

		if (null == key || "".equalsIgnoreCase(key.trim())
				|| "null".equals(key.trim())) {
			key = "groovy.file.path";
		}

		String filepath = SystemConfiguration.getString(key);

		//pattern = filepath + pattern;//如果需要指定groovy文件的存放地方将前面的注释去掉。

		// Set up infrastructure.
		LangNamespaceUtils
				.registerScriptFactoryPostProcessorIfNecessary(parserContext
						.getRegistry());

		try {
			Resource[] resources = getResources(parserContext
					.getReaderContext().getResourceLoader(), pattern);

			for (Resource resource : resources) {
				// Create script factory bean definition.
				GenericBeanDefinition bd = new GenericBeanDefinition();
				bd.setBeanClass(GroovyScriptFactory.class);
				//resource.
				//bd.setSource(resource.getInputStream());
				bd.setSource(resource);

				// Add constructor arguments.
				ConstructorArgumentValues cav = bd
						.getConstructorArgumentValues();
				int constructorArgNum = 0;

				// "file://D:/work/easygroovy/WebContent/WEB-INF/groovy/GroovyController.groovy"
				cav.addIndexedArgumentValue(constructorArgNum++, resource.getURI().toString());
				
				logger.info("file://" + resource.getFile().getAbsolutePath());

				String beanName = parserContext.getReaderContext()
						.generateBeanName(bd);
				String beanId = parseBeanId(resource.getFilename());
				//String beanId=parseBeanId(beanName);
				//bd.setBeanClassName(beanId);
				logger.info("register beanId=" + beanId);
				parserContext.getRegistry()
						.registerBeanDefinition(beanId, bd);
			}

		} catch (IOException x) {
			throw new RuntimeException(x);
		}

		return null;
	}

	private String parseBeanId(String fullName) {
		String beanId = "";
		int start = fullName.lastIndexOf("/") + 1;
		int end = fullName.indexOf(".groovy");
		beanId = fullName.substring(start, end);
		//beanId.replace("Impl", "");
		beanId = beanId.substring(0,1).toLowerCase() + beanId.substring(1);
		end = beanId.lastIndexOf("Impl");
		if(end > 0){
			beanId = beanId.substring(0,end);
		}
		return beanId;
	}

	/**
	 * Get <code>Resource</code>s for the given pattern
	 * 
	 * @param pattern
	 * @return
	 * @throws IOException
	 */
	private Resource[] getResources(ResourceLoader resourceLoader,
			String pattern) throws IOException {
		if (resourceLoader instanceof ResourcePatternResolver) {
			return ((ResourcePatternResolver) resourceLoader)
					.getResources(pattern);

		} else {
			return new Resource[] { resourceLoader.getResource(pattern) };
		}
	}

}
