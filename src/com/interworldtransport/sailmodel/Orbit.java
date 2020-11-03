/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailmodel.Orbit----------------------------------------
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
Use of this code or executable objects derived from it by the Licensee states their 
willingness to accept the terms of the license.
<p>
A prospective Licensee unable to find a copy of the license terms should contact
Interworld Transport for a free copy.
<p>
---com.interworldtransport.sailmodel.Orbit----------------------------------------
 */

package com.interworldtransport.sailmodel;
import com.interworldtransport.clados.*;

/** The class to represent a general orbit.
 * <p>
 * This is the general orbit class.  Objects with paths defineable as an orbit
 * may own a child of this class.  Classes like this one require a reference to
 * their owners to get the occasional information they need.  This class 
 * encapsulates the basic physics behind two body gravitational problems without 
 * assuming an orbit shape.  All perturbations on an ideal case are handled by 
 * other objects in the physical model.
 * <p>
 * Proper use of this class is accomplished when it is subclassed and handled 
 * by the object that owns it.  The GUI that owns the physical model may track 
 * the states of members of this class, but it should not interact in a controlling 
 * manner with an Orbit.
 * <p>
 * Future versions of the Orbit will include features from the following list.
 * <p>
 * <ul>
 * <li>Orbit Patching (sub-orbits)
 * <li>Relativistic considerations
 * <li>File I/O over the web for access to constants and a perturber library
 * <li>Anything else considered to be common to orbits of all types.
 * </ul>
 *
 * @version   0.01 $Date: 2000/05/31 02:46:17 $ 
 * @author   Dr Alfred W Differ
 * @see   com.interworldtransport.sailmodel.Sail
 * @see   com.interworldtransport.sailmodel.Perturber
 * @since SailAway 1.0
 */
abstract	class		Orbit {
/** This String is the name of the Orbit
 */
    protected	String		OrbitName;
/** This number is the product of the gravitation constant and the mass of the
 *  object being orbited.
 */
    protected	double		Micro;
/** This Monad is the unit vector pointing at the object in orbit
 */
    protected	Monad		RHat;
/** 
 * This object is the one responsible for handling all perturbation behavior 
 * of the orbit.
 */
    public		Perturber	Perturb;
/** 
 * This object point to the Parent Sail.  It is for reference purposes when the 
 * Orbit needs to know information from its parent.
 */
    protected	Sail		ParentSail;
/** This Monad points to the First Point Of Aries.
 *  In a more general coordinate system, this is the zero longitude direction along
 *  the reference equator.
 */
    protected	Monad		ARIES;
/**
 * This Monad points to where the sun is at the summer solstice.
 * In a more general coordinate system, this is the 90 degree longitude direction 
 * along the reference equator.
 */
    protected	Monad		GEMINI;
/**
 * This Monad is the reference equator for the orbit.  Inclination and RAN are 
 * measured against it.
 */
    protected	Monad		EQUATOR;
/** 
 * This boolean keeps track of whether the perturbation features are turned 
 * on or not.
 */
    public		boolean		PerturbationON=true;

