package src;

import edu.kit.ipd.sdq.kamp.architecture.AbstractArchitectureVersion;
import edu.kit.ipd.sdq.kamp.propagation.AbstractChangePropagationAnalysis;
import edu.kit.ipd.sdq.kamp.ruledsl.support.ChangePropagationStepRegistry;
import gen.RuleProviderBase;
import gen.rule.TestRule;

public class RuleProviderImpl extends RuleProviderBase {
	@Override
	public void onRegistryReady() {
		// if you want to disable a generated rule, you can override it with a special type of that rule and a no-op apply method as follows:
		
//		override(new TestRule() {
//			@Override
//			public void apply(AbstractArchitectureVersion version, ChangePropagationStepRegistry registry,
//					AbstractChangePropagationAnalysis changePropagationAnalysis) {
//				// do nothing
//			}
//		});
	}
	
	@Override
	public boolean areStandardRulesEnabled() {
		return false;
	}
}