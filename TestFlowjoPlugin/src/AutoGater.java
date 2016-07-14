/**
 * Gating Plugin: Creates contour gates based on user specified percentage 
 * @author kelly
 *
 */
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;

import com.treestar.lib.core.ExportFileTypes;
import com.treestar.lib.core.ExternalAlgorithmResults;
import com.treestar.lib.core.PopulationPluginInterface;
import com.treestar.lib.fjml.FJML;
import com.treestar.lib.xml.SElement;
/**
 * Gating Plugin: Creates contour gates based on user specified percentage 
 * @author kelly
 * 7/13/2016
 */

/**
 * This test class illustrates the implementation of the External Population Algorithm plugin.
 * It doesn't do anything useful, but attempts to show all the different types of return values
 * that can be used by ExternalAlgorithmResults.
 */
public class AutoGater implements PopulationPluginInterface {

    private List<String> fParameters = new ArrayList<String>(); // the list of $PnN parameter names to be used
    private SElement fOptions; // an XML element that can hold any additional options used by the algorithm	
    /* 
     * Function returns name of plugin that will be output to the cell inside the workspace
     * 
     * (non-Javadoc)
     * @see com.treestar.lib.core.ExternalPopulationAlgorithmInterface#getName()
     */
    @Override
    public String getName() {   	 return "Auto-Contour Gating";    }
    /* 
     * Return an XML element that fully describes the algorithm object and can be used to
     * reconstitute the state of the object.
     * 
     * (non-Javadoc)
     * @see com.treestar.lib.core.ExternalPopulationAlgorithmInterface#getElement()
     */
    @Override
    public SElement getElement() {
   	 SElement result = new SElement(getClass().getSimpleName());
   	 if (fOptions != null)
   		 result.addContent(new SElement(fOptions)); // create a copy of the XML element
   	 // construct an XML element for each parameter name
   	 for (String pName : fParameters)
   	 {
   		 SElement pElem = new SElement(FJML.Parameter);
   		 pElem.setString(FJML.name, pName);
   		 result.addContent(pElem);
   	 }
   	 return result;
    }

    /* 
     * Use the input XML element to set the state of the algorithm object
     * 
     *  could be null
     *  clear the parameter list and re-create from the XML element
     * (non-Javadoc)
     * @see com.treestar.lib.core.ExternalPopulationAlgorithmInterface#setElement(com.treestar.lib.xml.SElement)
     */
    @Override
    public void setElement(SElement elem) {
   	 fOptions = elem.getChild("Option"); 
   	 fParameters.clear();
   	 for (SElement child : elem.getChildren(FJML.Parameter))
   		 fParameters.add(child.getString(FJML.name));
    }
    /* (non-Javadoc)
     * @see com.treestar.lib.core.ExternalPopulationAlgorithmInterface#getParameters()
     */
    @Override
    public List<String> getParameters() {
   	 return fParameters;
    }
    /* (non-Javadoc)
     * @see com.treestar.lib.core.ExternalPopulationAlgorithmInterface#getIcon()
     */
    @Override
    public Icon getIcon() {
   	 	return null;
    }
  
    /* 
     * This method uses class ClusterPrompter to prompt the user for a list of parameters and number of clusters.
     * 
     * (non-Javadoc)
     * @see com.treestar.lib.core.ExternalPopulationAlgorithmInterface#promptForOptions(com.treestar.lib.xml.SElement, java.util.List)
     */
    @Override   
    public boolean promptForOptions(SElement fcmlQueryElement, List<String> parameterNames)
    {
   	 GuiFrontEnd UI = new GuiFrontEnd(parameterNames); 	
   	 return UI.PromptForOptions();
    }
   
    /* (non-Javadoc)
     * @see com.treestar.lib.core.ExternalPopulationAlgorithmInterface#invokeAlgorithm(com.treestar.lib.xml.SElement, java.io.File, java.io.File)
     */
    @Override
    public ExternalAlgorithmResults invokeAlgorithm(SElement fcmlElem, File sampleFile, File outputFolder) {

    	ExternalAlgorithmResults result = new ExternalAlgorithmResults();
       if (fParameters == null || fParameters.size() < 2)
           return result;
       String fXParm = GuiFrontEnd.getSelectX();
       String fYParm = GuiFrontEnd.getSelectY();
       GateProcessor gProcessor= new GateProcessor();
       
       List<List<Point>> vals = gProcessor.getContourPolygons(fcmlElem, fXParm, fYParm, "2%");
       
       vals= gProcessor.Reorder(vals);
       
       result.setGatingML(gProcessor.SetUp(vals, fcmlElem, fXParm, fYParm).toString());
       return result;
   }  
  
    /*
     * This method shows how to return different kinds of values using the ExternalAlgorithmResults object.
     * 
     * This method allows the algorithm to specify if the input file should be a CSV file (CSV_SCALE OR CSV_CHANNEL), or an FCS file based on algorithm's requirements.
     * @return ExportFileTypes One of three values (ExportFileTypes.FCS, ExportFileTypes.CSV_SCALE, ExportFileTypes.CSV_CHANNEL) will be returned according to algorithm's requirement.
     * 
     *  (non-Javadoc)
     * @see com.treestar.lib.core.ExternalPopulationAlgorithmInterface#useExportFileType()
     */
    @Override
    public ExportFileTypes useExportFileType() {
   	 return ExportFileTypes.CSV_SCALE;
    }

    /* (non-Javadoc)
     * @see com.treestar.lib.core.ExternalPopulationAlgorithmInterface#getVersion()
     */
    @Override
    public String getVersion() {   	 return "1.0";    }

}