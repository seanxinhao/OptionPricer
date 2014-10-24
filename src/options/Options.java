/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package options;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import algorithms.Algorithm;

/**
 * 
 * @author Xin
 */
public abstract class Options {

	protected boolean side;

	protected Double currentStockPrice;
	protected Double strikePrice;
	protected Double termInYears;
	protected Double interestRate;
	protected Double volatility;

	protected final HashMap<String, Class> registeredAlgorithms;

	protected Options() {
		registeredAlgorithms = new HashMap();
	}

	public void setCurrentStockPrice(Double csp) {
		currentStockPrice = csp;
	}

	public Double getCurrentStockPrice() {
		return currentStockPrice;
	}

	public void setStrikePrice(Double sp) {
		strikePrice = sp;
	}

	public Double getStrikePrice() {
		return strikePrice;
	}

	public void setTermInYears(Double t) {
		termInYears = t;
	}

	public Double getTermInYears() {
		return termInYears;
	}

	public void setInterestRate(Double ir) {
		interestRate = ir;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setVolatility(Double v) {
		volatility = v;
	}

	public Double getVolatilit() {
		return volatility;
	}

	public void setSide(boolean s) {
		side = s;
	}

	public boolean getSide() {
		return side;
	}

	public void registerAlgorithm(String algorithmID, Class algorithmClass) {
		registeredAlgorithms.put(algorithmID, algorithmClass);
	}

	public Algorithm createAlgorithm(String algorithmID) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class a = (Class) registeredAlgorithms.get(algorithmID);
		Constructor algorithmConstructor = a.getDeclaredConstructor(new Class[] {});
		return (Algorithm) algorithmConstructor.newInstance(new Object[] {});
	}

	public ArrayList<String> getAlgorithms() {
		ArrayList<String> result = new ArrayList<String>();
		for (String entry : registeredAlgorithms.keySet()) {
			result.add(entry);
		}
		return result;
	}

	public String[] getAlgorithmArgsNames(String algorithmID) {
		Algorithm a;
		try {
			a = (Algorithm) createAlgorithm(algorithmID);
			return a.getArgsNames();
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Double getPrice(String algorithmID, Double[] args) {
		Algorithm a;
		try {
			a = (Algorithm) createAlgorithm(algorithmID);
			a.setCurrentStockPrice(currentStockPrice);
			a.setInterestRate(interestRate);
			a.setSide(side);
			a.setStrikePrice(strikePrice);
			a.setTermInYears(termInYears);
			a.setVolatility(volatility);
			return a.calculate(args);
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.0;
	}
}
