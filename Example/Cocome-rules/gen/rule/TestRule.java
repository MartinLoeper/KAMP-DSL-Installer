package gen.rule;

import edu.kit.ipd.sdq.kamp.architecture.AbstractArchitectureVersion;
import edu.kit.ipd.sdq.kamp.propagation.AbstractChangePropagationAnalysis;
import edu.kit.ipd.sdq.kamp.ruledsl.support.ChangePropagationStepRegistry;
import edu.kit.ipd.sdq.kamp.ruledsl.support.IRule;
import edu.kit.ipd.sdq.kamp.util.LookupUtil;
import edu.kit.ipd.sdq.kamp.util.ModificationMarkCreationUtil;
import edu.kit.ipd.sdq.kamp4is.model.modificationmarks.ISChangePropagationDueToDataDependencies;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class TestRule implements IRule {
  public static Set lookupOperationInterfacefromOperationSignature(final OperationSignature operationSignature, final AbstractArchitectureVersion version) {
    Set<Resource> allResources = Collections.emptySet();
    
    Stream<OperationSignature> input =
    	Stream.of(operationSignature);
    
    Stream<org.palladiosimulator.pcm.repository.OperationInterface> markedOperationInterface = input
    	.map(it -> it.getInterface__OperationSignature());
    
    return markedOperationInterface.collect(Collectors.toSet());
  }
  
  @Override
  @SuppressWarnings("rawtypes")
  public void apply(final AbstractArchitectureVersion version, final ChangePropagationStepRegistry registry, final AbstractChangePropagationAnalysis changePropagationAnalysis) {
    LookupUtil.lookupMarkedObjectsWithLookupMethod(version, OperationSignature.class, org.palladiosimulator.pcm.repository.OperationInterface.class, TestRule::lookupOperationInterfacefromOperationSignature)
    	.forEach((result) -> {			 
    		Collection<ISChangePropagationDueToDataDependencies>changePropagationSteps = registry.getSubtypes(edu.kit.ipd.sdq.kamp4is.model.modificationmarks.ISChangePropagationDueToDataDependencies.class);
    		
    		if(changePropagationSteps.isEmpty()) {
    			throw new UnsupportedOperationException("The ChangePropagationAnalysis does not provide the requested ChangePropagationStep.");
    		} else if(changePropagationSteps.size() > 1) {
    			throw new UnsupportedOperationException("There is more than one candidate supplied for the selected ChangePropagationStep. Please make a more specific selection.");
    		} else {
    			changePropagationSteps.iterator().next().getInterfaceModifications().add(ModificationMarkCreationUtil.createModificationMark(result, edu.kit.ipd.sdq.kamp4is.model.modificationmarks.ISModificationmarksFactory.eINSTANCE.createISModifyInterface()));
    		}
    	});
  }
}
