
package edu.escuelaing.arem.microspringboot;

/**
 *
 * @author nicole.montana
 */
public class WebService {
    
    @RequestMapping("/hello")
    public static String hello(){
        return "Hello World";
    } 
    
    @RequestMapping("/status")
    public static String serverStatus(){
        return "Runnig";
    } 
    
}
