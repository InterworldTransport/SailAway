/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailmodel.OrbitEllipse-------------------------------
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
---com.interworldtransport.sailmodel.OrbitEllipse-------------------------------
 */

package com.interworldtransport.sailmodel;
import com.interworldtransport.clados.*;

/** The class to represent an elliptical orbit.
 * <p>
 * This is the general closed orbit class.  Objects in closed orbits maintain an
 * OrbitEllipse in order to let it deal with the physics of the environment.
 * This class requires a reference to its owner to get occasional information it
 * needs.  This class encapsulates the basic physics behind two body gravitation
 * problems.  All perturbations on the ideal case are handled by other objects 
 * in the physical model.
 * <p>
 * Proper use of this class is accomplished when it is constructed and handled
 * by the object that owns it.  The GUI that owns the physical model may track
 * the states of members in this class, but it should not interact in a 
 * controlling manner with the OrbitEllipse.
 * <p>
 * Operation of the OrbitEllipse is accomplished by the owning object's orbit
 * iterator methods.  A flag within the OrbitEllipse determines whether 
 * perturbations are considered in iterations.  Simple iteration is handled
 * directly by the OrbitEllipse.  Perturbations require the OrbitEllipse to ask
 * for further details from its Perturber.
 * <p>
 * Defining the OrbitEllipse in a more general way to include parabolic and
 * hyperbolic paths may be done in the future in order to support orbit patching
 * when the owning objects changes its FOO.
 * <p>
 * Future versions of the OrbitEllipse will include features from the following
 * list.
 * <p>
 * <ul>
 * <li>Orbit Patching (sub-orbits)
 * <li>Hyperbolic and Parabolic paths
 * <li>Relativistic considerations
 * <li>Complex I/O over the internet for constants and & perturber library
 * </ul>
 *
 * @version   0.30, $Date: 2001/11/03 08:43:10 $ 
 * @author   Dr Alfred W Differ
 * @see   com.interworldtransport.sailmodel.Sail
 * @see   com.interworldtransport.sailmodel.Perturber
 * @since SailAway 1.0
 */
public	class	OrbitEllipse {
/**
 * This String is the name of the Orbit
 */
    protected	String		OrbitName;
/**
 * This number is the product of the gravitation constant and the mass of the
 * object being orbited.
 */
    protected	double		Micro;
/**
 * This Monad is the unit vector pointing at the object in orbit
 */
    protected	Monad		RHat;
/**
 * This Monad is the Eccentricity 'vector' of the orbit.
 */
    protected	Monad		RungeLenz;
/**
 * This Monad is the Angular Momentum associated with the orbit.
 */
    protected	Monad		L;
/**
 * This object is the one responsible for handling all perturbation behavior of
 * the orbit.
 */
    public	Perturber	Perturb;
/**
 * This object point to the Parent Sail.  It is for reference purposes when the 
 * Orbit needs to know information from its parent.
 */
    protected	Sail		ParentSail;
/**
 * This Monad points to the First Point Of Aries.
 * In a more general coordinate system, this is the zero longitude mark 
 * along the reference equator.
 */
    protected	Monad		ARIES;
/**
 * This Monad points to where the sun is at the summer solstice.
 * In a more general coordinate system, this is the 90 degree longitude mark
 * along the reference equator.
 */
    protected	Monad		GEMINI;
/**
 * This Monad is the reference equator for the orbit.  Inclination and RAN are
 * measured against it.
 */
    protected	Monad		EQUATOR;
/**
 * This number holds a copy of the semi-major axis of the orbit.
 * It is used for convenience purposes only since real calculations use the 
 * Monads.
 */
    public	double		SemiMajorAxis;
    public	double		dsma;
/**
 * This number holds a copy of the eccentricity of the orbit.
 * It is used for convenience purposes only since real calculations use the 
 * Monads.
 */
    public	double		Eccentricity;
    public	double		decc;
/**
 * This number holds a copy of the inclination of the orbit.
 * It is used for convenience purposes only since real calculations use the 
 * Monads.
 */
    public	double		Inclination;
    public	double		dinc;
/**
 * This number holds a copy of the RAN of the orbit.
 * It is used for convenience purposes only since real calculations use the 
 * Monads.
 */
    public	double		RAN;
    public	double		dnod;
/**
 * This number holds a copy of the periapsis angle of the orbit.
 * It is used for convenience purposes only since real calculations use the 
 * Monads.
 */
    public	double		PeriAngle;
    public	double		dper;
/**
 * This number holds a copy of the true anomoly of the object on the orbit.
 */
    public	double		TrueAnomoly;
    public	double		danm;
/**
 * This Monad points along the RAN.
 */
    public	Monad		NodeLine;
/**
 * This Monad points 90 degrees ahead of the RAN along the orbit.
 */
    public	Monad		NodePlus90;
/**
 * This Monad points 90 degrees ahead of the periapsis along the orbit
 */
    public	Monad		LatusRectum;
/**
 * This number holds the delta time value associated with the phase increment.
 */
    public	double		TimeStep;
/**
 * This boolean keeps track of whether the perturbation features are turned on 
 * or not.
 */
    public	boolean		PerturbationON=false;
// Level one is for tracking through the code.
    private	boolean		DEBUG=true;	//Debug to level one
// Level two is for seeing high level values for variables.
    private	boolean		DEBUG2=true;	//Debug to level two
// Level three is for seeing the coefficients of monads.
    private	boolean		DEBUG3=false;	//Debug to level three
// Level four forces more testing to be done on certain monads
    private	boolean		DEBUG4=true;	//Debug to level three

    //private	double		PhaseStep;
    private	Monad		Rot;
    private	Monad		newRHat;  	//A convenience to keep here
    private	Monad		newPosition;  	//A convenience to keep here
    private	Monad		newVelocity;  	//A convenience to keep here

