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
public class AsianCallOption extends CallOptions{
    
    static {
        OptionsPricerFactory.getInstance().registerOption("AsianCallOption", AsianCallOption.class);
    }
  
    private static AsianCallOption instance;
    
    private AsianCallOption() {
        super();
    }
    
    public static AsianCallOption getInstance() {
        if (instance == null) {
            instance = new AsianCallOption();
        }
        return instance;
    }
    
}
