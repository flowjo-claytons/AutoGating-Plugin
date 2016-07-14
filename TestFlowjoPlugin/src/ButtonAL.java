/**
 * Gating Plugin: Creates contour gates based on user specified percentage 
 * @author kelly
 *	7/13/2016
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Gating Plugin: Creates contour gates based on user specified percentage 
 * @author kelly
 *
 */
public class ButtonAL implements ActionListener{

	/* 
	 *  Overriden function for action listener class.  It is called whenever 
	 * the button is clicked.  It checks to make sure that all of the user input is 
	 * correct.
	 * 
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try{
			int percentage = Integer.parseInt(GuiFrontEnd.Intensity.getText());
		
			
			if (percentage <= 100)
			{
				if (GuiFrontEnd.getSampleName().getText().equals(""))
				GuiFrontEnd.setCellSampleName(GuiFrontEnd.getSelectX() +"+" + GuiFrontEnd.getSelectY());
				else
					GuiFrontEnd.setCellSampleName(GuiFrontEnd.getSampleName().getText());
				GuiFrontEnd.setPercentageVal(percentage);
				GuiFrontEnd.setCont(true);
				GuiFrontEnd.GetWindow().dispose();
			}
			else 
			{
				JLabel error = new JLabel("Must enter percentage <= 100%");
				error.setFont(new Font("SansSerif", Font.PLAIN, 10));
				error.setForeground(Color.RED);
				error.setBounds(190, 105, 170, 20);
				GuiFrontEnd.getContentPanel().add(error);
				SwingUtilities.updateComponentTreeUI(GuiFrontEnd.GetWindow());
				
			}
		}
		catch(NumberFormatException exe)
		{
			JLabel error = new JLabel("Must enter percentage <= 100%");
			error.setFont(new Font("SansSerif", Font.PLAIN, 10));
			error.setForeground(Color.RED);
			error.setBounds(190, 105, 170, 20);
			GuiFrontEnd.getContentPanel().add(error);
			SwingUtilities.updateComponentTreeUI(GuiFrontEnd.GetWindow());
		}
		
	}
}

