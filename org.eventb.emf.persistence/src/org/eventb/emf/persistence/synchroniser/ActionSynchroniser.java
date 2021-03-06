package org.eventb.emf.persistence.synchroniser;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eventb.core.EventBAttributes;
import org.eventb.core.IAction;
import org.eventb.emf.core.EventBElement;
import org.eventb.emf.core.machine.Action;
import org.eventb.emf.core.machine.MachineFactory;
import org.eventb.emf.core.machine.MachinePackage;
import org.rodinp.core.IAttributeType;
import org.rodinp.core.IInternalElementType;
import org.rodinp.core.IRodinElement;
import org.rodinp.core.RodinDBException;

public class ActionSynchroniser extends AbstractSynchroniser {

	private static final Set<IAttributeType> handledAttributes = new HashSet<IAttributeType>();

	static {
		handledAttributes.add(EventBAttributes.ASSIGNMENT_ATTRIBUTE);
	}

	@Override
	protected Set<IAttributeType> getHandledAttributeTypes() {
		return handledAttributes;
	}

	@Override
	protected EventBElement createEventBElement() {
		return MachineFactory.eINSTANCE.createAction();
	}

	@Override
	protected EStructuralFeature getFeature() {
		return MachinePackage.eINSTANCE.getEvent_Actions();
	}

	@Override
	protected IInternalElementType<?> getRodinType() {
		return IAction.ELEMENT_TYPE;
	}

	@Override
	public EventBElement load(final IRodinElement rodinElement, final EventBElement emfParent, final IProgressMonitor monitor) throws RodinDBException, CoreException {
		// create EMF node
		Action eventBElement = (Action) super.load(rodinElement, emfParent, monitor);
		if (rodinElement instanceof IAction) {
			if (((IAction) rodinElement).hasAssignmentString()) {
				eventBElement.setAction(((IAction) rodinElement).getAssignmentString());
			}
		}
		return eventBElement;
	}

	@Override
	public IRodinElement save(final EventBElement emfElement, final IRodinElement rodinParent, final IProgressMonitor monitor) throws RodinDBException, CoreException {
		// create Rodin element
		IRodinElement rodinElement = super.save(emfElement, rodinParent, monitor);
		if (rodinElement instanceof IAction && emfElement instanceof Action) {
			if (((Action) emfElement).getAction() != null) {
				((IAction) rodinElement).setAssignmentString(((Action) emfElement).getAction(), monitor);
			}
		}
		return rodinElement;
	}

}
