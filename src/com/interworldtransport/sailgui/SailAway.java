//{{{ copyright statement
/*
<h2>Copyright</h2>
Copyright (c) 1998-2002 Interworld Transport.  All rights reserved.<br>
--------------------------------------------------------------------------------
<br>
---com.interworldtransport.sailgui.SailAway<br>
--------------------------------------------------------------------------------
<p>
Interworld Transport grants you ("Licensee") a license to this software 
under the terms of the GNU General Public License.<br>
A full copy of the license can be found bundled with this package or code file.
<p>
If the license file has become separated from the package, code file, or binary 
executable, the Licensee is still expected to read about the license at the 
following URL before accepting this material.
<blockquote><code>http://www.opensource.org/gpl-license.html</code></blockquote>
<p>
Use of this code or executable objects derived from it by the Licensee states
their willingness to accept the terms of the license.
<p>
A prospective Licensee unable to find a copy of the license terms should contact
Interworld Transport for a free copy.
<p>
--------------------------------------------------------------------------------
<br>
---com.interworldtransport.sailgui.SailAway<br>
--------------------------------------------------------------------------------
 */
//}}}
package com.interworldtransport.sailgui ;
//{{{ import statements
import com.interworldtransport.sailmodel.*;
import com.interworldtransport.sailevent.*;
import com.interworldtransport.clados.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
//}}}
//{{{ javadoc comment for SailAway class
/** com.interworldtransport.sailgui.SailAway
 * The SailAway class is intended to be the top class of the sailgui package.
 * Instantiating it properly should cause all other ojects in the simulation to
 * be correctly constructed and run.  No other class needs to be created by the
 * user from the command line to run the application.
 * <p>
 * Proper use of this class depends on the simulation to be run by the user.
 * Since this class is responsible for constructing the entire application, it 
 * must have some parameters passed to it to properly identify configuration 
 * files, simulation parameters, and other needed flags.
 * <p>
 * Operation of the SailAway class is done from the command line with the java
 * interpreter.  Batch or shell scripts are probably the easiest approach to
 * starting SailAway.
 * <p>
 * Defining future vesions of this class to include a large number of user
 * interface features is still required.  As this class stands now, it is 
 * minimally useful in running a solar sail simulation.
 * <p>
 * Future versions of this class will include features from the following list.
 * <p>
 * <ul>
 * <li>Reading of Configuration parameters from a .conf file
 * <li>Reading of an initial sail file from a .profile file
 * <li>Restarting of the application using a new .profile file
 * <li>Inclusion of a macro-oriented .command file for predefined Sail steps
 * <li>Textual representation of the Sail's parameters
 * <li>Textual representation of target parameters in a .mission file
 * <li>Representation of Sail's current, target, and delta parameters.
 * <li>Graphical representation of a Sail's Structure object.
 * <ul>
 * @version 0.11, $Date: 2002/02/23 08:50:53 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailmodel.Sail
 * @see com.interworldtransport.clados.Monad
 * @see com.interworldtransport.sailevent.SailEventModel
 */
