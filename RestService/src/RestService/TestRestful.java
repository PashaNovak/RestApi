package RestService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/schedule")
public class TestRestful {
    public TestRestful() {
    }
    
    @GET
    @Produces("application/xml")
    public String getSchdule() {
        StringBuffer xmlSchedule = new StringBuffer();
        try {
            FileReader fr = new FileReader("E:\\Ó÷¸áà\\Java\\RestService\\Resources\\Schedule.xml");
            BufferedReader in = new BufferedReader(fr);
            String tmpStr;
            while ((tmpStr = in.readLine()) != null) {
                xmlSchedule.append(tmpStr);
            }
            in.close();
            fr.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return xmlSchedule.toString();
    }
}

