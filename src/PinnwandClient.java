import java.rmi.Naming;

public class PinnwandClient {

    public static void main(String args[]) {
        try {
            PinnwandServer server = (PinnwandServer)Naming.lookup("pinnwand");
//            String result = server
//            System.out.println(result);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}