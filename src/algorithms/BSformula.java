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
public class BSformula extends Algorithm{
    
    static {
        AmericanCallOption.getInstance().registerAlgorithm("BSformula", BSformula.class);
        EuropeanCallOption.getInstance().registerAlgorithm("BSformula", BSformula.class);
        EuropeanPutOption.getInstance().registerAlgorithm("BSformula", BSformula.class);
    }
    
    private final String[] argsNames = null;

	@Override
	public String getName() {
		return "BSformula";
	}

	@Override
	public Algorithm createAlgorithm() {
		return new BSformula();
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
