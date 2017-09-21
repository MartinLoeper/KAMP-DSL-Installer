package gen.rule;

import edu.kit.ipd.sdq.kamp.architecture.AbstractArchitectureVersion;
import edu.kit.ipd.sdq.kamp.model.modificationmarks.AbstractModification;
import edu.kit.ipd.sdq.kamp.ruledsl.support.CausingEntityMapping;
import edu.kit.ipd.sdq.kamp.ruledsl.support.ChangePropagationStepRegistry;
import edu.kit.ipd.sdq.kamp.ruledsl.support.IDuplicateAwareRule;
import edu.kit.ipd.sdq.kamp.util.LookupUtil;
import edu.kit.ipd.sdq.kamp.util.ModificationMarkCreationUtil;
import edu.kit.ipd.sdq.kamp.util.PropagationStepUtil;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;

@SuppressWarnings("all")
public class LookUpEntryLevelSystemCallsWithSignaturesRule implements IDuplicateAwareRule {
  @Override
  public int getStepId() {
    return 1;
  }
  
  public static final Set<CausingEntityMapping<EntryLevelSystemCall, EObject>> lookupEntryLevelSystemCallfromOperationSignature(final CausingEntityMapping<OperationSignature, EObject> operationSignatureMapping, final AbstractArchitectureVersion<?> version) {
    Stream<CausingEntityMapping<OperationSignature, EObject>> input = Stream.of(operationSignatureMapping).peek(e -> e.addCausingEntityDistinct(operationSignatureMapping.getAffectedElement()));
    
    java.util.stream.Stream<CausingEntityMapping<org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall, EObject>> backmarkedEntryLevelSystemCall__1 = edu.kit.ipd.sdq.kamp.util.LookupUtil.lookupBackwardReference(version, org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall.class, "operationSignature__EntryLevelSystemCall", input, false).stream();
    
    return backmarkedEntryLevelSystemCall__1.collect(Collectors.toSet());
  }
  
  @Override
  public void apply(final AbstractArchitectureVersion<?> version, final ChangePropagationStepRegistry registry) {
    LookupUtil.lookup(version, OperationSignature.class, LookUpEntryLevelSystemCallsWithSignaturesRule::lookupEntryLevelSystemCallfromOperationSignature)
    	.forEach((result) -> {
    		if(PropagationStepUtil.isNewEntry(result, 1, registry)) {
    			AbstractModification<?, EObject> modificationMark = ModificationMarkCreationUtil.createModificationMark(result, edu.kit.ipd.sdq.kamp4bp.model.modificationmarks.BPModificationmarksFactory.eINSTANCE.createBPModifyEntryLevelSystemCall());
    			ModificationMarkCreationUtil.insertModificationMark(modificationMark, registry, edu.kit.ipd.sdq.kamp4bp.model.modificationmarks.BPInterBusinessProcessPropagation.class, "getAbstractUserActionModifications");
    			PropagationStepUtil.addNewModificationMark(result, modificationMark, 1, registry);
    		} else {
    			PropagationStepUtil.addToExistingModificationMark(result, 1, registry);
    		}
    	});
  }
}
