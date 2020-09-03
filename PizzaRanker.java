package pizzaRanker;
import java.io.IOException;
import java.util.*;


public class PizzaRanker {
	
	boolean ignoreSequence = true; //[pineapple, ham] and [ham , pineapple] are same toppings
	int topCount = 1;
	
	String fileName = "pizzas.json";	
	Map<String, Integer> map ;
	
	public PizzaRanker(String fileName, int topCount) {
		this.fileName = fileName;
		this.topCount = topCount;
	}
	
	public static void main(String[] args) throws IOException {
		int displayCount = 20;
		
		PizzaRanker pr = new PizzaRanker("pizzas.json", displayCount);
		PizzaUtility utility = new PizzaUtility();
	
		utility.downloadJsonFile("https://www.olo.com/pizzas.json" , pr.fileName);
		
		pr.setPizzaMap( utility.readAndParsePizzaJson(pr.fileName , pr.getToppingSequenceIgnore()) );

		pr.showByToppingsRank(pr.getTopCount());
	}
	
	public void setPizzaMap(Map<String, Integer> parsedOrders) {
		this.map = parsedOrders;
	}
	
	public boolean getToppingSequenceIgnore() {
		return this.ignoreSequence;
	}
	
	public int getTopCount() {
		return this.topCount;
	}
	
	   
	public  void showByToppingsRank(int topCounter){ 	
        Map<String, Integer> sortedToppingsMap = sortByToppingsOrderValue(map);   
        int rank = 0;
        
        for (Map.Entry<String, Integer> topping : sortedToppingsMap.entrySet()) { 
            System.out.println("Rank " + ++rank + ", " + topping.getKey() + ", Ordered Times= " + topping.getValue()); 
            
            if (rank >= topCounter) {
            	break;
            }
        }          
    } 
	
	
	public  HashMap<String, Integer> sortByToppingsOrderValue(Map<String, Integer> orderMap) { 
        List<Map.Entry<String, Integer> > orderlist =  new LinkedList<Map.Entry<String, Integer> >(orderMap.entrySet());
  
        Collections.sort(orderlist, new Comparator<Map.Entry<String, Integer> >() { 
            public int compare(Map.Entry<String, Integer> topping1,  Map.Entry<String, Integer> topping2) { 
                return (topping2.getValue()).compareTo(topping1.getValue()); //Max first , descending order
            } 
        }); 
          
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
        
        for (Map.Entry<String, Integer> orderedTopping : orderlist) { 
            temp.put(orderedTopping.getKey(), orderedTopping.getValue()); 
        } 
        return temp; 
    } 		
}
