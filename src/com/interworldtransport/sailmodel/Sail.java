//{{{ copyright statement
/* <h2>Copyright</h2>
Copyright (c) 1998-2000 Interwor
ld Transport.  All rights reserved.<br>
---com.interworldtransport.sailmodel.Sail---------------------------------------
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
---com.interworldtransport.sailmodel.Sail---------------------------------------
 */
//}}}

package com.interworldtransport.sailmodel;
import com.interworldtransport.sailgui.*;
import com.interworldtransport.clados.*;
import java.util.*;
import java.io.*;
//{{{ javadoc comment for Sail class
/** com.interworldtransport.sailmodel.Sail is the top class of sailmodel package
 * <p>
 * Sail is the general solar sail class.  It acts as the top of the physical
 * model for a solar sailing simulation.  This class has just enough information
 * within it to represent the sail.  It uses other classes within the sailmodel
 * package to hold details concerning the physics obeyed by the sail during a
 * simulation.
 * <p>
 * Proper use of this class is accomplished when one constructs it from a GUI 
 * designed to handle it properly.  Each of the items needed in the basic 
 * constructor form the core information within the Sail and the other parts of 
 * the model.  No other class within the physical model need be instantiated 
 * from the GUI to run a simulation.
 * <p>
 * Operation of the Sail is accomplished when one uses the methods designated 
 * for orbit iterations.  These methods are purposely designed to be very simple
 * forward and backward iterators.  Complex, macro-oriented mission iterators 
 * are intended to be part of the GUI.
 * <p>
 * Defining the Sail in a more general way to represent a generic object in 
 * orbit may be done in the future by making the sail specific parameters an 
 * add-on.  Such an adaptation is not needed at present.
 * <p>
 * Future versions of the Sail will include features from the following list.
 * <p>
 * <ul>
 * <li>Internal Structure
 * <li>Internal forces and torques
 * <li>Adjustable FOO's
 * <li>Active subcomponents for power, communications, and other needs
 * <li>Payload towing
 * <li>Complex file I/O over net for shared simulations & solution searches
 * </ul>
 * @version   0.26, $Date: 2002/02/23 08:50:53 $
 * @author   Dr Alfred W Differ
 * @see   com.interworldtransport.sailmodel.OrbitEllipse
 * @see   com.interworldtransport.sailmodel.Perturber
 * @see   com.interworldtransport.sailgui
 * @see   com.interworldtransport.clados
 * @since SailAway 1.0
 */
