import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class MessageLifetimeTrigger implements Runnable {

    PinnwandServer server;

    public MessageLifetimeTrigger(PinnwandServer server) {
        this.server = server;
    }

    @Override
    public void run() {

        try {

            while (true) {

                System.out.println("check Message List");
                List<Message> list = server.getMessageList();

                for (Iterator<Message> iterator = list.iterator(); iterator.hasNext(); ) {
                    Message msg = iterator.next();
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.SECOND, -server.getMessageLifetime());
                    if (calendar.getTime().after(msg.getTime())) {
                        System.out.println("Remove Message with Index:" + msg.getIndex());
                        iterator.remove();
                    }
                }
                server.setMessageList(list);

                System.out.println("Going to sleep ...zzzZZZ");

                // will sleep for at least 5 seconds (5000 miliseconds)
                Thread.sleep(10000L);
            }
        } catch (InterruptedException exc) {
            exc.printStackTrace();
        }
    }
}

