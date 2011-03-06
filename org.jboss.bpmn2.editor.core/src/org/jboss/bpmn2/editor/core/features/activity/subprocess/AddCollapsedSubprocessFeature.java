package org.jboss.bpmn2.editor.core.features.activity.subprocess;

import static org.jboss.bpmn2.editor.core.features.activity.subprocess.SubProcessFeatureContainer.TRIGGERED_BY_EVENT;

import org.eclipse.bpmn2.SubProcess;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.styles.AdaptedGradientColoredAreas;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ChopboxAnchor;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.graphiti.util.PredefinedColoredAreas;
import org.jboss.bpmn2.editor.core.features.FeatureSupport;
import org.jboss.bpmn2.editor.utils.StyleUtil;

@Deprecated
public class AddCollapsedSubprocessFeature extends AbstractAddFeature {

	public AddCollapsedSubprocessFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		boolean isSubProcess = context.getNewObject() instanceof SubProcess;
		boolean intoDiagram = context.getTargetContainer().equals(getDiagram());
		boolean intoLane = FeatureSupport.isTargetLane(context) && FeatureSupport.isTargetLaneOnTop(context);
		boolean intoParticipant = FeatureSupport.isTargetParticipant(context);
		return isSubProcess && (intoDiagram || intoLane || intoParticipant);
	}

	@Override
	public PictogramElement add(IAddContext context) {

		int width = context.getWidth() > 0 ? context.getWidth() : 100;
		int height = context.getHeight() > 0 ? context.getHeight() : 100;

		SubProcess subprocess = (SubProcess) context.getNewObject();

		IGaService gaService = Graphiti.getGaService();
		IPeService peService = Graphiti.getPeService();

		ContainerShape containerShape = peService.createContainerShape(context.getTargetContainer(), true);
		Rectangle invisibleRect = gaService.createInvisibleRectangle(containerShape);

		gaService.setLocationAndSize(invisibleRect, context.getX(), context.getY(), width, height + 18);

		Shape rectShape = peService.createShape(containerShape, false);
		RoundedRectangle rect = gaService.createRoundedRectangle(rectShape, 5, 5);
				
		StyleUtil.applyBGStyle(rect, this);		

		gaService.setLocationAndSize(rect, 0, 0, width, height);
		decorateRect(rect);
		link(rectShape, subprocess);

		Rectangle box = gaService.createRectangle(rect);
		gaService.setLocationAndSize(box, 40, 80, 20, 20);
		box.setFilled(false);
		box.setForeground(manageColor(StyleUtil.CLASS_FOREGROUND));
		decorateBox(box);

		Shape textShape = peService.createShape(containerShape, false);
		MultiText text = gaService.createDefaultMultiText(textShape, subprocess.getName());
		gaService.setLocationAndSize(text, 10, 10, 80, 80);
		text.setStyle(StyleUtil.getStyleForText(getDiagram()));
		text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setVerticalAlignment(Orientation.ALIGNMENT_TOP);
		text.getFont().setBold(true);
		link(textShape, subprocess);

		Polyline lineHorizontal = gaService.createPolyline(box, new int[] { 2, 10, 18, 10 });
		lineHorizontal.setForeground(manageColor(StyleUtil.CLASS_FOREGROUND));

		Polyline lineVertical = gaService.createPolyline(box, new int[] { 10, 2, 10, 18 });
		lineVertical.setForeground(manageColor(StyleUtil.CLASS_FOREGROUND));

		ChopboxAnchor anchor = peService.createChopboxAnchor(containerShape);
		anchor.setReferencedGraphicsAlgorithm(rect);

		peService.setPropertyValue(containerShape, TRIGGERED_BY_EVENT, "false");
		link(containerShape, subprocess);
		return containerShape;
	}

	protected void decorateRect(RoundedRectangle rect) {
	}

	protected void decorateBox(Rectangle box) {
	}
}