//}}}
public class Sail {
//{{{ data members
/** This String is the name of the Sail
 */
    protected	String		SailName;
/** 
 *  This object points to the GUI that handles this Sail.  
 *  It cannot be null since the sail must be handled by an interface.
 */
    public	SailAway	TheGUI;
/** This String is the name of the Sail profile used to initialize the sail.
 *  @see <a href="doc-files/Sail.profile">Profile File</a>
 */
    protected	String		ProfileName;
/** This String is the name of the Sail save file used to keep historical data
 *  @see <a href="doc-files/Sail.save">Generated Ephemeris File</a>
 */
    protected	String		SaveName;
/** This FileWriter points to the actual save file for historical data on sail.
 */
    protected	FileWriter	to;
/** This String is the name of the Mass to be orbited by the sail.
 *  FOO=Focus Of Orbit
 */
    protected	String		FOO;
/** This double is the value of the gravitation constant times the mass
 *  of the object to be orbited by the sail.
 */
    protected	double		Micro;      //ini values
/** This double is the amount of time elapsed during the simulation plus
 *  the original start date.
 */
    protected	double		MissionClock;    //number of seconds elapsed
/**
 * This Force Model is to be used for calculating the forces and torques
 * on the sail due to radiation pressure.
 */
    protected	RadiationForceModel	RFModel;
/** This monad is the current position of the sail.
 *  It gets updated from the orbit.
 */
    protected	Monad		Position;
/** This monad is the current velocity of the sail.
 *  It gets updated from the orbit.
 */
    protected	Monad		Velocity;
/** This monad is the current attitude of the sail.
 *  It will get updated from a future torque handler.
 */
    protected	Monad		Attitude;
/** This monad is the current internal angular momentum of the sail.
 *  It will get updated from a future torque handler.
 */
    protected	Monad		intlAngMomentum;
/** This class represents the orbit and all of its subparts of the sail.
 */
    public	OrbitEllipse	Orbit;
/** This double is a convenience for use by the Radiation Force Model.
 *  This value gets calculated often enough it is worth keeping for reuse.
 *  Any changes to the Attitude monad should force recalcuation of this term.
 */
    protected	double		cosAttitude;    //derived value
/** This double is a convenience for use by the Radiation Force Model.
 *  This value gets calculated often enough it is worth keeping for reuse.
 *  Any changes to the Attitude monad should force recalcuation of this term.
 */
    protected	double		sinAttitude;    //derived value
/** This class holds basic sail properties pulled from profiles and ini files.
 */
    protected	Properties	SailProps;
/** This boolean holds information about whether to save ephemeris information
 *  from the simulation.
 */
    protected	boolean		EphemerisSaveON=false;
/** 
 * This boolean holds information about whether to use an available Sail 
 * profile file.
 */
    protected	boolean		ProfileON=true;
/** Ths double holds information concerning the mass of the sail until the 
 *  internal structure of the Sail is better represented by a SailStruct.
 *  Will be absorbed into intlStructure later
 */
    public	double		SailMass;
/** Ths double holds information concerning the area of the sail until the 
 *  internal structure of the Sail is better represented by a SailStruct.
 *  Will be absorbed into intlStructure later
 */
    public	double		SailArea;
/** The SailStruct holds information about the internal structure of the Sail
 */
  //protected	SailStruct	intlStructure;
/** The SailPerturber holds information about the internal torques on the Sail.
 *  It is possible this object will get absorbed into the SailStruct and be 
 *  instantiated for each sail patch.
 */
  //protected	SailPerturber	intlAttPert;

