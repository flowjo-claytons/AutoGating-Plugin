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
 * This test class illustrates the implementation of the External Population Algorithm plugin.
 * It doesn't do anything useful, but attempts to show all the different types of return values
 * that can be used by ExternalAlgorithmResults.
 */
public class AutoGater implements PopulationPluginInterface {

    private List<String> fParameters = new ArrayList<String>(); // the list of $PnN parameter names to be used
    private SElement fOptions; // an XML element that can hold any additional options used by the algorithm
    @Override
    public String getName() {   	 return "Auto-Contour Gating";    }
    /*
     * Return an XML element that fully describes the algorithm object and can be used to
     * reconstitute the state of the object.
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
     */
    @Override
    public void setElement(SElement elem) {
   	 fOptions = elem.getChild("Option"); // could be null
   	 // clear the parameter list and re-create from the XML element
   	 fParameters.clear();
   	 for (SElement child : elem.getChildren(FJML.Parameter))
   		 fParameters.add(child.getString(FJML.name));
    }
    @Override
    public List<String> getParameters() {
   	 return fParameters;
    }
    @Override
    public Icon getIcon() {
   	 	return null;
    }
    /*
     * This method uses class ClusterPrompter to prompt the user for a list of parameters and number of clusters.
     */
    @Override   
    public boolean promptForOptions(SElement fcmlQueryElement, List<String> parameterNames)
    {
    	
     SElement algorithmElem = getElement();
     
   	 GuiFrontEnd UI = new GuiFrontEnd(parameterNames, algorithmElem);
   	
   	 return UI.PromptForOptions();
    }
   
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
     */
  
    
    /**
     * This method allows the algorithm to specify if the input file should be a CSV file (CSV_SCALE OR CSV_CHANNEL), or an FCS file based on algorithm's requirements.
     * @return ExportFileTypes One of three values (ExportFileTypes.FCS, ExportFileTypes.CSV_SCALE, ExportFileTypes.CSV_CHANNEL) will be returned according to algorithm's requirement.
     */
    @Override
    public ExportFileTypes useExportFileType() {
   	 return ExportFileTypes.CSV_SCALE;
    }

    @Override
    public String getVersion() {   	 return "1.0";    }

}