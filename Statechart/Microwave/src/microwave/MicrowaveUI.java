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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import microwave.microwave.IMicrowaveStatemachine.SCInterface;
import microwave.microwave.IMicrowaveStatemachine.SCInterfaceOperationCallback;

/**
 * This class creates a graphical user interface (GUI) for the microwave application.
 * It implements all the methods defined in the Statechart interface in model/microwave.sct.
 * It provides the API each button raises a specific event that triggers the Statechart.
 */
public class MicrowaveUI implements SCInterfaceOperationCallback {
	
	// Link to the Statechart
	SCInterface behavior;
	
	// Components of the GUI
	Image doorOpen, doorClosed, microwave;
	JLabel display, cooking;
	JButton open, close, start, stop, timer, highPower, lowPower;
	JButton[] digits = new JButton[10];
	JPanel mainPanel, doorPanel, commandPanel, digitPanel, topPanel, bottomPanel;
	JFrame frame;

	
	/**
	 * The following describes the event triggered by each button.
	 * <br>
	 * <b>Open :</b> raises the <code>open</code> event.
	 * <br>
	 * <b>Close :</b> raises the <code>close</code> event.
	 * <br>
	 * <b>Start :</b> raises the <code>start</code> event.
	 * <br>
	 * <b>Stop :</b> raises the <code>stop</code> event.
	 * <br>
	 * <b>Timer :</b> raises the <code>timer</code> event.
	 * <br>
	 * <b>High :</b> raises the <code>high</code> event.
	 * <br>
	 * <b>Low :</b> raises the <code>low</code> event.
	 * <br>
	 * <b>The 0-9 digits :</b> each raises the <code>digit</code> event as an integer (e.g., button 1 raises the event <code>digit</code> with value 1).
	 * 
	 */
	@SuppressWarnings("serial")
	public MicrowaveUI() {
		GridLayout digitLayout = new GridLayout(4, 3);
		digitLayout.setHgap(10);
		digitLayout.setVgap(10);
		digitPanel = new JPanel(digitLayout);
		Dimension digitPaneSize = new Dimension(100, 100);
		digitPanel.setPreferredSize(digitPaneSize);
		digitPanel.setMinimumSize(digitPaneSize);
		digitPanel.setMaximumSize(digitPaneSize);
		digitPanel.setSize(digitPaneSize);
		digitPanel.setBackground(Color.BLACK);
		
		display = new JLabel("  :  ", SwingConstants.CENTER);
		display.setBackground(Color.BLACK);
		display.setForeground(Color.GREEN);
		display.setFont(new Font("Courier",Font.ITALIC, 20));

		Dimension displaySize = new Dimension(100, 60);
		display.setPreferredSize(displaySize);
		display.setMinimumSize(displaySize);
		display.setMaximumSize(displaySize);
		display.setSize(displaySize);
		
		cooking = new JLabel(new ImageIcon("images/cooking.gif"));
		doorOpen = new ImageIcon("images/microwave_open.png").getImage();
		doorClosed = new ImageIcon("images/microwave_closed.png").getImage();
		microwave = doorClosed;
		open = new JButton("Open");
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { behavior.raiseOpen(); }
		});
		close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { behavior.raiseClose(); }
		});
		start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { behavior.raiseStart(); }
		});
		stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { behavior.raiseStop(); }
		});
		timer = new JButton("Timer");
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { behavior.raiseTimer(); }
		});
		highPower = new JButton("High");
		highPower.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { behavior.raiseHigh(); }
		});
		lowPower = new JButton("Low");
		lowPower.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { behavior.raiseLow(); }
		});
		Dimension digitSize = new Dimension(30, 30);
		for (int i = 0; i < digits.length; i++) {
			int value = i;
			digits[i] = new JButton(String.valueOf(value));
			digits[i].setPreferredSize(digitSize);
			digits[i].setMinimumSize(digitSize);
			digits[i].setMaximumSize(digitSize);
			digits[i].setSize(digitSize);
			digits[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) { behavior.raiseDigit(value); }
			});
			if (i > 0)
				digitPanel.add(digits[i]);
		}
		digitPanel.add(start);
		digitPanel.add(digits[0]);
		digitPanel.add(stop);
		
		BorderLayout topLayout = new BorderLayout();
		topLayout.setHgap(10);
		topLayout.setVgap(10);
		topPanel = new JPanel(topLayout);
		topPanel.setBackground(Color.BLACK);
		topPanel.add(BorderLayout.PAGE_START, display);
		topPanel.add(BorderLayout.LINE_START, highPower);
		topPanel.add(BorderLayout.CENTER, timer);
		topPanel.add(BorderLayout.LINE_END, lowPower);
		Dimension topPaneSize = new Dimension(100, 100);
		topPanel.setPreferredSize(topPaneSize);
		topPanel.setMinimumSize(topPaneSize);
		topPanel.setMaximumSize(topPaneSize);
		topPanel.setSize(topPaneSize);
		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.BLACK);
		bottomPanel.add(open);
		bottomPanel.add(close);
		
		BorderLayout commandLayout = new BorderLayout();
		commandLayout.setHgap(10);
		commandLayout.setVgap(10);
		commandPanel = new JPanel(commandLayout);
		commandPanel.setBackground(Color.BLACK);
		commandPanel.add(BorderLayout.PAGE_START, topPanel);
		commandPanel.add(BorderLayout.CENTER, digitPanel);
		commandPanel.add(BorderLayout.PAGE_END, bottomPanel);
		Dimension commandSize = new Dimension(210, doorClosed.getHeight(null)-350);
		commandPanel.setPreferredSize(commandSize);
		commandPanel.setMinimumSize(commandSize);
		commandPanel.setMaximumSize(commandSize);
		commandPanel.setSize(commandSize);
		
		doorPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(microwave, 0, 0, null);
			}
		};
		cooking.setBounds(200, 150, cooking.getWidth(), cooking.getHeight());
		cooking.setVisible(false);
		doorPanel.add(cooking);
		Dimension microwaveSize = new Dimension(microwave.getWidth(null)-120, microwave.getHeight(null));
		doorPanel.setPreferredSize(microwaveSize);
		doorPanel.setMinimumSize(microwaveSize);
		doorPanel.setMaximumSize(microwaveSize);
		doorPanel.setSize(microwaveSize);
		
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(doorPanel);
		mainPanel.add(BorderLayout.EAST, commandPanel);

		frame = new JFrame("Microwave");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(microwaveSize);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setVisible(true);
	}
	
	/*
	 * *************************************
	 * Interface methods for the Statechart
	 * *************************************
	*/

	/**
	 * Displays the cooking time in the format <code>XX:XX</code>.
	 * @param t	the time as an integer of the number of seconds.
	 */
	@Override
	public void displayTime(long t) {
		long minutes = t / 100, seconds = t % 100;
		if (minutes == 0)
			display.setText(String.format("  : %d", seconds));
		else
			display.setText(String.format("%d : %d", minutes, seconds));
		
	}

	/**
	 * Displays a message.
	 * @param msg	the message to display.
	 */
	@Override
	public void display(String msg) {
		display.setText(msg);
	}

	/**
	 * Resets the display to show <code>"  :  "</code>.
	 */
	@Override
	public void clearDisplay() {
		display.setText("  :  ");
	}

	/**
	 * Shows the cooking animation.
	 */
	@Override
	public void cook() {
		cooking.setVisible(true);
		frame.repaint();
	}

	/**
	 * Hides the cooking animation.
	 */
	@Override
	public void stopCook() {
		cooking.setVisible(false);
		frame.repaint();
	}

	/**
	 * Emits a beep from the operating system and lightens the display.
	 */
	@Override
	public void beepOn() {
		display.setBackground(Color.LIGHT_GRAY);
		topPanel.setBackground(Color.LIGHT_GRAY);
		Toolkit.getDefaultToolkit().beep();
	}

	/**
	 * Darkens the display.
	 */
	@Override
	public void beepOff() {
		display.setBackground(Color.BLACK);
		topPanel.setBackground(Color.BLACK);
	}

	/**
	 * Shows the microwave with the door closed.
	 */
	@Override
	public void closeDoor() {
		microwave = doorClosed;
		frame.repaint();
	}

	/**
	 * Shows the microwave with the door open.
	 */
	@Override
	public void openDoor() {
		microwave = doorOpen;
		frame.repaint();
	}

	/**
	 * Assigns the Statechart to the GUI.
	 * @param statechart	the Statechart that controls the behavior of the GUI.
	 */
	public void setBehavior(SCInterface statechart) {
		behavior = statechart;
	}
}