    public static final double  AU=149599650.0; //One Astronomical Unit in kilometers
    public static  final double  TU=5022675.7; //One Time Unit in seconds
    public static  final double  SOLMICRO=1.3271544E20; //Value of solar G in m3/s2
    public static  final double  EAU=6378.145; //One EarthOrbitUnit in kilometers
    public static  final double  ETU=806.8118744; //One EarthTime Unit in seconds
    public static  final double  EARTHMICRO=3.986012E14; //Value of Terran G in m3/s2
    public static  final double  ERADIUS=6.378145E6; //One EarthRadius Unit in meters
    public static  final double  SRADIUS=0.0; //One SolarRadius Unit in meters
//}}}
//The Constructors
//{{{ constructors that aren't ready yet
/** Raw copy constructor.  It is used when no name change is needed.
 */
/*
    public Sail(Sail pS)
	throws 	BadSailDefinitionException, 
		CantGetProfileException, 
		CantGetSaveException
	{
	this(pS.TheGUI, pS.getSaveName(), pS.getProfileName(), pS.getFOOName(),
	  pS.getPosition(), pS.getVelocity(), pS.getAttitude(), pS.getintlAngMom());
	}
 */
/** Secondary copy constructor.  It is used to copy a sail while pointing it to
 *  a different profile and save file.
 */
/*
    public Sail(String pSaveName, 
		String pProfileName, 
		Sail pS)
	throws 	BadSailDefinitionException, 
		CantGetProfileException, 
		CantGetSaveException
	{
	this(pS.TheGUI, pSaveName, pProfileName, pS.getFOOName(),
	  pS.getPosition(), pS.getVelocity(), pS.getAttitude(), pS.getintlAngMom());
	}
 */
/** Basic sail constructor.  It is used to build a sail from scratch.
 */
/*    public Sail(SailAway pGUI, 
		String pSaveName, 
		String pProfileName, 
		String pFOO,
		Monad pPosition, 
		Monad pVelocity, 
		Monad pAttitude, 
		Monad pintlAngMom)
	throws 	BadSailDefinitionException, 
		CantGetProfileException, 
		CantGetSaveException
	{
	this.TheGUI=pGUI;

	this.SaveName=pSaveName;
	if (SaveName.equals("null")) EphemerisSaveON=false;
	else EphemerisSaveON=true;

	this.ProfileName=pProfileName;
	if (ProfileName.equals("null")) ProfileON=false;
	else ProfileON=true;

	if (ProfileON) this.getProfileFile();

	this.FOO=pFOO;
	if (this.FOO.equals("Earth")) {this.Micro=this.EARTHMICRO;}
	else {
		if (this.FOO.equals("Sun")) {this.Micro=this.SOLMICRO;}
		}
	this.Position = new Monad(pPosition);
	this.Velocity = new Monad(pVelocity);
	this.Attitude = new Monad(pAttitude);
	this.intlAngMomentum = new Monad(pintlAngMom);  

	//All basic items are finished now.

	if (EphemerisSaveON) this.getSaveFile();
	this.setInitValues();
	this.setDerivedValues();

	try {
		this.Orbit=new OrbitEllipse(this);
		}
	catch (BadOrbitDefinitionException e) {
		System.out.println(e);
		System.out.println(e.getSourceMessage());
		System.out.println("Bad Orbit Defn.  Aborting sail instantiation.");
		throw new BadSailDefinitionException("No Orbit could be made.");
		}
	}//end of primary Sail Constructor
 */

//}}}
//{{{ constructor that uses profile files
/**
 * Short Sail constructor.  It is used when a profile file is to be read for the
 * bulk of the initialization information.  This profile file currently
 * violates the boundry between the physical model and the UI.  The Sail should
 * not need any external resources not already broken down and delivered by the 
 * UI as a set of prototype objects for Sail construction.
 */
    public Sail(SailAway pGUI, 
		String pSaveName, 
		String pProfileName, 
		String pFOO)
	throws 	BadSailDefinitionException, 
		CantGetProfileException, 
		CantGetSaveException
	{
	this.TheGUI=pGUI;

	this.SaveName=pSaveName;
	if (SaveName.equals("null")) {
		//No Ephemeris save file was provided on the command line.  
		//Look it up in the conf file.  If it exists, set SaveName to it and
		//leave the boolean ON.


		//If it does not exists, then turn the 
		//boolean OFF.
		EphemerisSaveON=false;
		}
	else EphemerisSaveON=true;

	this.ProfileName=pProfileName;
	if (ProfileName.equals("null")) {
		ProfileON=false;
		throw new BadSailDefinitionException("A profile must be available for the short constructor");
		}
	else ProfileON=true;

	if (ProfileON) this.getProfileFile();

	FOO=SailProps.getProperty("SailFOO");
	if (FOO.equals("Earth")) {Micro=EARTHMICRO;}
	else {
		if (FOO.equals("Sun")) {Micro=SOLMICRO;}
		}

	double[] tempC=new double[9];
	for (int i=0; i<9; i++) {
		tempC[i]=0.0;
		}

	tempC[2]=Double.parseDouble(SailProps.getProperty("SailPositionX"));
	tempC[3]=Double.parseDouble(SailProps.getProperty("SailPositionY"));
	tempC[4]=Double.parseDouble(SailProps.getProperty("SailPositionZ"));
	this.Position = new Monad("SailPosition", FOO, FOO+"0", 3, 0, tempC);

	tempC[2]=Double.parseDouble(SailProps.getProperty("SailVelocityX"));
	tempC[3]=Double.parseDouble(SailProps.getProperty("SailVelocityY"));
	tempC[4]=Double.parseDouble(SailProps.getProperty("SailVelocityZ"));
	this.Velocity = new Monad("SailVelocity", FOO, FOO+"0", 3, 0, tempC);

	tempC[2]=0.0;
	tempC[3]=0.0;
	tempC[4]=0.0;
	tempC[5]=Double.parseDouble(SailProps.getProperty("SailAttitudeXY"));
	tempC[6]=Double.parseDouble(SailProps.getProperty("SailAttitudeXZ"));
	tempC[7]=Double.parseDouble(SailProps.getProperty("SailAttitudeYZ"));
	this.Attitude = new Monad("SailAttitude", FOO, FOO+"0", 3, 0, tempC);

	tempC[5]=Double.parseDouble(SailProps.getProperty("SailIntLX"));
	tempC[6]=Double.parseDouble(SailProps.getProperty("SailIntLY"));
	tempC[7]=Double.parseDouble(SailProps.getProperty("SailIntLZ"));
	this.intlAngMomentum = new Monad("SailInternalL", FOO, FOO+"0", 3, 0, tempC);  
	
	tempC=null;
	//All basic items are finished now.

	if (EphemerisSaveON) this.getSaveFile();

	try {
		this.RFModel=new RadiationForceModel("RF Model for: "+this.SailName, this);
		this.Orbit=new OrbitEllipse(this);
		}
	catch (BadOrbitDefinitionException e) {
		System.out.println(e);
		System.out.println(e.getSourceMessage());
		System.out.println("Bad Orbit Defn.  Aborting sail instantiation.");
		throw new BadSailDefinitionException("No Orbit could be made.");
		}

	this.setInitValues();
	this.setDerivedValues();
	}//end of short sail constructor
//}}}
//{{{The Gettors
/** Get the name of the sail.
 *  @return The name of this sail as a string
 */
    public String getSailName() {
	return this.SailName;
	}

/** Get the name of the profile file used by the sail.
 *  @return The name of the Sail Profile File
 */
    public String getProfileName() {
	return this.ProfileName;
	}

/** Get the name of the save file used by the sail.
 *  @return The name of the Ephemeris file generated by this Sail
 */
    public String getSaveName() {
	return this.SaveName;
	}

/** Get the name of the mass at the focus of the orbit for the sail.
 *  @return The name of the object at the Focus of the Orbit.
 */
    public String getFOOName() {
	return this.FOO;
	}

/** Get the value of the gravitation constant times the mass of the FOO.
 *  @return The double value of the constant micro
 */
    public double getMicro() {
	return this.Micro;
	}

/** Get the value of the sail mission clock.
 *  @return the double value of the Sail's mission clock.
 */
    public double getMissionClock() {
	return this.MissionClock;
	}

/** Get the sail's current position.
 *  @return The monad representing the Sail's position
 */
    public Monad getPosition() {
	return this.Position;
 	}

/** Get the sail's current velocity.
 *  @return The monad representing the Sail's velocity
 */
    public Monad getVelocity() {
	return this.Velocity;
	}

/** Get the sail's current attitude.
 *  @return The monad representing the Sail's attitude
 */
    public Monad getAttitude() {
	return this.Attitude;
	}

/** Get the sail's current internal angular momentum.
 *  @return The monad representing the Sail's internal angular momentum
 */
    public Monad getintAngMomentum() {
	return this.intlAngMomentum;
	}

/** Get the sail's current orbit.
 *  @return The Orbit of the Sail
 */
    public OrbitEllipse getOrbit() {
	return this.Orbit;
	}

/** Get the sail's current Radiation Force Model.
 *  @return The RFModel of the Sail
 */
    public RadiationForceModel getRFModel() {
	return this.RFModel;
	}

/** 
 * Get the sail's current tolerance value for interation stepping.
 * @return string property showing Sail's tolerance to change on L for one 
 * orbit step.
 */
    public String getChangeAllowanceL() {
	return this.SailProps.getProperty("ChangeToleranceL");
	}

/** 
 * Get the sail's current tolerance value for interation stepping.
 * @return string property showing Sail's tolerance to change on RL for one 
 * orbit step.
 */
    public String getChangeAllowanceRL() {
	return this.SailProps.getProperty("ChangeToleranceRL");
	}

/** Get the sail's current front-side reflectance coefficient.
 *  @return string property showing the Sail's front-side reflectance coefficient.
 */
    public String getRf(){
	return this.SailProps.getProperty("Rf");
	}

/** Get the sail's current back-side reflectance coefficient.
 *  @return string property showing the Sail's back-side reflectance coefficient.
 */
    public String getRb(){
	return this.SailProps.getProperty("Rb");
	}

/** Get the sail's current front-side spectral reflectance coefficient.
 *  @return string property showing the Sail's front-side specular reflectance coefficient.
 */
    public String getSf(){
	return this.SailProps.getProperty("Sf");
	}

/** Get the sail's current back-side spectral reflectance coefficient.
 *  @return string property showing the Sail's back-side specular reflectance coefficient.
 */
    public String getSb(){
	return this.SailProps.getProperty("Sb");
	}

/** Get the sail's current front-side emmissivity coefficient.
 *  @return string property showing the Sail's front-side emmissivity coefficient.
 */
    public String getEf(){
	return this.SailProps.getProperty("Ef");
	}

/** Get the sail's current back-side emmissivity coefficient.
 *  @return string property showing the Sail's back-side emmissivity coefficient.
 */
    public String getEb(){
	return this.SailProps.getProperty("Eb");
	}

/** Get the sail's current front-side non-Lambertian coefficient.
 *  @return string property ing the Sail's front-side non-Lambertian coefficient.
 */
    public String getLf(){
	return this.SailProps.getProperty("Lf");
	}

/** Get the sail's current back-side non-Lambertian coefficient.
 *  @return string property showing the Sail's back-side non-Lambertian coefficient.
 */
    public String getLb(){
	return this.SailProps.getProperty("Lb");
	}

/** Get the sail's current surface area.
 *  @return The string property representing the Sail's front-side area.
 */
    public String getSailArea(){
	return this.SailProps.getProperty("SailArea");
	}

/** Get the sail's current surface mass.
 *  @return The string property representing the Sail's total mass.
 */
    public String getSailMass(){
	return this.SailProps.getProperty("SailMass");
	}

/** Get the sail's current cosine of the solar pointing angle.
 *  @return The double value of the cosine of the solar pointing angle.
 */
    public double getcosAttitude(){
	return this.cosAttitude;
	}

/** Get the sail's current sine of the solar pointing angle.
 *  @return The double value of the sine of the solar pointing angle.
 */
    public double getsinAttitude(){
	return this.sinAttitude;
	}
//}}}
//{{{The Settors
/** This method sets the sail's current mission clock to the new value pClock.
 */
    protected void setMissionClock(double pClock){
	this.MissionClock=pClock;
	}

/** This method adds to the sail's current mission clock the value pInc.
 */
    protected void addtoMissionClock(double pInc) {
	this.MissionClock=this.MissionClock+pInc;
	}

/** This method sets to the sail's current position to pPosition.
 */
    protected void setPosition(Monad pPosition) {
	if (pPosition.isMultiGrade()) {
		System.out.println("Position must not be a multi-vector.");
		return;
		}
	try {
		if (pPosition.isGrade()==1)
			this.Position=new Monad(pPosition);
		else
			System.out.println("Position must be a vector to work.");
		}
	catch (CladosException e) {;}
	}

/** This method sets to the sail's current velocity to pVelocity.
 */
    protected void setVelocity(Monad pVelocity) {
	if (pVelocity.isMultiGrade()) {
		System.out.println("Velocity must not be a multi-vector.");
		return;
		}
	try {
		if (pVelocity.isGrade()==1)
			this.Velocity=new Monad(pVelocity);
		else
			System.out.println("Velocity must be a vector to work.");
		}
	catch (CladosException e) {;}
	}

/** This method sets to the sail's current attitude to pAtt.
 */
    protected void setAttitude(Monad pAtt) {
	if (pAtt.isMultiGrade()) {
		System.out.println("Attitude must not be a multi-vector.");
		return;
		}
	try {
		if (pAtt.isGrade()==1)
			this.Attitude=new Monad(pAtt);
		else
			System.out.println("Attitude must be a bivector to work.");
		}
	catch (CladosException e) {;}
	}

/** This method sets to the sail's current internal angular momentum to pL.
 */
    protected void setintAngMomentum(Monad pL) {
	if (pL.isMultiGrade()) {
		System.out.println("Internal L must not be a multi-vector.");
		return;
		}
	try {
		if (pL.isGrade()==1)
			this.intlAngMomentum=new Monad(pL);
		else
			System.out.println("Internal L must be a bivector to work.");
		}
	catch (CladosException e) {;}
	}
//}}}
//{{{The Published API
/**
 * This method steps the sail forward one iteration of the simulated settings.
 */
    public void stepForward(double pDPhase) {
	this.setDerivedValues();
	double pLAllow=Double.parseDouble(this.getChangeAllowanceL());
	double pRLAllow=Double.parseDouble(this.getChangeAllowanceRL());
	double step=0.0;
	if (pDPhase==0.0) {
		step=Double.parseDouble(SailProps.getProperty("InitialPhaseStep"));
		}
	else 	step=pDPhase; 
	try {
		Orbit.iteratePath(step, pLAllow, pRLAllow);
		//The Sail Position, Velocity and Attitude angles are i
		//forced to refresh by the OrbitEllipse.
		if (EphemerisSaveON) {
			try {
				this.saveSailActivity();
				}
			catch(IOException e) {
				System.out.println("!Put info in Eph file.");
				System.out.println("Switching mode.");
				EphemerisSaveON=false;
				}
			}
		}
	catch (SailException e) {
		System.out.println("An Exception occured during stepForward.");
		System.out.println("An Exception at iteratePath stops Sail");
		System.out.println("from knowing its next set of properties.");
		System.out.println(e.getSourceMessage());
		}
	}//end of stepForward method

/**
 * This method steps the sail backward one iteration of the simulated settings.
 */
    public void stepBackward(double pDPhase) {
	this.setDerivedValues();
	double pLAllow=Double.parseDouble(this.getChangeAllowanceL());
	double pRLAllow=Double.parseDouble(this.getChangeAllowanceRL());
	double step=0.0;
	if (pDPhase==0.0) {
		step=Double.parseDouble(this.SailProps.getProperty("InitialPhaseStep")); 
		step=step * -1.0;
		}
	else 	step=-1.0*pDPhase; 
	try {
		Orbit.iteratePath(step, pLAllow, pRLAllow);
		//The Sail Position, Velocity and Attitude angles are i
		//forced to refresh by the OrbitEllipse.
		if (EphemerisSaveON){
			try {
				this.saveSailActivity();
				}
			catch(IOException e) {
				System.out.println("Can't save info to the Eph file.");
				System.out.println("Switching mode.");
				EphemerisSaveON=false;
				}
			}
		}
	catch (SailException e) {
		System.out.println("An Exception occured during stepForward.");
		System.out.println("An Exception at iteratePath would stop Sail from");
		System.out.println("knowing its next set of attitude values.");
		System.out.println(e);
		}
	}//end of stepBackward method

/** 
 * This method closes the files associated with the sail to clean up the
 * simulation.  Closing files won't be necessary in the future, but closing
 * interfaces will be.
 */
    public void terminateSail() {
	try {
		this.to.close();
		}
	catch(Exception e) {
		System.out.println("Unable to close the Save file.");
	 	}
	finally {
		if (this.to != null) {
			this.to=null;
			}
		return;
		}
	}
//}}}
//{{{The Hidden actions
/** This method sets to the value of the terms kept for calculation convience.
 *  These terms include 
 *  <ul>
 *  <li>cosine of the sail pointing angle
 *  <li>sine of the sail pointing angle
 *  </ul>
 */
    protected void setDerivedValues() {

	Monad tempA1=new Monad("TempAttitude1 for cosAtt", this.Attitude);
	Monad tempA2=new Monad("TempAttitude2 for sinAtt", this.Attitude);
	Monad tempR=new Monad("tempPosition for scalar Att", this.Position);
	try {
		tempA1.LocalDual();    //tempA1 is now a vector
		tempA1.Normalize();
		tempA2.LocalDual();    //tempA2 is now a vector
		tempA2.Normalize();
		tempR.Normalize();

		tempA1.Dot(tempR);    //tempA1 is now a scalar
		tempA2.Wedge(tempR);  //tempA2 is now a bivector

		this.cosAttitude=tempA1.GradeProject(0)[0];
		this.sinAttitude=tempA2.MagnitudeOf();
		}
	catch (CladosException e) {
		System.out.println(e);
		System.out.println(e.getSourceMessage());
		System.exit(0);
		}
	finally {
		tempR=null;
		tempA1=null;
		tempA2=null;
		}
	} //end of setting of derived values.
/** 
 *  This method sets the initial values of property objects from save and profile files.
 *  It also starts the mission clock to a default value.
 */
    protected void setInitValues() {

	this.MissionClock=0.0;  //Until we can read the Config file.
	}

/** 
 * This method does the actual file handling for the profile file.  It will be
 * eliminated in the future to avoid crossing the PM/UI boundry.  Instead, the
 * UI will deliver to the Sail a prototype object with all profile info in it.
 */
    protected void getProfileFile() throws CantGetProfileException {
	//Use the Profile file to define the specifics for the sail.
	//This file also includes the details for att, mass, area, spin rates, etc
	File fProfile = null;
	boolean bProfile = false;
	try {
		fProfile = new File(ProfileName);
		bProfile = fProfile.exists();
		if (bProfile) bProfile=fProfile.isFile();
		if (bProfile) bProfile=fProfile.canRead();
		if (!bProfile) {
			 throw new CantGetProfileException("Profile not readable.");
			}
		//Getting here implies the profile file has been found and is readable

		this.SailProps = new Properties();
		SailProps.load(new BufferedInputStream(new FileInputStream(fProfile)));

			//ProfileData now has entire Profile contents
			//Extra activity in this function depends on Profile format
		this.SailName=SailProps.getProperty("SailName");

	//The Mass and Area numbers will get pulled into the intlStructure object in 
	//a later version.
	//The profile file will be complex enough to hold several reflectors and will
	//probably get read by the object that makes the intlStructure.

		this.SailMass=Double.parseDouble(this.getSailMass());
		this.SailArea=Double.parseDouble(this.getSailArea());
		this.MissionClock=Double.parseDouble(this.SailProps.getProperty("MissionClockStart"));

		System.out.println("Properties for "+ this.SailName +" are loaded.");
		}
	catch (Exception e) {
		System.out.println("IO Problem:  Incomplete Profile read.");
		throw new CantGetProfileException("No Access to Sail Profile file.");
		}
	finally {
		fProfile=null;
		}
  }

/**
 * This method does the initial file handling for the save file.
 * It doesn't do much right now except check to see if the file is there.
 * In the future, this method will be removed in favor or a routine that
 * delivers information to the UI or the Notification Tool.  The PM should not
 * be handling it's own File I/O because it might not even be on the same
 * machine as the UI or Notification Tool.
 */
    protected void getSaveFile() throws CantGetSaveException {
	File fSave=null;
	boolean bSave=false;
	try {
		fSave=new File(SaveName);
		bSave=fSave.exists();
		if (bSave) bSave=fSave.isFile();
		if (bSave) bSave=fSave.canWrite();
		if (!bSave) {
			throw new CantGetSaveException("No access to Eph file.");
			}
		//Getting here implies the Save file has been found and is writeable

		this.to=new FileWriter(fSave);
		System.out.println("FileWriter to Eph file is open.");

		}//end of try block
	catch(IOException e) {
		System.out.println("IO Problem:  Incomplete Save file write");
		}
	finally {
		if (this.to == null) EphemerisSaveON=false;
		if (this.to != null) 
			System.out.println("Ephemeris File Open and ready");
		fSave=null;
		}

	}

/** 
 * This method saves historical sail data to the save file.
 * In early versions it will be used to write the ephemeris information along 
 * with other data.
 * In the future, this method will be removed in favor or a routine that
 * delivers information to the Library Interface (LI).  The PM should not
 * be handling it's own File I/O because it might not even be on the same
 * machine as the LI.
 */
    protected void saveSailActivity() throws IOException {
	this.to.write(this.SailName);
	this.to.write(", ");
	this.to.write(Double.valueOf(MissionClock).toString());
	this.to.write(", ");
	this.to.write(Double.valueOf(Orbit.SemiMajorAxis).toString());
	this.to.write(", ");
	this.to.write(Double.valueOf(Orbit.Eccentricity).toString());
	this.to.write(", ");
	this.to.write(Double.valueOf(Orbit.Inclination).toString());
	this.to.write(", ");
	this.to.write(Double.valueOf(Orbit.RAN).toString());
	this.to.write(", ");
	this.to.write(Double.valueOf(Orbit.PeriAngle).toString());
	this.to.write(", ");
	this.to.write(Double.valueOf(Orbit.TrueAnomoly).toString());
	this.to.write("\n");
	this.to.flush();
	return;
	}//end of saveSailActivity

/**
 * This method is nowhere near ready yet.  It must use a file of its own since the
 * save file has evolved into the ephemeris.
 *  
 * This method saves the current profile of the active sail.
 *
 * In the future, this method will be removed in favor or a routine that
 * delivers information to the Library Interface (LI).  The PM should not
 * be handling it's own File I/O because it might not even be on the same
 * machine as the LI.
 */
    protected void saveSailProfile(){

/*  File fSave=null;
	try {
		File fSave=new File(this.SaveName);
		if(fSave.exists()) {
			if(fSave.isFile()) {
				if(fSave.canWrite()){

					//Save file is reachable if we make it here.  
					//Open it and write this header to it.

	String headpiece="#  This is a non-default sail profile file.";
	SailProps.store(new BufferedOutputStream(new FileOutputStream(fSave)), headpiece);

					}
        else {
      throw new IOException("No access to the save output file.");
      }
        }
    else {//SaveName is not a file...so bail out
        throw new IOException("The save output file is actually a directory.");
        }
    }
      else {//Create a file since it doesn't exist yet.
    fSave.createNewFile();
    if(fSave.exists()) {
        if(fSave.canWrite()) {

      //Save file is reachable if we make it here.  Open it and write this header to it.
      String headpiece="#  This is a non-default sail profile file.";
      SailProps.store(new BufferedOutputStream(new FileOutputStream(fSave)), headpiece);
      
      }
        else {
      throw new IOException("No access to the save output file.");
      }
        }
    else {
        throw new IOException("No access to the save output file.");
        }    
    }//end file create else block

      }
  catch(IOException e) {
      System.out.println("IO Problem:  Incomplete Save file write");
      }
  finally {
          ;
      }//Are all the associated streams closed?
*/
	} //End of saveSailProfile Method

//}}}
    } //end of Sail Class

