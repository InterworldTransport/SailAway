/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailmodel.BadSailDefinitionException-------------------
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
---com.interworldtransport.sailmodel.BadSailDefinitionException-------------------
*/

package com.interworldtransport.sailmodel;

/** com.interworldtransport.sailmodel.BadSailDefinitionException
 *  This exception is thrown when the user interface has difficulty creating
 *  a valid sail from the parameters it is given.  All such problems come here.
 *  
 * @version 0.15, 2000/02/07
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailmodel
 * @see com.interworldtransport.clados
 *   
 */
public class BadSailDefinitionException extends SailException {

    public BadSailDefinitionException(String pMessage){
	super(pMessage);
	}

    } //end of BadSailDefinitionException class

