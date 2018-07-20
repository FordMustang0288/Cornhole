//Erik Archambault
//CSC 212 Final Project; Cornhole Scoring Applet
/*
* The circle class will define and implement a circle shape
*/

import java.awt.*;

public class Circle extends Shape
{
	public int radius;
	public int x;
	public int insideX;
	public int y, insideY;
   
	//Our constructor
	public Circle(int xPos, int yPos, int radius){
		//We call the super class' position and also construct a local
		//radius property
		super(xPos, yPos);
		this.radius = radius;
		
		//The insideX and insideY variables are meant to be
		//used to track whether or not our bags are inside to
		//keep proper score
		insideX = xPos;
		insideY = yPos;
	}	
	
	//
	//	The only graphical method of the class is the paint method.
	//
	public void paint(Graphics pane)
	{
		pane.setColor(Color.black);			//	A circle
		pane.fillOval(horizontal, vertical, radius, radius);	//		and
		pane.setColor(Color.black);			//		a black frame
		pane.drawOval(horizontal, vertical, radius, radius);	//		around it
	}
	
	//The isInside method tracks whether or not our bean bag is within
	//the boundaries of the circle for scoring purposes
	public boolean isInside(int x, int y, int bagWidth, int bagHeight){
		if((x> (insideX) && (x < (insideX + radius - bagWidth)) && 
				(y > (insideY) && (y<(insideY + radius - bagHeight))))){
			return true;
		}
			return false;				
	}

	
}	// end Circle