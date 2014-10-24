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
public class AmericanPutOption extends PutOptions{
    
    static {
        OptionsPricerFactory.getInstance().registerOption("AmericanPutOption", AmericanPutOption.class);
    }
  
    private static AmericanPutOption instance;
    
    private AmericanPutOption() {
        super();
    }
    
    public static AmericanPutOption getInstance() {
        if (instance == null) {
            instance = new AmericanPutOption();
        }
        return instance;
    }
    
}
