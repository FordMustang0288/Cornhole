//Erik Archambault
//CSC 212 Final Project; Cornhole Scoring Applet

/*
 * Provides the base of the application as well as determining a window size and background image.
*/

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.net.URL;

import javax.imageio.ImageIO;

public class anApplet extends Applet
{
	//
	//	The init method initializes the applet
	//
	public void init()
	{
		//Setting the window size
		setSize(400, 600);
		try{
			//Setting the background image to be a picture of grass
			URL pic = new URL(getDocumentBase(),"grass.jpg");
			bgGrass = ImageIO.read(pic);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		//Our initialization
		theVisuals = new Visuals(this);					
	}

	//
	//	The paint method refreshes the applet
	//
	public void paint(Graphics pane)
	{
		if(bgGrass !=null) // checks if the image is not null
		{
			pane.drawImage(bgGrass,0,0, this); // draw the image at 125 pixels from the left and 10 from the top
		}
		if (theVisuals != null)
			theVisuals.paint(pane);
	}

	//
	//	Our instance variable(s)
	//
	private Visuals theVisuals;
	Image bgGrass;

}	// end Lab
