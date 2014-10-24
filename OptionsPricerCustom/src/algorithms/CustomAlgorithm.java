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
public class CustomAlgorithm extends Algorithm{
    
    static {
        AmericanCallOption.getInstance().registerAlgorithm("CustomAlgorithm", CustomAlgorithm.class);
        EuropeanCallOption.getInstance().registerAlgorithm("CustomAlgorithm", CustomAlgorithm.class);
        EuropeanPutOption.getInstance().registerAlgorithm("CustomAlgorithm", CustomAlgorithm.class);
    }
    
    private final String[] argsNames = null;

	@Override
	public String getName() {
		return "CustomAlgorithm";
	}

	@Override
	public Algorithm createAlgorithm() {
		return new CustomAlgorithm();
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
