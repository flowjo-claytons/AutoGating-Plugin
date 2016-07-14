import java.awt.Component;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;

public class FJToolTipRenderer  extends DefaultListCellRenderer{

	Vector<String> toolTip;
	@Override
	public Component getListCellRendererComponent(JList list, Object value,int index, boolean selected, boolean hasFocus)
	{
		JComponent comp = (JComponent)super.getListCellRendererComponent(list, value, index, selected, hasFocus);
		if (-1 < index && null != value && null != toolTip){
			list.setToolTipText(toolTip.get(index));
		}
		return comp;
		
	}
	public void setToolTips(Vector<String> tooltips)
	{
		this.toolTip= tooltips;
	}
	
}
