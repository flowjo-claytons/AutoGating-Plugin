import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.treestar.flowjo.engine.FEML;
import com.treestar.flowjo.engine.Query;
import com.treestar.flowjo.engine.SimpleQueryCallback;
import com.treestar.flowjo.engine.utility.EPluginHelper;
import com.treestar.lib.FJPluginHelper;
import com.treestar.lib.PluginHelper;
import com.treestar.lib.fcs.ParameterUtil;
import com.treestar.lib.fjml.FJML;
import com.treestar.lib.fjml.types.DisplayType;
import com.treestar.lib.xml.GatingML;
import com.treestar.lib.xml.SElement;

public class GateProcessor {
	  public  List<List<Point>> Reorder( List<List<Point>> vals)
	   {
	   	ArrayList<List<Point>> newList= new ArrayList<List<Point>>(vals.size());
	   	int temp=0;
	   	while (!vals.isEmpty())
	   	{
	   		temp = 0;
	   		for (int j = 0; j < vals.size(); j++)
	   		{
		    		if ( vals.get(temp).size() > vals.get(j).size())
		    		{
		    			temp = j;
		    		}		
	   		}
	   		newList.add(vals.remove(temp));
	   	}    	
	   	return newList;
	   }
	  
	  public SElement SetUp(List<List<Point>> vals, SElement fcmlElem, String fXParm, String fYParm)
	   {
	   	//boolean stop = false;
	   	SElement gate = new SElement(GatingML.gatingML2);
	   	int retrievalIndex =0;
	   	SElement currentBest = new SElement(gate);
	   	double lowestDifference= 100;
	   	for(retrievalIndex =0; retrievalIndex <vals.size(); retrievalIndex++)
	   	{	    	 
		         if (!vals.isEmpty())
		         {
		     		
		             String sampleURI = EPluginHelper.getSampleURI(fcmlElem);
		            
		             SElement polyGate = new SElement(GatingML.PolygonGate);
		             gate.addContent(polyGate);
		             polyGate.setString(GatingML.id, "ContourTestGate");
		
		             SElement dElem = new SElement(GatingML.dimension);
		             polyGate.addContent(dElem);
		             SElement tElem = new SElement(GatingML.FCS_DIMENSION);
		             dElem.addContent(tElem);
		             
		             try{
		 	            tElem.setString(GatingML.NAME, fXParm.substring(0, fXParm.indexOf(" :")));
		 	           }
		             catch(StringIndexOutOfBoundsException e)
		             {
		             	tElem.setString(GatingML.NAME, fXParm);
		             }
		             dElem = new SElement(GatingML.dimension);
		             polyGate.addContent(dElem);
		             tElem = new SElement(GatingML.FCS_DIMENSION);
		             dElem.addContent(tElem);
		             try{
		             	//System.out.println(fYParm.substring(0, fYParm.indexOf(" :")));
		 	            tElem.setString(GatingML.NAME, fYParm.substring(0, fYParm.indexOf(" :")));   
		             }
		              catch(StringIndexOutOfBoundsException e)
		             {
		             	 tElem.setString(GatingML.NAME, fYParm);
		             }
		             
		            // double toDecimal = (double)GuiFrontEnd.getPercentageVal()/100;
		            
		       
		            gate =  Compute(gate, polyGate, vals, retrievalIndex, sampleURI);
		            
		            int populationCOunt = CompareResult(fcmlElem, gate);
		            double popPercentage = (double)populationCOunt/PluginHelper.getNumExportedEvents(fcmlElem);
		            //System.out.println(PluginHelper.getNumExportedEvents(fcmlElem));
		            popPercentage = Math.abs((popPercentage*100)-(double)GuiFrontEnd.getPercentageVal());
		            
		            if (popPercentage <.5 )
		            {
		            	return gate;
		            }
		            if (popPercentage < lowestDifference)
		            {
		            	lowestDifference = popPercentage;
		            	currentBest= new SElement(gate);
		            }
		            gate.removeContent();
		         }
		    }
	   	
	   	return currentBest;
	   }  
	   public SElement Compute(SElement gate, SElement polyGate, List<List<Point>> vals, int retrievalIndex, String sampleURI)
	   {
	   	 for (Point pt : vals.get(retrievalIndex))
	        {
	   		 double x;
	   		 double y;
	   		 try{
	   			  x= FJPluginHelper.channelToScale(sampleURI, GuiFrontEnd.getSelectX(), pt.x, 256);
	   		 }
	   		 catch(StringIndexOutOfBoundsException e)
	   		 {
	   			 x = FJPluginHelper.channelToScale(sampleURI, GuiFrontEnd.getSelectX().substring(0, GuiFrontEnd.selectX.indexOf(" :")), pt.x, 256);
	   		 }
	            try{
	           	  y= FJPluginHelper.channelToScale(sampleURI, GuiFrontEnd.getSelectY().substring(0, GuiFrontEnd.selectY.indexOf(" :")), 256-pt.y, 256);
	            }
	            catch(StringIndexOutOfBoundsException e)
	            {
	           	 y = FJPluginHelper.channelToScale(sampleURI, GuiFrontEnd.getSelectY(), 256-pt.y, 256);
	            }
	            SElement gv = new SElement(GatingML.vertex);
	            polyGate.addContent(gv);
	            SElement gc = new SElement(GatingML.coordinate);
	            gv.addContent(gc);
	            gc.setDouble(GatingML.value, x);
	            gc = new SElement(GatingML.coordinate);
	            gv.addContent(gc);
	            gc.setDouble(GatingML.value, y);
	        } 
	    	return gate;       
	    }  
	   public static int CompareResult(SElement parentPop, SElement gate)
	   {	
	   	try{
	   	 SElement polyCopy = new SElement(gate.getChild(GatingML.PolygonGate));
	        SElement gateCopy = new SElement(FJML.Gate);
	        gateCopy.addContent(polyCopy);
	        
	        SElement parentCopy = new SElement(parentPop.getChild(FEML.FcmlQuery));
	      
	        parentCopy.removeChild(FJML.ExternalPopNode);
	        
	        SElement stat  = new SElement(FJML.Statistic);
	        stat.setString(FJML.name, "Count");
	        
	        parentCopy.addContent(gateCopy);
	        parentCopy.addContent(stat);
	        
	        SElement tempCopy = new SElement(parentPop);
	        tempCopy.removeChild(FEML.FcmlQuery);
	        tempCopy.removeChild(FJML.ExternalPopNode);
	        tempCopy.addContent(parentCopy);
	              
	        SimpleQueryCallback callback = new SimpleQueryCallback();
	        Query query = new Query(tempCopy, callback);
	        query.executeQuery();
	            
	        SElement data= callback.getResultElement();
	      
	        data = data.getChild(FEML.FcmlQuery);
	        String strVersion  = data.toString();
	        String values = strVersion.substring(strVersion.indexOf("value"), strVersion.indexOf("gateId"));
	        values = values.replaceAll("\\D+","");
	        return Integer.parseInt(values);
	   	}
	   	catch(Exception e)
	   	{
	   		return 0;
	   	}
	   }

