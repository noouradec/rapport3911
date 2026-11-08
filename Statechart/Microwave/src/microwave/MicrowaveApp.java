/**
 *  This file is part of Microwave.
 *  
 * This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *   
 * @author Eugene Syriani
 * @version 1.0
 * @since JDK 1.8
 */

package microwave;

import microwave.microwave.IMicrowaveStatemachine.SCInterface;
import microwave.microwave.MicrowaveStatemachine;

public class MicrowaveApp {
	/**
	 * This is the main class that runs the microwave application.
	 */

	public static void main(String[] args) {
		
		MicrowaveStatemachine sc = new MicrowaveStatemachine();
		//TODO: uncomment the following statement if using time in the Statechart (e.g., a transition with trigger after 1s)
		sc.setTimer(new TimerService());
		
		MicrowaveUI gui = new MicrowaveUI();
		SCInterface sc_int = sc.getSCInterface();
		sc_int.setSCInterfaceOperationCallback(gui);	// connect SC to GUI
		gui.setBehavior(sc_int);						// connect GUI to SC

		sc.init();
		sc.enter();
	}

}