//}}}
public class SailAway extends JFrame {
//{{{ data elements
/**
 * The Event Model that handles the initiation of all events in the application.
 */
    public	SailEventModel	EventModel;
    
/**
 * The Menu Bar for the application.
 */
    public	SailAwayMenu	TheMenuBar;
    
/**
 * The Tool Bar for the application.
 */
    protected	JToolBar	TheToolBar;
    
/**
 * The Sail being simulated within the application.
 */
    public	Sail		TestSail;

/**
 * The Center Display Panel for the application.
 */
    protected	JPanel		CenterBar;

/**
 * This vector contains a list of the active Monad displayers.
 * All entries are of type SailMonad.
 * All sail actions must iterate through this vector
 * to force refreshes of the displayed information.
   */
    public	Vector		MonadDisplays;
    public	Vector		GraphicDisplays;
    public	Vector		DetailDisplays;

/**
 * This OrbitGraphic is used to present a visual display of the OrbitEllipse
 * within the Sail.  This object will be altered to use Java3D soon.  When that
 * alteration is done, the name of this object will be dropped while the object
 * itself is absorbed into the GraphicDisplays vector like the MonadDisplayers
 * were.
 */
    //public	OrbitGraphic	OrbDrawing;
    
/**
 * This SailGraphic is used to present a visual display of the Sail attitude.
 * This object will be altered to use Java3D soon.  When that alteration is 
 * done, the name of this object will be dropped while the object itself is 
 * absorbed into the GraphicDisplays vector like the MonadDisplayers were.
 */
    //public	SailGraphic	SailDrawing;
    
/**
 * This OrbitDetailing is used to present a numeric display of the Orbit 
 * parameters of the Sail.  The name of this object will be dropped while the 
 * object itself is absorbed into the DetailDisplays vector like the 
 * MonadDisplayers were.
 */
    public	OrbitDetailing	OrbParm;

/**
 * This SailDetailing is used to present a numeric display of Sail parameters.
 * The name of this object will be dropped while the object itself is absorbed 
 * into the DetailDisplays vector like the MonadDisplayers were.
 */
    public	SailDetailing	SailParm;

/**
 * The Status Display Panel for the application.
 */
    public	StatusBar	StatusLine;

/** This String is the name of the Initialization file used for background ini needs
 *  @see <a href="doc-files/SailAway.conf">SailAway Configuration File</a>
 */
    protected	String		IniName;
/** This class holds basic ini properties pulled from ini files.
 *  @see <a href="doc-files/SailAway.conf">SailAway Configuration File</a>
 */
    public	Properties	IniProps;
//}}}
//{{{ SailAway constructor (string, string, string, string)
/**
 * The SailAway class is the top of the user interface package.  It is responsible
 * for setting up the interface, event model, and the physical model with the intended
 * simulation.  After the setup, SailAway hangs back and lets its subparts handle 
 * other events.
 * @param  pTitle     String to be placed in application Title Bar 
 * @param  pSaveFile  String for naming the file to accept simulation output
 * @param  pProfile   String for naming the file that represents the sail 
 * @param  pConfFile  String for naming the application configuration file
 */
    public	SailAway(	String pTitle, 
				String pSaveFile, 
				String pProfile, 
				String pConfFile) {

	super(pTitle);

	this.addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent e) {
			TestSail.terminateSail();
			System.out.println(e);
			System.exit(0);
			}
		}
	);

	Container cp=this.getContentPane();

	this.IniName=pConfFile;
	this.getConfigProps(pConfFile);
	
	ClassLoader cl = this.getClass().getClassLoader();
	ImageIcon splImg = new ImageIcon(cl.getResource("images/SplashImage.gif"));
	SplashWindow SailAwaySplash = new SplashWindow(splImg);
	

/*
 * These displayers are constructed to correlate various objects in the BUI that
 * present displays of application internals with the objects in the event model
 * that trigger them into aciton.
 */
	this.MonadDisplays = new Vector();
	this.GraphicDisplays = new Vector();
	this.DetailDisplays = new Vector();

/*
 * Time to construct the application menu bar.
 */	
	this.TheMenuBar=new SailAwayMenu();
	this.setJMenuBar(this.TheMenuBar);
	
/*
 * Time to construct the application tool bar.
 */
	//this.TheToolBar=new ToolBarPanel();
	//cp.add(this.TheToolBar, "North");
	//this.constructToolBar();

/*
 * Time to construct the application status bar.
 */
	this.StatusLine=new StatusBar(this);
	cp.add(StatusLine, "South");

/*
 * Time to construct a the application event model now that enough of the GUI 
 * exists.
 */
	this.EventModel=new SailEventModel(this);

