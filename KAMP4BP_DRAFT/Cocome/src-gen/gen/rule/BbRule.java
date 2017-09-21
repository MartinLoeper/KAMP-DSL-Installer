package gen.rule;

import edu.kit.ipd.sdq.kamp.architecture.AbstractArchitectureVersion;
import edu.kit.ipd.sdq.kamp.model.modificationmarks.AbstractModification;
import edu.kit.ipd.sdq.kamp.ruledsl.support.CausingEntityMapping;
import edu.kit.ipd.sdq.kamp.ruledsl.support.ChangePropagationStepRegistry;
import edu.kit.ipd.sdq.kamp.ruledsl.support.IRule;
import edu.kit.ipd.sdq.kamp.util.LookupUtil;
import edu.kit.ipd.sdq.kamp.util.ModificationMarkCreationUtil;
import edu.kit.ipd.sdq.kamp.util.PropagationStepUtil;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class BbRule implements IRule {
  public static Set<CausingEntityMapping<DataType, EObject>> lookupDataTypefromOperationSignature(final OperationSignature operationSignature, final AbstractArchitectureVersion<?> version) {
    Stream<CausingEntityMapping<OperationSignature, EObject>> input = Stream.of(new CausingEntityMapping(operationSignature, operationSignature));
    
    java.util.stream.Stream<CausingEntityMapping<org.palladiosimulator.pcm.repository.Parameter, EObject>> markedParameter__1 = edu.kit.ipd.sdq.kamp.util.LookupUtil.lookupForwardReference(input, true, "parameters__OperationSignature", org.palladiosimulator.pcm.repository.Parameter.class, false);
    java.util.stream.Stream<CausingEntityMapping<org.palladiosimulator.pcm.repository.DataType, EObject>> markedDataType__2 = edu.kit.ipd.sdq.kamp.util.LookupUtil.lookupForwardReference(markedParameter__1, false, "dataType__Parameter", org.palladiosimulator.pcm.repository.DataType.class, false);
    
    return markedDataType__2.collect(Collectors.toSet());
  }
  
  @Override
  public void apply(final AbstractArchitectureVersion<?> version, final ChangePropagationStepRegistry registry) {
    LookupUtil.lookup(version, OperationSignature.class, BbRule::lookupDataTypefromOperationSignature)
    	.forEach((result) -> {
    		if(PropagationStepUtil.isNewEntry(result, -1, registry)) {
    			AbstractModification<?, EObject> modificationMark = ModificationMarkCreationUtil.createModificationMark(result, edu.kit.ipd.sdq.kamp4is.model.modificationmarks.ISModificationmarksFactory.eINSTANCE.createISModifyDataType());
    			ModificationMarkCreationUtil.insertModificationMark(modificationMark, registry, edu.kit.ipd.sdq.kamp4is.model.modificationmarks.ISChangePropagationDueToDataDependencies.class, "getDatatypeModifications");
    			PropagationStepUtil.addNewModificationMark(result, modificationMark, -1, registry);
    		} else {
    			PropagationStepUtil.addToExistingModificationMark(result, -1, registry);
    		}
    	});
  }
}
