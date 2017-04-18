package RESTClient;
import java.net.URI;
import java.text.SimpleDateFormat;

import com.thoughtworks.xstream.XStream;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class ClientREST {

    public static void main(String[] args) { 

        String endPoint = "http://localhost:8080/RestService";

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);

        WebResource service = client.resource(endPoint);
        while (true) {
            try {
                System.out.println("Choose nessesary operation, please: ");
                System.out.println("1 - schedule.");
                System.out.println("2 - reservation ticket.");
                System.out.println("3 - my booking.");
                System.out.println("4 - cancel booking.");
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                int choose = Integer.parseInt(in.readLine());
                switch (choose) {
                    case 1:
                    {
                        String xmlMovies = service.path("rest").path("schedule")
                                .accept(MediaType.APPLICATION_XML).get(String.class);

                        XStream stream = new XStream();
                        stream.alias("movie", Movie.class);
                        stream.alias("schedule", Movie[].class);
                        Movie[] movies = (Movie[]) stream.fromXML(xmlMovies);
                        for (Movie current : movies){
                        	System.out.println("---------------------------------------");
                            System.out.println("Information about movies: ");
                            System.out.println("Movie Title: " + current.getMovieTitle());
                            System.out.println("Cost: " + current.getCost());
                            System.out.println("Time: " + current.getTime());
                            System.out.println("Date: " + current.getDate());
                            System.out.println("---------------------------------------");
                        }
                        break;
                    }
                    case 2:
                    {
                    	int chooser = 0;
                    	String xmlMovies = service.path("rest").path("schedule")
                                .accept(MediaType.APPLICATION_XML).get(String.class);

                        XStream stream1 = new XStream();
                        stream1.alias("movie", Movie.class);
                        stream1.alias("schedule", Movie[].class);
                        Movie[] movies = (Movie[]) stream1.fromXML(xmlMovies);
                        Ticket addedTicket = null;
                        String title = "";
                        System.out.println("Select movie: ");
                        while (true){
                        	for (int i = 0; i < movies.length; i++){
                            	System.out.println( (i+1) + " " + movies[i].getMovieTitle() );
                            }
                        	chooser = Integer.parseInt(in.readLine());
                        	if (chooser > movies.length || chooser <= 0){
                        		System.out.println("Please, enter the right number");
                        	} else {
                        		title = movies[chooser-1].getMovieTitle();
                        		for (Movie current : movies){
                                	if (current.getMovieTitle().equals(title)){
                                		addedTicket = new Ticket(current);
                                		break;
                                	}
                                }
                        		break;
                        	}
                        }
                        
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        
                        String time = addedTicket.getTime();
                        while (true){
                        	System.out.println("Select session time: " + time);
                        	String mytime = in.readLine();
                        	if (!time.contains(mytime)){
                        		System.out.println("Please, choose the right time");
                        	} else if (time.contains(mytime)){
                        		try{
                        			sdf.parse(mytime);
                            		addedTicket.setTime(mytime);
                            		break;
                        		} catch (Exception e) {
                        			System.out.println("Incorrect time!");
                        		}
                        	} else {
                        		System.out.println("Incorrect!");
                        	}
                        }
                        
                        int k = 0;
                        System.out.println("Input amount: ");
                        k = Integer.parseInt(in.readLine());
                        String masPlace = "";
                        for (int c = 0; c < k; c++){
                        	masPlace = masPlace + ((int)(Math.random()*100))+",";
                        }
                        
                        String changedPlaces = masPlace.substring(0, masPlace.length()-1);
                        addedTicket.setAmount(k);
                        addedTicket.setPlaces(changedPlaces);
                        int id = (int)(Math.random()*500);
                        addedTicket.setId(id);
                        
                        XStream stream = new XStream();
                        stream.alias("ticket", Ticket.class);
                        String xmlTicket = stream.toXML(addedTicket);
                        
                        service.path("rest").path("tickets").type("application/xml").post(xmlTicket);
                        break;
                    }
                    case 3:{
                    	String xmlTickets = service.path("rest").path("tickets")
                                .accept(MediaType.APPLICATION_XML).get(String.class);

                        XStream stream = new XStream();
                        stream.alias("ticket", Ticket.class);
                        stream.alias("Tickets", Ticket[].class);
                        Ticket[] tickets = (Ticket[]) stream.fromXML(xmlTickets);
                        for (Ticket current : tickets){
                        	System.out.println("---------------------------------------");
                            System.out.println("Information about tickets: ");
                            System.out.println("Movie Title: " + current.getMovieTitle());
                            System.out.println("Date: " + current.getDate());
                            System.out.println("Cost: " + current.getCost());
                            System.out.println("Time: " + current.getTime());
                            System.out.println("Places: " + current.getPlaces());
                            System.out.println("ID: " + current.getId());
                            System.out.println("Amount of places: " + current.getAmount());
                            System.out.println("---------------------------------------");
                        }
                        break;
                    }
                    case 4:{
                    	String xmlTickets = service.path("rest").path("tickets")
                                .accept(MediaType.APPLICATION_XML).get(String.class);
                    	XStream stream = new XStream();
                        stream.alias("ticket", Ticket.class);
                        stream.alias("Tickets", Ticket[].class);
                        Ticket[] ticketsMas = (Ticket[]) stream.fromXML(xmlTickets);
                        List<Ticket> tickets = new ArrayList<>();
                        
                        for(int i = 0; i < ticketsMas.length; i++){
                        	tickets.add(ticketsMas[i]);
                        }
                        
                    	System.out.println("Input tiket's id: ");
                    	int id = Integer.parseInt(in.readLine());
                    	for (int i = 0; i < tickets.size(); i++){
                    		if (tickets.get(i).getId() == id){
                    			 tickets.remove(i);
                    			break;
                    		}
                    	}
                    	
                    	Ticket[] newMyTickets = tickets.toArray(new Ticket[tickets.size()]);
                    	
                    	stream.alias("ticket", Ticket.class);
                    	stream.alias("Tickets", Ticket[].class);
                    	String newTickets = stream.toXML(newMyTickets);
                    	if (newTickets.equals("<Tickets/>")){
                    		newTickets = "<Tickets></Tickets>";
                    	}
                    	service.path("rest").path("tickets").type("application/xml").put(newTickets);
                    	
                    	break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

class Movie{
	String date = "";
	String time = "";
	String cost = "";
	String title = "";
	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getMovieTitle(){
		return title;
	}
	
	public void setMovieTitle(String title){
		this.title = title;
	}
	
	public String getCost(){
		return cost;
	}
	
	public void setCost(String cost){
		this.cost = cost;
	}
	
	public String getTime(){
		return time;
	}	
	
	public void setTime(String time){
		this.time = time;
	}
}
class Ticket{
	int amount = 0;
	int id = 0;
	String time = "";
	String title = "";
	String cost = "";
	String places = "";
	String date = "";
	
	public Ticket(Movie movie){
		this.time = movie.getTime();
		this.cost = movie.getCost();
		this.title = movie.getMovieTitle();
		this.date = movie.getDate();
	}
	
	public String getDate(){
		return date;
	}
	
	public void setPlaces(String places){
		this.places = places;
	}
	
	public void setTime(String time){
		this.time = time;
	}
	
	public String getTime(){
		return time;
	}
	
	public String getPlaces(){
		return places;
	}
	
	public String getMovieTitle(){
		return title;
	}
	
	public String getCost(){
		return cost;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public int getAmount(){
		return amount;
	}
}
