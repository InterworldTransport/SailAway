/*
 * <h2>Copyright</h2>
 * Copyright (c) 1998-2001 Interworld Transport.  All rights reserved.<br>
 * ---com.interworldtransport.sailgui.GraphicDisplayer3D----------------------------
 * <p>
 * Interworld Transport grants you ("Licensee") a license to this software
 * under the terms of the GNU General Public License.<br>
 * A full copy of the license can be found bundled with this package or code file.
 * <p>
 * If the license file has become separated from the package, code file, or binary
 * executable, the Licensee is still expected to read about the license at the
 * following URL before a-ccepting this material.
 * <blockquote><code>http://www.opensource.org/gpl-license.html</code></blockquote>
 * <p>
 * Use of this code or executable objects derived from it by the Licensee states
 * their willingness to accept the terms of the license.
 * <p>
 * A prospective Licensee unable to find a copy of the license terms should contact
 * Interworld Transport for a free copy.
 * <p>
 * ---com.interworldtransport.sailgui.GraphicDisplayer3D *--------------------------
 */

package com.interworldtransport.sailgui;

import java.util.*;
import java.awt.*;
import javax.swing.*;


/**
 *  com.interworldtransport.sailgui.GraphicDisplayer3D Root class of all SailAway graphics3D
 *  panels. Purpose: This class acts as the root class of all graphics3D 
 *  panels that draw representations of a SailAway Object. Members and Methods common to
 *  all such panels are defined here. Members and Methods particular to a representation
 *  are defined in subclasses.
 *
 * @author     Dr Alfred W Differ
 * @version    0.03, $Date: 2001/11/03 09:02:54 $
 * @see        com.interworldtransport.sailgui.SailAway
 * @see        com.interworldtransport.sailmodel.OrbitEllipse
 * @see        com.interworldtransport.sailmodel
 * @see        com.interworldtransport.clados
 */
public abstract class GraphicDisplayer3D extends JFrame {
/**
 *  This String is the name of the graphic displayer. This name is used to
 *  help identify the graphic to be requested when refreshes are needed.
 *
 * @since
 */
    public String Name;
/**
 *  Description of the Field
 *
 * @since
 */
    public Container cp;
/**
 *  The GraphicOwner is the object to which ShowPiece belongs. Reqeusts for
 *  coefficients are made to this owner.
 *
 * @since
 */
    public Object GraphicOwner;
/**
 *  ShowPiece is a Vector of Objects to be shown in the graphic frame. Reqeusts
 *  for information to be translated to an image are made to the owner.
*
 * @since
 */
    public Vector ShowPiece;
/**
 *  A reference to the parent GUI.
 *
 * @since
 */
    protected SailAway ParentGUI;


/**
 *  Construct and parent information for the presenter.
 *
 * @param  pName  Name the graphical frame so it may be found on a Vector
 * @param  pGUI   Name the GUI that owns this frame
 * @since
 */
	public GraphicDisplayer3D( String pName, SailAway pGUI ) {
		super( pName );
		this.Name = pName;
		this.ParentGUI = pGUI;
		this.cp = this.getContentPane();
		cp.setLayout( new BorderLayout() );
		setSize( 350, 350 );
	}
	//end of SailDraw Panel constructor
	
	/**
	 *  This method is used by the EventModel to set the Owner object for the ShowPiece
	 *  right after the Frame is instantiated.
	 *
	 * @param  pOwner  The new GraphicOwner value
	 * @since
	 */
	public void setGraphicOwner( Object pOwner ) {
		GraphicOwner = pOwner;
	}

	/**
	 *  This method is used by the EventModel to reset the Graphic for the ShowPiece
	 *  and then show it.
	 *
	 * @since
	 */
	public abstract void setGraphicDisplay();

}

