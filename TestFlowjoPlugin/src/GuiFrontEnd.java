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

import com.treestar.lib.xml.SElement;
//
public class GuiFrontEnd {
	private static final long serialVersionUID = 1L;

	public static JDialog window;
	static JPanel contentPanel;
	static SElement contentElement;
	static String selectX, selectY;
	static boolean cont;
	static JButton runButton;
	static JLabel YID, XID, Description;
	static JLabel imgLabel;
	static JComboBox xParam, yParam;
	static JTextField Intensity;
	static int percentageVal;
	JTextField percentageBox;
	String [] scaleVals;
	Vector<String> parameters;
		
	GuiFrontEnd(List<String> samples, SElement elem){		
		
		window = new JDialog(new JFrame(), "Cell Contour Analyzer", true);
		window.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		cont = false;
		contentElement = elem;

		Vector<String> strVec = new Vector<String>(samples);	
		selectY= selectX=strVec.elementAt(0);
		
		Description = new JLabel("<html>This plugin takes a population and gates around a discrete user specified percentage. First select the X and Y parameters and then specify the percentage of events you would like to gate around.</html>");
		Description.setBounds(5, 10, 370, 75);
		
		xParam= new JComboBox(strVec);
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
		
		JLabel perLab = new JLabel("%");
		perLab.setBounds(255, 80, 60, 30);
		perLab.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		
		
	
		runButton = new JButton("RUN");
		runButton.setBounds(190, 145, 150, 40);
		runButton.addActionListener(new ButtonAL());
		contentPanel = new JPanel();
		contentPanel.setBounds(0, 0, 380, 280);
		
		contentPanel.setLayout(null);
		
		contentPanel.add(Description);
		contentPanel.add(Intensity);
		
		contentPanel.add(xParam);
		contentPanel.add(yParam);
		contentPanel.add(runButton);
		contentPanel.add(XID);
		contentPanel.add(YID);
		contentPanel.add(perLab);	
		window.add(contentPanel);	
		window.setIconImage(new ImageIcon("fence.png").getImage());
		window.setLayout(null);
		window.setSize(380, 280);
	}
	public boolean PromptForOptions()
	{	
		window.setVisible(true);	
		return cont;
	}
		
	public static JDialog GetWindow(){
		return window;
	}
	public static void setCont(boolean shouldCont)
	{
		cont = shouldCont;
	}	
	public static SElement getContentElement()
	{
		return contentElement;
	}
	public static JPanel getContentPanel() {
		return contentPanel;
	}
	public static void setContentPanel(JPanel contentPanel) {
		GuiFrontEnd.contentPanel = contentPanel;
	}
	public static JButton getRunButton() {
		return runButton;
	}
	public static void setRunButton(JButton runButton) {
		GuiFrontEnd.runButton = runButton;
	}
	
	public static JLabel getYId() {
		return YID;
	}
	public static void setYId(JLabel yId) {
		YID = yId;
	}
	public static JLabel getImgLabel() {
		return imgLabel;
	}
	public static void setImgLabel(JLabel imgLabel) {
		GuiFrontEnd.imgLabel = imgLabel;
	}
	
	public static JDialog getWindow() {
		return window;
	}
	public static void setWindow(JDialog window) {
		GuiFrontEnd.window = window;
	}
	public static String getSelectX() {
		return selectX;
	}
	public static void setSelectX(String selectX) {
		GuiFrontEnd.selectX = selectX;
	}
	public static String getSelectY() {
		return selectY;
	}
	public static void setSelectY(String selectY) {
		GuiFrontEnd.selectY = selectY;
	}
	public static JLabel getYID() {
		return YID;
	}
	public static void setYID(JLabel yID) {
		YID = yID;
	}
	public static JLabel getXID() {
		return XID;
	}
	public static void setXID(JLabel xID) {
		XID = xID;
	}
	public static JComboBox getxParam() {
		return xParam;
	}
	public static void setxParam(JComboBox xParam) {
		GuiFrontEnd.xParam = xParam;
	}
	public static JComboBox getyParam() {
		return yParam;
	}
	public static void setyParam(JComboBox yParam) {
		GuiFrontEnd.yParam = yParam;
	}
	public static JTextField getIntensity() {
		return Intensity;
	}
	public static void setIntensity(JTextField intensity) {
		Intensity = intensity;
	}
	public static int getPercentageVal() {
		return percentageVal;
	}
	public static void setPercentageVal(int percentageVal) {
		GuiFrontEnd.percentageVal = percentageVal;
	}
	public JTextField getPercentageBox() {
		return percentageBox;
	}
	public void setPercentageBox(JTextField percentageBox) {
		this.percentageBox = percentageBox;
	}
	public String[] getScaleVals() {
		return scaleVals;
	}
	public void setScaleVals(String[] scaleVals) {
		this.scaleVals = scaleVals;
	}
	public Vector<String> getParameters() {
		return parameters;
	}
	public void setParameters(Vector<String> parameters) {
		this.parameters = parameters;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static boolean isCont() {
		return cont;
	}
	public static void setContentElement(SElement contentElement) {
		GuiFrontEnd.contentElement = contentElement;
	}
	
}
