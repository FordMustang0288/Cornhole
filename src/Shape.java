//Erik Archambault
//CSC 212 Final Project; Cornhole Scoring Applet

/* Here we define a generic shape that is then
 * used to create our platform, circle, bags, and scoreboards
 */
public class Shape
{

	public int horizontal;
    public int vertical;
    public int id;
    
    // Our default constructor the houses a horizontal
    //and vertical position to be used with our child classes
    public Shape (int xPos, int yPos){
    	horizontal = xPos;
    	vertical = yPos;    	
    }
    
    //The setID method will be used to track each of our bags
    //on an individual basis
    public void setID(int value){
    	this.id = value;
    }
    
    //We also want to know which bags we are working with
    public int getID(){   
    	return id;
    }
    
    public void paint(){
    
    }
}//End Shape
