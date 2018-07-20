//Erik Archambault
//CSC 212 Final Project; Cornhole Scoring Applet

/*
 * The stack list is based upon the use of nodes which hold two scores. 
 * The two team’s score is stored within each node for each inning. 
 * Of course, a single inning is represented by a single node.
*/

import java.awt.*;
import java.awt.event.*;

public class Node
{
	private Node next;						
	public int t1Score;
	public int t2Score;

	//	Constructors:
	//	=============	
	public Node(){
		t1Score = 0;
		t2Score = 0;
		next = null;
	}
	
	//
	//	The following access method sets the element in the node
	//
	public void setInningScores(int t1GivenScore, int t2GivenScore){
		t1Score = t1GivenScore;
		t2Score = t2GivenScore;		
	}

	//This method returns the score for team 1 in a particular inning
	public int getT1InningScore(){
		return t1Score;
	}
	
	//This method returns the score for team 2 in a particular inning
	public int getT2InningScore(){
		return t2Score;
	}

	//This method determines the next node in our list
	public void setNext(Node someNode)
	{
		next = someNode;
	}

	//The getNext method returns the next node in the list
	public Node getNext()
	{
		return next;
	}
}	//	end of Node