/*<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailmodel.ForceModel---------------------------------
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
---com.interworldtransport.sailmodel.ForceModel---------------------------------
 */

package com.interworldtransport.sailmodel;

import com.interworldtransport.clados.*;

/**
 * com.interworldtransport.sailmodel.ForceModel The generic acceleration
 * calculator.
 * <p>
 * This is the calculator class responsible for knowing the physics behind
 * impulsive forces.
 * <p>
 * Operation of Force Models is accomplished when the Sail or a Perturber uses a
 * <code>generateForce</code> method. This method causes the calculations of
 * tangential and normal acceleration monads. These requests are totaled and
 * passed back to the caller.
 * <p>
 * 
 * @version 0.20, $Date: 2000/12/12 08:38:25 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailmodel.Sail
 * @see com.interworldtransport.sailmodel.Perturber
 * @see com.interworldtransport.sailmodel.RadiationForceModel
 */
abstract class ForceModel {
	/**
	 * This string is the name of the Force Model. Naming allows for future use of
	 * mulitple Force Models in one Perturber.
	 */
	protected String ForceName;
	/**
	 * This class is the reference to the Perturber that owns the IF Model.
	 */
	protected Sail ParentSail;
	/**
	 * This monad stores the force monad generated by the represented force.
	 */
	protected Monad Force;
	/**
	 * This boolean flag determines whether the generateForce method is to calculate
	 * its monad from normal and tangential parts or as one object. Being able to
	 * pick between these two approaches allows the ForceModel to be a parent to a
	 * larger number of possible forces perturbing a spacecraft orbit.
	 */
	protected boolean ForceBreakOnPlane;
	private boolean DEBUG = false;

//The Constructors.

	/**
	 * This constructor is called by a Perturber creating a new IF Model for use.
	 */
	public ForceModel(String pFName, Sail pSail) {

		this.setFName(pFName);
		this.setParentSail(pSail);
	}

//The Gettor Methods.

	/**
	 * This method returns the string value of the Force Model's name.
	 */
	public String getFName() {
		return this.ForceName;
	}

	/**
	 * This method returns the a reference to the Parent Sail.
	 */
	public Sail getParentSail() {
		return this.ParentSail;
	}

//The settor Methods.

	/**
	 * This method sets the name of the Force Model to pFName
	 */
	protected void setFName(String pFName) {
		this.ForceName = pFName;
	}

	/**
	 * This method sets the reference to the Parent Sail to pParent
	 */
	protected void setParentSail(Sail pParent) {
		this.ParentSail = pParent;
	}

	/**
	 * This method copies the fundamental properties from the Parent spacecraft
	 * needed for force calculations.
	 */
	abstract void setParentItems();

	/**
	 * This method resets the basic derived parameters used to calculate a
	 * perturbing force. It should be called after any change that alters the
	 * components underlying the force calculations.
	 */
	abstract void setDerivedItems();

//The complicated calculation Methods

	/**
	 * This method calculates the force monad. Future versions will iterate through
	 * a finite set of structural elements to sum the forces on each.
	 */
	abstract Monad generateForceMonad();

	/**
	 * This method calculates the Normal force monad. The resulting monad is the
	 * normal component of the force on the vehicle. Future versions will iterate
	 * through a finite set of structural elements to sum the normal forces on each.
	 */
	abstract Monad generateNormalForce();

	/**
	 * This method calculates the Tangential force monad. The resulting monad is the
	 * tangential component of the radiation force on a flat sail. Future versions
	 * will iterate through a finite set of sail elements to sum the tangential
	 * forces on each.
	 */
	abstract Monad generateTangentForce();

	/**
	 * This method collects the Normal and Tangential force monads, adds them, and
	 * then scales the result by the value of a perfect absorber facing directly at
	 * the FOO. The resulting monad is the radiation force on the sail. Future
	 * versions will not change much since the work is done elsewhere.
	 */
	public Monad generateForce() {
		Monad tempForce = null;
		if (ForceBreakOnPlane) {
			tempForce = this.generateNormalForce();
			Monad tempTangentForce = this.generateTangentForce();
			try {
				tempForce.Add(tempTangentForce);
			} catch (CladosException e) {
				System.out.println("Add of perturbing forces failed.");
				System.exit(0);
			} // errors caught earlier
		} else {
			tempForce = this.generateForceMonad();
		}
		if (DEBUG) {
			System.out.println("Force generated magnitude is " + tempForce.MagnitudeOf());
		}
		return tempForce;
	}

}// end of Force Model class
