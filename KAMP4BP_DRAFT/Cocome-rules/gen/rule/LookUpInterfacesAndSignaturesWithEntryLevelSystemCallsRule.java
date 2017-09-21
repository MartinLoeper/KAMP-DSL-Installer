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
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;

@SuppressWarnings("all")
public class LookUpInterfacesAndSignaturesWithEntryLevelSystemCallsRule implements IDuplicateAwareRule {
  @Override
  public int getStepId() {
    return 0;
  }
  
  public static final Set<CausingEntityMapping<OperationInterface, EObject>> lookupOperationInterfacefromEntryLevelSystemCall(final CausingEntityMapping<EntryLevelSystemCall, EObject> entryLevelSystemCallMapping, final AbstractArchitectureVersion<?> version) {
    Stream<CausingEntityMapping<EntryLevelSystemCall, EObject>> input = Stream.of(entryLevelSystemCallMapping);
    
    java.util.stream.Stream<CausingEntityMapping<org.palladiosimulator.pcm.repository.OperationSignature, EObject>> markedOperationSignature__1 = edu.kit.ipd.sdq.kamp.util.LookupUtil.lookupForwardReference(input, false, "operationSignature__EntryLevelSystemCall", org.palladiosimulator.pcm.repository.OperationSignature.class, true);
    java.util.stream.Stream<CausingEntityMapping<org.palladiosimulator.pcm.repository.OperationInterface, EObject>> backmarkedOperationInterface__3 = edu.kit.ipd.sdq.kamp.util.LookupUtil.lookupBackwardReference(version, org.palladiosimulator.pcm.repository.OperationInterface.class, "signatures__OperationInterface", markedOperationSignature__1, false).stream();
    
    return backmarkedOperationInterface__3.collect(Collectors.toSet());
  }
  
  @Override
  public void apply(final AbstractArchitectureVersion<?> version, final ChangePropagationStepRegistry registry) {
    LookupUtil.lookup(version, EntryLevelSystemCall.class, LookUpInterfacesAndSignaturesWithEntryLevelSystemCallsRule::lookupOperationInterfacefromEntryLevelSystemCall)
    	.forEach((result) -> {
    		if(PropagationStepUtil.isNewEntry(result, 0, registry)) {
    			AbstractModification<?, EObject> modificationMark = ModificationMarkCreationUtil.createModificationMark(result, edu.kit.ipd.sdq.kamp4is.model.modificationmarks.ISModificationmarksFactory.eINSTANCE.createISModifyInterface());
    			ModificationMarkCreationUtil.insertModificationMark(modificationMark, registry, edu.kit.ipd.sdq.kamp4is.model.modificationmarks.ISChangePropagationDueToDataDependencies.class, "getInterfaceModifications");
    			PropagationStepUtil.addNewModificationMark(result, modificationMark, 0, registry);
    		} else {
    			PropagationStepUtil.addToExistingModificationMark(result, 0, registry);
    		}
    	});
  }
}
