
package edu.escuelaing.arem.microspringboot.ejemplo;

import edu.escuelaing.arem.microspringboot.RequestMapping;

/**
 *
 * @author nicole.montana
 */
public class JunitTest {
    
    @Test
    public static void m1(){}
   
    public static void m2(){}
    
    @Test
    public static void m3(){}
    
    @Test
    public static void m4() throws Exception{
        throw(new Exception("Error m4"));
    }
    
    @RequestMapping("/")
    public static void m5(){
        System.out.println("Hello");
    }
    
    @Test
    public static void m6(){}
    
}