    //public static final long      serialVersionUID=?? ;

//The Constructors.

/** 
 * Basic orbit constructor.  It is used to build an orbit for a known sail.
 * It is also the only constructor tested at this time.
 */
    public	Orbit(Sail pSail) {

	OrbitName = "Orbit Of:"+pSail.getSailName();
	Micro = pSail.getMicro();
	ParentSail = pSail;
	PerturbationON = Boolean.parseBoolean(ParentSail.SailProps.getProperty("PerturbationON"));
	Perturb=new Perturber("Prtbr:"+OrbitName, this);
	setInitialFrame();
		
	//calculate the objects that define the orbit.
	//calculate the derived objects that are convenient
	//to keep and display for the user.
	//Do both of these, though, in the child class.
	//Any failure within the child should result in a 
	//BadOrbitDefinitionException
	}

//The Gettor Methods.

/** This method returns the name of the orbit.
 *  @return The name of the Orbit as a string
 */
    public	String		getOrbitName()  {
	return this.OrbitName;
	}

/** This method returns the value of Micro.
 *  @return The double value of micro.
 */
    public	double		getMicro() {
	return this.Micro;
	}

/**
 * This method returns the unit Monad for the position of the object on the orbit.
 * @return The monad representing the current RHat of the Orbit.
 */
    public	Monad		getRHat() {
	return this.RHat;
	}

/** 
 * This method returns a copy of the unit Monad for the position of the object 
 * on the orbit.
 * @return The monad representing the current RHat of the Orbit.
 */
    public	Monad		getRHatCopy() {
	return new Monad(this.RHat);
	}

/** This method returns the Perturbation handling object.
 *  @return The Orbit's current Perturber.
 */
    public	Perturber	getPerturb() {
	return this.Perturb;
	}


//The settor Methods.

/** This method sets the string for the orbit name.
 */
    protected void  setOrbitName(String pOrbitName)  {
	this.OrbitName=pOrbitName;
	}

/** This method sets the value of Micro.
 */
    protected void  setMicro(double pMicro) {
	this.Micro=pMicro;
	}

/** This method copies the unit Monad for the position of the object on the orbit.
 *  to the next step iteration Monad in preparation for being rotated.
 */
    public	void		setnewRHat() {
	this.newRHat=new Monad(this.RHat);
	}

/** This method sets the Monad for the unit position 'vector'.
 */
    protected	void	setRHat(Monad pRHat) 
	throws BadPositionException {

	try {
		if (pRHat.isGrade()==1) {
			this.RHat=pRHat;
			}
		else
			System.out.println("RHat needs to be a vector to work.");
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("RHat must not be a multi-vector.");
		throw new BadPositionException(	this, 
										"RHat can't be a multi-vector when set.", 
										pRHat);
		}
	}

/** This method sets the object for the Orbit Perturber.
 */
    protected void  setPerturber(Perturber pPerturber) {
	this.Perturb=pPerturber;
	}

/** 
 * This method sets the monads that represent the frame of the orbit.
 * EQUATOR, ARIES, and GEMINI are usually set to the ecliptic, first point of 
 * Aries, and the summer solstice location respectively for solar orbits.  The 
 * concepts generalize for other frames.
 */
    protected void  setInitialFrame(){
	double[] tempC={0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
	this.ARIES=new Monad("Aries", this.RHat);
	this.ARIES.setCoeff(tempC);
	tempC[2]=0.0;
	tempC[3]=1.0;
	this.GEMINI=new Monad("Gemini", this.RHat);
	this.GEMINI.setCoeff(tempC);
	this.EQUATOR=new Monad("Equator", this.ARIES);
	try {
		this.EQUATOR.Wedge(this.GEMINI);
		}
	catch (CladosException e) {
		System.out.println(e);
		System.exit(0);
		}      //impossible exception should stop application.
	}

/** 
 * This method sets the derived values and monads that represent the the often 
 * calculated objects of the orbit.  The method sets things like the Latus Rectum, 
 * Nodeline, SMA, Eccentricity, Inclination, RAN, PeriAngle, and TrueAnomoly by 
 * calculating them first and then setting the data members.  
 * The actual list of items calculated depends on the child class and the shape
 * it represents.
 */
    abstract	 void  setDerivedValues();

//The Internal Calculating Methods
/** 
 * This method calculates the Monad for the RHat 'vector'.
 */
    private	Monad	calculateRHat() 
	throws CladosException {

	Monad tempRH=null;
	try {
		tempRH=new Monad(ParentSail.getPosition());
		tempRH.Normalize();
		tempRH.setMonadName("Unit position monad:");
		tempRH.GradePart(1);
		}
	catch (CladosException e) {
		System.out.println("Calculation of RHat failed.");
		System.exit(0);
		}

	return tempRH;
    }
/** 
 * This method calculates the Monad for the RHat 'vector'.
 */
    private	Monad	calculateRHat(Monad pPosition) 
	throws CladosException {

	Monad tempRH=null;
	try {
		tempRH=new Monad(pPosition);
		tempRH.Normalize();
		tempRH.setMonadName("Unit position monad:");
		tempRH.GradePart(1);
		}
	catch (CladosException e) {
		System.out.println("Calculation of RHat failed.");
		System.exit(0);
		}

	return tempRH;
    }

//The State Checking Methods


//The complicated operations

/** 
 * This method calculates the next position for the object on the orbit and other 
 * related information.  This method is the heart of the Kepler approach for 
 * projecting an object along an orbit.
 */
    abstract	void	iteratePath(double pDPhase, double pAllow);

/** 
 * This method forces the recalculation of the Derived Members of the Orbit.
 * Future versions of it will check to see if it is worth recalculating all
 * members or just some of them.  The current version just calls setDerivedValues().
 */
    public void    recalculateDerivedMembers() {
	this.setDerivedValues();
	}

/** 
 * This method returns a Monad that represents the next unit position 'vector' 
 * for the object on the orbit.
 */
    abstract	Monad		predictPosition();

/** 
 * This method returns a Monad that represents the next velocity 'vector' for 
 * the object on the orbit.
 */
    abstract	Monad		predictVelocity(); 

/** 
 * This method returns a value for the true anomoly increment from the time 
 * increment in an interation step.  This calculation requires Kepler's method 
 * and is not ready yet.
 */
    abstract	double		ConvertTimeToPhase(	double pTime, 
    							double pErrorAllowance) ;
	//This section solves Kepler's problem.  
	//Known delta-t implies a delta-true anomoly.
	//This problem involves a transendental equation, so a numerical process 
	//must approximate the solution.

/** 
 * This method returns a value for the time increment from the true anomoly 
 * increment in an interation step.  This method is needed to keep up the Mission 
 * Clock.
 */
    abstract	double		ConvertPhaseToTime(double pPhase);
    //pPhase is a delta true anomoly

/** 
 * This method transforms the orbit reference frame using the passed Monad to 
 * transform the old frame.  This method is not ready yet.  
 * It will be needed to cope with low inclination cases.
 */
    abstract	void		TransformOrbitReference(Monad pReference);

    }//End of Orbit class

