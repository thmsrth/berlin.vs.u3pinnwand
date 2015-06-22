import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class ClientThreadsLifetimeTrigger implements Runnable {

    PinnwandServer server;

    public ClientThreadsLifetimeTrigger(PinnwandServer server) {
        this.server = server;
    }

    @Override
    public void run() {

        try {

            while (true) {

                System.out.println("check Client Thread List");
                List<ClientThread> list = server.getClientThreadList();

                for (Iterator<ClientThread> iterator = list.iterator(); iterator.hasNext(); ) {
                    ClientThread ct = iterator.next();
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.SECOND, -server.getMessageLifetime());
                    if (calendar.getTime().after(ct.getLoginTime())) {
                        System.out.println("Remove Client with IP:" + ct.getIpAddress());
                        iterator.remove();
                    }
                }

                server.setClientThreadList(list);

                System.out.println("Going to sleep ...zzzZZZ");

                // will sleep for at least 5 seconds (5000 miliseconds)
                Thread.sleep(10000L);
            }
        } catch (InterruptedException exc) {
            exc.printStackTrace();
        }
    }
}

