/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailmodel.BadVelocityTryException----------------------
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
---com.interworldtransport.sailmodel.BadVelocityTryException----------------------
*/

package com.interworldtransport.sailmodel;
import com.interworldtransport.clados.*;

/** com.interworldtransport.sailmodel.BadVelocityTryException
 *  This exception is thrown when the Orbit Iterator fails to find the velocity
 *  monad for the next iteration.  This happens most often when the velocity
 *  monad produced is not coplanar with the angular momentum.  All such
 *  problems come here.
 *  
 * @version 0.15, 2000/02/07 
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailmodel
 * @see com.interworldtransport.clados
 */

public class BadVelocityTryException extends SailException {
    public Monad 	SourceL;
    public Monad	SourcePosition;

    public BadVelocityTryException(Monad pSource, String pMessage, Monad pPosition){
	super(pMessage);
	this.SourceL=new Monad(pSource);
	this.SourcePosition=new Monad(pPosition);
	}

    public Monad	getSourceL() {
	return this.SourceL;
	}

public Monad	getSourcePosition() {
	return this.SourcePosition;
	}
    } //end of BadVelocityTryException class

