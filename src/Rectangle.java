//Erik Archambault
//CSC 212 Final Project; Cornhole Scoring Applet

/*
 * In addition to creating/painting the platform used to track game play, 
 * the Rectangle class also controls the scoring aspect of the game. 
 * This is due to the fact that the scoring zones are on the platform, or in a hole cut from the platform.
*/

import java.awt.*;

public class Rectangle extends Shape
{
	//The width and height of the platform
	public int width;
    public int height;
    //A circle named bullseye to track scoring boundaries
    public Circle bullseye;
	
    //Our constructor with super positioning and local dimensions
	public Rectangle(int xPos, int yPos, int width, int height){
		super(xPos, yPos); 
		this.width = width;
		this.height = height;
	}
	
	//The setBullseye method references our circle so that we can 
	//track whether or not we are inside that circle later on in the class
	public void setBullseye(Circle cir){
		bullseye = cir;
	}
	
	//Tracking where each bag is placed and whether it is within the boundaries of the circle or 
	//the platform by using the isInside method. We then need to worry about the cancellation type 
	//scoring rule which completes the determineCurrentScore method
	public void determineCurrentScore(BeanBag bag, ScoreBoard ScoringTeam, ScoreBoard OtherTeam)
	{
		//Our default out of play score is 0		
		int scoreValue = 0;
		
		//If the bag is within the circle we have a value of 3
		if(bullseye.isInside(bag.horizontal, bag.vertical, bag.width, bag.height))
		{		
			scoreValue = 3;
		} 
		//On the platform, 1
		else if(this.isInside(bag.horizontal, bag.vertical, bag.width, bag.height))
		{
			scoreValue = 1;
		} 
		
		//We then assign the bag score and an id to the hash table
		ScoringTeam.setBagScore(scoreValue, bag.getID());
		
		int ScoringTeamCurrentScore = ScoringTeam.getScore();
		int ScoringTeamTempScore = ScoringTeam.getTempScore();
		int OtherTeamCurrentScore = OtherTeam.getScore();
		int OtherTeamTempScore = OtherTeam.getTempScore();
		
		//Based on the cornhole rules we need to factor cancellation type scoring
		//That is done by finding any bag and its ID that has a score value in 
		//increments of 3.
		if((OtherTeamCurrentScore - ScoringTeamTempScore) >=0){
			//The temp score will keep track of values 3, 6, 9, 12
			//Therefore these functions will also work with a throwing score of 1
			OtherTeam.setScore(OtherTeamCurrentScore - ScoringTeamTempScore);
		}
		//We then determine if the two bags will cancel out	
		if((ScoringTeamCurrentScore - OtherTeamTempScore) >=0){
			ScoringTeam.setScore(ScoringTeamCurrentScore - OtherTeamTempScore);
		}else{
			ScoringTeam.setScore(ScoringTeamCurrentScore - ScoringTeamTempScore);
		}

	}
	
	//The isInside method returns whether the bag is on the platform or not
	public Boolean isInside(int x, int y, int width, int height)
	{		
		if((x > (this.horizontal - width/2) && x < (this.horizontal + this.width - (width/2))) && 
				y > (this.vertical - height/2) && y < (this.vertical + this.height - (height/2))){
			return true;
		}
			return false;
	}
	
	//
	//	The only graphical method of the class is the paint method.
	//
	public void paint(Graphics pane)
	{
		
		pane.setColor(new Color(205,133,63));			//	A green square
		pane.fillRect(horizontal, vertical, width, height);	//		and
		pane.setColor(Color.black);			//		a black frame
		pane.drawRect(horizontal, vertical, width, height);	//		around it
	}
}	// end Rectangle

