import java.rmi.Naming;
import java.rmi.RemoteException;

public class PinnwandServer implements  Pinnwand {

    private String presharedKey = "test123";
    private int maxNumMessages = 20;
    private int maxLengthMessage = 160;
    private String nameOfServic = "Pinnwand";
    private int messageLifetime = 600; //sec

    public PinnwandServer() throws RemoteException {

    }

    public static void main(String args[]) {
        try {
            Naming.rebind("pinnwand", new PinnwandServer());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public int login(String password) throws RemoteException {
        int returnVal;

        if (password == this.presharedKey){
            // login
            returnVal = 0;
        } else {
            // error
            returnVal = 1;
        }
        return returnVal;
    }

    @Override
    public int getMessageCount() throws RemoteException {
        return 0;
    }

    @Override
    public String[] getMessages() throws RemoteException {
        return new String[0];
    }

    @Override
    public String getMessage(int index) throws RemoteException {
        return null;
    }

    @Override
    public boolean putMessage(String msg) throws RemoteException {
        return false;
    }
}
