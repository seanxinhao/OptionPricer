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
public class AsianPutOption extends PutOptions{
    
    static {
        OptionsPricerFactory.getInstance().registerOption("AsianPutOption", AsianPutOption.class);
    }
  
    private static AsianPutOption instance;
    
    private AsianPutOption() {
        super();
    }
    
    public static AsianPutOption getInstance() {
        if (instance == null) {
            instance = new AsianPutOption();
        }
        return instance;
    }
    
}
