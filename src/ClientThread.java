import java.util.Calendar;
import java.util.Date;

public class ClientThread {
    private String ipAddress;
    private Date loginTime;

    public ClientThread(String ipAddress) {
        this.ipAddress = ipAddress;
        Calendar calendar = Calendar.getInstance();
        this.loginTime = calendar.getTime();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Date getLoginTime() {
        return loginTime;
    }
}
