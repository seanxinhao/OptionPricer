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
public class EuropeanCallOption extends CallOptions{
    
    static {
        OptionsPricerFactory.getInstance().registerOption("EuropeanCallOption", EuropeanCallOption.class);
    }
  
    private static EuropeanCallOption instance;
    
    private EuropeanCallOption() {
        super();
    }
    
    public static EuropeanCallOption getInstance() {
        if (instance == null) {
            instance = new EuropeanCallOption();
        }
        return instance;
    }
    
}
