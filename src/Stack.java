//Erik Archambault
//CSC 212 Final Project; Cornhole Scoring Applet


/*A stack was chosen as the collection structure in order to track each inning of game play. 
 * The way in which a stack works allows quicker manipulation to add/undo innings. 
*/

import java.awt.*;
import java.awt.event.*;

public class Stack
{
	//A reference to the first node (if any)
	public Node head;					

	//
	//	C o n s t r u c t o r s
	//	=======================
	//
	//	By default, we will start with ... nothing.
	//
	public Stack(){										
	}

	public Stack(int t1Given, int t2Given){
		//No score at the head yet
		head = null;						
		
		//Our stack will house a score for each team
		t1Given = 0;							
		t2Given = 0;
	}

	//
	//	Determines whether the stack is empty or not.
	//
	public boolean isEmpty(){
		if(head == null)
			return true;
		return false;
	}
	
	//
	//	Adds score to the top of the stack of nodes,
	//		by putting a new node ahead of the current head.
	//
	protected void push(int t1Given, int t2Given){
		Node placeHolder = new Node();
		
		placeHolder.setInningScores(t1Given, t2Given);
		placeHolder.setNext(head);
		
		head = placeHolder;
	}
	
	//Since we can only return one value from a method, we have
	//two distinct peek methods. This one returns team 1's score 
	//value for a particular inning
	public int peekT1() throws ListException{
		if (isEmpty())
			throw new ListException("List empty");
		return head.getT1InningScore();
	}
	
	//This one returns team 2's score value for a particular inning
	public int peekT2() throws ListException{
		if (isEmpty())
			throw new ListException("List empty");
		return head.getT2InningScore();
	}
	
	//This method will walk through the stack and provide use with the highest
	//score for any inning for team 1 during the game. It will then return that value
	public int findBestTeam1(){
		Node tmpHead = head;
	    int best;
	    best = tmpHead.getT1InningScore();
	    tmpHead=tmpHead.getNext();
	    int tmpBestScore = 0;
	    while(tmpHead != null)
	    {
	    	tmpBestScore = tmpHead.getT1InningScore();
	        if(tmpBestScore > best)
	            best = tmpBestScore;
	        tmpHead=tmpHead.getNext();
	    }
	    return best;
	}
	
	//This method will do the same for team 2
	public int findBestTeam2(){
		Node tmpHead = head;
	    int best;
	    best = tmpHead.getT2InningScore();
	    tmpHead=tmpHead.getNext();
	    int tmpBestScore = 0;
	    while(tmpHead != null)
	    {
	    	tmpBestScore = tmpHead.getT2InningScore();
	        if(tmpBestScore > best)
	            best = tmpBestScore;
	        tmpHead=tmpHead.getNext();
	    }
	    return best;
	}
	
	//The getTeam1Score will return all of team 1's scores
	public int GetTeam1Score(){
		Node tmpHead = head;
	    int score = 0;
	    	    
	    while(tmpHead != null)
	    {
	    	score += tmpHead.getT1InningScore();
	        tmpHead=tmpHead.getNext();
	    }
	    return score;
	}
	
	//This method will return all of team 2's scores
	public int GetTeam2Score(){
		Node tmpHead = head;
	    int score = 0;
	    
	    while(tmpHead != null)
	    {
	    	score += tmpHead.getT2InningScore();
	        tmpHead=tmpHead.getNext();
	    }
	    return score;
	}
	
	//	Pops the element at the top of the stack (if there is any element).	
	public void pop(){
		if (!isEmpty())
				head = head.getNext();
	}	
	
	//The clear method erases our stack
	public void Clear(){
		while (head != null)
		{
			pop();
		}		
	}
}//End Stack