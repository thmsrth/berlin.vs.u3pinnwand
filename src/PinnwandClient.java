import java.rmi.Naming;

public class PinnwandClient {

	public static void main(String args[]) {
		try {
			// Pinnwand server = (Pinnwand)Naming.lookup("pinnwand");
			PinnwandServer server = (PinnwandServer) Naming.lookup("pinnwand");
			
			// Schicke Nachricht
			if(server.putMessage("Hallo zaeme!!!")){
				System.out.println("Nachricht erfolgreich an Pinnwand geschickt.");
			}else{
				System.out.println("Nachricht zu lang!");
			}
			
			// Schicke 2. Nachricht
			if(server.putMessage("Aues klaaar?")){
				System.out.println("Nachricht erfolgreich an Pinnwand geschickt.");
			}else{
				System.out.println("Nachricht zu lang!");
			}
			
			// Schicke zu lange Nachricht
			if(server.putMessage("Mehr als 160 Zeicheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
					+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
					+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
					+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
					+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeen")){
				System.out.println("Nachricht erfolgreich an Pinnwand geschickt.");
			}else{
				System.out.println("Nachricht zu lang!");
			}
			
			// Zeige Anzahl Nachrichten
			System.out.println("Anzahl Nachrichten: "+server.getMessageCount());
			
			// Gib Nachricht 0 zurueck
			String result = server.getMessage(0);
			System.out.println(result);
			
			// Liste alle Nachrichten auf
			String[] messages = new String[20];
			messages = server.getMessages();
			for(String s : messages){
				System.out.println(s);
			}
			
			// System.out.println(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}