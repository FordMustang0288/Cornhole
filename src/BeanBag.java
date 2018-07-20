//Erik Archambault
//CSC 212 Final Project; Cornhole Scoring Applet

/*
* Cornhole is based around the use of corn filled bags. 
* This class creates 8 bags: 4 blue (Team 1) and 4 red (Team 2). 
* The system also tracks whether or not the user’s mouse 
* is within a bag, as well as where the bag is intended to move.
*/

import java.awt.*;

public class BeanBag extends Shape
{
	//The following variables are declared to set
	//the properties and characteristics of our bean bag
	//class which builds upon the shape class
	public int width;
    public int height;
    public Color bagColor;
    
    //A variable to track the movement of our beanbag
    public boolean movedAlready = false;
	
    //Our constructor
	public BeanBag(int xPos, int yPos, int width, int height, Color bagColor, int id){
		//We call the super class' position and also construct local
		//width, height and color properties
		super(xPos, yPos); 
		this.width = width;
		this.height = height;
		this.bagColor = bagColor;
		super.setID(id);
	}
	
	//The paint method draws our particular bean bags
	public void paint(Graphics pane){
		//We pass in a color(blue or red) to the setColor
		//method which then paints the shape according to
		//width, height, and positioning
		pane.setColor(bagColor);		
		pane.fillRect(horizontal, vertical, width, height);
		//We do the same for the black outline of the shape
		pane.setColor(Color.black);			
		pane.drawRect(horizontal, vertical, width, height);	
	}
	
	//The isInside method tracks the positioning the the bag
	//so that we can properly score whether it is in the hole
	//on the platform, or out of play
	public Boolean isInside(int x, int y){
		 if (x >= horizontal && x < horizontal+width && y >= vertical && y < vertical+height)
	         return true;
	      else
	         return false;
	}
	
	// Move the shape by dx pixels horizontally and dy pixels vertically
    // (by changing the position of the top-left corner of the shape).
	public void moveBy(int dx, int dy){
		horizontal += dx;
		vertical += dy;
 }

}	// end BeanBag