	   public List<List<Point>> getContourPolygons(SElement fcmlElem, String paramXName, String paramYName, String level)
	    {
	        paramXName = ParameterUtil.stripStainName(paramXName);
	        paramYName = ParameterUtil.stripStainName(paramYName);
	        SElement queryElem = new SElement(fcmlElem);
	        SElement fcmlQueryElem = queryElem.getChild(FEML.FcmlQuery);
	        if (fcmlQueryElem == null) return null;
	        fcmlQueryElem.removeChild(FJML.ExternalPopNode);
	        
	        SElement graphElem = new SElement(FJML.Graph);
	        fcmlQueryElem.addContent(graphElem);
	        graphElem.setBool(FJML.smoothing, true);
	        graphElem.setBool("fast", true);
	        graphElem.setString(FJML.type, DisplayType.Contour.toString());

	        SElement axis = new SElement(FJML.Axis);
	        graphElem.addContent(axis);
	        axis.setString(FJML.dimension, FJML.x);
	        axis.setString(FJML.name, paramXName);
	        axis = new SElement(FJML.Axis);
	        graphElem.addContent(axis);
	        axis.setString(FJML.dimension, FJML.y);
	        axis.setString(FJML.name, paramYName);

	        SElement settings = new SElement(FJML.GraphSettings);
	        graphElem.addContent(settings);
	        settings.setString(FJML.level, level);

	        SimpleQueryCallback callback = new SimpleQueryCallback();
	        Query query = new Query(queryElem, callback);
	        query.executeQuery();
	        SElement queryResult = callback.getResultElement();
	        fcmlQueryElem = queryResult.getChild(FEML.FcmlQuery);
	        if (fcmlQueryElem == null) return null;
	        graphElem = fcmlQueryElem.getChild(FJML.Graph);
	        if (graphElem == null) return null;
	        graphElem = graphElem.getChild(FJML.svg);
	        if (graphElem == null) return null;
	        List<List<Point>> result = new ArrayList<List<Point>>();
	        //int contourCount=graphElem.getChildren(FJML.path).size();
	        //contourCount= graphElem.getChildren(FJML.path).size();
	        
	        for (SElement pathElem : graphElem.getChildren(FJML.path))
	        {
	        	String pts = pathElem.getString(FJML.d);
		        if (pts.isEmpty())
		           continue;
		        List<Point> polyPts = new ArrayList<Point>();
		        result.add(polyPts);
		        StringTokenizer tokenizer = new StringTokenizer(pts);
		        while (tokenizer.hasMoreTokens())
	            {
	                String token = tokenizer.nextToken();
	                if ("M".equals(token) || ("L".equals(token)))
	                {
	                    String xVal = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
	                    String yVal = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
	                    if (xVal.isEmpty() || yVal.isEmpty())
	                        continue;
	                    polyPts.add(new Point(Integer.parseInt(xVal), Integer.parseInt(yVal)));
	                }
	            }
	        }
	        return result;
	    }
	
}
