package src;

import edu.kit.ipd.sdq.kamp.ruledsl.runtime.KampConfiguration;
import edu.kit.ipd.sdq.kamp.ruledsl.support.IConfiguration;

// this configuration is used to do A/B-testing
// in order to test results, run the change propagation analysis, create workplan, switch the TEST_MODE_ENABLED flag and do it again
@KampConfiguration
public class Configuration implements IConfiguration {

	// true = KAMP DSL disabled, standard rules enabled
	// false = KAMP-DSL enabled, standard rules disabled    
	public static final boolean TEST_MODE_ENABLED = false;
	
	@Override
	public boolean areKampStandardRulesEnabled() {
		return TEST_MODE_ENABLED;
	}

	@Override
	public boolean isKampDslEnabled() {
		return !TEST_MODE_ENABLED;
	}
  
}
