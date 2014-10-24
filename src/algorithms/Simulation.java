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
public class Simulation extends Algorithm{
    
    static {
        AmericanCallOption.getInstance().registerAlgorithm("Simulation", Simulation.class);
        AmericanPutOption.getInstance().registerAlgorithm("Simulation", Simulation.class);
        EuropeanCallOption.getInstance().registerAlgorithm("Simulation", Simulation.class);
        EuropeanPutOption.getInstance().registerAlgorithm("Simulation", Simulation.class);
        AsianCallOption.getInstance().registerAlgorithm("Simulation", Simulation.class);
        AsianPutOption.getInstance().registerAlgorithm("Simulation", Simulation.class);
    }
    
    private final String[] argsNames = null;

	@Override
	public String getName() {
		return "Simulation";
	}

	@Override
	public Algorithm createAlgorithm() {
		return new Simulation();
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
