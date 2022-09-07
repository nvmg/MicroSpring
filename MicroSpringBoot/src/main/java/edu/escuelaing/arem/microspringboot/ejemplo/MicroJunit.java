package edu.escuelaing.arem.microspringboot.ejemplo;

import edu.escuelaing.arem.microspringboot.RequestMapping;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author nicole.montana
 */
public class MicroJunit {
    
    public static void main(String... args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException{
        String className = args[0];
        Class c = Class.forName(className);
        Method[] declaredMethods = c.getDeclaredMethods();
        
        int passed = 0;
        int failed = 0;
        
        for(Method m : declaredMethods){
            if(m.isAnnotationPresent(RequestMapping.class)){
                try{
                    System.out.println("Invoking: " + m.getName() + " in class: " + c.getName());
                    m.invoke(null);
                    passed = passed + 1;
                }catch(Exception e){
                    failed = failed + 1;
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Passed: " + passed + ", Failed: " + failed);
    }
    
}
