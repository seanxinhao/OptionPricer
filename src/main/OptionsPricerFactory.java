/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import options.Options;

/**
 *
 * @author Xin
 */
public class OptionsPricerFactory {
    
    private static OptionsPricerFactory instance;
    private final HashMap registeredOptions; 
    
    private OptionsPricerFactory() {
        registeredOptions = new HashMap();
    }
    
    public static synchronized OptionsPricerFactory getInstance() {
        if (instance == null) {
            instance = new OptionsPricerFactory();
        }
        return instance;
    }
    
    public void registerOption(String optionID, Class optionClass) {
        registeredOptions.put(optionID, optionClass);
    }
    
    public Options getOption(String optionID) throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Class c_option = (Class)registeredOptions.get(optionID);
        Method m_getInstance = c_option.getMethod("getInstance", null);
        Options option = (Options)m_getInstance.invoke(null, null);
        
        return option;
    }
    
}
