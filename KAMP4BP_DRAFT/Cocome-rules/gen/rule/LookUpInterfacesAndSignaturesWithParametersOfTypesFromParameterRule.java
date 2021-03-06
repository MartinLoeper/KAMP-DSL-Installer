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
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;

@SuppressWarnings("all")
public class LookUpInterfacesAndSignaturesWithParametersOfTypesFromParameterRule implements IDuplicateAwareRule {
  @Override
  public int getStepId() {
    return 0;
  }
  
  public static final Set<CausingEntityMapping<OperationInterface, EObject>> lookupOperationInterfacefromDataType(final CausingEntityMapping<DataType, EObject> dataTypeMapping, final AbstractArchitectureVersion<?> version) {
    Stream<CausingEntityMapping<DataType, EObject>> input = Stream.of(dataTypeMapping);
    
    java.util.stream.Stream<CausingEntityMapping<org.palladiosimulator.pcm.repository.Parameter, EObject>> backmarkedParameter__1 = edu.kit.ipd.sdq.kamp.util.LookupUtil.lookupBackwardReference(version, org.palladiosimulator.pcm.repository.Parameter.class, "dataType__Parameter", input, false).stream();
    java.util.stream.Stream<CausingEntityMapping<org.palladiosimulator.pcm.repository.OperationSignature, EObject>> backmarkedOperationSignature__2 = edu.kit.ipd.sdq.kamp.util.LookupUtil.lookupBackwardReference(version, org.palladiosimulator.pcm.repository.OperationSignature.class, "parameters__OperationSignature", backmarkedParameter__1, true).stream();
    java.util.stream.Stream<CausingEntityMapping<org.palladiosimulator.pcm.repository.OperationInterface, EObject>> backmarkedOperationInterface__4 = edu.kit.ipd.sdq.kamp.util.LookupUtil.lookupBackwardReference(version, org.palladiosimulator.pcm.repository.OperationInterface.class, "signatures__OperationInterface", backmarkedOperationSignature__2, false).stream();
    
    return backmarkedOperationInterface__4.collect(Collectors.toSet());
  }
  
  @Override
  public void apply(final AbstractArchitectureVersion<?> version, final ChangePropagationStepRegistry registry) {
    LookupUtil.lookup(version, DataType.class, LookUpInterfacesAndSignaturesWithParametersOfTypesFromParameterRule::lookupOperationInterfacefromDataType)
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
