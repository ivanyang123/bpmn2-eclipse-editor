package org.jboss.bpmn2.editor.core.features;

import static org.jboss.bpmn2.editor.core.utils.FeatureSupport.getShape;

import java.lang.reflect.Method;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class UpdateBaseElementNameFeature extends AbstractUpdateFeature {

	public static final String TEXT_ELEMENT = "baseelement.text";

	public UpdateBaseElementNameFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canUpdate(IUpdateContext context) {
		BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(context.getPictogramElement(),
		        BaseElement.class);
		if (element == null)
			return false;
		return hasName(element);
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		ContainerShape container = (ContainerShape) context.getPictogramElement();

		BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(context.getPictogramElement(),
		        BaseElement.class);

		String elementName = getName(element);
		Shape textShape = getShape(container, TEXT_ELEMENT, Boolean.toString(true));
		String name = ((Text) textShape.getGraphicsAlgorithm()).getValue();

		if (elementName != null) {
			return elementName.equals(name) ? Reason.createFalseReason() : Reason.createTrueReason();
		} else if (name != null) {
			return name.equals(elementName) ? Reason.createFalseReason() : Reason.createTrueReason();
		} else {
			return Reason.createFalseReason();
		}
	}

	@Override
	public boolean update(IUpdateContext context) {
		ContainerShape container = (ContainerShape) context.getPictogramElement();
		FlowElement element = (FlowElement) BusinessObjectUtil.getFirstElementOfType(context.getPictogramElement(),
		        FlowElement.class);
		Shape textShape = getShape(container, TEXT_ELEMENT, Boolean.toString(true));
		((AbstractText) textShape.getGraphicsAlgorithm()).setValue(element.getName());
		layoutPictogramElement(context.getPictogramElement());
		return true;
	}

	private String getName(BaseElement element) {
		try {
			return (String) element.getClass().getMethod("getName").invoke(element);
		} catch (Exception e) {
			return null;
		}
	}

	private boolean hasName(BaseElement element) {
		boolean found = false;
		for (Method m : element.getClass().getMethods()) {
			if (m.getName().equals("getName")) {
				found = true;
				break;
			}
		}
		return found;
	}
}