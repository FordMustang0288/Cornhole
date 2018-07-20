//Erik Archambault
//CSC 212 Final Project; Cornhole Scoring Applet

/*
 * This class controls the creation and manipulation of all graphical objects. 
 * It also organizes the various methods and actions that 
 * are performed during use.
*/

import java.awt.*;							//	AWT = "Abstract Window Toolkit"
import java.awt.event.*;					//		or "Another Window Toolkit"
import java.applet.Applet;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Visuals extends Canvas implements ActionListener, ItemListener,
									MouseListener, MouseMotionListener
{
	//Spacing for the buttons
	private final int DELTA = 25;			
	
	//These two variables are used to know which collection the beanBag belongs to
	private BeanBag[] t1Bags;
	private BeanBag[] t2Bags;
	
	//We call our applet associated with the main program   
	private Applet theApplet;
	//Before painting can begin, we check that our objects have been initialized
	private boolean initializationComplete = false;		
	
	//The user interface consists of a Rectangle platform
	private Rectangle platform;
	//A circle hole
	private Circle hole;
	//And 8 corn/bean bags; 4 blue and 4 red
	private BeanBag blueBag1,
					blueBag2,
					blueBag3,
					blueBag4;
	
	private BeanBag redBag1,
					redBag2,
					redBag3,
					redBag4;
	//The bags have predetermined height and width
	private int bagWidth;
	private int bagHeight;
	//The following variables are used to animate the bags
	//By determining if it has been dragged or is moving.
	private BeanBag DraggedBeanBag = null;
	private boolean t1BeanBagMoving = false;
	//During dragging, these record the x and y coordinates of the previous
	//position of the mouse
	int prevDragX;  
	int prevDragY;
	
	//Our four scoreboards are created
	//Two to show the current score and two for total score
	private ScoreBoard p1TotalScore;
	private ScoreBoard p1CurrentScore;
	private ScoreBoard p2TotalScore;
	private ScoreBoard p2CurrentScore;
	
	//We also use three buttons with our interface
	//A next inning button, a new game button, and an undo scoring button
	private Abutton inningButton,
					gameButton,
					undoButton;		

	//To keep the mouse location
	private int lastX, lastY;		
	
	//Our inning collection which holds the team scores
	private Stack innings;			
	

	
//
//	Constructor
//	===========
//
//	The constructor sets the scene for the user interaction.
//
	public Visuals(){
	}

	public Visuals(Applet mainProgram){
		theApplet = mainProgram;

		//Checks on the mouse and tracks motion
		theApplet.addMouseListener(this);	
		theApplet.addMouseMotionListener(this);
		
		//Two arrays are created to store the team's bags
		t1Bags = new BeanBag[4];
		t2Bags = new BeanBag[4];
		
		//Setting the x and y position of our buttons
		int x = DELTA;
		int y = DELTA;
		
		//The following sets the three buttons with a certain color, size
		//and position
		y = 450;
		inningButton = new Abutton("Next Inning", Color.white, 160, y);

		y += Abutton.BUTTON_HEIGHT * 1.5;
		undoButton = new Abutton("Undo Scoring", Color.red, 160, y);
		
		y += Abutton.BUTTON_HEIGHT * 1.5;
		gameButton = new Abutton("New Game", Color.lightGray, 160, y);

		x += Abutton.BUTTON_WIDTH + DELTA;	
		
		//The platform position is defined and then the platform is created
		int platformWidth = 200;
		int platformHeight = 300;
		int platformXoffset = 100;
		platform = new Rectangle(platformXoffset,50,platformWidth,platformHeight);
		
		//Draw perfectly in the top 1/4 of the platform and exactly in the middle.
		//The circle is then created
		int circleRadius = 70;
		int circleXoffset = platformWidth - (circleRadius/2);
		int circleYoffset = platformHeight - (circleRadius*2) - platformXoffset;		
		hole = new Circle(circleXoffset, circleYoffset, circleRadius);
		
		//To track boundaries we assing the hole/circle to be the platform's bullseye
		platform.setBullseye(hole);
		
		//Instead of initiating our bag positions here, we call the helper method
		//to perform the same fucntion
		resetBags();
		
		//The scoreboards are created and intialized along with the colored digits
		//and headings
		p1TotalScore = new ScoreBoard(20, 50, 60, 50, Color.blue, "Total");
		p1CurrentScore = new ScoreBoard(20, 115, 60, 50, Color.blue, "Current");
		p1CurrentScore.setTotalScoreBoard(p1TotalScore);
		
		p2TotalScore = new ScoreBoard( 330, 50, 60, 50, Color.red, "Total");
		p2CurrentScore = new ScoreBoard(330, 115, 60, 50, Color.red, "Current");
		p2CurrentScore.setTotalScoreBoard(p2TotalScore);
		
		//We use a stack to hold our inning collection
		innings = new Stack();
		
		//Everything is initialized, we can now paint
		initializationComplete = true;			
	}
	
	//The resetBags method sets the eight bags to their default starting
	//location and assigns each to it's particular array
	public void resetBags(){
		//Set the bag size 
		bagWidth = 25;
		bagHeight = 25;
		
		int blueInitalX = 40;
		int redInitalX = 335;
		int blueInitalY = 410;
		int redInitalY = 410;;		
				
		blueBag1 = new BeanBag(blueInitalX, blueInitalY, bagWidth, bagHeight, Color.blue, 1);
		blueBag2 = new BeanBag(blueInitalX, blueInitalY+=30, bagWidth, bagHeight, Color.blue,2);
		blueBag3 = new BeanBag(blueInitalX, blueInitalY+=30, bagWidth, bagHeight, Color.blue,3);
		blueBag4 = new BeanBag(blueInitalX, blueInitalY+=30, bagWidth, bagHeight, Color.blue,4);
		redBag1 = new BeanBag(redInitalX, redInitalY, bagWidth, bagHeight, Color.red,5);
		redBag2 = new BeanBag(redInitalX, redInitalY+=30, bagWidth, bagHeight, Color.red,6);
		redBag3 = new BeanBag(redInitalX, redInitalY+=30, bagWidth, bagHeight, Color.red,7);
		redBag4 = new BeanBag(redInitalX, redInitalY+=30, bagWidth, bagHeight, Color.red,8);
		
		//Below we assing the bags into their particular array by array name and index
		//index[?] 0
		t1Bags[0] = blueBag1;
		t2Bags[0] = redBag1;
		
		//index[?] 1
		t1Bags[1] = blueBag2;
		t2Bags[1] = redBag2;

		//index[?] 2
		t1Bags[2] = blueBag3;
		t2Bags[2] = redBag3;
		
		//index[?] 3
		t1Bags[3] = blueBag4;
		t2Bags[3] = redBag4;
	}
	
	//The newGame method resets the bags, erases the scoreboards
	//and clears our inning collection
	public void NewGame(){
		resetBags();
		FactorScore(true);
		innings.Clear();
	}
	
	//The factorScore method performs two functions based on the boolean argument.
	//False, and we know the game is continuing. The current score is added onto the total 
	//score and then the current board is cleared. 
	//We also determine if either team has scored 21 and won by more than 1 point
	//True, and we reset all boards to 0, and erase the bags hash table information
	private void FactorScore(boolean resetGame){
		if(initializationComplete){
			if(!resetGame) {
				//We grab the score from the total scoreboard
				int p1Total = p1CurrentScore.totalBoard.getActualScore();
				int p2Total = p2CurrentScore.totalBoard.getActualScore();
				
				//The total scoreboard becomes += Current Score
				p1CurrentScore.totalBoard.setScore(p1Total + p1CurrentScore.getActualScore());
				p2CurrentScore.totalBoard.setScore(p2Total + p2CurrentScore.getActualScore());
				
				//The two team's inning scores are pushed to the top of the stack
				innings.push(p1CurrentScore.getActualScore(), p2CurrentScore.getActualScore());
				
				p1Total = p1CurrentScore.totalBoard.getActualScore();
				p2Total = p2CurrentScore.totalBoard.getActualScore();
				
				//We determine if either team has scored 21 and done so 
				//by a greater amount than the other team
				if((p1Total >= 21 || p2Total >= 21) &&
					Math.abs(p1Total - p2Total) > 1){
					findWinner(p1Total, p2Total);
				}
			}
			else{
				//We set the total board to be 0
				p1CurrentScore.totalBoard.setScore(0);
				p2CurrentScore.totalBoard.setScore(0);
			}
			//The current board is set to 0
			p1CurrentScore.setScore(0);
			p2CurrentScore.setScore(0);
			
			//We clear the hash table information
			p1CurrentScore.ClearBagScore();
			p2CurrentScore.ClearBagScore();
		}
	}
	
	//The findWinner method determines which of our two teams has
	//a higher score and passes that string to showstats as an argument
	private void findWinner(int p1Total, int p2Total){
		String winningTeam = ""; 
		if(p1Total > p2Total){
			winningTeam = "Blue Team";
		}else if(p2Total > p1Total){
			winningTeam = "Red Team";
		}		
		showStats(winningTeam);
	}	
	
	//The showStats method shows the winning team as well
	//as the highest inning score for either team
	private void showStats(String teamName){
		int team1Best = innings.findBestTeam1();
		int team2Best = innings.findBestTeam2();		
		
		JOptionPane.showMessageDialog(theApplet, teamName + " WINS!!!" +
				"\nBlue Team's Highest Inning Score: " + team1Best +
				"\nRed Team's Highest Inning Score: " + team2Best);
		//We start a new game after the statistics are shown
		NewGame();
	}
	
	//The undoLastInning method pops the top node from the inning stack
	//and remove those scores from the game total. The current scoreboard is reset
	//to 0, the bag hash table is reset for those items, and the bags themselves
	//are graphically reset
	private void undoLastInning() throws ListException {
		
		if(!innings.isEmpty()) {
			innings.pop();
			
			p1CurrentScore.totalBoard.setScore(innings.GetTeam1Score());
			p2CurrentScore.totalBoard.setScore(innings.GetTeam2Score());
			p1CurrentScore.setScore(0);
			p2CurrentScore.setScore(0);
			p1CurrentScore.ClearBagScore();
			p2CurrentScore.ClearBagScore();
			
			resetBags();
		}
	} 

	//
	//	The next method checks where the mouse is been clicked,
	//		and performs the associated action.
	//
	private void check(){
		theApplet.showStatus(" ");
		try {
			if (inningButton.isInside(lastX, lastY)) {
				resetBags();
				FactorScore(false);
			}
			else if (undoButton.isInside(lastX, lastY)) {
				undoLastInning();
			}
			else if (gameButton.isInside(lastX, lastY)){
				NewGame();
			}
			else  {
				//theApplet.showStatus("What?");
			}
		}
		catch (Exception problem) {
			System.err.println("***** We caught an exception *****");
			System.err.println("Here is the message: " +
								problem.getMessage());
			System.err.println("Here is the stack:");
			problem.printStackTrace();
			System.err.println("**********************************");

			theApplet.showStatus("Exception: " +
								problem.getMessage());
		}
		finally {
			theApplet.repaint();
		}
	}

	//
	//	The only "graphical" method of the class is the paint method.
	//
	synchronized public void paint(Graphics pane){
				
		if (initializationComplete) {
			//The below code allows us to avoid an exception as the shapes were not initialized
			if(platform != null)					
				platform.paint(pane); 
			if(hole != null)
				hole.paint(pane);
			if(blueBag1 != null)
				blueBag1.paint(pane);
			if(blueBag2 != null)
				blueBag2.paint(pane);
			if(blueBag3 != null)
				blueBag3.paint(pane);
			if(blueBag4 != null)
				blueBag4.paint(pane);
			if(redBag1 != null)
				redBag1.paint(pane);
			if(redBag2 != null)
				redBag2.paint(pane);
			if(redBag3 != null)
				redBag3.paint(pane);
			if(redBag4 != null)
				redBag4.paint(pane);
			if(p1TotalScore != null)
				p1TotalScore.paint(pane);
			if(p1CurrentScore != null)
				p1CurrentScore.paint(pane);
			if(p2TotalScore != null)
				p2TotalScore.paint(pane);
			if(p2CurrentScore != null)
				p2CurrentScore.paint(pane);			
		}
		if (inningButton != null)
			inningButton.paint(pane);
		if (undoButton != null)
			undoButton.paint(pane);
		if (gameButton != null)
			gameButton.paint(pane);			
	}

//
//	M o u s e L i s t e n e r
//	=========================
//
//	The implementation of the MouseListener requires the implementation of the
//		following five methods.  Each method deals with a particular event
//		associated with the mouse.
//
//	mouseClicked:	invoked when the mouse button is pressed and released at the
//					same location.
//	mousePressed:	invoked when the mouse button is pressed down
//	mouseReleased:	invoked when the mouse button is released
//	mouseEntered:	invoked when the mouse pointer moves into a component
//					(i.e., the Frame)
//	mouseExited:	invoked when the mouse pointer moves out of a component
//
	synchronized public void mouseClicked(MouseEvent event){
		check();								//	To handle the mouse click
	}

	synchronized public void mousePressed(MouseEvent event){
		lastX = event.getX();					//	Update the mouse location
		lastY = event.getY();
		flipWhenInside();						//	Flip any button hit by the mouse
		
		//The function below determines if we are within a bean bag and whether or 
		//not it is moving
		if(!FindHeldBeanBag(t1Bags, lastX, lastY)){
			FindHeldBeanBag(t2Bags, lastX, lastY);
			t1BeanBagMoving = false;					
		} 
		else{
			t1BeanBagMoving = true;
		}
	}
	
	//We determine if we are within a beanbag by cycling through each
	//of the bags and comparing that information to the mouse information
	private boolean FindHeldBeanBag(BeanBag[] bags, int x, int y){
		boolean foundBag = false;
		
		for (int i = bags.length-1; i >= 0; i--)
		{
			BeanBag bb = bags[i];
			if(bb.isInside(x, y))
			{
				foundBag = true;
				DraggedBeanBag = bb;
				prevDragX = x;
	            prevDragY = y;
	            //This repaint call removes artifacts that
	            //would be left behind from the animation
	            repaint();
			}
			if(foundBag) break;
		}
		//If we are within a bag return true
		return foundBag;
	}
	
	//The mousedragged function controls the movement of the bags based on 
	//the comparable movement of the mosue
	synchronized public void mouseDragged(MouseEvent evt){
     // User has moved the mouse.  Move the dragged shape by the same amount.
    int x = evt.getX();
    int y = evt.getY();
    if (DraggedBeanBag != null) {
    	DraggedBeanBag.moveBy(x - prevDragX, y - prevDragY);
       prevDragX = x;
       prevDragY = y;
       theApplet.repaint();
       //redraw canvas to show shape in new position
       DraggedBeanBag.paint(theApplet.getGraphics());      
      
    }
 }
	// User has released the mouse.  Move the dragged shape, then set
    // shapeBeingDragged to null to indicate that dragging is over.
    // If the shape lies completely outside the canvas, remove it
    // from the list of shapes (since there is no way to ever move
    // it back onscreen).
	synchronized public void mouseReleased(MouseEvent evt){
       
		//Flip any button hit by the mouse
		flipWhenInside();						
		
	    int x = evt.getX();
	    int y = evt.getY();
	    if (DraggedBeanBag != null) {
	    	DraggedBeanBag.moveBy(x - prevDragX, y - prevDragY);
	    	ScoreBoard movingTeam;
	    	ScoreBoard otherTeam;
	    	if(t1BeanBagMoving) 
	    	{
	    		movingTeam = p1CurrentScore;
	    		otherTeam = p2CurrentScore;
	    	} else {
	    		movingTeam = p2CurrentScore;
	    		otherTeam = p1CurrentScore;
	    	}	    	
	    	platform.determineCurrentScore(DraggedBeanBag, movingTeam, otherTeam);
	    	DraggedBeanBag = null;
	       repaint();
	    }
	 }


	synchronized public void mouseEntered(MouseEvent event) {}
	synchronized public void mouseExited(MouseEvent event) {}

	synchronized public void mouseMoved(MouseEvent e){
		
	}
	
	private void flipWhenInside()	{
		if (inningButton.isInside(lastX, lastY))
			inningButton.flip();
		else if (undoButton.isInside(lastX, lastY))
			undoButton.flip();
		else if (gameButton.isInside(lastX,  lastY))
			gameButton.flip();

		theApplet.repaint();
	}

	//Unused method, required by the compiler to avoid build errors
	public void itemStateChanged(ItemEvent arg0){
		// TODO Auto-generated method stub		
	}

	//Unused method, required by the compiler to avoid build errors
	public void actionPerformed(ActionEvent arg0){
		// TODO Auto-generated method stub		
	}

}	//	end Visuals