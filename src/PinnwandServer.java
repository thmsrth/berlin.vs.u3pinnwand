import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class PinnwandServer implements  Pinnwand {

    private String presharedKey = "test123";
    private int maxNumMessages = 20;
    private int maxLengthMessage = 160;
    private String nameOfService = "Pinnwand";
    private int messageLifetime = 600; //sec

    private List<Message> messageList;
    private int index;

    public PinnwandServer() throws RemoteException {
        messageList = new ArrayList<Message>();
        index = 0;
        new Thread(new MessageLifetimeTrigger(this));
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
        return this.messageList.size();
    }

    @Override
    public String[] getMessages() throws RemoteException {
        String[] list = new String[messageList.size()];
        int i = 0;
        for(Message m: messageList){
            list[i] = m.getContent();
            i++;
        }
        return list;
    }

    @Override
    public String getMessage(int index) throws RemoteException {
        String message = "";
        for(Message m: messageList){
            if (m.getIndex() == index) {
                message = m.getContent();
            }
        }
        return message;
    }

    @Override
    public boolean putMessage(String msg) throws RemoteException {
        if (maxLengthMessage < msg.length()){
            return false;
        }
        if (maxNumMessages == messageList.size()){
            messageList.remove(0);
            messageList.add(new Message(index, msg));
            index++;
            return true;
        }
        messageList.add(new Message(index, msg));
        return true;
    }

    public List<Message> getMessageList(){
        return this.messageList;
    }

    public int getMessageLifetime(){
        return this.messageLifetime;
    }

    public void setMessageList(List<Message> messageList){
        this.messageList = messageList;
    }

}
