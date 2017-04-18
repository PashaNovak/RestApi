package RestService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/tickets")
    public class TestRestfulTickets{
    	public TestRestfulTickets(){
    		
    	}
    	
    	@GET
        @Produces("application/xml")
        public String getSchdule() {
            StringBuffer xmlTickets = new StringBuffer();
            try {
                FileReader fr = new FileReader("E:\\Ó÷¸áà\\Java\\RestService\\Resources\\Tickets.xml");
                BufferedReader in = new BufferedReader(fr);
                String tmpStr;
                while ((tmpStr = in.readLine()) != null) {
                    xmlTickets.append(tmpStr);
                }
                in.close();
                fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return xmlTickets.toString();
        }
    	
    	@POST
        @Consumes("application/xml")
        public void addTicket(String xmlTicket){
        	StringBuffer existTickets = new StringBuffer();
            try {
                FileReader fr = new FileReader("E:\\Ó÷¸áà\\Java\\RestService\\Resources\\Tickets.xml");
                BufferedReader in = new BufferedReader(fr);
                String tmpStr;
                while ((tmpStr = in.readLine()) != null) {
                    existTickets.append(tmpStr);
                }
                existTickets.insert(existTickets.indexOf("</Tickets>"), xmlTicket);
                fr.close();
                in.close();
                FileWriter fw = new FileWriter("E:\\Ó÷¸áà\\Java\\RestService\\Resources\\Tickets.xml");
                fw.write(existTickets.toString());
                fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
    	
    	@PUT
        @Consumes("application/xml")
        public void delTicket(String xmlTicket){
        		StringBuffer existTickets = new StringBuffer();
                try {
                    FileReader fr = new FileReader("E:\\Ó÷¸áà\\Java\\RestService\\Resources\\Tickets.xml");
                    BufferedReader in = new BufferedReader(fr);
                    String tmpStr;
                    while ((tmpStr = in.readLine()) != null) {
                        existTickets.append(tmpStr);
                    }
                    existTickets.delete(0, existTickets.length());
                    existTickets.append(xmlTicket);
                    fr.close();
                    in.close();
                    FileWriter fw = new FileWriter("E:\\Ó÷¸áà\\Java\\RestService\\Resources\\Tickets.xml");
                    fw.write(existTickets.toString());
                    fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
        	
        }
    }
