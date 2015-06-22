import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

public class PinnwandServer extends UnicastRemoteObject implements Pinnwand {

    private String presharedKey = "test123";
    private int maxNumMessages = 20;
    private int maxLengthMessage = 160;
    private String nameOfService = "Pinnwand";
    private int messageLifetime = 60; //sec

    private int clientLifetime = 300; //sec

    private List<Message> messageList;
    private int index;

    private List<ClientThread> clientThreadList;


    public PinnwandServer() throws RemoteException {
        super();
        messageList = new ArrayList<Message>();
        clientThreadList = new ArrayList<ClientThread>();
        index = 0;
        new Thread(new MessageLifetimeTrigger(this));
    }

    public static void main(String args[]) {
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            PinnwandServer server = new PinnwandServer();
            MessageLifetimeTrigger msgTrigger = new MessageLifetimeTrigger(server);
            ClientThreadsLifetimeTrigger ctTrigger = new ClientThreadsLifetimeTrigger(server);

            Naming.rebind(server.getNameOfService(), server);
            new Thread(msgTrigger).start();
            new Thread(ctTrigger).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public int login(String password) throws RemoteException {
        int returnVal;

        if (password == this.presharedKey) {

            if (!checkLoggedIn()) {
                this.clientThreadList.add(new ClientThread(getClient()));
            }
            // login
            returnVal = 0;
        } else {
            // error
            returnVal = 1;
        }
        return returnVal;
    }

    private boolean checkLoggedIn() {
        for (ClientThread ct : clientThreadList) {
            if (ct.getIpAddress().equals(getClient())) return true;
        }
        return false;
    }

    private String getClient() {
        String client = "";

        try {
            client = getClientHost();
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public int getMessageCount() throws RemoteException {
        if (checkLoggedIn()) {
            return this.messageList.size();
        }
        return 0;
    }

    @Override
    public String[] getMessages() throws RemoteException {
        if (checkLoggedIn()) {
            String[] list = new String[messageList.size()];
            int i = 0;
            for (Message m : messageList) {
                list[i] = m.getContent();
                i++;
            }
            return list;
        }
        return new String[0];
    }

    @Override
    public String getMessage(int index) throws RemoteException {
        if (checkLoggedIn()) {
            String message = "";
            for (Message m : messageList) {
                if (m.getIndex() == index) {
                    message = m.getContent();
                }
            }
            return message;
        }
        return null;
    }

    @Override
    public boolean putMessage(String msg) throws RemoteException {
        if (checkLoggedIn()) {
            if (maxLengthMessage < msg.length()) {
                return false;
            }
            if (maxNumMessages == messageList.size()) {
                messageList.remove(0);
                messageList.add(new Message(index, msg));
                index++;
                return true;
            }
            messageList.add(new Message(index, msg));
            return true;
        }
        return false;
    }

    public String getNameOfService() {
        return this.nameOfService;
    }

    public List<Message> getMessageList() {
        return this.messageList;
    }

    public int getMessageLifetime() {
        return this.messageLifetime;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public int getClientLifetime() {
        return clientLifetime;
    }

    public List<ClientThread> getClientThreadList() {
        return clientThreadList;
    }

    public void setClientThreadList(List<ClientThread> clientThreadList) {
        this.clientThreadList = clientThreadList;
    }
}
