package src;

import org.palladiosimulator.pcm.repository.DataType;

import edu.kit.ipd.sdq.kamp.architecture.AbstractArchitectureVersion;
import edu.kit.ipd.sdq.kamp.ruledsl.runtime.KampRule;
import edu.kit.ipd.sdq.kamp.ruledsl.support.ChangePropagationStepRegistry;
import edu.kit.ipd.sdq.kamp.ruledsl.support.IRule;
import edu.kit.ipd.sdq.kamp.util.LookupUtil;
import gen.rule.LookUpInterfacesAndSignaturesWithParametersOfTypesFromReturnTypeRule;

// this one uses the processResult logic from the parent rule but performs a custom lookup via LookUpInterfacesAndSignaturesWithParametersOfTypesFromReturnTypeRule::lookupOperationInterfacefromDataType
@KampRule(parent=CustomLookUpInterfacesAndSignaturesWithParametersOfTypesFromParameterRule.class, enabled=true, disableAncestors=false)
public class CustomLookUpInterfacesAndSignaturesWithParametersOfTypesFromReturnTypeRule implements IRule {
	private final CustomLookUpInterfacesAndSignaturesWithParametersOfTypesFromParameterRule parentRule;
	
	public CustomLookUpInterfacesAndSignaturesWithParametersOfTypesFromReturnTypeRule(CustomLookUpInterfacesAndSignaturesWithParametersOfTypesFromParameterRule parentRule) {
		this.parentRule = parentRule;
	}

	@Override
	public void apply(AbstractArchitectureVersion<?> version, ChangePropagationStepRegistry registry) {
		LookupUtil.lookup(version, DataType.class, LookUpInterfacesAndSignaturesWithParametersOfTypesFromReturnTypeRule::lookupOperationInterfacefromDataType)
			.forEach((result) -> {
				this.parentRule.processResult(result, version, registry);
		});
	}

}
