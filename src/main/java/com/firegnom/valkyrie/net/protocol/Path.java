/*******************************************************************************
 * Copyright (c) 2010 Maciej Kaniewski (mk@firegnom.com).
 * 
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 * 
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 * 
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software Foundation,
 *    Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 * 
 *    Contributors:
 *     Maciej Kaniewski (mk@firegnom.com) - initial API and implementation
 ******************************************************************************/
package com.firegnom.valkyrie.net.protocol;

// TODO: Auto-generated Javadoc
/**
 * MooDS Class File for Java.
 * 
 * @author Generated by MooDS Generator v2.1.0 - 2006 CNAM, INT, Filao
 */

public class Path {

	// attributes
	/** The step. */
	public Step[] step;

	// constructor
	/**
	 * Instantiates a new path.
	 */
	public Path() {
	}

	// setter/getter methods
	/**
	 * Gets the step.
	 * 
	 * @return the step
	 */
	public Step[] getStep() {
		return step;
	}

	/**
	 * Sets the step.
	 * 
	 * @param _local_var
	 *            the new step
	 */
	public void setStep(final Step[] _local_var) {
		step = _local_var;
	}

}
