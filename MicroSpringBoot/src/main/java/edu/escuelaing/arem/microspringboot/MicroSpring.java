package edu.escuelaing.arem.microspringboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author nicole.montana
 */
public class MicroSpring {
  
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, URISyntaxException {
        String className = args[0];
        Class c = Class.forName(className);
        Method[] declareMethods = c.getDeclaredMethods();
        
        Map<String, Method> rutas = new HashMap<String, Method>();

        for (Method m : declareMethods) {
            if (m.isAnnotationPresent(RequestMapping.class)) {
                try {
                    rutas.put(m.getAnnotation(RequestMapping.class).value(), m);
                    System.out.println("Invoking: " + m.getName() + " in class " + c.getName());
                    //System.out.println(m.invoke(null));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean run = true;
        while (run) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;

            String path = null;
            
            boolean primera = true;
            while ((inputLine = in.readLine()) != null) {
                if(primera){ 
                    path = inputLine.split(" ")[1];
                    primera = false;
                if (!in.ready()) {
                    break;
                }
            }
            
            String content = "Errom ";
            if(rutas.containsKey(path)){
                content = (String) rutas.get(path).invoke(null);
            }
            
            outputLine = "HTTP/1.1 200 Ok\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + content;
            out.println(outputLine);

            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }
}
