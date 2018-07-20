//Erik Archambault
//CSC 212 Final Project; Cornhole Scoring Applet


/*This class is in charge of the scoring within the user interface. 
 * There are 4 scoreboards where each team has 2. The scoreboards per team are used to track the 
 * current inning score as well as the total game score. It not only does this by controlling the 
 * digit based displays, but also the bags themselves. 
 * Each bag has an ID within a hashmap as well as it’s score value based on where it was placed.
*/

import java.awt.*;
import java.util.Enumeration;
import java.util.Hashtable;

public class ScoreBoard extends Shape
{
	//The scoreboard variables are a bit more in depth
	//as we need to keep track of the bean bag hash table,
	//as well as the scoreboard headings and colors.
	public int width;
    public int height;
    public Color scoreColor;
    public String heading = "";
    public int score = 0;
    public Hashtable<Integer,Integer> bagScores = new Hashtable<Integer, Integer>();    
    public ScoreBoard totalBoard;
    
    //constructor we generate by calling the super class in addition to local
    //dimensions, colors, and strings    
	public ScoreBoard(int xPos, int yPos, int width, int height, Color scoreColor, String heading)	{
		super(xPos, yPos); 
		this.width = width;
		this.height = height;
		this.scoreColor = scoreColor;
		//To create equal string spacing between "total" and current"
		if(heading.length() == 5){
			this.heading = " " + heading;
		}else{
		this.heading = heading;
		}
		
	}
	
	//The setScore method takes in the scorevalue and assigns it to score
	public void setScore(int Scorevalue){		
		score = Scorevalue;		
	}
	
	//The getScore function is a bit more complicated in which we have a hash table 
	//that houses each bag with its particular ID and score value. We keep track of a running score 
	//while checking each ID and then return the running score to our scoreboards.
	public int getScore()
	{	
		int runningScore = 0;
		
		Enumeration<Integer> enumKey = bagScores.keys();
		while(enumKey.hasMoreElements()) {
			Integer key = enumKey.nextElement();
			int val = bagScores.get(key);
			runningScore += val;
		}		
		return runningScore;
	}
	
	//We return the total score
	public int getActualScore(){
		return this.score;
	}
	
	//The setbag score creates our hash table with a score value
	//and an ID
	public void setBagScore(int Scorevalue, int id){
		if(bagScores.containsKey(id))
		{
			//Overrides the key of the hashtable
			bagScores.remove(id);
		} 
		//The put method does the heavy lifting
		bagScores.put(id, Scorevalue);
	}
	
	//The getTempScore method does the same as above, 
	//except it only worries about scores with increments of
	//3's as it is used in cancellation scoring
	public int getTempScore(){	
		int runningScore = 0;
		
		Enumeration<Integer> enumKey = bagScores.keys();
		while(enumKey.hasMoreElements()) {
			Integer key = enumKey.nextElement();
			int val = bagScores.get(key);
			if(val == 3)
				runningScore += val;
		}		
		return runningScore;
	}
	
	public void ClearBagScore(){
		bagScores.clear();
	}
	
	//The setTotalScoreboard allows us to manipulate the
	//total game scoring
	public void setTotalScoreBoard(ScoreBoard board)
	{
		totalBoard = board;
	}
	//
	//	The only graphical method of the class is the paint method.
	//
	public void paint(Graphics pane)
	{
		int fontSize = 16;
		Font OrigFont = pane.getFont();
		
		//Fill the scoreboards as white
		pane.setColor(Color.white);			
		pane.fillRect(horizontal, vertical, width, height);	
		//Draw the scoreboard frame
		pane.setColor(Color.black);			
		pane.drawRect(horizontal, vertical, width, height);	
		
		//Draw the headers
		pane.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
		pane.setColor(scoreColor);
		pane.drawString(heading, horizontal+5, vertical + (fontSize));
		
		//Draw the scores
		fontSize = height;
		pane.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
		
		int xOffset;
		if(score >= 10){
			xOffset = 7;
		}
		else {
			xOffset = 15;
		}
		pane.drawString(Integer.toString(score), horizontal + xOffset , vertical + fontSize);
		
		//Bring our font back to original
		pane.setFont(OrigFont);
	}

}	// end ScoreBoard
