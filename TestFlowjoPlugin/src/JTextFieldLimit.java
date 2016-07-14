
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

/**
 * Gating Plugin: Creates contour gates based on user specified percentage 
 * @author kelly
 *	7/13/2016
 */
public class JTextFieldLimit extends PlainDocument{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1599481899967153961L;
	/**
	 * 
	 */
	private int limit;
	/**
	 * Contructor takes limit that textfield must be restrained to
	 * @param lim
	 */
	JTextFieldLimit(int lim)
	{
		super();
		this.limit=lim;
	}

	/** (non-Javadoc)
	 * @see javax.swing.text.PlainDocument#insertString(int, java.lang.String, javax.swing.text.AttributeSet)
	 */
	public void insertString(int offset, String str, AttributeSet attr)
	{
		if(str == null)return;
		try{
			if ((getLength()+ str.length())<=limit && Integer.parseInt(str)< 100){
				super.insertString(offset, str, attr);
			}
		}
		catch(Exception e)
		{
		}
	}
	
}
