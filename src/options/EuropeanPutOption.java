/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package options;

import main.OptionsPricerFactory;

/**
 *
 * @author Xin
 */
public class EuropeanPutOption extends PutOptions{
    
    static {
        OptionsPricerFactory.getInstance().registerOption("EuropeanPutOption", EuropeanPutOption.class);
    }
  
    private static EuropeanPutOption instance;
    
    private EuropeanPutOption() {
        super();
    }
    
    public static EuropeanPutOption getInstance() {
        if (instance == null) {
            instance = new EuropeanPutOption();
        }
        return instance;
    }
    
}
