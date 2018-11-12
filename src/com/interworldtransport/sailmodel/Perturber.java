/*<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailmodel.Perturber----------------------------------
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
---com.interworldtransport.sailmodel.Perturber----------------------------------
 */

package com.interworldtransport.sailmodel;
import com.interworldtransport.clados.*;

/**
 * The class for handling perturbations on elliptical orbits.
 * <p>
 * This is the general orbit perturbation handling class.  all orbit objects own
 * a Perturber if they are to handle any physics more complicated than two-body
 * gravitation.  The Perturber handles its calculators and converts the
 * accelerations into orbit parameter changes.  The calculators are other
 * objects withing the physical model responsible for generating acceleration
 * monads for various types of perturbations.
 * <p>
 * Proper use of this class is accomplished by the Orbit that constructs it.
 * Each of the items needed by the Perturber are acquired from the Orbit that
 * owns it.  This object never gets instantiated except from an Orbit.
 * <p>
 * Operation of the Perturber is accomplished when the Orbit uses its 
 * <code>calculateChanges</code> method.  This method causes a cascade of 
 * requests to the Perturber's calculators for acceleration monads.  These 
 * requests are totaled and used to figure and deliver new orbit parameters.
 * <p>
 * Defining a Perturber in a more general way to support a variety of
 * perturbation approaches may be done in the future.  If it is done, there
 * will likely be different versions of the Perturber and an Orbit would own one
 * of each.  Such and adaptation is not needed at present.
 * <p>
 * Future versions of the Perturber will support features from the following
 * list.
 * <ul>
 * <li>Internal torques
 * <li>Better validation of perturbation assumptions
 * <li>Delivery of perturbation accuracy information
 * <li>Perturbation techniques for unbounded orbits
 * </ul>
 * @version 0.21, $Date: 2001/11/03 08:43:10 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailmodel.OrbitEllipse
 * @see com.interworldtransport.sailmodel.Sail
 */