    //public static final long      serialVersionUID=?? ;

//The Constructors.

/** 
 * Basic orbit constructor.  It is used to build an orbit for a known sail.
 * It is also the only constructor tested at this time.
 */
    public	OrbitEllipse(Sail pSail) 
		throws		BadOrbitDefinitionException {

	this.OrbitName="Orbit Of:"+pSail.getSailName();
	this.Micro=pSail.getMicro();
	this.ParentSail=pSail;

	try {
		//L is figured from Position and velocity
		this.setL(this.calculateAngularMomentum());

		//RHat is figured from Position
		this.setRHat(this.calculateRHat());

		//RL needs L, velocity, and RHat to be calculated.
		this.setRungeLenz(this.calculateRungeLenz());

		}
	catch (BadPositionException e) {
		System.out.println("Ill defined position killed orbit.");
		throw new BadOrbitDefinitionException(
			"Badly defined position killed orbit.");
		}
	catch (BadAngularMomentumException e) {
		System.out.println("Badly defined L kills orbit.");
		throw new BadOrbitDefinitionException(
			"Badly defined angular momentum killed orbit.");
		}
	catch (BadRungeLenzException e) {
		System.out.println("Badly defined RL kills orbit.");
		throw new BadOrbitDefinitionException(
			"Badly defined RungeLenz killed orbit.");
		}
	catch (CladosException e) {
		System.out.println("Clados Error constructing Orbit Monads");
		System.out.println(e.getSourceMessage());
		System.exit(0);
		}
	this.setInitialFrame();
	this.setDerivedValues();      //Complete the derived parameters 
	try {	//change catch statements to catch Bad Perturber Exceptions
		//when it is ready to be thrown by the Perturber.
		PerturbationON=new Boolean(ParentSail.SailProps.getProperty(
			"PerturbationON")).booleanValue();
		//Declare a Perturber whether perturbations are on or not.
		//This is done because the Perturber is the object that 
		//really owns the iteration step.  It is the perturber that 
		//really gets to decide whether to reset orbit parameters 
		//after a phase step.
		this.Perturb=new Perturber("Prtbr:"+OrbitName, this);
		}
	catch (BadAngularMomentumException e) {
		throw new BadOrbitDefinitionException(
			"Badly defined angular momentum killed perturber.");
		}
	catch (BadRungeLenzException e) {
		throw new BadOrbitDefinitionException(
			"Badly defined RungeLenz killed perturber.");
		}
	catch (BadAttitudeException e) {
		throw new BadOrbitDefinitionException(
			"Bad Sail Attitude killed perturber.");
		}
	}

//The Gettor Methods.

/**
 * This method returns the name of the orbit.
 * @return The name of the Orbit as a string
 */
    public	String		getOrbitName()  {
	return this.OrbitName;
	}

/**
 * This method returns the value of Micro.
 * @return The double value of micro.
 */
    public	double		getMicro() {
	return this.Micro;
	}

/**
 * This method returns the unit Monad for the position of the object.
 * @return The monad representing the current RHat of the Orbit.
 */
    public	Monad		getRHat() {
	return this.RHat;
	}

/**
 * This method returns a copy of the unit Monad for the position of the object.
 * @return The monad representing the current RHat of the Orbit.
 */
    public	Monad		getRHatCopy() {
	return new Monad(this.RHat);
	}

/**
 * This method returns the Monad RungeLenz
 * @return Monad representing the current RungeLenz of the Orbit.
 */
    public	Monad		getRungeLenz() {
	return this.RungeLenz;
	}

/**
 * This method returns the Monad for the angular momentum.
 * @return The monad representing the current Angular Momentum of the Orbit.
 */
    public	Monad		getL() {
	return this.L;
	}

/**
 * This method returns a copy of the Monad for the angular momentum.
 * @return  monad representing the current Angular Momentum of the Orbit.
 */
    public	Monad		getLCopy() {
	return new Monad(this.L);
	}

/**
 * This method returns the Perturbation handling object.
 * @return The Orbit's current Perturber.
 */
    public	Perturber	getPerturber() {
	return this.Perturb;
	}

/**
 * This method returns the value of the average angular speed of the object.
 * @return Double representing the average angular speed along the orbit
 */
    public	double		getAveAngSpeed() {
	return (0.5/Math.PI)*Math.sqrt(
		this.Micro/(Math.pow(SemiMajorAxis,3.0)));
	}

/**
 * This method returns the value of the Eccentric anomoly associated with the
 * current true anomoly.
 * @return The double value representing the eccentric anomoly along the orbit
 */
    public	double		getEccentricAnomoly() {
	return (Math.acos(this.Eccentricity+Math.cos(this.TrueAnomoly)));
	}

/**
 * This method returns the value of the Mean anomoly associated with the
 * current true anomoly.
 * @return The double value representing the mean anomoly along the orbit
 */
    public	double		getMeanAnomoly() {
	double temp=this.getEccentricAnomoly();
	return (temp-Eccentricity*Math.sin(temp));
	}

/**
 * This method returns the value of the area of the orbit.  It is useful as a
 * calculational shortcut and for graphic display by a GUI.
 */
    public	double		getOrbitArea() {
	return (Math.PI*SemiMajorAxis*getSemiMinorAxis());
	}
/**
 * This method returns the value of semi-minor axis for the orbit.
 * @return The double value representing the semi minor axis of the orbit
 */
    public	double		getSemiMinorAxis() {
	return (SemiMajorAxis*Math.sqrt(1-Eccentricity*Eccentricity));
	}

/**
 * This method returns the value of the size of the latus rectum.
 * @return The double value representing the latus rectum of the orbit
 */
    public	double		getLatusRectumD() {
	return (SemiMajorAxis*(1-Eccentricity*Eccentricity));
	}

/**
 * This method returns the monad for the latus rectum.
 * @return The monad representing the latus rectum of the orbit
 */
    public	Monad		getLatusRectum() {
	return this.LatusRectum;
	}

/**
 * This method returns the monad for the Node Line.
 * @return The monad representing the Node Line of the orbit
 */
    public	Monad		getNodeLine() {
	return this.NodeLine;
	}

/**
 * This method returns the monad for the NodePlus90.
 * @return The monad representing the NodePlus90 of the orbit
 */
    public	Monad		getNodePlus90() {
	return this.NodePlus90;
	}

/**
 * This method gets the scaling constant used to calculate the latus rectum.
 * @return The double value representing the latus scaling constant of the orbit
 */
    public	double		getLatusScaling() {
	return (1.0+this.Eccentricity*Math.cos(this.TrueAnomoly));
	}

/**
 * This method returns the value of the distance to the focus of the orbit.
 * It is calculated independent of the Postion vector from the sail.
 * @return The double value representing the distance to the FOO.
 */
    public	double		getPrimaryDistance() {
	return (this.getLatusRectumD()/this.getLatusScaling());
	}

/**
 * This method returns the value of the specific potential energy.
 * @return Double representing the potential energy of the object on the orbit
 */
    public	double		getPotentialEnergy() {  //specific
	return (-1.0*this.getMicro()/this.getPrimaryDistance());
	}

/**
 * This method returns the value of the specific kinetic energy
 * @return Double representing the kinetic energy of the object on the orbit
 */
    public	double		getKineticEnergy() {  //specific
	Monad tempL=new Monad(this.L);
	double SQL=0.0;
	try {
		tempL.LeftMultiply(this.L);
		SQL=tempL.GradeProject(0)[0];
		}
	catch (CladosException e) {;}    
		//Ignore impossible exception.  
		//L can always left multiply L.
	double temp=0.5*SQL/(getPrimaryDistance()*getPrimaryDistance());
	tempL=null;
	return temp;
	}

/**
 * This method returns the value of the specific total energy
 * @return Double representing the total energy of the object on the orbit
 */
    public	double		getTotalEnergy() {  //specific
	return (this.getPotentialEnergy()+this.getKineticEnergy());
	}

/**
 * This method gets the monad used as the reference to the First Point of ARIES.
 * @return The monad used for the first point of ARIES
 */
    public	Monad		getARIES() {
	return this.ARIES;
	}

/**
 * This method returns the monad used as the reference to 90 degrees longitude
 * or GEMINI
 * @return The monad used for GEMINI
 */
    public	Monad		getGEMINI() {
	return this.GEMINI;
	}

/**
 * This method returns the monad used as the reference to the EQUATOR
 * @return The monad used for EQUATOR
 */
    public	Monad		getEQUATOR() {
	return this.EQUATOR;
	}

//The settor Methods.

/**
 * This method sets the string for the orbit name.
 */
    protected void  setOrbitName(String pOrbitName)  {
	this.OrbitName=pOrbitName;
	}

/**
 * This method sets the value of Micro.
 */
    protected void  setMicro(double pMicro) {
	this.Micro=pMicro;
	}

/**
 * This method sets the Monad for the unit position 'vector'.
 */
    protected void  setRHat(Monad pRHat) 
	throws BadPositionException {

	try {
		if (pRHat.isGrade()==1) {
			if (this.isDirectionInPlane(pRHat)) {
				this.RHat=pRHat;
				}
			else {
				System.out.println("No setting out plane RHat");
				throw new BadPositionException(this, 
					"RHat must be in plane.",pRHat);
				}
			}
		else {
			System.out.println("RHat must be a vector to work.");
			throw new BadPositionException(this, 
				"RHat must be a vector when set.",pRHat);
			}
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("RHat must not be a multi-vector.");
		throw new BadPositionException(this, 
			"RHat must not be  a multi-vector when being set.", 
			pRHat);
		}
	}

/**
 * This method copies the unit monad for the position of the object on the orbit
 * to the next step iteration monad in preparation for being rotated.
 */
    public	void		setnewRHat() {
	this.newRHat=new Monad(this.RHat);
	}

/**
 * This method sets the Monad for the new position 'vector'.
 */
    protected void  setnewPosition(Monad pM) 
	throws BadPositionException {

	try {
		if (pM.isGrade()==1) {
			if (this.isDirectionInPlane(pM)) {
				this.newPosition=pM;
				}
			else {
				System.out.println("No setting out plane newP");
				throw new BadPositionException(this, 
					"newPosition must be in plane.",pM);
				}
			}
		else {
			System.out.println("newP must be vector in set.");
			throw new BadPositionException(this, 
				"newPosition must be a vector when set.",pM);
			}
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("newPosition must not be a multi-vector.");
		throw new BadPositionException(this, 
			"newPosition must not be multi-vector when being set.", 
			pM);
		}
    }

/**
 * This method sets the Monad for the new velocity 'vector'.
 */
    protected void  setnewVelocity(Monad pM) 
	throws BadVelocityTryException {

	try {
		if (pM.isGrade()==1) {
			if (this.isDirectionInPlane(pM)) {
				this.newVelocity=pM;
				}
			else {
				System.out.println("No setting out plane newV");
				throw new BadVelocityTryException(this.L, 
					"newVelocity must be in plane.",pM);
				}
			}
		else {
			System.out.println("newV must be vector in set.");
			throw new BadVelocityTryException(this.L, 
				"newVelocity must be a vector when set.",pM);
			}
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("newVelocity must not be a multi-vector.");
		throw new BadVelocityTryException(this.L, 
			"newVelocity must not be multi-vector when being set.", 
			pM);
		}
    }

/** 
 * This method sets the Monad for the Runge-Lenz 'vector'.
 * It is checked for validity by seeing if it lies in the L plane.
 */
    protected void  setRungeLenz(Monad pRungeLenz) 
	throws BadRungeLenzException {

	try {
		if (pRungeLenz.isGrade()==1) {
			if (this.isDirectionInPlane(pRungeLenz)) {
				this.RungeLenz=pRungeLenz;
				}
			else {
				System.out.println("RL not in plane.  No set.");
				}
			}
		else {
			System.out.println("RungeLenz must be vector to work.");
			}
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("RungeLenz must not be a multi-vector.");
		throw new BadRungeLenzException(this, 
			"RungeLenz must not be  a multi-vector when being set.",
			 pRungeLenz);
		}
	}

/** 
 * This method sets the Monad for the angular momentum.
 * It is checked for validity by checking the grade of the passed Monad.
 */
    protected void  setL(Monad pL) 
	throws BadAngularMomentumException {

	try {
		if (pL.isGrade()==2) 
			this.L=pL;
		else {
			System.out.println(
				"Angular Momentum must be a bivector to work.");
			this.L=new Monad(pL);
			this.L.GradePart(2);
			}
		}
	catch (BladeOutOfRangeException ex) {
		System.out.println("Angular Momentum set attempt failed.");
		System.exit(0);
		}
	catch (NoDefinedGradeException eg) {
		System.out.println(
				"Angular Momentum must not be a multi-vector.");
		throw new BadAngularMomentumException(this, 
				"L must not be  a multi-vector when being set.",
				 pL);
		}
	}

/**
 * This method sets the object for the Orbit Perturber.
 */
    protected void  setPerturber(Perturber pPerturber) {
	this.Perturb=pPerturber;
	}

/** 
 * This method sets the semi-major axis after some other routine calculates it.
 * This orbit parameter is kept as a convenience only.  The parameter set used
 * by this class for orbit definition is {Angular Momentum, RungeLenz}.
 */
    protected void  setSemiMajorAxis(double pSMA) {
	this.SemiMajorAxis=pSMA;
	}

/** 
 * This method sets the eccentricity after some other routine calculates it.
 * This orbit parameter is kept as a convenience only.  The parameter set used
 * by this class for orbit definition is {Angular Momentum, RungeLenz}.
 */
    protected void  setEccentricity(double pEccentricity) {
	this.Eccentricity=pEccentricity;
	}

/** 
 * This method sets the inclination after some other routine calculates it.
 * This method will force the inclination to a value between 0 and pi.
 * This orbit parameter is kept as a convenience only.  The parameter set used
 * by this class for orbit definition is {Angular Momentum, RungeLenz}.
 */
    protected void  setInclination(double pInclination) {
	if(pInclination<0.0) pInclination=Math.abs(pInclination);
	while (pInclination>=2.0*Math.PI){
		pInclination=pInclination-2.0*Math.PI;
		}
	if(pInclination>Math.PI) pInclination=Math.PI-pInclination;
	this.Inclination=pInclination;
	}

/** 
 * This method sets  the longitude of the right ascension node after some other 
 * routine calculates it.  This method will force the inclination to a value
 * between 0 and pi.
 * This orbit parameter is kept as a convenience only.  The parameter set used
 * by this class for orbit definition is {Angular Momentum, RungeLenz}.
 */
    protected void  setRAN(double pRAN) {
	while (pRAN<0.0){
		pRAN=pRAN+2.0*Math.PI;
		}
	while (pRAN>2.0*Math.PI){
		pRAN=pRAN-2.0*Math.PI;
		}
	this.RAN=pRAN;
	}

/** 
 * This method sets the value of the longitude of the periapsis angle along the
 * orbit after some other routine calculates it.
 * This method will force the inclination to a value between 0 and 2*pi.
 * This orbit parameter is kept as a convenience only.  The parameter set used
 * by this class for orbit definition is {Angular Momentum, RungeLenz}.
 */
    protected void  setPeriAngle(double pPeriAngle) {
	while (pPeriAngle<0.0){
		pPeriAngle=pPeriAngle+2.0*Math.PI;
		}
	while (pPeriAngle>2.0*Math.PI){
		pPeriAngle=pPeriAngle-2.0*Math.PI;
		}
	this.PeriAngle=pPeriAngle;
	}

/** 
 * This method sets the true anomoly (angle) along the orbit after some other 
 * routine calculates it.  This method will force the inclination to a value
 * between 0 and 2*pi.
 */
    protected void  setTrueAnomoly(double pTrueAnomoly) {
	while (pTrueAnomoly<0.0){
		pTrueAnomoly=pTrueAnomoly+2.0*Math.PI;
		}
	while (pTrueAnomoly>2.0*Math.PI){
		pTrueAnomoly=pTrueAnomoly-2.0*Math.PI;
		}
	this.TrueAnomoly=pTrueAnomoly;
	}

/** 
 * This method sets the monads that represent the frame of the orbit.
 * EQUATOR, ARIES, and GEMINI are usually set to the ecliptic, first point of
 * Aries, and the summer solstice location respectively for solar orbits.
 * The concepts generalize for other frames.
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
		System.out.println("Failed to set initial frame.");
		System.out.println(e);
		System.exit(0);
		}      //impossible exception should stop application.
	}

/** 
 * This method sets the derived values and monads that represent the the often
 * calculated objects of the orbit.  The method sets the Latus Rectum, Nodeline,
 * SMA, Eccentricity, Inclination, RAN, PeriAngle, and TrueAnomoly by 
 * calculating them first and then setting the data members.  The list is
 * figured in the following order: Latus Rectum, NodeLine, Eccentricity,
 * Inclination, RAN, PeriAngle, TrueAnomoly, SMA
 */
    protected void  setDerivedValues(){
	//System.out.println("Setting the derived values");
	try {
		this.calculateLatusRectum();
		this.calculateNodeLine();
		this.calculateNodePlus90();
		}
	catch (CladosException e) {
		//fail on clados errors since none should occur
		System.out.println(e);
		System.out.println(e.getSourceMessage());
		System.out.println("LatusRectum, NodeLine, NodePlus90 error.");
		System.exit(-1);
		}
	try {
		this.calculateEccentricity();
		this.calculateInclination();
		this.calculateRAN();
		this.calculatePeriapsis();
		this.calculateTrueAnomaly();
		this.calculateSMA();
		}
	catch (CladosException e) {
		//fail on clados errors since none should occur
		System.out.println(e);
		System.out.println(e.getSourceMessage());
		System.out.println(
			"Ecc, Inc, RAN, Periapsis, True Anomoly or SMA error.");
		System.exit(-1);
		}
	}

//The Internal Calculating Methods
/** 
 * This method calculates the LatusRectum orbit parameter.
 */
    protected	void	calculateLatusRectum() 
	throws CladosException {

	try {
		this.LatusRectum=new Monad("Latus Rectum", this.getL());
		this.LatusRectum.Dot(this.getRungeLenz());
		this.LatusRectum.Normalize();
		this.LatusRectum.Scale(-1.0);
			//vector now point to 90 deg TrAnom
		this.LatusRectum.GradePart(1);
		//this.LatusRectum.Scale(getLatusRectumD());
		}
	catch (CladosException e) {
		System.out.println("Calculation of LatusRectum failed.");
		System.exit(0);
		}
	}
/** 
 * This method calculates the NodeLine orbit parameter.
 */
    protected	void	calculateNodeLine() 
	throws CladosException {

	try {
		this.NodeLine=new Monad("Line Of Nodes", this.getL());
		this.NodeLine.Normalize();
		this.NodeLine.Wedge(this.EQUATOR);  // A bivector
		this.NodeLine.LocalDual();
			//A vector pointing at the Ascending Node:
			//NodeLine set
		if (NodeLine.MagnitudeOf()!=0.0) {
			NodeLine.Normalize();
			}
		this.NodeLine.GradePart(1);
		}
	catch (CladosException e) {
		System.out.println("Calculation of NodeLine failed.");
		System.exit(0);
		}
	}
/** 
 * This method calculates the NodePlus90 orbit parameter.
 */
    protected	void	calculateNodePlus90() 
	throws CladosException {
	try {
		this.NodePlus90=new Monad("Node plus 90", this.getL());
		this.NodePlus90.RightMultiply(this.NodeLine);
			//NodePlus90 set
		if (NodePlus90.MagnitudeOf()!=0.0) {
			this.NodePlus90.Normalize();
			}
		this.NodePlus90.GradePart(1);
		}
	catch (CladosException e) {
		System.out.println("Calculation of NodePlus90 failed.");
		System.exit(0);
		}
	}
/** 
 * This method calculates the Eccentricity orbit parameter.
 * This orbit parameter is kept as a convenience only.  The parameter set used
 * by this class for orbit definition is {Angular Momentum, RungeLenz}.
 */
    protected	void	calculateEccentricity() 
	throws CladosException {

	this.decc=this.Eccentricity*-1.0;
	Monad tempE=new Monad(this.getRungeLenz());
	tempE.LeftMultiply(this.getRungeLenz());
	this.Eccentricity=Math.sqrt(tempE.GradeProject(0)[0]);    //Ecc set
	this.decc=this.decc+this.Eccentricity;
	tempE=null;
	}
/** 
 * This method calculates the Inclination orbit parameter.
 * This orbit parameter is kept as a convenience only.  The parameter set used
 * by this class for orbit definition is {Angular Momentum, RungeLenz}.
 */
    protected	void	calculateInclination() 
	throws CladosException {

	this.dinc=this.Inclination*-1.0;
	Monad tempL=new Monad("OrbitNorth", this.L);
	Monad tempEQ=new Monad("Celestial North", this.EQUATOR);

	tempL.Normalize();
	tempL.LocalDual();
	tempEQ.LocalDual();
	tempL.Dot(tempEQ);              //A scalar
	this.Inclination=Math.acos(tempL.GradeProject(0)[0]);    //Inc set
	this.dinc=this.dinc+this.Inclination;
	tempL=null;
	tempEQ=null;
	}
/** 
 * This method calculates the RAN orbit parameter.
 * This orbit parameter is kept as a convenience only.  The parameter set used
 * by this class for orbit definition is {Angular Momentum, RungeLenz}.
 */
    protected	void	calculateRAN() 
	throws CladosException {

	this.dnod=this.RAN*-1.0;
	Monad tempN1=new Monad(this.NodeLine);
	Monad tempN2=new Monad(this.NodeLine);

	tempN1.Dot(this.ARIES);
	tempN2.Dot(this.GEMINI);
	double cosRAN=tempN1.GradeProject(0)[0];
	double sinRAN=tempN2.GradeProject(0)[0];
	if (sinRAN>=0.0) {
		this.RAN=Math.acos(cosRAN);
		}
	else {
		this.RAN=2.0*Math.PI-Math.acos(cosRAN);      //RAN set
		}
	this.dnod=this.dnod+this.RAN;
	tempN1=null;
	tempN2=null;
	}
/** 
 * This method calculates the periapsis orbit parameter.
 * This orbit parameter is kept as a convenience only.  The parameter set used
 * by this class for orbit definition is {Angular Momentum, RungeLenz}.
 */
    protected	void	calculatePeriapsis() 
	throws CladosException {

	this.dper=this.PeriAngle*-1.0;
	Monad tempR1=new Monad(this.RungeLenz);
	Monad tempR2=new Monad(this.RungeLenz);

	tempR1.Normalize();
	tempR2.Normalize();
	tempR1.Dot(this.NodeLine);
	tempR2.Dot(this.NodePlus90);
	double cosPeriAngle=tempR1.GradeProject(0)[0];
	double sinPeriAngle=tempR2.GradeProject(0)[0];
	if (sinPeriAngle>=0.0) {
		this.PeriAngle=Math.acos(cosPeriAngle);
		}
	else {
		PeriAngle=2.0*Math.PI-Math.acos(cosPeriAngle);//PeriAngle set
		}
	this.dper=this.dper+this.PeriAngle;
	tempR1=null;
	tempR2=null;
	}
/** 
 * This method calculates the True Anomaly orbit parameter.
 */
    protected	void	calculateTrueAnomaly()
	throws CladosException {

	this.danm=this.TrueAnomoly*-1.0;
	Monad tempR3=new Monad(this.getRHat());
	Monad tempR4=new Monad(this.getRHat());
	Monad tempR5=new Monad(this.getRungeLenz());
	//Monad tempR6=new Monad(this.LatusRectum);

	if (tempR5.MagnitudeOf()!=1.0) {
		tempR5.Normalize();
		}
	//if (tempR6.MagnitudeOf()!=1.0) {
	//	tempR6.Normalize();
	//	}
	
	tempR3.Dot(tempR5);
	tempR4.Dot(this.LatusRectum);
	//tempR4.Dot(tempR6);
	double cosTrueAnomoly=tempR3.GradeProject(0)[0];
	double sinTrueAnomoly=tempR4.GradeProject(0)[0];
	if (sinTrueAnomoly>=0.0) {
		this.setTrueAnomoly(Math.acos(cosTrueAnomoly));
		}
	else {
		this.setTrueAnomoly(2.0*Math.PI-Math.acos(cosTrueAnomoly));
			//True Anomoly set
		}
	this.danm=this.danm+this.TrueAnomoly;
	tempR3=null;
	tempR4=null;
	tempR5=null;
	}
/** 
 * This method calculates the semi-major axis orbit parameter.
 * This orbit parameter is kept as a convenience only.  The parameter set used
 * by this class for orbit definition is {Angular Momentum, RungeLenz}.
 */
    protected	void	calculateSMA() 
	throws CladosException {

	this.dsma=this.SemiMajorAxis*-1.0; //dsma has the initial sma now.
	Monad tempL4=new Monad(this.getL());
	tempL4.LeftMultiply(this.getL());
	tempL4.Scale(-1.0/(this.getMicro()*(1.0-Math.pow(Eccentricity,2))));
	tempL4.Scale(1.0/(Math.pow(ParentSail.SailMass,2.0)));
	this.SemiMajorAxis=tempL4.GradeProject(0)[0];      //SMA set
	this.dsma=this.dsma+this.SemiMajorAxis; //dsma now has final-initial sma
	tempL4=null;
	}
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
/** 
 * This method calculates the Monad for the Angular Momentum 'bivector'.
 */
    private	Monad	calculateAngularMomentum() 
	throws CladosException {

	Monad tempL=null;
	try {
		tempL=new Monad("L Of:"+this.getOrbitName(),
				this.ParentSail.getPosition());
		tempL.Wedge(this.ParentSail.getVelocity());
			//tempL is now complete
		tempL.Scale(new Double(
		  ParentSail.SailProps.getProperty("SailMass")).doubleValue());
		tempL.GradePart(2);
		}
	catch (CladosException e) {
		System.out.println("Calculation of Angular Momentum failed.");
		System.exit(0);
		}
	return tempL;
    }
/** 
 * This method calculates the Monad for the Angular Momentum 'bivector'.
 * The method expects to havethe position and velocity monads passed in.
 */
    private	Monad	calculateAngularMomentum(Monad pPosition,
						Monad pVelocity) 
	throws CladosException {

	Monad tempL=null;
	try {
		tempL=new Monad("Specific L: "+this.getOrbitName(), pPosition);
		tempL.Wedge(pVelocity);    //tempL is now complete
		tempL.GradePart(2);
		}
	catch (CladosException e) {
		System.out.println("Calculation of Angular Momentum failed.");
		System.exit(0);
		}

	return tempL;
    }
/** 
 * This method calculates the Monad for the Runge-Lenz 'vector'.
 * The settor checks for validity by seeing if it lies in the L plane later.
 */
    private	Monad	calculateRungeLenz() 
	throws CladosException {
	if (DEBUG) System.out.println("About to calculate RL");
	Monad tempRL=null;
	try {
		tempRL=new Monad("RungeLenz Of:"+getOrbitName(), this.L);
		tempRL.Dot(ParentSail.getVelocity());
				//leaves part of L perpendicular to V
		tempRL.Scale(1.0/this.Micro);
		tempRL.Scale(1.0/(new Double(
		  ParentSail.SailProps.getProperty("SailMass")).doubleValue()));
		tempRL.Subtract(this.RHat);
				//tempRL is now the Eccentricity vector.	
		tempRL.GradePart(1);
		if (DEBUG) System.out.println("Done calculating RL");
		}
	catch (CladosException e) {
		System.out.println("Calculation of RungeLenz failed.");
		System.exit(0);
		}

	return tempRL;
    }
/** 
 * This method calculates the Monad for the Runge-Lenz 'vector'.
 * It expects to be passed the Velocity monad.
 * The settor checks for validity by seeing if it lies in the L plane later.
 */
    private	Monad	calculateRungeLenz(Monad pVelocity) 
	throws CladosException {

	Monad tempRL=null;
	try {
		tempRL=new Monad("RungeLenz Of:"+getOrbitName(), this.L);
		tempRL.Dot(pVelocity);    //leaves part of L perpendicular to V
		tempRL.Scale(1.0/this.Micro);
			//No scaling by the sail mass can be done here.
			//The L is probably a specific L anyway...
		tempRL.Subtract(this.RHat);
			//tempRL is now the Eccentricity vector.
		tempRL.GradePart(1);
		}
	catch (CladosException e) {
		System.out.println("Calculation of RungeLenz failed.");
		System.exit(0);
		}
	return tempRL;
    }

//The State Checking Methods

/**
 * This method returns a boolean telling whether the inclination of the orbit
 * is low relative to the refrence equator.  In later versions, it will figure
 * the boolean without relying on the conveniet Inclination value or force
 * its evaluation before the checking is done.
 */
    public	boolean		isLowInclination(double pAllowance) {
	if (Inclination <= pAllowance || Inclination >= Math.PI-pAllowance) {
		return true;
		}
	else {
		return false;
		}
	}//end of isLowInclination method

/**
 * This method returns a boolean telling whether the eccentricity of the orbit
 * is low.  Low eccentricities lead to inefficiencies in the perturbation 
 * technique.  In later versions, it will figure the boolean without relying on
 * the convenient Eccentricity value or force its evaluation before the checking
 * is done.
 */
    public	boolean		isLowEccentricity(double pAllowance) {
	if (this.Eccentricity <= pAllowance) {
		return true;
		}
	else {
		return false;
		}
	}

/**
 * This method returns a boolean telling whether the eccentricity of the orbit
 * is high.  High eccentricities lead to violations of the perturbation
 * technique.  In later versions, it will figure the boolean without relying on
 * the convenient Eccentricity value or force its evaluation before the checking
 * is done.
 */
    public	boolean		isHighEccentricity(double pAllowance) {
	if (this.Eccentricity >= 1.0-pAllowance) {
		return true;
		}
	else {
		return false;
		}
	}

/**
 * This method returns a boolean telling whether the two orbits are identical
 * in details.  This is useful to detect perturbation inefficiencies.
 */
    public	boolean		isEqual(OrbitEllipse pOrb) {
	//Check the individual parts of the Orbit to see if they are identical.
	//The frames must also match, so checks should be made against Monads.
	if (this.getMicro() != pOrb.getMicro()) return false;
	//if (this.getRungeLenz() != pOrb.getRungeLenz()) return false;
	//if (this.getL() != pOrb.getL()) return false;
	//These two won't work.  The actual details of the monads must be 
	//checked by using the Monad's 'equal' method which isn't done yet.
	return true;
	}

//The complicated operations

/** 
 * This method calculates the next position for the object on the orbit and
 * other related information.  This method is the heart of the Kepler approach
 * for projecting an object along an orbit.
 */
    public	void		iteratePath(	double pDPhase, 
						double pLAllow,
						double pRLAllow) 
				throws 	PerturbationAssumptionException, 
					BadSailDefinitionException, 
					BadAngularMomentumException, 
					BadRungeLenzException, 
					BadAttitudeException, 
					BadPositionException, 
					BadVelocityTryException {

	//It may be assumed that the orbit constants have been set before the
	//application calls this method.  Pass initial step information to the
	//Perturber whether perturbations are on or not.  The Perturber is the
	//real owner of the phase step.
	//The Perturber is responsible for determining whether a step is too 
	//large and produces an unacceptable change to L and RL.  This is the
	//main reason the Perturber is used even when the individual perturbing
	//forces are turned off.

	if (pDPhase!=0.0) {
		double tempPhaseStep=Perturb.getDPhase();
		if (tempPhaseStep==0.0) {
			tempPhaseStep=0.01;
			}//This takes care of initial conditions.
		
		if (Math.abs(tempPhaseStep)-Math.abs(pDPhase)>0.0) {
			Perturb.setDPhase(pDPhase);
			}
		else {
			if (pDPhase*tempPhaseStep<0.0) {
				tempPhaseStep*=-1.0;
				Perturb.setDPhase(tempPhaseStep);
				}
			//no else is needed here since we can leave it alone.
			}
		ConvertPhaseToTime(Perturb.getDPhase());
		}
	else {
		double tempPhaseStep=Perturb.getDPhase();
		if (tempPhaseStep==0.0) {
			Perturb.setDPhase(0.01);
			ConvertPhaseToTime(Perturb.getDPhase());
			}
		//no else is neede here since we can leave it alone.
		}


	//Check that ecc and inc aren't too small or too big for perturbation.
	//If they are, turn off perturbation check and throw an exception.
	if (this.isLowInclination(Math.PI*1.0/180.0)) {
		PerturbationON=false;
		throw new PerturbationAssumptionException(
			"Inclination too low for tech to work well.");
		}
	if (this.isLowEccentricity(0.01)) {
		PerturbationON=false;
		throw new PerturbationAssumptionException(
			"Eccentricity too low for tech to work well.");
		}
	if (this.isHighEccentricity(0.01)) {
		PerturbationON=false;
		throw new PerturbationAssumptionException(
			"Eccentricity too high for tech to work well.");
		}

	//Figure the changes to the orbit parameters over phase slice by calling
	//on the Perturber to calculate them.  Actual placement of the Sail on
	//the Orbit is handled by the Orbit since no parameter changes are
	//occuring at that stage.
	if (DEBUG) {
		System.out.println("Calling Perturber's calculator");
		}
	this.Perturb.calculateChanges(pLAllow, pRLAllow);

	//This is where Sail's attitude change can be calculated when torques on
	//the sail are handled.
	//This is done whether perturbations are ON or not.
	;

	//Locate the sail on current orbit at later phase using Phasestep 
	//used by Perturber.  The L and RL to be used should be the ones
	//at the beginning of the step.
	//If newPosition and newVelocity are figured using the old L and 
	//RL, wouldn't it make sense they will not lie on the L plane after
	//the iteration is done?  They will lie upon the old L plane!
	//Lets try it the other way around for a little while.
//----------------------------------

	if (PerturbationON) {

	//Use Perturber's new orbit constants and parameters to replace old
	//ones in OrbitEllipse.
	//Call Perturber gettors and overwrite OrbitEllipse values with the
	//resultants.
	//Waiting this long to copy newL and newRL to the orbit means we have 
	//propagated Position and velocity along the old L and RL parameter set.

		this.setL(new Monad(Perturb.getnewL()));
		if (DEBUG2) {
			System.out.println("Orbit L := Perturb newL");
			System.out.println("L^2 is now: "
					+this.L.SQMagnitudeOf());
			}

		this.setRungeLenz(new Monad(Perturb.getnewRL()));
		if (DEBUG2) {
			System.out.println("Orbit RL := Perturb newRL");
			System.out.println("RL^2 is now: "
					+this.RungeLenz.SQMagnitudeOf());
	//Both orbit parameters are reset now.  Perturbation affects on the 
	//orbit are complete and present within the Orbit Object.
			}
		}

//----------------------------------

  	try {
		this.predictPosition();
		if (newPosition != null) {
			if (newPosition.isGrade()==1) {
				this.predictVelocity();
				if (newVelocity != null) {
					if (newVelocity.isGrade()==1) {
					    ParentSail.setVelocity(newVelocity);
					    ParentSail.setPosition(newPosition);
					    newVelocity=null;
					    newPosition=null;
					    }
					else {
					    System.out.println(
				"predictVelocity returned a nongrade=1 monad.");
					    System.exit(0);
						}
					}
				else {
				    //predictVelocity failed to return an object
				    System.out.println(
				"predictVelocity returned a null reference.");
				    System.exit(0);
					}
				}
			else {//newPosition failed to be of grade 1
				System.out.println(
				"predictPosition returned a nongrade=1 monad.");
				System.exit(0);
				}
			}
		else {//predictPosition failed to return an object.
			System.out.println(
				"predictPosition returned a null reference.");
			System.exit(0);
			}
		ParentSail.setDerivedValues();
		}
	catch (NoDefinedGradeException e) {
		System.out.println(e.getSourceMonad().getMonadName()+
				" is the Monad that failed grade check.");
		System.exit(0);
		}
//----------------------------------
/*
	if (PerturbationON) {

	//Use Perturber's new orbit constants and parameters to replace old
	//ones in OrbitEllipse.
	//Call Perturber gettors and overwrite OrbitEllipse values with the
	//resultants.
	//Waiting this long to copy newL and newRL to the orbit means we have 
	//propagated Position and velocity along the old L and RL parameter set.

		this.setL(new Monad(Perturb.getnewL()));
		if (DEBUG2) {
			System.out.println("Orbit L := Perturb newL");
			System.out.println("L^2 is now: "
					+this.L.SQMagnitudeOf());
			}

		this.setRungeLenz(new Monad(Perturb.getnewRL()));
		if (DEBUG2) {
			System.out.println("Orbit RL := Perturb newRL");
			System.out.println("RL^2 is now: "
					+this.RungeLenz.SQMagnitudeOf());
	//Both orbit parameters are reset now.  Perturbation affects on the 
	//orbit are complete and present within the Orbit Object.
			}
		}
 */ 
//----------------------------------
	//Send TimeStep to the Sail's Mission Clock adder.  The TimeStep was
	//already figured at the start of this method and refigured  by the
	//Perturber if perturbations are on.
	ParentSail.addtoMissionClock(TimeStep);

	//Reset the important Derived members for use by the sail and befor
	//the next iteration.
	this.recalculateDerivedMembers();

	//We are done with this acceptable phase step.  Time to return.
	return;
  
	//At the end of a phase interval, the sail will be where it would have
	//been using the old orbit constants, but it will have the new orbit
	//constants.
	}

/** 
 * This method forces the recalculation of the Derived Members of the Orbit.
 * Future versions of it will check to see if it is worth recalculating all
 * members or just some of them.
 * The current version just calls setDerivedValues().
 */
    public void    recalculateDerivedMembers() {
	this.setDerivedValues();
	}

/** 
 * This method returns a Monad that is projected into the plane of the user's
 * choice.  The first monad argument is the projection plane.  The second monad
 * argument is the monad to be projected.  The returned Monad is the result of
 * the operation.
 */
    private	Monad		projectDirectionIntoPlane(Monad pP, Monad pM) {

	Monad returnMonad=new Monad(pM);
	Monad tempMonad=new Monad(pM);
	Monad tempL9=new Monad(pP);

	try {
		//Make a mirror image of pM in the pP plane.
		tempMonad.RightMultiply(tempL9);
		tempMonad.LeftMultiply(tempL9);
		tempMonad.Scale(-1.0);

		//Average the mirror image and pM together to get projection.
		returnMonad.Add(tempMonad);
		returnMonad.Scale(0.5);

		}
	catch (CladosException e) {
		System.out.println("Clados error while projecting.");
		System.exit(1);
		}

	//If the scaling was done right, returnMonad is in the pP plane.
	//If the scaling was done wrong, returnMonad is exactly out of pP plane.

	return returnMonad;
    }

/** 
 * This method returns a boolean that informs us whether the a monad lies on the
 * orbit plane or not.  pM is in the orbit plane if the result of wedging pM 
 * with L is strictly a vector.
 * It is possible for the orbit plane represented by L and the monad to be 
 * checked to have radically different magnitudes.  Since there is some loss of
 * precision at the end of the double primitive, it isn't really fair to check
 * two monads with noticeably different magnitudes.  To be fair, the monads
 * are tested in a normalized state against a normalized L.
 */
    private	boolean		isDirectionInPlane(Monad pM) {

	boolean returnflag=false;

	Monad tempL9=new Monad(this.L);
	Monad tempM=new Monad(pM);

	try {
		tempM.Normalize();
		tempL9.Normalize();
		double LMag=tempL9.MagnitudeOf();

		if (tempM.isGrade()==1) {
			tempL9.Wedge(tempM);
			double WMag=tempL9.MagnitudeOf();

	//We may need to tolerate some non-vector stuff.  The ratio of WMag to
	//LMag gives a level of variation.  High precision in the initial 
	//position and velocity should lead us to expect high precision in the 
	//orbit check.  This expectation can only go so far, though, when the 
	//double primitive can hold a limited number of digits.
	//The level of tolerance coded in the conditional above reflects the 
	//precision expectation of this simulation.  It should not be hard-coded
	//though.  We should get it from Sail.profile.

			if (Math.abs(WMag/LMag)>=0.000000000000001) {
				if (DEBUG2) {
//----------------------------------
	System.out.println(" ");
	System.out.println(
		"*****>Monad being checked failed test: "+tempM.getMonadName());
	System.out.println("*****>Tolerance ratio: "+WMag/LMag);
//----------------------------------
					}

				if (DEBUG3) {	
//----------------------------------
	double tcc[]=tempL9.getCoeff();
	double tc1[]=tempM.getCoeff();
	System.out.println(
		"Monad Names:\t"+"Wedge product\t"+tempM.getMonadName());
	System.out.println("Scalar:\t\t"+tcc[1]+"\t\t"+tc1[1]);
	System.out.println("Vector:\t\t"+tcc[2]+"\t\t"+tc1[2]);
	System.out.println("Vector:\t\t"+tcc[3]+"\t\t"+tc1[3]);
	System.out.println("Vector:\t\t"+tcc[4]+"\t\t"+tc1[4]);
	System.out.println("Bivector:\t"+tcc[5]+"\t\t"+tc1[5]);
	System.out.println("Bivector:\t"+tcc[6]+"\t\t"+tc1[6]);
	System.out.println("Bivector:\t"+tcc[7]+"\t\t"+tc1[7]);
	System.out.println("PScalar:\t"+tcc[8]+"\t\t"+tc1[8]);
//----------------------------------
					}
				returnflag=false;
				}//end of MagnitudeOf if
			else {
				if (DEBUG2) {	
//----------------------------------
	System.out.println(" ");
	System.out.println(
		"     >Monad being checked passed test: "+tempM.getMonadName());
	System.out.println("     >Tolerance ratio: "+WMag/LMag);
//----------------------------------
					}

				returnflag=true;
				}
			}//end of isGrade if
		else {
			System.out.println(
			  "Monad checked for 'in-plane' needs to be a vector.");
			System.out.println(
			  tempM.getMonadName()+" is the Monad that failed.");
			returnflag=false;
			}
		}//end of try
	catch (NoDefinedGradeException e) {
		System.out.println(
			"Monad checked for 'in-plane' can't be multigrade.");
		System.out.println(
			e.getSourceMonad().getMonadName()+" is failed Monad.");
		returnflag=false;
		}//end of catch
	catch (CladosException e) {
		System.out.println(
			"Clados problem while checking 'in-plane' status.");
		returnflag=false;
		}//end of catch
	finally {
		tempL9=null;
		}
	return returnflag;
    }//end of isDirectionInPlane method
/** 
 * This method returns a Monad that represents the next unit position 'vector'
 * for the object on the orbit.
 */
    public	void		predictPosition() 
				throws 	BadPositionException { 

	this.setnewRHat();		//Starting place for newRHat
	this.Rot=new Monad("Rotation in orbit plane", this.getL());
	Monad returnMonad=null;

	try {
		this.Rot.Normalize();
		double tempDPhase=Perturb.getDPhase();
		this.newRHat.Rotate("active", this.Rot, tempDPhase);
		this.newRHat.setMonadName("RHat post-iteration");
		this.newRHat.GradePart(1);

		returnMonad = new Monad(this.newRHat);
		double tempD=this.getLatusRectumD()/this.getLatusScaling();
		returnMonad.Scale(tempD);
		returnMonad.setMonadName("Position post-iteration");
		returnMonad.GradePart(1);

		//Set of RHat now checks whether RHat is in plane.
		//The check does not need to be done here.
		this.setRHat(this.newRHat);

		//Set of newPosition now checks whether newPosition is in plane.
		//The check does not need to be done here.
		this.setnewPosition(returnMonad);

		returnMonad=null;
		this.Rot=null;
		}
	catch (BladeOutOfRangeException e) {
		System.out.println(e.getSourceMessage());
		throw new BadPositionException(this, 
			"NewRHat or newPosition had a grade out of range error",
			this.newRHat);
		}	//No clados errors should occur. 
			//Orbit errors are passed to caller.
	catch (CladosException e) {
		System.out.println(e.getSourceMessage());
		throw new BadPositionException(this, 
			"NewRHat or newPosition had a clados error", 
			this.newRHat);
		}	//No clados errors should occur.
			//Orbit errors are passed to caller.
	return;
	}

/** 
 * This method returns a Monad that represents the next velocity 'vector' for
 * the object on the orbit.
 */
    public	void		predictVelocity() 
				throws 	BadPositionException, 
					BadVelocityTryException { 

	//This function will be used to feed the GUI method to draw the
	//hodograph later.
 	Monad tempVelocity=null;
 
	Monad tempL8=new Monad("newVelocity", this.L);;
	Monad tempNPos=new Monad(this.newPosition);
	try {

		//This method for figuring Velocity uses the relation between
		//Angular Momentum, Position, and Velocity.
		tempNPos.Inverse();
		tempL8.LeftMultiply(tempNPos);
		tempL8.Scale(1.0/(new Double(ParentSail.SailProps.getProperty(
				"SailMass")).doubleValue()));
		tempL8.setMonadName("New Velocity at end of step");
		tempL8.GradePart(1);

//----------------------------------
/*
		//This method for figuring Velocity uses a relation between
		//Angular Momentum, RHat, RungeLenz, and Velocity
		tempL8.Scale(1.0/this.Micro);
		tempL8.Scale(1.0/(new Double(ParentSail.SailProps.getProperty(
				"SailMass")).doubleValue()));
		tempL8.Inverse();
		Monad tempLI2=new Monad(tempL8);
		tempL8.RightMultiply(this.getRHat());
		tempLI2.RightMultiply(this.getRungeLenz());
		tempL8.Add(tempLI2);
		tempL8.GradePart(1);
		tempLI2=null;
 */
//----------------------------------
		tempVelocity=tempL8;
		this.setnewVelocity(tempVelocity);
		tempL8=null;
		tempNPos=null;
		}
	catch(CladosException e) {
		System.out.println(e.getSourceMessage());
		tempL8=null;
		tempNPos=null;
		throw new BadVelocityTryException(this.getL(), 
				"New Velocity try: "+e.getSourceMessage(), 
				this.newPosition);
		}
	finally {
		tempL8=null;
		tempNPos=null;
		}
	return;

	}//end of predictVelocity method

/** 
 * This method returns a value for the true anomoly increment from the time
 * increment in an interation step.
 * This calculation requires Kepler's method and is not ready yet.
 */
    public double   ConvertTimeToPhase(double pTime, double pErrorAllowance) {
	//This section solves Kepler's problem.  Known delta-t implies a delta 
	//true anomoly.
	//This problem involves a transendental equation, so a numerical process
	//must approximate 
	//the solution.
	return 0.0;
        }

/** 
 * This method returns a value for the time increment from the true anomoly
 * increment in an interation step.  This method is needed to keep up the 
 * Mission Clock and to help a Perturber determine the affect of various
 * perturbing forces on the orbit parameters.
 */
    public void  ConvertPhaseToTime(double pDPhase) { 

	double NewTrueAnomoly=TrueAnomoly+pDPhase;
	double ERatio1=Math.sqrt((1-Eccentricity)/(1+Eccentricity));
	double ERatio2=0.5*Eccentricity*Math.sqrt(1-Eccentricity*Eccentricity);
	double CTerm1=Math.atan(ERatio1*Math.tan(this.TrueAnomoly/2.0));
	double CTerm2=ERatio2*Math.sin(TrueAnomoly)/
			(1+Eccentricity*Math.cos(TrueAnomoly));
	double FTerm1=Math.atan(ERatio1*Math.tan(NewTrueAnomoly/2.0));
	double FTerm2=ERatio2*Math.sin(NewTrueAnomoly)/
			(1+Eccentricity*Math.cos(NewTrueAnomoly));
	if (DEBUG3) {
		System.out.println("ERatio1: "+ERatio1+"\tERatio2: "+ERatio2);
		System.out.println("FTerm1: "+FTerm1+"\tCTerm1: "+CTerm1);
		System.out.println("FTerm2: "+FTerm2+"\tCTerm2: "+CTerm2);
		}

	TimeStep=((FTerm1-FTerm2)-(CTerm1-CTerm2))/(0.5*this.getAveAngSpeed());

	if (DEBUG2) {
		System.out.println(" ");
		System.out.println("DPhase:"+pDPhase+"\t=>TimeStep:"+TimeStep);
		System.out.println("=>Implied Orbit Period:"+TimeStep/pDPhase);
		}
	return;
	}

/** 
 * This method transforms the orbit reference frame using the passed Monad to
 * transform the old frame.
 * This method is not ready yet.
 * It will be needed to cope with low inclination cases.
 */
    public void    TransformOrbitReference(Monad pReference) {
	//Redefine ARIES & GEMINI and recalcuate EQUATOR.  
	//Then call setDerivedValues to redefine all related parameters.
	//Only allow this function if new ARIES & GEMINI have matching reference
	//frames and feet.
	//Make sure to rename orbit references.
	;
	}

    }//End of OrbitEllipse class
//</pre>
