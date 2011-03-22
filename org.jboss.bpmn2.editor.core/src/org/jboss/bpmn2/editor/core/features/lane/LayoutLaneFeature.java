/******************************************************************************* 
 * Copyright (c) 2011 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.bpmn2.editor.core.features.lane;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.jboss.bpmn2.editor.core.di.DIUtils;
import org.jboss.bpmn2.editor.core.utils.FeatureSupport;

public class LayoutLaneFeature extends AbstractLayoutFeature {

	public LayoutLaneFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canLayout(ILayoutContext context) {
		return FeatureSupport.isLane(context.getPictogramElement());
	}

	@Override
	public boolean layout(ILayoutContext context) {
		FeatureSupport.redraw((ContainerShape) context.getPictogramElement());
		DIUtils.updateDIShape(context.getPictogramElement());
		return true;
	}
}
