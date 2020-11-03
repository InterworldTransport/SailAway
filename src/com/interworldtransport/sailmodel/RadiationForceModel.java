/*<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailmodel.RadiationForceModel------------------------
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
---com.interworldtransport.sailmodel.RadiationForceModel------------------------
 */

package com.interworldtransport.sailmodel;
import com.interworldtransport.clados.*;

/** com.interworldtransport.sailmodel.RadiationForceModel  The acceleration 
 * calculator for Radiation force pressure.
 * <p>
 * This is the calculator class responsible for knowing the physics behind 
 * radiation pressure.
 * <p>
 * Proper use of this class is accomplished by the Sail that constructs it.
 * Each of the items needed by this Force Model are acquired from the Sail.
 * This object never gets instantiated except from a Sail.
 * <p>
 * Operation of the Radiation Force Model is accomplished when the Sail or a 
 * Perturber uses its <code>generateForce</code> method.  This method causes the
 * calculations of tangential and normal acceleration monads.  These requests 
 * are totaled and passed back to the caller.
 * <p>
 * Defining a Radiation Force Model in a more general way to support a torques 
 * and relativistic approaches may be done in the future.  Such and adaptation
 * is not needed at present.
 * <p>
 * Future versions of Perturber will support features from the following list.
 * <ul>
 * <li>Torques
 * <li>Relativistic issues
 * </ul>
 * @version 0.20, $Date: 2001/11/03 08:43:10 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailmodel.OrbitEllipse
 */