/*
 * Time to construct a sail based upon information in the Profile file.  
 * This call creates the physical model for this applicaiton.  With its
 * completion, we have a GUI, an event model, and a physical model.  We are now
 * ready for interaction with a user or mission macro.
 */
	this.createDefaultSail(pSaveFile, pProfile);

	
	//this.linkEventsToSail(TestSail);

	if (SailAwaySplash != null) {
		SailAwaySplash.dispose();
		SailAwaySplash=null;
		}
	}
//}}}
//{{{ getConfigProps
    protected void getConfigProps(String pConfName) {
	File fIni=null;
	boolean bIni = false;
	try {
		fIni=new File(pConfName);
		bIni=fIni.exists();
		if (bIni) bIni=fIni.isFile();
		if (bIni) bIni=fIni.canRead();
		if (!bIni) throw new CantGetIniException("The INI support file is not present or ready.");
		//Getting here implied the configuration file has been found and readable

		this.IniProps=new Properties(System.getProperties());
		this.IniProps.load(new BufferedInputStream(new FileInputStream(fIni)));
		
		}//end of try block
	catch(Exception e) {
		System.out.println("IO Problem:  Incomplete Access to Associated INI files.");
		//throw new CantGetIniException("No Access to INI file.");
		}
	}
//}}}
//{{{ createDefaultSail
    protected void createDefaultSail(String pSaveFile, String pProfile) {

	try {
		this.TestSail = new Sail(this, pSaveFile, pProfile, "Sun");
		//This little constructor violates the boundry between the physical
		//model and the user interface.  The physical model should not have to
		//interact with any local resources like profile or save files.  It
		//should be enough to had information back the the UI or Notification
		//Tool and have them do it. 
		
		}
	catch (SailException e) {
		System.out.println(e);
		System.out.println(e.getSourceMessage());
		System.out.println("Problem making default sail.");
		System.exit(0);
		}
	if (StatusLine.StatusInUseFlag=true) {
		StatusLine.StatusMessage.setStatusMsg("Default Sail finished.");
		}

	return;
	}
//}}}
//{{{ constructToolBar()  needs more work!
/*
    protected void constructToolBar() {
	//No ToolBar ready yet. 
	String tStatus="null";
	if (IniProps!=null) tStatus=IniProps.getProperty("SailAway.Desktop.ShowToolBar");
	if (tStatus.equals("1")) {
		;//No ToolBar ready yet. 
		}
	else {
		;//No ToolBar ready yet. 
		}
	}
 */
//}}}
//{{{ linkEventsToSail(Sail)
    public void linkEventsToSail(Sail pSail) {
	EventModel.setDemoSail(pSail);
	}
//}}}
//{{{ main(String[]) 
   public static void main(String[] args) {

	String ConfName="null";
	String ProfileName="null";
	String SaveName="null";
	String TitleName="null";
	if (args.length%2==1) {
		System.out.println("Usage: SailAway [-t TitleString] [-s Sail.save] [-p Sail.profile] [-c SailAway.conf]");
		System.exit(0);
		}
	for (int i=0; i<args.length; i=i+2) {
		//System.out.println("Loop "+i+" "+args[i]+" "+args[i+1]);
		if (args[i].equals("-c")) ConfName=args[i+1];
		if (args[i].equals("-p")) ProfileName=args[i+1];
		if (args[i].equals("-s")) SaveName=args[i+1];
		if (args[i].equals("-t")) TitleName=args[i+1];
		}
	if (ConfName.equals("null")) {
		ConfName="./configuration/SailAway.conf";
		}
	System.out.println("Configuration File name is "+ConfName);
	if (TitleName.equals("null")) {
		TitleName="SailAway Solar Sail Simulator - available under the terms of the GPL";
		}
	if (SaveName.equals("null")) {
		SaveName="./Sail.save";
		}
	if (ProfileName.equals("null")) {
		ProfileName="./configuration/Sail.profile";
		}

	JFrame fr = new SailAway(TitleName, SaveName, ProfileName, ConfName);
	fr.pack();
	fr.setVisible(true);

	}//end of main method
//}}}
    }//end of SailAway class


