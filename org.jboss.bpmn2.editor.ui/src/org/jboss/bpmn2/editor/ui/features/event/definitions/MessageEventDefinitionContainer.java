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
package org.jboss.bpmn2.editor.ui.features.event.definitions;

import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.MessageEventDefinition;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.IColorConstant;
import org.jboss.bpmn2.editor.core.ModelHandler;
import org.jboss.bpmn2.editor.core.features.event.definitions.CreateEventDefinition;
import org.jboss.bpmn2.editor.core.features.event.definitions.DecorationAlgorithm;
import org.jboss.bpmn2.editor.core.features.event.definitions.EventDefinitionFeatureContainer;
import org.jboss.bpmn2.editor.core.utils.GraphicsUtil;
import org.jboss.bpmn2.editor.core.utils.GraphicsUtil.Envelope;
import org.jboss.bpmn2.editor.core.utils.StyleUtil;
import org.jboss.bpmn2.editor.ui.ImageProvider;

public class MessageEventDefinitionContainer extends EventDefinitionFeatureContainer {

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof MessageEventDefinition;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateMessageEventDefinition(fp);
	}

	@Override
	protected Shape drawForStart(DecorationAlgorithm algorithm, ContainerShape shape) {
		return drawEnvleope(algorithm, shape);
	}

	@Override
	protected Shape drawForEnd(DecorationAlgorithm algorithm, ContainerShape shape) {
		return drawFilledEnvelope(algorithm, shape);
	}

	@Override
	protected Shape drawForThrow(DecorationAlgorithm algorithm, ContainerShape shape) {
		return drawFilledEnvelope(algorithm, shape);
	}

	@Override
	protected Shape drawForCatch(DecorationAlgorithm algorithm, ContainerShape shape) {
		return drawEnvleope(algorithm, shape);
	}

	@Override
	protected Shape drawForBoundary(DecorationAlgorithm algorithm, ContainerShape shape) {
		return drawEnvleope(algorithm, shape);
	}

	private Shape drawEnvleope(DecorationAlgorithm algorithm, ContainerShape shape) {
		Shape envelopeShape = Graphiti.getPeService().createShape(shape, false);
		Envelope env = GraphicsUtil.createEventEnvelope(envelopeShape);
		env.rect.setForeground(algorithm.manageColor(StyleUtil.CLASS_FOREGROUND));
		env.line.setForeground(algorithm.manageColor(StyleUtil.CLASS_FOREGROUND));
		return envelopeShape;
	}

	private Shape drawFilledEnvelope(DecorationAlgorithm algorithm, ContainerShape shape) {
		Shape envelopeShape = Graphiti.getPeService().createShape(shape, false);
		Envelope env = GraphicsUtil.createEventEnvelope(envelopeShape);
		env.rect.setFilled(true);
		env.rect.setBackground(algorithm.manageColor(StyleUtil.CLASS_FOREGROUND));
		env.rect.setForeground(algorithm.manageColor(StyleUtil.CLASS_FOREGROUND));
		env.line.setForeground(algorithm.manageColor(IColorConstant.WHITE));
		return envelopeShape;
	}

	public static class CreateMessageEventDefinition extends CreateEventDefinition {

		public CreateMessageEventDefinition(IFeatureProvider fp) {
			super(fp, "Message Definition", "Marks that event expects a message");
		}

		@Override
		protected EventDefinition createEventDefinition(ICreateContext context) {
			return ModelHandler.FACTORY.createMessageEventDefinition();
		}

		@Override
		protected String getStencilImageId() {
			return ImageProvider.IMG_16_MESSAGE;
		}
	}
}
