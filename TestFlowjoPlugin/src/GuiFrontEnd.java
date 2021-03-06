//Copyright 2016 Kelly Honsinger
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Gating Plugin: Creates contour gates based on user specified percentage 
 * @author kelly
 *	7/13/2016
 */
public class GuiFrontEnd {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static JDialog window;
	/**
	 * 
	 */
	static JPanel contentPanel;
	/**
	 * 
	 */
	/**
	 * 
	 */
	/**
	 * 
	 */
	static String selectX, selectY, cellSampleName;
	/**
	 * 
	 */
	static boolean cont;

	/**
	 * 
	 */
	static JButton runButton;

	/**
	 * 
	 */
	/**
	 * 
	 */
	/**
	 * 
	 */
	/**
	 * 
	 */
	static JLabel YID, XID, Description, SampID;
	/**
	 * 
	 */
	/**
	 * 
	 */
	static JComboBox xParam, yParam;

	/**
	 * 
	 */
	/**
	 * 
	 */
	static JTextField Intensity, SampleName;

	/**
	 * 
	 */
	static int percentageVal;

	/**
	 * 
	 */
	JTextField percentageBox;

	/**
	 * 
	 */
	String [] scaleVals;

	/**
	 * 
	 */
	Vector<String> parameters;

	/**
	 * @param samples
	 * GuiFrontEnd constructor creates all of the GUI's elements and sets them in the layout
	 */

