/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

import options.*;

/**
 *
 * @author Xin
 */
public class NumericalIntegration extends Algorithm{
    
    static {
        AmericanCallOption.getInstance().registerAlgorithm("NumericalIntegration", NumericalIntegration.class);
        AmericanPutOption.getInstance().registerAlgorithm("NumericalIntegration", NumericalIntegration.class);
        EuropeanCallOption.getInstance().registerAlgorithm("NumericalIntegration", NumericalIntegration.class);
        EuropeanPutOption.getInstance().registerAlgorithm("NumericalIntegration", NumericalIntegration.class);
    }
    
    private final String[] argsNames = null;

	@Override
	public String getName() {
		return "NumericalIntegration";
	}

	@Override
	public Algorithm createAlgorithm() {
		return new NumericalIntegration();
	}

	@Override
	public Double calculate(Double[] args) {
		return 1.0;
	}

	@Override
	public String[] getArgsNames() {
		return null;
	}
	
	
}
