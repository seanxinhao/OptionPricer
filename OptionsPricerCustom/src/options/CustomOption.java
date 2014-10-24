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
public class CustomOption extends CallOptions{
    
    static {
        OptionsPricerFactory.getInstance().registerOption("CustomOption", CustomOption.class);
    }
  
    private static CustomOption instance;
    
    private CustomOption() {
        super();
    }
    
    public static CustomOption getInstance() {
        if (instance == null) {
            instance = new CustomOption();
        }
        return instance;
    }
    
}