	GuiFrontEnd(List<String> samples){		
		
		window = new JDialog(new JFrame(), "Cell Contour Analyzer", true);
		window.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		cont = false;

		Vector<String> strVec = new Vector<String>(samples);	
		selectY= selectX=strVec.elementAt(0);
		
		Description = new JLabel("<html>This plugin takes a population and gates around a discrete user specified percentage. First select the X and Y parameters and then specify the percentage of events you would like to gate around.</html>");
		Description.setBounds(5, 10, 370, 75);
		
		
		FJToolTipRenderer toolTips = new FJToolTipRenderer();
		toolTips.setToolTips(strVec);
		
		xParam= new JComboBox(strVec);
		xParam.setRenderer(toolTips);
		xParam.setBounds(35, 80, 150, 30);
		xParam.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox xBox = (JComboBox)e.getSource();
				selectX = (String)xBox.getSelectedItem();
				
			}
			
		});
		
		yParam= new JComboBox(strVec);
		yParam.setRenderer(toolTips);
		yParam.setBounds(35, 150, 150, 30);
		yParam.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox yBox = (JComboBox)e.getSource();
				selectY = (String)yBox.getSelectedItem();
			}
			
		});
		
		XID = new JLabel("X:");
		XID.setBounds(10,80, 20, 30);
		XID.setFont(new Font("SansSerif", Font.BOLD, 14));
		YID= new JLabel("Y:");
		YID.setBounds(10, 150, 20, 30);
		YID.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		Intensity = new JTextField(3);
		Intensity.setBounds(190, 80, 60, 30);
		Intensity.setFont(new Font("SansSerif", Font.PLAIN, 18));
		Intensity.setDocument(new JTextFieldLimit(3));
		
		SampleName = new JTextField();
		SampleName.setBounds(35, 220, 140, 30);
		SampleName.setFont(new Font("SansSerif", Font.PLAIN, 18));
		SampleName.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox xBox = (JComboBox)e.getSource();
				cellSampleName = (String)xBox.getSelectedItem();	
			}
		});
		
		SampID = new JLabel("Name of sub-pop:");
		SampID.setBounds(35, 190, 150, 25);
		SampID.setFont(new Font("SansSerif", Font.PLAIN, 18));

		
		JLabel perLab = new JLabel("%");
		perLab.setBounds(255, 80, 60, 30);
		perLab.setFont(new Font("SansSerif", Font.PLAIN, 16));
	
		runButton = new JButton("RUN");
		runButton.setBounds(190, 215, 150, 40);
		runButton.addActionListener(new ButtonAL());
		contentPanel = new JPanel();
		contentPanel.setBounds(0, 0, 380, 280);
		
		contentPanel.setLayout(null);
		
		contentPanel.add(Description);
		contentPanel.add(Intensity);
		
		contentPanel.add(SampleName);
		contentPanel.add(SampID);
		
		
		contentPanel.add(xParam);
		contentPanel.add(yParam);
		contentPanel.add(runButton);
		contentPanel.add(XID);
		contentPanel.add(YID);
		contentPanel.add(perLab);	
		window.add(contentPanel);	
		window.setIconImage(new ImageIcon("fence.png").getImage());
		window.setLayout(null);
		window.setSize(380, 320);
	}
	/**
	 * PromptForOptions activates the gui, causing it to appear on the user's side
	 * @return boolean
	 */
	public boolean PromptForOptions()
	{	
		window.setVisible(true);	
		return cont;
	}
	
	/**
	 * @return JDialog
	 */
	public static JDialog GetWindow(){return window;}
	/**
	 * @param shouldCont
	 */
	public static void setCont(boolean shouldCont){cont = shouldCont;}	
	/**
	 * @return JPanel
	 */

	public static JPanel getContentPanel() {return contentPanel;}
	/**
	 * @param contentPanel
	 */

	public static void setContentPanel(JPanel contentPanel) {GuiFrontEnd.contentPanel = contentPanel;}
	/**
	 * @return JButton
	 */

	public static JButton getRunButton() {return runButton;}
	/**
	 * @param runButton
	 */

	public static void setRunButton(JButton runButton) {GuiFrontEnd.runButton = runButton;}
	/**
	 * @return JLabel
	 */
	public static JLabel getYId() {
		return YID;
	}
	/**
	 * @param yId
	 */

	public static void setYId(JLabel yId) {
		YID = yId;
	}

	
	/**
	 * @return JDialog
	 */

	public static JDialog getWindow() {
		return window;
	}
	/**
	 * @param window
	 */
	public static void setWindow(JDialog window) {
		GuiFrontEnd.window = window;
	}
	/**
	 * @return String
	 */

	public static String getSelectX() {
		return selectX;
	}
	/**
	 * @param selectX
	 */

	public static void setSelectX(String selectX) {
		GuiFrontEnd.selectX = selectX;
	}
	/**
	 * @return String
	 */

	public static String getSelectY() {
		return selectY;
	}
	/**
	 * @param selectY
	 */

	public static void setSelectY(String selectY) {
		GuiFrontEnd.selectY = selectY;
	}
	/**
	 * @return JLabel
	 */

	public static JLabel getYID() {
		return YID;
	}
	/**
	 * @param yID
	 */

	public static void setYID(JLabel yID) {
		YID = yID;
	}
	/**
	 * @return JLabel
	 */

	public static JLabel getXID() {
		return XID;
	}
	/**
	 * @param xID
	 */

	public static void setXID(JLabel xID) {
		XID = xID;
	}
	/**
	 * @return JComboBox
	 */

	public static JComboBox getxParam() {
		return xParam;
	}
	/**
	 * @param xParam
	 */

	public static void setxParam(JComboBox xParam) {
		GuiFrontEnd.xParam = xParam;
	}
	/**
	 * @return JComboBox
	 */

	public static JComboBox getyParam() {
		return yParam;
	}
	/**
	 * @param yParam
	 */

	public static void setyParam(JComboBox yParam) {
		GuiFrontEnd.yParam = yParam;
	}
	/**
	 * @return JTextField
	 */

	public static JTextField getIntensity() {
		return Intensity;
	}
	/**
	 * @param intensity
	 */
	public static void setIntensity(JTextField intensity) {
		Intensity = intensity;
	}
	/**
	 * @return int
	 */

	public static int getPercentageVal() {
		return percentageVal;
	}
	/**
	 * @param percentageVal
	 */

	public static void setPercentageVal(int percentageVal) {
		GuiFrontEnd.percentageVal = percentageVal;
	}
	/**
	 * @return JTextField
	 */

	public JTextField getPercentageBox() {
		return percentageBox;
	}
	/**
	 * @param percentageBox
	 */

	public void setPercentageBox(JTextField percentageBox) {
		this.percentageBox = percentageBox;
	}
	/**
	 * @return String[]
	 */

	public String[] getScaleVals() {
		return scaleVals;
	}
	/**
	 * @param scaleVals
	 */

	public void setScaleVals(String[] scaleVals) {
		this.scaleVals = scaleVals;
	}
	/**
	 * @return Vector<String>
	 */

	public Vector<String> getParameters() {
		return parameters;
	}
	/**
	 * @param parameters
	 */

	public void setParameters(Vector<String> parameters) {
		this.parameters = parameters;
	}
	/**
	 * @return long
	 */

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return boolean
	 */

	public static boolean isCont() {
		return cont;
	}
	
	/**
	 * @return String
	 */
	public static String getCellSampleName() {
		return cellSampleName;
	}
	/**
	 * @param cellSampleName
	 */
	public static void setCellSampleName(String cellSampleName) {
		GuiFrontEnd.cellSampleName = cellSampleName;
	}
	/**
	 * @return JLabel
	 */
	public static JLabel getDescription() {
		return Description;
	}
	/**
	 * @param description
	 */
	public static void setDescription(JLabel description) {
		Description = description;
	}
	/**
	 * @return JLabel
	 */
	public static JLabel getSampID() {
		return SampID;
	}
	/**
	 * @param sampID
	 */
	public static void setSampID(JLabel sampID) {
		SampID = sampID;
	}
	/**
	 * @return JTextField
	 */
	public static JTextField getSampleName() {
		return SampleName;
	}
	/**
	 * @param sampleName
	 */
	public static void setSampleName(JTextField sampleName) {
		SampleName = sampleName;
	}
}
