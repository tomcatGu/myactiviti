package org.crusoe.web;

import java.util.List;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.exceptions.TemplateProcessingException;

import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;
import org.thymeleaf.standard.fragment.StandardFragment;
import org.thymeleaf.standard.fragment.StandardFragmentProcessor;
import org.thymeleaf.standard.processor.attr.StandardFragmentAttrProcessor;
import org.thymeleaf.util.PrefixUtils;

public class MergeProcessor extends AbstractAttrProcessor {

	public static final int ATTR_PRECEDENCE = 100;
	public static final String ATTR_NAME = "merge";
	public static final String FRAGMENT_ATTR_NAME = StandardFragmentAttrProcessor.ATTR_NAME;

	public MergeProcessor() {
		super(ATTR_NAME);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ProcessorResult processAttribute(Arguments arguments,
			Element element, String attributeName) {
		final String attributeValue = element.getAttributeValue(attributeName);

		final boolean substituteInclusionNode = getSubstituteInclusionNode(
				arguments, element, attributeName, attributeValue);

		final StandardFragment fragmentAndTarget = getStandardFragment(
				arguments, element, attributeName, attributeValue,
				substituteInclusionNode);

		final List<Node> fragmentNodes = fragmentAndTarget.extractFragment(
				arguments.getConfiguration(), arguments,
				arguments.getTemplateRepository());

		if (fragmentNodes == null) {
			throw new TemplateProcessingException("Cannot correctly process \""
					+ attributeName + "\" attribute. "
					+ "Fragment specification \"" + attributeValue
					+ "\" matched null.");
		}

		// element.clearChildren();
		element.setRecomputeProcessorsImmediately(true);
		element.removeAttribute(attributeName);
		Node firstNode = element.getFirstChild();
		if (substituteInclusionNode) {

			element.setChildren(fragmentNodes);
			element.getParent().extractChild(element);

		} else {

			for (final Node fragmentNode : fragmentNodes) {
				element.insertBefore(firstNode, fragmentNode);

			}

		}

		return ProcessorResult.OK;
	}

	@Override
	public int getPrecedence() {
		// TODO Auto-generated method stub
		return ATTR_PRECEDENCE;
	}

	protected final StandardFragment getStandardFragment(
			final Arguments arguments, final Element element,
			final String attributeName, final String attributeValue,
			final boolean substituteInclusionNode) {

		final String targetAttributeName = getTargetAttributeName(arguments,
				element, attributeName, attributeValue);

		return StandardFragmentProcessor.computeStandardFragmentSpec(
				arguments.getConfiguration(), arguments, attributeValue, null,
				targetAttributeName);

	}

	protected String getTargetAttributeName(final Arguments arguments,
			final Element element, final String attributeName,
			final String attributeValue) {

		if (attributeName != null) {
			final String prefix = PrefixUtils.getPrefix(attributeName);
			if (prefix != null) {
				return "th" + ":" + FRAGMENT_ATTR_NAME;
			}
		}
		return FRAGMENT_ATTR_NAME;

	}

	protected boolean getSubstituteInclusionNode(final Arguments arguments,
			final Element element, final String attributeName,
			final String attributeValue) {
		// th:include does not substitute the inclusion node
		return false;
	}

}
