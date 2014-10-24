/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

/**
 * 
 * @author Xin
 */
public abstract class Algorithm {

	protected boolean side;

	protected Double currentStockPrice;
	protected Double strikePrice;
	protected Double termInYears;
	protected Double interestRate;
	protected Double volatility;

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

	abstract public String[] getArgsNames();

	abstract public String getName();

	abstract public Algorithm createAlgorithm();

	abstract public Double calculate(Double[] args);
}
