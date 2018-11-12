/* 
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.SailAwayMenu---------------------------------
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
---com.interworldtransport.sailgui.SailAwayMenu---------------------------------
 */

package com.interworldtransport.sailgui;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/** com.interworldtransport.sailgui.SailAwayMenu
 * The SailAwayMenu class is intended to be the class that encapsulates the menu
 * used in SailAway.  This menu could easily be defined in the SailAway class
 * and used to be in earlier versions.  For the sake of maintenance, however, 
 * its definition has been moved to a separate class and file.
 * <p>
 * There is nothing especially important about the layout of this class.  It
 * should not be instantiated except by the SailAway application.  Alterations
 * to this class should be made in careful coordination with the classes of the
 * SailAway event model.
 * @version 0.10. $Date: 2001/11/03 09:02:54 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailevent.SailEventModel
 */
 
 public class SailAwayMenu extends JMenuBar {
    
/**
 * The File Parent Menu for the application.
 */
    public	JMenu			mnuFile;
    public	JMenuItem		mniNew;
    public	JMenuItem		mniOpen;
    public	JMenuItem		mniCapture;
    public	JMenuItem		mniPrint;
    public	JMenuItem		mniExit;

/**
 * The Edit Parent Menu for the application.
 */
    public	JMenu			mnuEdit;
    public	JMenuItem		mniSailProps;
    public	JMenuItem		mniOrbitProps;
    public	JMenuItem		mniViewPoint;

/**
 * The Tools Parent Menu for the application.
 */
    public	JMenu			mnuTools;
    public	JMenuItem		mniOptions;
    public	JMenuItem		mniTimeRate;
    public	JMenu			mnuToolsPictures;
    public	JCheckBoxMenuItem	mniOrbitGraphic;
    public	JCheckBoxMenuItem	mniSailGraphic;
    public	JMenu			mnuToolsDetails;
    public	JCheckBoxMenuItem	mniOrbitDetailing;
    public	JCheckBoxMenuItem	mniSailDetailing;
    public	JCheckBoxMenuItem	mniSailMoments;
    public	JMenu			mnuToolsMonads;
    public	JMenu			mnuToolsSMonads;
    public	JCheckBoxMenuItem	mniSailPosition;
    public	JCheckBoxMenuItem	mniSailVelocity;
    public	JCheckBoxMenuItem	mniSailAttitude;
    public	JCheckBoxMenuItem	mniSailInternalL;
    public	JMenu			mnuToolsOMonads;
    public	JCheckBoxMenuItem	mniOrbitL;
    public	JCheckBoxMenuItem	mniOrbitRL;
    public	JCheckBoxMenuItem	mniOrbitRHat;
    public	JCheckBoxMenuItem	mniOrbitNodeLine;
    public	JCheckBoxMenuItem	mniOrbitNodePlus90;
    public	JCheckBoxMenuItem	mniOrbitLatusRectum;
    public	JCheckBoxMenuItem	mniOrbitARIES;
    public	JCheckBoxMenuItem	mniOrbitGEMINI;
    public	JCheckBoxMenuItem	mniOrbitEQUATOR;
    public	JMenu			mnuToolsPMonads;
    public	JCheckBoxMenuItem	mniPerturberDL;
    public	JCheckBoxMenuItem	mniPerturberDRL;
    public	JCheckBoxMenuItem	mniPerturberSumForce;
    public	JCheckBoxMenuItem	mniPerturberRadForce;
    public	JCheckBoxMenuItem	mniPerturberHarmForce;
    public	JCheckBoxMenuItem	mniPerturberNBodForce;
    public	JCheckBoxMenuItem	mniPerturberFluidForce;
    public	JCheckBoxMenuItem	mniPerturberMagForce;
    public	JCheckBoxMenuItem	mniPerturberImpForce;

/**
 * The Actions Parent Menu for the application.
 */
    public	JMenu			mnuActions;
    public	JMenuItem		mniIterateFor;
    public	JMenuItem		mniIterateBak;
    public	JMenuItem		mniChangeView;

/**
 * The Window Parent Menu for the application.
 */
    public	JMenu			mnuWindows;
    public	JMenuItem		mniPack;
    public	JMenuItem		mniClose;
    public	JMenuItem		mniIconify;

/**
 * The Help Parent Menu for the application.
 */
    public	JMenu			mnuHelp;
    public	JMenuItem		mniSupport;
    public	JMenuItem		mniAbout;
    
/**
 * This vector contains a list of the menu items that can act as Monad 
 * displayers.  All entries are of type JCheckBoxMenuItem.  Creation of the 
 * Tool|Sail Monad event handler must iterate through this vector to ensure it 
 * registers with each menu for action events.
 */
    public	Vector			MonadDisplayers;
/**
 * This vector contains a list of the menu items that can act as Graphic 
 * displayers.  All entries are of type JCheckBoxMenuItem.  Creation of the 
 * Tool|Images... event handler must iterate through this vector to ensure it 
 * registers with each menu for action events.
 */

    public	Vector			GraphicDisplayers;
/**
 * This vector contains a list of the menu items that can act as Detailing 
 * displayers.  All entries are of type JCheckBoxMenuItem.  Creation of the 
 * Tool|Details... event handler must iterate through this vector to ensure it 
 * registers with each menu for action events.
 */

    public	Vector			DetailDisplayers;

/**
 * The SailAwayMenu class is intended to be the class that encapsulates the menu
 * used in SailAway.  This menu could easily be defined in the SailAway class
 * and used to be in earlier versions.  For the sake of maintenance, however, 
 * its definition has been moved to a separate class and file.
 */
    public	SailAwayMenu() {
	super();
	this.constructMenuBar();
	
    }
    
    protected void constructMenuBar() {

	this.MonadDisplayers = new Vector(19);
	this.GraphicDisplayers = new Vector(1);
	this.DetailDisplayers = new Vector(1);

	this.mnuFile=new JMenu("File");
	this.add(this.mnuFile);
	this.mnuEdit=new JMenu("Edit");
 	this.add(this.mnuEdit);
	this.mnuTools=new JMenu("Tools");
	this.add(this.mnuTools);
	this.mnuActions=new JMenu("Actions");
	this.add(this.mnuActions);
	this.mnuWindows=new JMenu("Windows");
	this.add(this.mnuWindows);
	this.mnuHelp=new JMenu("Help");
	this.add(this.mnuHelp);  //Menus are added

	this.mniNew=new JMenuItem("New Sail");
	this.mnuFile.add(this.mniNew);
	this.mniOpen=new JMenuItem("Open Sail");
	this.mnuFile.add(this.mniOpen);
	this.mniCapture=new JMenuItem("Capture Data");
	this.mnuFile.add(this.mniCapture);
	this.mniPrint=new JMenuItem("Print All");
	this.mnuFile.add(this.mniPrint);
	this.mniExit=new JMenuItem("Exit");
	this.mnuFile.add(this.mniExit);    //File Menu items added

	this.mniSailProps=new JMenuItem("Sail Properties");
	this.mnuEdit.add(this.mniSailProps);
	this.mniOrbitProps=new JMenuItem("Orbit Properties");
	this.mnuEdit.add(this.mniOrbitProps);
	this.mniViewPoint=new JMenuItem("View Point Info");
	this.mnuEdit.add(this.mniViewPoint);

	//Now add the details on the Tools Menu
	//
	this.mniTimeRate=new JMenuItem("Time Rate");
	this.mnuTools.add(this.mniTimeRate);
	//
	//The Tool Graphics
	//
	this.mnuToolsPictures=new JMenu("Images...");
	this.mnuTools.add(this.mnuToolsPictures);
		//
	this.mniOrbitGraphic=new JCheckBoxMenuItem("Show Orbit");
	this.mnuToolsPictures.add(this.mniOrbitGraphic);
		this.GraphicDisplayers.addElement(mniOrbitGraphic);
		//
	this.mniSailGraphic=new JCheckBoxMenuItem("Show Sail");
	this.mnuToolsPictures.add(this.mniSailGraphic);
		this.GraphicDisplayers.addElement(mniSailGraphic);
	//
	//The Tool Detailers
	//
	this.mnuToolsDetails=new JMenu("Detailers...");
	this.mnuTools.add(this.mnuToolsDetails);
		//
	this.mniOrbitDetailing=new JCheckBoxMenuItem("Show Orbit Details");
	this.mnuToolsDetails.add(this.mniOrbitDetailing);
		this.DetailDisplayers.addElement(mniOrbitDetailing);
		//
	this.mniSailDetailing=new JCheckBoxMenuItem("Show Sail Details");
	this.mnuToolsDetails.add(this.mniSailDetailing);
		this.DetailDisplayers.addElement(mniSailDetailing);
	//
	//The Tool Monads
	//
	this.mnuToolsMonads=new JMenu("Monads...");
	this.mnuTools.add(this.mnuToolsMonads);
		//
	this.mnuToolsSMonads=new JMenu("Sail Monads...");
	this.mnuToolsMonads.add(this.mnuToolsSMonads);
		this.mniSailPosition=new JCheckBoxMenuItem("Show Sail Position");
		this.mnuToolsSMonads.add(this.mniSailPosition);
		this.MonadDisplayers.addElement(mniSailPosition);
		this.mniSailVelocity=new JCheckBoxMenuItem("Show Sail Velocity");
		this.mnuToolsSMonads.add(this.mniSailVelocity);
		this.MonadDisplayers.addElement(mniSailVelocity);
		this.mniSailAttitude=new JCheckBoxMenuItem("Show Sail Attitude");
		this.mnuToolsSMonads.add(this.mniSailAttitude);
		this.MonadDisplayers.addElement(mniSailAttitude);
		this.mniSailInternalL=new JCheckBoxMenuItem("Show Sail Internal L");
		this.mnuToolsSMonads.add(this.mniSailInternalL);
		this.MonadDisplayers.addElement(mniSailInternalL);
		//
	this.mnuToolsOMonads=new JMenu("Orbit Monads...");
	this.mnuToolsMonads.add(this.mnuToolsOMonads);
		mniOrbitL=new JCheckBoxMenuItem("Show Orbit L");
		mnuToolsOMonads.add(this.mniOrbitL);
		MonadDisplayers.addElement(mniOrbitL);
		mniOrbitRL=new JCheckBoxMenuItem("Show Orbit RungeLenz");
		mnuToolsOMonads.add(this.mniOrbitRL);
		MonadDisplayers.addElement(mniOrbitRL);

		mniOrbitRHat=new JCheckBoxMenuItem("Show Orbit RHat");
		mnuToolsOMonads.add(this.mniOrbitRHat);
		MonadDisplayers.addElement(mniOrbitRHat);

		mniOrbitNodeLine=new JCheckBoxMenuItem("Show Orbit Node Line");
		mnuToolsOMonads.add(this.mniOrbitNodeLine);
		MonadDisplayers.addElement(mniOrbitNodeLine);

		mniOrbitNodePlus90=new JCheckBoxMenuItem("Show Orbit Node Plus 90");
		mnuToolsOMonads.add(this.mniOrbitNodePlus90);
		MonadDisplayers.addElement(mniOrbitNodePlus90);

		mniOrbitLatusRectum=new JCheckBoxMenuItem("Show Orbit Latus Rectum");
		mnuToolsOMonads.add(this.mniOrbitLatusRectum);
		MonadDisplayers.addElement(mniOrbitLatusRectum);

		mniOrbitARIES=new JCheckBoxMenuItem("Show Orbit ARIES");
		mnuToolsOMonads.add(this.mniOrbitARIES);
		MonadDisplayers.addElement(mniOrbitARIES);
		mniOrbitGEMINI=new JCheckBoxMenuItem("Show Orbit GEMINI");
		mnuToolsOMonads.add(this.mniOrbitGEMINI);
		MonadDisplayers.addElement(mniOrbitGEMINI);
		mniOrbitEQUATOR=new JCheckBoxMenuItem("Show Orbit EQUATOR");
		mnuToolsOMonads.add(this.mniOrbitEQUATOR);
		MonadDisplayers.addElement(mniOrbitEQUATOR);
		//
	this.mnuToolsPMonads=new JMenu("Perturber Monads...");
	this.mnuToolsMonads.add(this.mnuToolsPMonads);
		this.mniPerturberDL=new JCheckBoxMenuItem("Show Perturber DL");
		this.mnuToolsPMonads.add(this.mniPerturberDL);
		this.MonadDisplayers.addElement(mniPerturberDL);
		this.mniPerturberDRL=new JCheckBoxMenuItem("Show Perturber DRL");
		this.mnuToolsPMonads.add(this.mniPerturberDRL);
		this.MonadDisplayers.addElement(mniPerturberDRL);
		this.mniPerturberSumForce=new JCheckBoxMenuItem("Show Perturber SumForce");
		this.mnuToolsPMonads.add(this.mniPerturberSumForce);
		this.MonadDisplayers.addElement(mniPerturberSumForce);
		this.mniPerturberRadForce=new JCheckBoxMenuItem("Show Perturber RadForce");
		this.mnuToolsPMonads.add(this.mniPerturberRadForce);
		this.MonadDisplayers.addElement(mniPerturberRadForce);
		this.mniPerturberHarmForce=new JCheckBoxMenuItem("Show Perturber HarmForce");
		this.mnuToolsPMonads.add(this.mniPerturberHarmForce);
		this.MonadDisplayers.addElement(mniPerturberHarmForce);
		this.mniPerturberNBodForce=new JCheckBoxMenuItem("Show Perturber NBodForce");
		this.mnuToolsPMonads.add(this.mniPerturberNBodForce);
		this.MonadDisplayers.addElement(mniPerturberNBodForce);
		this.mniPerturberFluidForce=new JCheckBoxMenuItem("Show Perturber FluidForce");
		this.mnuToolsPMonads.add(this.mniPerturberFluidForce);
		this.MonadDisplayers.addElement(mniPerturberFluidForce);
		this.mniPerturberMagForce=new JCheckBoxMenuItem("Show Perturber MagForce");
		this.mnuToolsPMonads.add(this.mniPerturberMagForce);
		this.MonadDisplayers.addElement(mniPerturberMagForce);
		this.mniPerturberImpForce=new JCheckBoxMenuItem("Show Perturber ImpForce");
		this.mnuToolsPMonads.add(this.mniPerturberImpForce);
		this.MonadDisplayers.addElement(mniPerturberImpForce);
		//
	//
	this.mniOptions=new JMenuItem("Options");
	this.mnuTools.add(this.mniOptions);

	this.mniIterateFor=new JMenuItem("Iterate Forward");
	this.mnuActions.add(this.mniIterateFor);
	this.mniIterateBak=new JMenuItem("Iterate Backward");
	this.mnuActions.add(this.mniIterateBak);
	this.mniChangeView=new JMenuItem("Change View Point");
	this.mnuActions.add(this.mniChangeView);

	this.mniPack=new JMenuItem("Compact GUI");
	this.mnuWindows.add(this.mniPack);
	this.mniClose=new JMenuItem("Close Sail");
	this.mnuWindows.add(this.mniClose);
	this.mniIconify=new JMenuItem("Iconify");
	this.mnuWindows.add(this.mniIconify);  //windows menu items added

	this.mniSupport=new JMenuItem("Support");
	this.mnuHelp.add(this.mniSupport);
	this.mniAbout=new JMenuItem("About");
	this.mnuHelp.add(this.mniAbout);  //Help Menu items added

	}
    
   }
