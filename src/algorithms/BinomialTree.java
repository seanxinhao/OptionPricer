/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithms;

import java.text.DecimalFormat;

import options.*;

/**
 * 
 * @author Xin
 */
public class BinomialTree extends Algorithm {

	static {
		AmericanCallOption.getInstance().registerAlgorithm("BinomialTree", BinomialTree.class);
		AmericanPutOption.getInstance().registerAlgorithm("BinomialTree", BinomialTree.class);
		EuropeanCallOption.getInstance().registerAlgorithm("BinomialTree", BinomialTree.class);
		EuropeanPutOption.getInstance().registerAlgorithm("BinomialTree", BinomialTree.class);
	}

	private final String[] argsNames = { "Time Intervals" };

	@Override
	public String getName() {
		return "BinomialTree";
	}

	@Override
	public Algorithm createAlgorithm() {
		return new BinomialTree();
	}

	@Override
	public Double calculate(Double[] args) {
		//args[0] numTimeIntervals
		final class Price {
			public double stockPrice;
			public double optionPrice;
		}
        int i, j;
        int numTimeIntervals = args[0].intValue();
		double deltaT	= termInYears / args[0];
		// Original values suggested by Hull (actually, from Cox, Ross and Rubinstein (1979))
		//double up		= exp(volatility * sqrt(deltaT));
		//double down	= 1 / up;
		//double a		= exp(interestRate * deltaT);
		//double upProb	= (a - down) / (up - down);
		//double downProb = 1.0 - upProb;
		// End Hull
		// Alternate values suggested by Prof. Hrusa
		double up		= 1.0 + interestRate * deltaT + (volatility*Math.sqrt(deltaT));
		double down		= 1.0 + interestRate * deltaT - (volatility*Math.sqrt(deltaT));
		double upProb	= 0.5;
		double downProb = 0.5;
		// End Hrusa
		double binomValue;
		Price[][] binomialTree = new Price[numTimeIntervals+1][numTimeIntervals+1];
		for (i = 0; i <= args[0]; i++)
			for (j = 0; j <= numTimeIntervals; j++)
				binomialTree[i][j] = new Price();

		// Fill the stockPrice component of the binomialTree
		for (i = 0; i <= numTimeIntervals; i++) {
			for (j = 0; j <= i; j++) {
				binomialTree[i][j].stockPrice = currentStockPrice * Math.pow(up, j) * Math.pow(down, i-j);
			}
		}
		// Fill the optionPrices at the terminal nodes
		for (j = 0; j <= numTimeIntervals; j++) {
                    if (side == true) //call option
                        binomialTree[numTimeIntervals][j].optionPrice =
				Math.max(binomialTree[numTimeIntervals][j].stockPrice - strikePrice, 0.0);
                    else //put option
			binomialTree[numTimeIntervals][j].optionPrice =
				Math.max(strikePrice - binomialTree[numTimeIntervals][j].stockPrice, 0.0);
		}
		// Now work backwards, filling optionPrices in the rest of the tree
		double discount = Math.exp(-interestRate*deltaT);
		for (i = numTimeIntervals-1; i >= 0; i--) {
			for (j = 0; j <= i; j++) {
                            // fix this to account for side
                            if(side == true)  //call option
				binomialTree[i][j].optionPrice = 
					Math.max(binomialTree[i][j].stockPrice - strikePrice,		
						discount*(upProb*binomialTree[i+1][j+1].optionPrice +
							downProb*binomialTree[i+1][j].optionPrice));
                            else //put option
                                binomialTree[i][j].optionPrice = 
					Math.max(strikePrice - binomialTree[i][j].stockPrice,		
						discount*(upProb*binomialTree[i+1][j+1].optionPrice +
							downProb*binomialTree[i+1][j].optionPrice));
			}
		}		
		// Report the result, then clean up
		binomValue = binomialTree[0][0].optionPrice;
                //Round up
                DecimalFormat df = new DecimalFormat("0.00");
                binomValue = Double.valueOf(df.format(binomValue).toString());
		return binomValue;
	}

	@Override
	public String[] getArgsNames() {
		return argsNames;
	}

}
