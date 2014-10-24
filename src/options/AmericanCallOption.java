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
public class AmericanCallOption extends CallOptions{
    
    static {
        OptionsPricerFactory.getInstance().registerOption("AmericanCallOption", AmericanCallOption.class);
    }
  
    private static AmericanCallOption instance;
    
    private AmericanCallOption() {
        super();
    }
    
    public static AmericanCallOption getInstance() {
        if (instance == null) {
            instance = new AmericanCallOption();
        }
        return instance;
    }
    
}
