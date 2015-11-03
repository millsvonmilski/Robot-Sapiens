/*
* Scheduler.java - Kernel: the kernel of MadKit
* Copyright (C) 1998-2002 Olivier Gutknecht, Fabien Michel, Jacques Ferber
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.

* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.

* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package madkit.kernel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/** This class defines a generic threaded scheduler agent. It holds a collection of activators.
    @author Fabien Michel (MadKit 3.0 05/09/01) and 2.0 (Overlooker).
    @author Olivier Gutknecht version 1.0
    @since MadKit 2.0
    @version 3.0
*/

public abstract class Scheduler extends Agent
{
	private Collection activators = new HashSet(7);
	
synchronized public void addActivator(Activator a)
{
	if(currentKernel.addOverlooker(getAddress(), a, null))
		activators.add(a);
}

synchronized public void addActivator(Activator a, Object accessCard)
{
	if(currentKernel.addOverlooker(getAddress(), a, accessCard))
		activators.add(a);
}
 
synchronized public void removeActivator(Activator a)
{
	if(activators.remove(a))
		currentKernel.removeOverlooker(a);
}

final public Activator[] getActivators()
{
	return(Activator[]) activators.toArray(new Activator[0]);
}

final synchronized public void removeAllActivators()
{
	for (Iterator i = activators.iterator();i.hasNext();)
	{
		currentKernel.removeOverlooker((Overlooker) i.next());
		i.remove();
	}
}

public void end()
{
	removeAllActivators();
}

}
