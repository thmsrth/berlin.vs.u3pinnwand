
package de.dpunkt.rmi.hello;

import java.rmi.Naming;

public class PinnwandClient {

    public static void main(String args[]) {
        try {
            PinnwandServer server = (PinnwandServer)Naming.lookup("pinnwand");
            String result = server.sayHello();
            System.out.println(result);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}