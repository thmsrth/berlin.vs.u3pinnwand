import java.util.Calendar;
import java.util.List;

public class MessageLifetimeTrigger implements Runnable {

        PinnwandServer server;

        public MessageLifetimeTrigger(PinnwandServer server){
            this.server = server;
        }

        @Override
        public void run() {

            try {

                while(true) {

                    System.out.println("check Message List");
                    List<Message> list = server.getMessageList();

                    for(Message m: list){
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.SECOND, - server.getMessageLifetime());
                        if (calendar.getTime().after(m.getTime())){
                            list.remove(m);
                        }
                    }
                    server.setMessageList(list);

                    System.out.println("Going to sleep ...zzzZZZ");

                    // will sleep for at least 5 seconds (5000 miliseconds)
                    Thread.sleep(10000L);
                }
            } catch ( InterruptedException exc ) {
                exc.printStackTrace();
            }
        }
}

