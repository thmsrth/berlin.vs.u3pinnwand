
import java.util.Calendar;
import java.util.Date;

public class Message {
    private int index;
    private String content;
    private Date time;

    public Message(int index, String content){
        this.index = index;
        this.content = content;
        Calendar calendar = Calendar.getInstance();
        this.time = calendar.getTime();
    }

    public int getIndex(){
        return this.index;
    }

    public String getContent(){
        return this.content;
    }

    public Date getTime(){
        return this.time;
    }
}
