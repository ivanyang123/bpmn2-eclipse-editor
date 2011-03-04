package org.jboss.bpmn2.editor.core.features.bendpoint;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IRemoveBendpointContext;
import org.eclipse.graphiti.features.impl.DefaultRemoveBendpointFeature;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.jboss.bpmn2.editor.core.Activator;
import org.jboss.bpmn2.editor.core.ModelHandler;
import org.jboss.bpmn2.editor.core.ModelHandlerLocator;
import org.jboss.bpmn2.editor.core.features.BusinessObjectUtil;

public class RemoveBendpointFeature extends DefaultRemoveBendpointFeature {

	public RemoveBendpointFeature(IFeatureProvider fp) {
	    super(fp);
    }
	
	@Override
	public void removeBendpoint(IRemoveBendpointContext context) {
	    super.removeBendpoint(context);
	    try {
			FreeFormConnection connection = context.getConnection();
			BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(connection, BaseElement.class);
			ModelHandler modelHandler = ModelHandlerLocator.getModelHandler(getDiagram().eResource());
			BPMNEdge edge = (BPMNEdge) modelHandler.findDIElement(getDiagram(), element);
			edge.getWaypoint().remove(context.getBendpointIndex() + 1);
		} catch (Exception e) {
			Activator.logError(e);
		}
	}
}