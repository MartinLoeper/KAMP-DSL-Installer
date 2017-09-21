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
import org.palladiosimulator.pcm.repository.EventGroup;

@SuppressWarnings("all")
public class LookUpInterfacesAndSignaturesWithParametersOfTypesFromParameter2Rule implements IDuplicateAwareRule {
  @Override
  public int getStepId() {
    return 0;
  }
  
  public static final Set<CausingEntityMapping<EventGroup, EObject>> lookupEventGroupfromDataType(final CausingEntityMapping<DataType, EObject> dataTypeMapping, final AbstractArchitectureVersion<?> version) {
    Stream<CausingEntityMapping<DataType, EObject>> input = Stream.of(dataTypeMapping);
    
    java.util.stream.Stream<CausingEntityMapping<org.palladiosimulator.pcm.repository.Parameter, EObject>> backmarkedParameter__1 = edu.kit.ipd.sdq.kamp.util.LookupUtil.lookupBackwardReference(version, org.palladiosimulator.pcm.repository.Parameter.class, "dataType__Parameter", input, false).stream();
    java.util.stream.Stream<CausingEntityMapping<org.palladiosimulator.pcm.repository.EventType, EObject>> backmarkedEventType__2 = edu.kit.ipd.sdq.kamp.util.LookupUtil.lookupBackwardReference(version, org.palladiosimulator.pcm.repository.EventType.class, "parameter__EventType", backmarkedParameter__1, true).stream();
    java.util.stream.Stream<CausingEntityMapping<org.palladiosimulator.pcm.repository.EventGroup, EObject>> backmarkedEventGroup__4 = edu.kit.ipd.sdq.kamp.util.LookupUtil.lookupBackwardReference(version, org.palladiosimulator.pcm.repository.EventGroup.class, "eventTypes__EventGroup", backmarkedEventType__2, false).stream();
    
    return backmarkedEventGroup__4.collect(Collectors.toSet());
  }
  
  @Override
  public void apply(final AbstractArchitectureVersion<?> version, final ChangePropagationStepRegistry registry) {
    LookupUtil.lookup(version, DataType.class, LookUpInterfacesAndSignaturesWithParametersOfTypesFromParameter2Rule::lookupEventGroupfromDataType)
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