public class RadiationForceModel extends ForceModel{

/**
 * This is the frontside emmissivity coefficient.
 */
    private	double			Ef;
/**
 * This is the backside emmissivity coefficient.
 */
    private	double			Eb;
/**
 * This is the frontside reflectivity coefficient.
 */
    private	double			Rf;
/**
 * This is the backside reflectivity coefficient.
 */
    private	double			Rb;
/**
 * This is the frontside non-Lambertian coefficient.
 */
    private	double			Lf;
/**
 * This is the backside non-Lambertian coefficient.
 */
    private	double			Lb;
/**
 * This is the frontside specular reflectivity coefficient.
 */
    private	double			Sf;
/**
 * This is the backside specular reflectivity coefficient.
 */
    private	double			Sb;
/**
 * This is the Solar Luminosity density magnitude in Watts/sq meter
 */
    private	double			W;
/**
 * This is the magnitude of the force on a one sq meter perfect absorber.
 * It is used so the force equations only need to calculate the ratio of the
 * forces between a total absorber and the real reflector.
 */
    private	double			AbsorberForceMag;
/**
 * This is a unit monad representing the attitude to the sail surface.  It is 
 * a copy of the one kept by the Sail.
 */
    private	Monad			SailAtt;
/**
 * This is a unit monad representing the normal to the sail surface.  It is 
 * figured from the dual of the Sail Attitude.
 */
    protected	Monad			SailNormal;
 /**
 * This is a unit monad representing the direction of specular reflection from
 * the sail surface.  It must be in the SailNormal-SailSunLine plane and be a 
 * mirror reflection across the SailNormal-NORTH plane.
 */
    protected	Monad			SailReflect;
/**
 * This is a unit monad representing the direction from the sun to the sail 
 * surface.  It is figured from the SailPosition monad.
 */
    protected	Monad			SailSunLine;
/**
 * This double acts as a temporary holding bin for the cosine of the solar
 * pitch angle.
 */
    private	double			tempcos;
    /**
 * This double acts as a temporary holding bin for the sine of the solar pitch
 * angle.
 */
    private	double			tempsin;
/**
 * This is the Solar Luminosity magnitude in Watts.
 */
    private	double			Luminosity=3.9E26;
 /**
 * This is the Steffan-Boltzmann constant in Watts/sq meter/ sq sq Kelvin.
 */
    private	double			STEFFBOLTZ=5.67032E-08;
/**
 * This is the speed of light constant in meters/second.
 */
    private	double			LIGHTSPEED=299792458;
    private	boolean			DEBUG=false;

//The Constructors.

/** 
 *  This constructor is called by a Perturber creating a new RF Model
 */
    public	RadiationForceModel(	String pRFName, 
					Sail pSail) {

	super(pRFName, pSail);
	this.ForceBreakOnPlane=true;
	this.setParentItems();
	this.setDerivedItems();
	}


//The Gettor Methods.

/** 
 * This method returns the Monad for the SailNormal
 */
    public	Monad		getSailNormal() {
	return this.SailNormal;
	}

/** 
 * This method returns the Monad for the SailReflect
 */
    public	Monad		getSailReflect() {
	return this.SailReflect;
	}

/** 
 * This method returns the Monad for the SailSunLine
 */
    public	Monad		getSailSunLine() {
	return this.SailSunLine;
	}

/** 
 * This method returns the value of the private Luminosity data member.
 */
    public	double		getLuminosity() {
	return this.W;
	}

/** 
 * This method calculates the temperature of the sail fabric using emmissivity 
 * information.
 */
    public	double		getTemperature(){
	double tempSQSQ=(1-Rf)*W/((Ef+Eb)*STEFFBOLTZ);
	return Math.sqrt(Math.sqrt(tempSQSQ));    //returns T in Kelvins.
	}

//The settor Methods.

/** 
 * This method copies the basic sail fabric properties from the Parent Sail.
 * Attention is paid to whether the sail is facing forward relative to the FOO.
 */
    protected	void		setParentItems(){

	double tempcos=this.ParentSail.getcosAttitude();
	if (tempcos >= 0.0) {        //Sail is facing forwards
		Rf = Double.parseDouble(ParentSail.getRf());
		Rb = Double.parseDouble(ParentSail.getRb());
		Sf = Double.parseDouble(ParentSail.getSf());
		Sb = Double.parseDouble(ParentSail.getSb());
		Ef = Double.parseDouble(ParentSail.getEf());
		Eb = Double.parseDouble(ParentSail.getEb());
		Lf = Double.parseDouble(ParentSail.getLf());
		Lb = Double.parseDouble(ParentSail.getLb());
		}
	else {            //Sail is facing backwards
		Rf = Double.parseDouble(ParentSail.getRb());
		Rb = Double.parseDouble(ParentSail.getRf());
		Sf = Double.parseDouble(ParentSail.getSb());
		Sb = Double.parseDouble(ParentSail.getSf());
		Ef = Double.parseDouble(ParentSail.getEb());
		Eb = Double.parseDouble(ParentSail.getEf());
		Lf = Double.parseDouble(ParentSail.getLb());
		Lb = Double.parseDouble(ParentSail.getLf());
		}
	}

/** 
 * This method resets the derived parameters used to calculate radiation forces.
 * It should be called after any change to the sail position relative to FOO.
 */
    protected	void		setDerivedItems(){
	double SolDistanceSQ=ParentSail.getPosition().SQMagnitudeOf();
	this.W=Luminosity/(SolDistanceSQ*4.0*Math.PI);
	this.AbsorberForceMag=this.W*ParentSail.SailArea/LIGHTSPEED;
	
	
	try {
		//Get a copy of the Sail Attitude for convenience.
		this.SailAtt=new Monad("SailAttitude copy", 
						ParentSail.getAttitude());
		SailAtt.Normalize();
		if (SailAtt.isGrade()!=2) {
			System.out.println("Sail Attitude must be a bivector.");
			System.exit(0);
			}
		
		//Calculate the sail normal vector.  It often points at the sun.
		this.SailNormal=new Monad("Sail Normal", this.SailAtt);
		SailNormal.LocalDual();
		SailNormal.Normalize();
		if (SailNormal.isGrade()!=1) {
			System.out.println("Sail Normal must be a vector.");
			System.exit(0);
			}
		
		//Calculate the sail SunLine vector
		this.SailSunLine=new Monad("Sail SunLine", 
						ParentSail.getPosition());
		SailSunLine.Normalize();
		if (SailSunLine.isGrade()!=1) {
			System.out.println("Sail SunLine must be a vector.");
			System.exit(0);
			}
		
		//Calculate the sail Reflection vector
		//The Sail reflection vector is a reflection of the SunLine
		//vector on the attitude plane.
		this.SailReflect=new Monad(this.SailSunLine);
		SailReflect.RightMultiply(this.SailNormal);
		SailReflect.LeftMultiply(this.SailNormal);
		SailReflect.Scale(-1.0);
		if (SailReflect.isGrade()!=1) {
			System.out.println("Sail Reflection must be a vector.");
			System.exit(0);
			}
		
		//Calculate the cosine of the solar pitch angle next.
		Monad tempA1=new Monad("SailNormalToDot", this.SailNormal);
		tempA1.Dot(SailSunLine);
		this.tempcos=tempA1.GradeProject(0)[0];
		
		//Calculate the sine of the solar pitch angle next.
		Monad tempA2=new Monad("Another Sail Normal", 
						this.SailNormal);
		tempA2.Wedge(SailSunLine);  //tempA2 is now a bivector
		this.tempsin=tempA2.MagnitudeOf();
		
		}
	catch (CladosException e) {
		System.out.println(e);
		System.out.println(e.getSourceMessage());
		System.exit(0);
		}
	if (DEBUG) {
		System.out.println("Solar Luminosity density is: "+W);
		System.out.println("Absorber Magnitude is: "+AbsorberForceMag);
		}
	}
	
//The complicated calculation Methods
    public	Monad		generateForceMonad() {
	//In order to call this method instead of the Normal and tangential
	//force methods will require treating the radiation force in terms of
	//its contributions from reflection, absorption, and emission.  If each
	//is treated as a whole object, they may be summed at the end and 
	//delivered to the calling object
	
	//Figure out the force monad from absorption of sunlight
	Monad ForceAbsorb=new Monad(this.SailSunLine);
	ForceAbsorb.Scale(this.AbsorberForceMag*tempcos);
	
	//Figure out the force monad from specular reflection of sunlight
	Monad ForceReflectSpecular=new Monad(this.SailReflect);
	ForceReflectSpecular.Scale(-1.0*Rf*Sf*this.AbsorberForceMag*tempcos);
	
	//Figure out the force monad from dispersive reflection of sunlight
	Monad ForceReflectDisp=new Monad(this.SailNormal);
	ForceReflectDisp.Scale(Lf*Rf*(1.0-Sf)*this.AbsorberForceMag*tempcos);
	
	//Figure out the force monad from radiated energy
	Monad ForceRadiative=new Monad(this.SailNormal);
	ForceRadiative.Scale((1-Rf)*(Ef*Lf-Eb*Lb)*
				this.AbsorberForceMag*tempcos/(Ef+Eb));
	
	//Now add them all up and return the result.
	Monad tempReturn=new Monad(ForceAbsorb);
	tempReturn.Scale(0.0);
	try {
		tempReturn.Add(ForceAbsorb);
		tempReturn.Add(ForceReflectSpecular);
		tempReturn.Add(ForceReflectDisp);
		tempReturn.Add(ForceRadiative);
		}
	catch (CladosException e) {
		System.out.println("Unable to add radiation force parts.");
		System.exit(0);
		}
	
	return tempReturn;
	}

/** 
 * This method calculates the Normal force monad.
 * The resulting monad is the normal component of the radiation force on a flat
 * sail.  Future versions will iterate through a finite set of sail elements to
 * sum the normal forces on each.
 */
    public	Monad		generateNormalForce(){
/*
	// tempcos and tempsin are recalculated before getting here.
	double scaler=((1+Rf*Sf)*tempcos*tempcos)+(Lf*Rf*(1-Sf)*tempcos)+
				((Lf*Ef-Lb*Eb)*(1-Rf)*tempcos/(Ef+Eb));
	//Now multiply the scaler against a unit vector made from the negative
	//dual of unit attitude.
	Monad tempA=new Monad(this.SailNormal);
	
	if (tempcos >= 0.0) {
		tempA.Scale(-1.0);
		}
	tempA.Scale(scaler);			//This looks like a force.
	tempA.Scale(this.AbsorberForceMag);	//The perturber needs an accel
	
	if (DEBUG) {
		System.out.println("Normal force is ");
		double[] tc=tempA.getCoeff();
		System.out.println(tc[1]+
					"\t"+tc[2]+" : "+tc[3]+" : "+tc[4]+
					"\t"+tc[5]+" : "+tc[6]+" : "+tc[7]+
					"\t"+tc[8]);
		}
	return tempA
 */
	return null;
	}

/** 
 * This method calculates the Tangential force monad.
 * The resulting monad is the tangential component of the radiation force on a
 * flat sail.  Future versions will iterate through a finite set of sail 
 * elements to sum the tangential forces on each.
 */
    public	Monad		generateTangentForce(){
/*
	// tempcos and tempsin are recalculated before getting here.
	double scaler2=(1-Rf*Sf)*tempcos*tempsin;
	//Now multiply the scaler against a unit vector in the attitude plane 
	//along projection of position

	try {
		Monad tempA1=new Monad(this.SailtAtt);
		Monad tempA2=new Monad(this.SailAtt);
		
		tempA2.Dot(SailSunLine);
		tempA2.Normalize();
		//tempA2 is in Attitude plane but perpendicular to tangent force
		tempA1.Dot(tempA2);
		tempA1.Normalize();	
		//tempA1 is in Attitude plane but antiparallel to tangent force
		tempA1.Scale(-1.0);
		//Check this on a few attitudes to see if it works.
		tempA1.Scale(scaler2);
		//This is a force after multiplying by the AbsorberForceMag
		tempA1.Scale(this.AbsorberForceMag);
		tempA1.GradePart(1);
		}
	catch (DotDefinitionException ed) {
		System.out.println("Dot definition problem in Tangent force");
		System.out.println(ed.getSourceMessage());
		System.out.println(ed);
		System.exit(0);
		}			//errors caught earlier
	catch (CladosException e) {
		System.out.println("Working with Tangent force.  clados error");
		System.exit(0);
		}			//errors caught earlier

	if (DEBUG) {
		System.out.println("Tangent force is ");
		double[] tc=tempA1.getCoeff();
		System.out.println(tc[1]+
					"\t"+tc[2]+" : "+tc[3]+" : "+tc[4]+
					"\t"+tc[5]+" : "+tc[6]+" : "+tc[7]+
					"\t"+tc[8]);
		}
	return tempA1;
 */
 	return null;
	}

    }//end of RadiationForceModel class

