package pizzaRanker;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PizzaUtility {
	Map<String, Integer> map = new HashMap<>();
	public static void test() {
		System.out.println("Testing Piza Utility");
	}
	
	public void downloadJsonFile(String url, String fileName) throws IOException {
		try {
			URL website = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		}
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}	
	
	public Map<String, Integer> readAndParsePizzaJson(String fileName ,boolean ignoreSequence) {
		try (FileReader reader = new FileReader(fileName)) {
			map.clear();
			map = new HashMap<>();
			
			JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
 
            JSONArray orders = (JSONArray) obj;        

            orders.forEach( order -> parsePizzaOrder( (JSONObject) order,  ignoreSequence)) ;
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return map;
	}	
	
	private  void parsePizzaOrder(JSONObject order, boolean ignoreSequence) {

		String toppingKey;
		JSONArray toppings = (JSONArray) order.get("toppings");
		
		if(ignoreSequence == true) {
			if (toppings.size() > 1 ) {	//toppings combo , make it unique by sorting
				Collections.sort(toppings);
			}
			toppingKey = toppings.toString();
		}
		else{
			toppingKey = toppings.toString();
		}	 	  
	   
	  if(map.containsKey(toppingKey)) {
		   map.put(toppingKey, map.get(toppingKey)+1 ); //increment value each time
	   }
	   else {
		   map.put(toppingKey, 1);	//first time entry
	   } 
	}
	
}
