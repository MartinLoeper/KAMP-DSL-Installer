package src;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationSignature;

import edu.kit.ipd.sdq.kamp.architecture.AbstractArchitectureVersion;
import edu.kit.ipd.sdq.kamp.model.modificationmarks.AbstractModification;
import edu.kit.ipd.sdq.kamp.ruledsl.runtime.KampRule;
import edu.kit.ipd.sdq.kamp.ruledsl.support.CausingEntityMapping;
import edu.kit.ipd.sdq.kamp.ruledsl.support.ChangePropagationStepRegistry;
import edu.kit.ipd.sdq.kamp.ruledsl.support.IDuplicateAwareRule;
import edu.kit.ipd.sdq.kamp.ruledsl.support.IRule;
import edu.kit.ipd.sdq.kamp.util.LookupUtil;
import edu.kit.ipd.sdq.kamp.util.ModificationMarkCreationUtil;
import edu.kit.ipd.sdq.kamp.util.PropagationStepUtil;
import edu.kit.ipd.sdq.kamp4is.model.modificationmarks.ISModifyInterface;
import edu.kit.ipd.sdq.kamp4is.model.modificationmarks.ISModifySignature;
import gen.rule.LookUpInterfacesAndSignaturesWithParametersOfTypesFromParameterRule;

// this rules illustrates how to add Sub-Modification marks
// we should provide dedicated classes for the user to support adding sub modification marks PROGRAMATICALLY (not in the dsl itself)
// a suggestion is provided by: https://github.com/MartinLoeper/KAMP-DSL/issues/74
@KampRule(parent=LookUpInterfacesAndSignaturesWithParametersOfTypesFromParameterRule.class, enabled=true, disableAncestors=true)
public class CustomLookUpInterfacesAndSignaturesWithParametersOfTypesFromParameterRule implements IRule {
	private final IDuplicateAwareRule parentRule;
	
	public CustomLookUpInterfacesAndSignaturesWithParametersOfTypesFromParameterRule(LookUpInterfacesAndSignaturesWithParametersOfTypesFromParameterRule parentRule) {
		this.parentRule = parentRule;
	}
	
	@Override
	public void apply(AbstractArchitectureVersion<?> version, ChangePropagationStepRegistry registry) {
		LookupUtil.lookup(version, DataType.class, LookUpInterfacesAndSignaturesWithParametersOfTypesFromParameterRule::lookupOperationInterfacefromDataType)
    		.forEach((result) -> {
    			processResult(result, version, registry);
    	});
	}
	
	public void processResult(CausingEntityMapping<? extends Interface, EObject> result, AbstractArchitectureVersion<?> version, ChangePropagationStepRegistry registry) {
		CausingEntityMapping<OperationSignature, ?> parent = result.getParentMappingOfType(OperationSignature.class);
		ISModifySignature sigModificationMark = edu.kit.ipd.sdq.kamp4is.model.modificationmarks.ISModificationmarksFactory.eINSTANCE.createISModifySignature();
		sigModificationMark.setAffectedElement(parent.getAffectedElement());
		sigModificationMark.getCausingElements().add(parent.getSourceMapping().getAffectedElement());
		
		if(PropagationStepUtil.isNewEntry(result, this.parentRule.getStepId(), registry)) {
			ISModifyInterface modificationMark = ModificationMarkCreationUtil.createModificationMark(result, edu.kit.ipd.sdq.kamp4is.model.modificationmarks.ISModificationmarksFactory.eINSTANCE.createISModifyInterface());
			// TODO use one instance of equality helper!!
			if(!modificationMark.getSignatureModifications().stream().anyMatch(m -> new EqualityHelper().equals(m, sigModificationMark)))
				modificationMark.getSignatureModifications().add(sigModificationMark);
			
			ModificationMarkCreationUtil.insertModificationMark(modificationMark, registry, edu.kit.ipd.sdq.kamp4is.model.modificationmarks.ISChangePropagationDueToDataDependencies.class, "getInterfaceModifications");
			PropagationStepUtil.addNewModificationMark(result, modificationMark, this.parentRule.getStepId(), registry);
		} else {
			AbstractModification<?, EObject> cMark = PropagationStepUtil.getExistingModificationMark(result, this.parentRule.getStepId(), registry);
			// this should be safe ... But dig into it ... Is it possible for rules to safe different Markers for the same affected element?
			ISModifyInterface cInterfaceMark = (ISModifyInterface) cMark;
			// TODO use one instance of equality helper!!
			if(!cInterfaceMark.getSignatureModifications().stream().anyMatch(m -> new EqualityHelper().equals(m, sigModificationMark)))
				cInterfaceMark.getSignatureModifications().add(sigModificationMark);
			
			// add the causing entities from the result element
			PropagationStepUtil.addToExistingModificationMark(result, this.parentRule.getStepId(), registry);
		}
	}

}