public class Perturber {
/**
 * This string is the name of the Perturber.
 */
    protected	String			PName;
/**
 * This double is value being used for orbit iterations.
 */
    protected	double			DPhase;
/**
 * This double is value being used for orbit iteration step tolerance for L.
 */
    protected	double			LAllowance;
/**
 * This double is value being used for orbit iteration step tolerance for RL.
 */
    protected	double			RLAllowance;
/**
 * This OrbitEllipse is the parent object of this Perturber.
 */
    protected	OrbitEllipse		ParentOrbit;
/**
 * This RadiationForceModel is one of the force calculators of this Perturber.
 */
    protected 	RadiationForceModel	RFModel;
/**
 * This HarmonicForceModel is one of the force calculators of this Perturber.
 */
    //protected	HarmonicForceModel	HFModel;
/**
 * This FluidDragForceModel is one of the force calculators of this Perturber.
 */
    //protected	FluidDragForceModel	FFModel;
/**
 * This NBodyGravForceModel is one of the force calculators of this Perturber.
 */
    //protected	NBodyGravForceModel	NFModel;
/**
 * This MagDragForceModel is one of the force calculators of this Perturber.
 */
    //protected	MagDragForceModel	MFModel;
/**
 * This ImpulisiveForceModel is one of the force calculators of this Perturber.
 */
    //protected	ImpulsiveForceModel	IFModel;
/**
 * This Monad is a copy of the Attitude of the Sail.  It isn't used much here,
 * but it will be needed when the Perturber takes on the task of calculating
 * torques.
 */
    protected	Monad			SailAtt;
/**
 * This Monad is the change of the Attitude of the Sail.  It isn't used much
 * here, but it will be needed when the Perturber takes on the task of
 * calculating torques.
 */
    protected	Monad			DSailAtt;
/**
 * This Monad is the new Attitude of the Sail.  It isn't used much here, but it
 * will be needed when the Perturber takes on the task of calculating torques.
 */
    protected	Monad			newSailAtt;
/**
 * This Monad is a copy of the Angluar Momentum of the Orbit.
 */
    protected	Monad			L;
/**
 * This Monad is the change of the Angluar Momentum of the Orbit.
 */
    protected	Monad			DL;
/**
 * This Monad is the new Angluar Momentum of the Orbit.
 */
    protected	Monad			newL;
/**
 * This Monad is a copy of the Runge-Lenz vector of the Orbit.
 */
    protected	Monad			RL;
/**
 * This Monad is the change to the Runge-Lenz vector of the Orbit.
 */
    protected	Monad			DRL;
/**
 * This Monad is the new Runge-Lenz vector of the Orbit.
 */
    protected	Monad			newRL;
/**
 * This Monad starts as a copy of the NodeLine monad of the Orbit.
 * It gets used to hold the new NodeLine at the end of a perturbation step.
 */
    protected	Monad			NodeLine;
/**
 * This Monad starts as a copy of the NodePlus90 monad of the Orbit.
 * It gets used to hold the new NodePlus90 at the end of a perturbation step.
 */
    protected	Monad			NodePlus90;
/**
 * This Monad starts as a copy of the LatusRectum monad of the Orbit.
 * It gets used to hold the new LatusRectum at the end of a perturbation step.
 */
    protected	Monad			LatusRectum;
/**
 * RefZero is a zero magnitude reference monad used as a convient place to point
 * calculators not being used or being reset between iteration steps.
 */
    private	Monad			RefZero;
/**
 * This double is the magnitude of the force monad component pointing along the
 * R axis.
 */
    public	double			R;
/**
 * This monad is the R-axis of the force component R.
 */
    public	Monad			Rref;
/**
 * This double is the magnitude of the force monad component pointing along the
 * S axis.
 */
    public	double			S;
/**
 * This monad is the S-axis of the force component S.
 */
    public	Monad			Sref;
/**
 * This double is the magnitude of the force monad component pointing along the
 * W axis.
 */
    public	double			W;
/**
 * This monad is the W-axis of the force component W.
 */
    public	Monad			Wref;
/**
 * This monad is the force contribution due to E & M Radiation.
 */
    public	Monad			RadForce;
/**
 * This monad is the force contribution due to non-spherical gravitation terms.
 */
    public	Monad			HarmForce;
/**
 * This monad is the force contribution due to multi-body gravitation terms.
 */
    public	Monad			NBodForce;
/**
 * This monad is the force contribution due to Fluid Drag terms.
 */
    public	Monad			FluidForce;
/**
 * This monad is the force contribution due to Magnetic Drag (Eddy) terms.
 */
    public	Monad			MagForce;
/**
 * This monad is the force contribution due to impulsive terms.
 */
    public	Monad			ImpForce;
/**
 * This monad is the rotation plane used to rotate monads around the L Plane.
 */
    protected	Monad			RotsL;
/**
 * This monad is the rotation plane used to rotate monads around the EQUATOR.
 */
    protected	Monad			RotsE;
/**
 * This monad is the rotation plane used to rotate monads around the NodeLine.
 */
    protected	Monad			RotsN;
/**
 * This monad is the sum of all the force contributions.
 */
    public	Monad			SumForce;
/**
 * This boolean holds information about whether the Perturber is to use any 
 * perturbing force while calculating perturbation affects.
 */
    public	boolean			GlobalPerturbON;
/**
 * This boolean holds information about whether the Radiation Force Perturbation
 * is to be considered while calculating perturbation forces.
 */
    public	boolean			RFPerturbON;	//value set by construct
/**
 * This boolean holds information about whether the Gravitation Harmonic Force
 * Perturbation is to be considered while calculating perturbation forces.
 */
    public	boolean			HFPerturbON;	//default set to false
/**
 * This boolean holds information about whether the Fluid Drag Force 
 * Perturbation is to be considered while calculating perturbation forces.
 */
    public	boolean			FFPerturbON;	//default set to false
/**
 * This boolean holds information about whether the Gravitational N-Body Force
 * Perturbation is to be considered while calculating perturbation forces.
 */
    public	boolean			NFPerturbON;	//default set to false
/**
 * This boolean holds information about whether the Magnetic Drag Force
 * Perturbation is to be considered while calculating perturbation forces.
 */
    public	boolean			MFPerturbON;	//default set to false
/**
 * This boolean holds information about whether the Impulsive Force
 * Perturbation is to be considered while calculating perturbation forces.
 */
    public	boolean			IFPerturbON;	//default set to false
// Level one is for tracking through the code.
    private	boolean			DEBUG=true;
// Level two is for seeing high level values of some variables.
    private	boolean			DEBUG2=true;
// Level three is for seeing the coefficients of monads.
    private	boolean			DEBUG3=false;

//The Constructors.
/**
 * The constructor for Perturber assumes an OrbitEllipse is calling it and
 * passing itself for reference to all needed information.  Since later Orbit 
 * objects may own more than one perturber to handle a variety of perturbation
 * techniques, the Perturber needs a distinct name to help with identification.
 */
    public	Perturber(	String pName, 
				OrbitEllipse pOrbit) 
			throws	BadAngularMomentumException,
				BadRungeLenzException,
				BadAttitudeException {

	this.PName = pName;
	this.ParentOrbit = pOrbit;
	this.RefZero = new Monad("Reference Zero", 
				pOrbit.getL().getFrameName(), 
				pOrbit.getL().getFootName(), 3, 0, "Zero");
	this.RadForce = new Monad("Radiation Pressure Force", this.RefZero);
	this.HarmForce = new Monad(this.RefZero);
	this.NBodForce = new Monad(this.RefZero);
	this.FluidForce = new Monad(this.RefZero);
	this.MagForce = new Monad(this.RefZero);
	this.ImpForce = new Monad(this.RefZero);
	this.SumForce = new Monad("Summed Perturbing Force", this.RefZero);
	this.setDPhase(0.01);
	this.setDerivedValues();

	IFPerturbON=new Boolean(
		this.ParentOrbit.ParentSail.SailProps.getProperty(
		"IFPerturbationON")).booleanValue();
	if (IFPerturbON) {
		//this.IFModel = new ImpulsiveForceModel(
		//"IF Model for: "+this.ParentOrbit.getOrbitName(), this);
		}

	MFPerturbON=new Boolean(
		this.ParentOrbit.ParentSail.SailProps.getProperty(
		"MFPerturbationON")).booleanValue();
	if (MFPerturbON) {
		//this.MFModel = new MagDragForceModel(
		//"MF Model for: "+this.ParentOrbit.getOrbitName(), this);
		}

	NFPerturbON=new Boolean(
		this.ParentOrbit.ParentSail.SailProps.getProperty(
		"NFPerturbationON")).booleanValue();
	if (NFPerturbON) {
		//this.NFModel = new NBodyGravForceModel(
		//"NF Model for: "+this.ParentOrbit.getOrbitName(), this);
		}

	FFPerturbON=new Boolean(
		this.ParentOrbit.ParentSail.SailProps.getProperty(
		"FFPerturbationON")).booleanValue();
	if (FFPerturbON) {
		//this.FFModel = new FluidDragForceModel(
		//"FF Model for: "+this.ParentOrbit.getOrbitName(), this);
		}

	HFPerturbON=new Boolean(
		this.ParentOrbit.ParentSail.SailProps.getProperty(
		"HFPerturbationON")).booleanValue();
	if (HFPerturbON) {
		//this.HFModel = new HarmonicForceModel(
		//"HF Model for: "+this.ParentOrbit.getOrbitName(), this);
		}

	RFPerturbON=new Boolean(
		this.ParentOrbit.ParentSail.SailProps.getProperty(
		"RFPerturbationON")).booleanValue();
	if (RFPerturbON) {
		//this.RFModel = new RadiationForceModel(
		//"RF Model for: "+this.ParentOrbit.getOrbitName(), this);
		this.RFModel =this.ParentOrbit.ParentSail.getRFModel();
		}

	}

//The Gettor Methods.
/**
 * Get the name of this Perturber.
 * @return String
 */
    public	String		getPerturberName(){
	return  this.PName;
	}
/**
 * Get the parent OrbitEllipse of this Perturber.
 * @return OrbitEllipse
 */
    public	OrbitEllipse		getParentOrbit(){
	return  this.ParentOrbit;
	}
/**
 * Get the child RadiationForceModel of this Perturber.
 * @return RadiationForceModel
 */
    public	RadiationForceModel	getRFModel(){
	return  this.RFModel;
	}
/**
 * Get the copy of the Sail's Attitude monad kept within this Perturber.
 * @return Monad
 */
    public	Monad		getSailAtt() {
	return this.SailAtt;
	}
/**
 * Get the copy of the new Sail's Attitude monad kept within this Perturber.
 * @return Monad
 */
    public	Monad		getnewSailAtt() {
	return this.newSailAtt;
	}
/**
 * Get the change of the Sail's Attitude monad kept within this Perturber.
 * @return Monad
 */
    public	Monad		getDSailAtt() {
	return this.DSailAtt;
	}
/**
 * Get the Radiation Pressure Force monad
 * @return Monad
 */
    public	Monad		getRadForce() {
	return this.RadForce;
	}
/**
 * Get the Gravity Harmonics Force monad
 * @return Monad
 */
    public	Monad		getHarmForce() {
	return this.HarmForce;
	}
/**
 * Get the Gravity N Body Force monad
 * @return Monad
 */
    public	Monad		getNBodForce() {
	return this.NBodForce;
	}
/**
 * Get the Fluid Drag Force monad
 * @return Monad
 */
    public	Monad		getFluidForce() {
	return this.FluidForce;
	}
/**
 * Get the Magnetic Drag Force monad
 * @return Monad
 */
    public	Monad		getMagForce() {
	return this.MagForce;
	}
/**
 * Get the Impulsive Force monad
 * @return Monad
 */
    public	Monad		getImpForce() {
	return this.ImpForce;
	}
/**
 * Get the Summed Force monad
 * @return Monad
 */
    public	Monad		getSumForce() {
	return this.SumForce;
	}
/**
 * Get the magnitude of the force monad component pointing along the R axis.
 * @return double
 */
    public	double		getR() {
	return this.R;
	}
/**
 * Get the magnitude of the force monad component pointing along the S axis.
 * @return double
 */
    public	double		getS() {
	return this.S;
	}
/**
 * Get the magnitude of the force monad component pointing along the W axis.
 * @return double
 */
    public	double		getW() {
	return this.W;
	}
/**
 * Get the copy of the angular momentum monad kept within this Perturber. 
 * @return Monad
 */
    public	Monad		getL() {
	return this.L;
	}
/**
 * Get the copy of the new angular momentum monad kept within this Perturber. 
 * @return Monad
 */
    public	Monad		getnewL() {
	return this.newL;
	}
/**
 * Get the change to the angular momentum monad kept within this Perturber. 
 * @return Monad
 */
    public	Monad		getDL() {
	return this.DL;
	}
/**
 * Get the copy of the runge-lenz monad kept within this Perturber. 
 * @return Monad
 */
    public	Monad		getRL() {
	return this.RL;
	}
/**
 * Get the copy of the new Runge-Lenz monad kept within this Perturber. 
 * @return Monad
 */
    public	Monad		getnewRL() {
	return this.newRL;
	}
/**
 * Get the change to the runge-lenz monad kept within this Perturber. 
 * @return Monad
 */
    public	Monad		getDRL() {
	return this.DRL;
	}
/**
 * Get the value of the orbit step used to increment true anomaly within i
 * this Perturber. 
 * @return double
 */
    public	double		getDPhase(){
	return this.DPhase;
	}


//The Settor Methods.

/**
 * Set the value of the Perturber used by the Orbit instance.
 */
    protected	void		setPerturberName(String pPName)  {
	this.PName=pPName;
	}
/**
 * Set the value of the phase step offered from outside the Perturber.
 */
    protected	void		setDPhase(double pDPhase) {
	this.DPhase=pDPhase;
	}
/**
 * Set the reference to the Parent orbit for this Perturber.
 */
    protected	void		setParentOrbit(OrbitEllipse pParentOrbit)  {
	this.ParentOrbit=pParentOrbit;
	}
/**
 * Set the reference to the Radiation Force Model used by this Perturber
 */
    protected	void		setRFModel(RadiationForceModel pRFModel)  {
	this.RFModel=pRFModel;
	}
/**
 * Set the monad used as the Attitude of the Sail
 * Check the monad for validity before setting the reference.
 * Attitude monads must be of grade 2.
 */
    public	void		setSailAtt(Monad pAtt)
	throws BadAttitudeException {
	try {
		if (pAtt.isGrade()==2)
			this.SailAtt=new Monad(pAtt);
		else {
			System.out.println("Attitude needs to be a bivector.");
			this.SailAtt=new Monad(pAtt);
			this.SailAtt.GradePart(2);
			}
		}
	catch (BladeOutOfRangeException ex) {
		System.out.println("Attitude attempt within Perturber failed.");
		System.exit(0);
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("Attitude must not be a multivector.");
		throw new BadAttitudeException(ParentOrbit.ParentSail, 
			"Att mustn't be a multi-vector when set by Perturber.",
			 pAtt);
		}
	}
/**
 * Set the monad used as the Delta Attitude of the Sail
 * Check the monad for validity before setting the reference.
 * Delta Attitude monads must be of grade 2.
 */
    public	void		setDSailAtt(Monad pDAtt)
	throws BadAttitudeException {
	try {
		if (pDAtt.isGrade()==2)
			this.DSailAtt=new Monad(pDAtt);
		else {
			System.out.println("Delta Attitude must be bivector.");
			this.DSailAtt=new Monad(pDAtt);
			this.DSailAtt.GradePart(2);
			}
		}
	catch (BladeOutOfRangeException ex) {
		System.out.println("Delta Attitude within Perturber failed.");
		System.exit(0);
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("Delta Attitude must not be a multivector.");
		throw new BadAttitudeException(ParentOrbit.ParentSail, 
			"DAtt mustn't be a multi-vector when set by Perturber.",
			pDAtt);
		}
	}
/**
 * Set the monad used as the new Attitude of the Sail
 * Check the monad for validity before setting the reference.
 * Attitude monads must be of grade 2.
 */
    public	void		setnewSailAtt(Monad pAtt)
	throws BadAttitudeException {
	try {
		if (pAtt.isGrade()==2)
			this.newSailAtt=new Monad(pAtt);
		else {
			System.out.println("New Attitude must to be bivector.");
			this.newSailAtt=new Monad(pAtt);
			this.newSailAtt.GradePart(2);
			}
		}
	catch (BladeOutOfRangeException ex) {
		System.out.println("New Attitude attempt by Perturber failed.");
		System.exit(0);
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("New Attitude must not be a multivector.");
		throw new BadAttitudeException(ParentOrbit.ParentSail, 
			"newSailAtt can't be multi-grade if set by Perturber.",
			 pAtt);
		}
	}
/**
 * Set the monad used as the Angular Momentum of the Orbit
 * Check the monad for validity before setting the reference.
 * Angular Momentum monads must be of grade 2.
 */
    protected	void		setL(Monad pL)
	throws BadAngularMomentumException {
	try {
		if (pL.isGrade()==2)
			this.L=new Monad(pL);
		else {
			System.out.println("L must be bivector.");
			this.L=new Monad(pL);
			this.L.GradePart(2);
			}
		}
	catch (BladeOutOfRangeException ex) {
		System.out.println("Angular Momentum within Perturber failed.");
		System.exit(0);
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("Angular Momentum mustn't be multivector.");
		throw new BadAngularMomentumException(ParentOrbit, 
			"L mustn't be multi-vector when  set by Perturber.", 
			pL);
		}
	}
/**
 * Set the monad used as the Delta Angular Momentum of the Orbit 
 * Check the monad for validity before setting the reference.
 * Delta Angular Momentum monads must be of grade 2.
 */
    protected	void		setDL(Monad pDL)
	throws BadAngularMomentumException {
	try {
		if (pDL.isGrade()==2)
			this.DL=new Monad(pDL);
		else {
			System.out.println("DL must be a bivector to work.");
			this.DL=new Monad(pDL);
			this.DL.GradePart(2);
			}
		}
	catch (BladeOutOfRangeException ex) {
		System.out.println("DL within Perturber failed.");
		System.exit(0);
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("DL must not be a multivector.");
		throw new BadAngularMomentumException(ParentOrbit, 
			"DL mustn't be multi-vector when set by Perturber.", 
			pDL);
		}
	}
/**
 * Set the monad used as the New Angular Momentum of the Orbit
 * Check the monad for validity before setting the reference.
 * Angular Momentum monads must be of grade 2.
 */
    protected	void		setnewL(Monad pL)
	throws BadAngularMomentumException {
	try {
		if (pL.isGrade()==2)
			this.newL=new Monad(pL);
		else {
			System.out.println("L must be bivector.");
			this.newL=new Monad(pL);
			this.newL.GradePart(2);
			}
		}
	catch (BladeOutOfRangeException ex) {
		System.out.println("New Angular Momentum in Perturber failed.");
		System.exit(0);
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("New Angular Momentum can't be multigrade.");
		throw new BadAngularMomentumException(ParentOrbit, 
			"newL mustn't be multi-vector when  set by Perturber.", 
			pL);
		}
	}
/**
 * Set the monad used as the RungeLenz of the Orbit 
 * Check the monad for validity before setting the reference.
 * RungeLenz monads must be of grade 1.
 */
    protected	void		setRL(Monad pRL)
	throws BadRungeLenzException {
	try {
		if (pRL.isGrade()==1)
			this.RL=new Monad(pRL);
		else {
			System.out.println("RungeLenz must be vector to work.");
			this.RL=new Monad(pRL);
			this.RL.GradePart(1);
			}
		}
	catch (BladeOutOfRangeException ex) {
		System.out.println("RungeLenz within Perturber failed.");
		System.exit(0);
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("RungeLenz must not be a multivector.");
		throw new BadRungeLenzException(ParentOrbit, 
			"RL mustn't be a multi-vector when set by Perturber.",
			pRL);
		}
	}
/**
 * Set the monad used as the newRungeLenz of the Orbit 
 * Check the monad for validity before setting the reference.
 * newRungeLenz monads must be of grade 1.
 */
    protected	void		setnewRL(Monad pRL)
	throws BadRungeLenzException {
	try {
		if (pRL.isGrade()==1)
			this.newRL=new Monad(pRL);
		else {
			System.out.println("newRungeLenz must be vector.");
			this.newRL=new Monad(pRL);
			this.newRL.GradePart(1);
			}
		}
	catch (BladeOutOfRangeException ex) {
		System.out.println("New RungeLenz within Perturber failed.");
		System.exit(0);
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("New RungeLenz must not be a multivector.");
		throw new BadRungeLenzException(ParentOrbit, 
			"newRL can't be a multi-vector when set by Perturber.",
			pRL);
		}
	}
/**
 * Set the monad used as the Delta RungeLenz of the Orbit 
 * Check the monad for validity before setting the reference.
 * Delta RungeLenz monads must be of grade 1.
 */
    protected	void		setDRL(Monad pDRL) 
	throws BadRungeLenzException {
	try {
		if (pDRL.isGrade()==1)
			this.DRL=new Monad(pDRL);
		else {
			System.out.println("Delta RL needs to be a vector.");
			this.DRL=new Monad(pDRL);
			this.DRL.GradePart(1);
			}
		}
	catch (BladeOutOfRangeException ex) {
		System.out.println("Delta RL within Perturber failed.");
		System.exit(0);
		}
	catch (NoDefinedGradeException eg) {
		System.out.println("Delta RL must not be a multivector.");
		throw new BadRungeLenzException(ParentOrbit, 
			"DRL mustn't be multi-vector when set by Perturber.", 
			pDRL);
		}
	}
/**
 * This method copies the orbit parameter set by asking it's parent OrbitEllipse
 * for values.  It then sets the fields holding the changes to the parameters
 * to zero.
 */
    protected	void		setDerivedValues()
				throws	BadAngularMomentumException,
					BadRungeLenzException,
					BadAttitudeException {

	this.setL(ParentOrbit.getL());
	this.setRL(ParentOrbit.getRungeLenz());
	this.setSailAtt(ParentOrbit.ParentSail.getAttitude());

	this.RefZero = new Monad("Reference Zero", L.getFrameName(), 
				L.getFootName(), 3, 0, "Zero");

	this.setnewL(ParentOrbit.getL());
	this.setnewRL(ParentOrbit.getRungeLenz());
	this.setnewSailAtt(ParentOrbit.ParentSail.getAttitude());

	this.GlobalPerturbON=ParentOrbit.PerturbationON;

	}

//Other methods
/**
 * This method will one day help to see if two Perturbers are identical.
 * It doesn nothing for now except return true.
 * @return boolean
 */ 
    public	boolean		isEqual(Perturber pPerturber) {

	//Check the individual parts of the Orbit to see if they are identical.
	//The frames must also match, so checks should be made against Monads.
	//if (!this.getParentOrbit().isEqual(pPerturber.getParentOrbit())) {
	//	 return false;
	//	}
	//if (!this.getRFModel().isEqual(pPerturber.getRFModel())) return false;
	return true;
	}
/**
 * This method returns true if the true anomaly orbit step produces changes to
 * the angular momentum and RungeLenz that are small enough to be considered 
 * small relative to their original values.  The method returns false if the  
 * changes are two large.  This method is used to determine whether a smaller
 * orbit step should have been used to calculate perturbation forces.
 * @return boolean
 */ 
    private	boolean		isDPhaseSmall() {

	//This method compares the magnitude of the DL, and DRL Monads to the
	//L, and RL Monads.  If the magnitudes are small by comparison, this 
	//method returns true.  If they are not small enough, this method 
	//returns false.  No concern is made yet over the direction of the 
	//change monads.  Only size...
	//The measure of smallness for L is contained in 'LAllowance'.
	//The measure of smallness for RL is contained in 'RLAllowance'.
	//The measure of smallness for SailAtt is contained in ...

	double tempDLMag=this.DL.MagnitudeOf();
	double tempLMag=this.L.MagnitudeOf();
	double tempDRLMag=this.DRL.MagnitudeOf();
	double tempRLMag=this.RL.MagnitudeOf();
	
	boolean test1=this.isDirectionInPlane(this.newL, this.newRL);
// 	if (!test1) {
// 		System.out.println("small-check>NewRL is not in newL plane.");
// 		}
	
	if ((tempDLMag/tempLMag)>this.LAllowance) {
		System.out.println("    Too Big-->DLMag: "+tempDLMag);
		System.out.println("    Compared to-->LMag: "+tempLMag);
		return false;
		}
	if ((tempDRLMag/tempRLMag)>this.RLAllowance) {
		System.out.println("    Too Big-->DRLMag: "+tempDRLMag);
		System.out.println("    Compared to-->RLMag: "+tempRLMag);
		return false;
		}

	return true;
	}
/**
 * This internal method cuts the true anomaly orbit step in two and returns it.
 * Future versions of this method may take a quicker approach and look for
 * perturbation assumption violations.
 */
    private	void		makeAnotherPhaseOffer() {

	this.DPhase=this.DPhase/2;
	return ;
	}
/**
 * The results of a non-intertial force are parsed into the reference frame used
 * by the perturber to integrate accelerations for orbit parameter changes.
 * This method is no longer used since the perturber focuses upon DL and DRL
 * now.  It should be possible to delete this method.
 */
    public	void		parseNonInertialForce() {
	//SumForce must be calculated before comming here.
	//The resulting force is then passed to this try block
	//for parsing into component accelerations.
	try {
		Monad RadForceR=new Monad(SumForce);
		Monad RadForceS=new Monad(SumForce);
		Monad RadForceW=new Monad(SumForce);
		RadForceR.Dot(this.Rref);      //RadForce component R in spart
		RadForceS.Dot(this.Sref);      //RadForce component S in spart
		RadForceW.Dot(this.Wref);      //RadForce component W in spart
		this.R=RadForceR.GradeProject(0)[0];
		this.S=RadForceS.GradeProject(0)[0];
		this.W=RadForceW.GradeProject(0)[0];
		}
	catch (CladosException e) {
		System.out.println(e);
		System.out.println(e.getSourceMessage());
		System.out.println("Clados problem while parsing RadForce");
		System.exit(0);
		}          //There should be no clados errors.
	}

/**
 * This method is for making the RSW reference frame into which the force monad
 * will be parsed.
 */
    private	void		makeLocalRefFrame() {

	try {
		this.Rref=new Monad(	"Unit R", 
					ParentOrbit.ParentSail.getPosition());
		this.Rref.Normalize();			//Unit R is complete
		this.Wref=new Monad("Unit W", this.L);
 		this.Wref.LocalDual();
		this.Wref.Normalize();			//Unit W is complete
		this.Sref=new Monad("Unit S", this.Wref);
		this.Sref.Wedge(this.Rref);
		this.Sref.LocalDual();			//Unit S is complete
		}
	catch (CladosException e) {
		System.out.println(e);
		System.out.println("Clados problem while creating RefFrame");
		System.exit(0);
		}          //There should be no clados errors.

	try {
		this.RotsE=new Monad(	"Rotator Eq Temp", 
					this.ParentOrbit.EQUATOR);
		RotsE.Normalize();

		this.RotsN=new Monad(	"Rotator Node Temp", 
					this.ParentOrbit.NodeLine);
		RotsN.LocalDual();
		RotsN.Normalize();

		this.RotsL=new Monad("Rotator L Temp", this.L);
		RotsL.Normalize();
		}
	catch (CladosException e) {
		System.out.println(e);
		System.out.println("Clados problem while creating RefPlanes");
		System.exit(0);
		}          //There should be no clados errors.

	}
/**
 * This method adds the various forces that may act as perturbing forces.  All
 * of them should be added whether that type of perturbation is one or not.
 *  Those that are not on will be of zero magnitude, so there is not problem.
 */
    protected	void		sumPerturbingForces() {
	try {
		this.SumForce=new Monad("Sum of Perturb Forces", this.RefZero);
		SumForce.Add(RadForce);
		if (DEBUG3) {
			double[] tc=SumForce.getCoeff();
			System.out.println("Summed Force is: ");
			System.out.println(tc[1]+"\t"
					+tc[2]+" : "+tc[3]+" : "+tc[4]+"\t"
					+tc[5]+" : "+tc[6]+" : "+tc[7]+"\t"
					+tc[8]);
			}
		//SumForce.Add(HarmForce);
		//SumForce.Add(NBodForce);
		//SumForce.Add(FluidForce);
		//SumForce.Add(MagForce);
		//SumForce.Add(ImpForce);

		}
	catch (CladosException e) {
		System.out.println(e);
		System.out.println("Clados problem while adding Force parts");
		System.exit(0);
		}
	}//end of Perturbation force summing method.

/**
 * This method is the main entry from the parent OrbitEllipse.  If the Orbit is
 * required to consider perturbation forces while iterating, it asks the 
 * Perturber to furnish a new set of orbit parameters that take into account the
 * perturbing forces.  In this way, the Orbit knows nothing about perturbations
 * and only iterates using it's standard orbit parameters.  The Perturber alters
 * those parameters in the physically correct manner before the Orbit can make
 * another iteration.
 * <ul>
 * <li>Actual calculations are carried out by private methods for each element
 * called from here.
 * <li>A perturbation validity check is made to see if a smaller phase step
 * should have been used.
 * <li>The requested Phase Step is alread set within Perturber before this
 * method is called.
 * </ul>
 */
    protected	void		calculateChanges(	double pLAllow, 
							double pRLAllow)
				throws	BadAngularMomentumException,
					BadRungeLenzException,
					BadAttitudeException {

	this.GlobalPerturbON=ParentOrbit.PerturbationON;
	if (!GlobalPerturbON) {
		//This is where basic things get calculated that have to be
		//done when the perturbing forces are turned off.
		return;
		}

	this.LAllowance=pLAllow;
	this.RLAllowance=pRLAllow;
	if (DEBUG) {
		System.out.println("LAllowance used is: "+pLAllow);
		System.out.println("RLAllowance used is: "+pRLAllow);
		}
	boolean accept=false;

	//Get the orbit parameters from the Orbit before going on. 
	this.setDerivedValues();
	if (DEBUG) {
		System.out.println("Perturber Derived Values being set");
		}


	this.RadForce=this.RefZero;
	//this.HarmForce=this.RefZero;
	//this.NBodForce=this.RefZero;
	//this.FluidForce=this.RefZero;
	//this.MagForce=this.RefZero;
	//this.ImpForce=this.RefZero;

  //Reset derived values in the Force Models and then ask them to generate a
  //force monad.
	if (RFPerturbON) {
		RFModel.setDerivedItems();
		RadForce=RFModel.generateForceMonad();
		//RadForce.Scale(1/ParentOrbit.ParentSail.SailMass);	
		//This last line would make F into acceleration.
		//It is not needed if F is used to get torque to get DL

		if (DEBUG3) {
		double[] tc=RadForce.getCoeff();
		System.out.println("new Rad Force is: ");
		System.out.println(	tc[1]+"\t"
					+tc[2]+" : "+tc[3]+" : "+tc[4]+"\t"
					+tc[5]+" : "+tc[6]+" : "+tc[7]+"\t"
					+tc[8]);
			}
		}
	if (HFPerturbON) {
		//HFModel.setDerivedItems();
		//HarmForce=HFModel.generateForce();
		}

	if (NFPerturbON) {
		//NFModel.setDerivedItems();
		//NBodForce=NFModel.generateForce();
		}

	if (FFPerturbON) {
		//FFModel.setDerivedItems();
		//FluidForce=FFModel.generateForce();
		}

	if (MFPerturbON) {
		//MFModel.setDerivedItems();
		//MagForce=MFModel.generateForce();
		}

	if (IFPerturbON) {
		//IFModel.setDerivedItems();
		//ImpForce=IFModel.generateForce();
		}

	//Now perform the initial setup of parts that remain constant while an 
	//acceptable DPhase is being found.
	this.makeLocalRefFrame();
	this.sumPerturbingForces();

	//Raise the Phase Step slightly so future calls to this method do not
	//have to trudge through small steps that were required near periapsis.
	this.DPhase=this.DPhase*1.5;

	while (!accept) {      //accept tracks validity of DPhase

		//Figure out the time step related to the phase step
		//This method calls to the orbit to do the figuring
		//and leaves its result there.  The methods that actually
		//use the TimeStep reach up to the orbit to get it.
		//
		//If the Perturber owns the phase step, why do we have the 
		//orbit doing the calculation to convert it to time?
		ParentOrbit.ConvertPhaseToTime(this.getDPhase());

		//Now figure out the changes to L and RL and Att
		this.findDL(SumForce, ParentOrbit.TimeStep);
		this.findDRL(SumForce, ParentOrbit.TimeStep);
		//this.findDSailAtt(SumForce, ParentOrbit.TimeStep);

		//and set the newL, newRL and newAtt from these
		try {
			this.newL=new Monad("New Angular Momentum", this.L);
			this.newL.Add(this.DL);
			if (DEBUG2) {
//----------------------------------
		System.out.println("   LMag: "+this.L.MagnitudeOf()+
				"\tnewLMag: "+this.newL.MagnitudeOf());
//----------------------------------
				}
			this.newRL=new Monad("New Runge Lenz", this.RL);
			this.newRL.Add(this.DRL);
			if (DEBUG2) {
//----------------------------------
		System.out.println("   RLMag: "+this.RL.MagnitudeOf()+
				"\tnewRLMag: "+this.newRL.MagnitudeOf());
//----------------------------------
				}
			//this.newSailAtt=new Monad("New Element Attitude",
			//					this.SailAtt);
			//this.newSailAtt.Add(this.DSailAtt);
			}
		catch (CladosException e) {
			System.out.println("Can't add monads.");
			System.out.println(e.getSourceMessage());
			}

  //Compare the DMonads to their Monads for smallness. Use p*Allow for
  //comparisons.  If they are not small, call for another phase offer and 
  //branch to the top again.  If they are small, we are done here and we pass 
  //control back to the Orbit method that called here.

		accept=this.isDPhaseSmall();
		if (!accept) this.makeAnotherPhaseOffer();

		}//end of phase step hunting while loop.  
		//Breakout will occur when Phase step is allowed.

  //We could use the changes to monad orbit parameters to figure changes to the 
  //old parameter set most people find familiar.  It doesn't matter to the
  //perturber, though, so we won't do it here.  Only the Orbit has to figure
  //the old parameters for the sake of presenting them to the user.

	return;
	}//end of calculateChanges method.

/**
 * Calculate the change to the Sail element attitude.
 */
    private	void		findDSailAtt(Monad pForce, double pTimeStep) {
	//When we are ready to figure torques on the sail element, this
	//method must be fleshed out.
	this.DSailAtt = new Monad("Delta Sail Attitude", this.RefZero);
	}
/**
 * Calculate the change to the angular momentum.  This method works by 
 * first forming the torque (from position ^ SummedForce) and scaling by the
 * TimeStep.  The result is DL.
 */
    private	void		findDL(Monad pForce, double pTimeStep) {
	try {
		this.DL = new Monad("Delta L", 
					ParentOrbit.ParentSail.getPosition());
		this.DL.Wedge(pForce);
		this.DL.Scale(pTimeStep);
		}
	catch (CladosException e) {
		System.out.println(e.getSourceMessage());
		System.out.println("Failed to figure DL in perturber.");
		}
	}
/**
 * Calculate the change to the RungeLenz.  This method works by finding two
 * terms and adding them.  The first term is DL dot velocity scaled by micro
 * and the sail mass inverses.  The second term is L dot SummedForce scaled
 * by micro and the squared mass inverses.  The two denominators would be the
 * same if we tracked the sail's linear momentum instead of it's velocity.
 *
 * This method will work, but fail logically if the monad for DL is not found
 * before calling for DRL.  The two equations are coupled and must be done in
 * order to have physical meaning.
 */
    private	void		findDRL(Monad pForce, double pTimeStep) {
	//make sure the findDL method is called before calling this one.
	try {
		double tempM = ParentOrbit.ParentSail.SailMass;
		double tempU = ParentOrbit.getMicro();

		this.DRL = new Monad("Delta RL", this.DL);

		this.DRL.Dot(ParentOrbit.ParentSail.getVelocity());
		this.DRL.Scale(1.0/(tempU*tempM));
		this.DRL.GradePart(1);

		Monad tempF = new Monad(this.L);
		//tempF.Add(this.DL);	//This makes it newL?
		tempF.Dot(this.SumForce);
		tempF.Scale(1.0/(Math.pow(tempM,2.0)*tempU));
		if (DEBUG3) {
		double[] tc=tempF.getCoeff();
		System.out.println("tempF is: ");
		System.out.println(	tc[1]+"\t"
					+tc[2]+" : "+tc[3]+" : "+tc[4]+"\t"
					+tc[5]+" : "+tc[6]+" : "+tc[7]+"\t"
					+tc[8]);
			}
		tempF.GradePart(1);

		this.DRL.Add(tempF);
		this.DRL.Scale(pTimeStep);
		
		if (DEBUG3) {
		double[] tc=this.DRL.getCoeff();
		System.out.println("DRL is: ");
		System.out.println(	tc[1]+"\t"
					+tc[2]+" : "+tc[3]+" : "+tc[4]+"\t"
					+tc[5]+" : "+tc[6]+" : "+tc[7]+"\t"
					+tc[8]);
			}
		
		}
	catch (CladosException e) {
		System.out.println(e.getSourceMessage());
		System.out.println("Failed to figure DRL in perturber.");
		}
	}

/** 
 * This method returns a boolean that informs us whether a monad lies on the 
 * orbit or not.  pM is in the orbit plane if the result of wedging pM 
 * with L is strictly a vector.
 * It is possible for the orbit plane represented by L and the moand to be
 * checked to have radically different nagnitudes.  Since there is some loss of
 * precision at the end of the double primitive, it isn't really fair to check
 * two monads with noticeably different magnitudes.  To be fair, the monads 
 * are tested in a normalized state against a normalized L.
 */
    private	boolean		isDirectionInPlane(Monad pP, Monad pM) {

	boolean returnflag=false;

	Monad tempL9=new Monad(pP);
	Monad tempM=new Monad(pM);

	try {
		tempM.Normalize();
		tempL9.Normalize();
		double LMag=tempL9.MagnitudeOf();

		if (tempM.isGrade()==1) {
			tempL9.Wedge(tempM);
			double WMag=tempL9.MagnitudeOf();

	//We may need to tolerate some non-vector stuff.  The ratio of WMag to 
	//LMag give a level of variation.  High precision in the initial 
	//position and velocity should lead us to expect high precision in the 
	//orbit check.  This expectation can only go so far, though, when the 
	//double primitive can hold a limited number of digits.
	//The level of tolerance coded in the conditional above reflects the 
	//precision expectation of this simulation.  It should not be hard-coded
	//though.  We should get it from Sail.profile.

			if (Math.abs(WMag/LMag)>=0.0000000000000001) {
				if (DEBUG2) {	
//----------------------------------
	//System.out.println(" ");
	System.out.println(
	"P****>Monad failed plane test: "+tempM.getMonadName()+" ! in: "+
							tempL9.getMonadName());
	System.out.println("P****>Tolerance ratio: "+WMag/LMag);
//----------------------------------
					}

				if (DEBUG2) {	
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
	//System.out.println(" ");
	System.out.println(
		"P    >Monad being checked passed test: "+tempM.getMonadName());
	System.out.println("P    >Tolerance ratio: "+WMag/LMag);
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

    }//end of Perturber class

