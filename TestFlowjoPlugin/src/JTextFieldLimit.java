
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument{

	/*
	 * Contructor takes limit that textfield must be restrained to
	 */
	private static final long serialVersionUID = -1599481899967153961L;
	private int limit;
	JTextFieldLimit(int lim)
	{
		super();
		this.limit=lim;
	}
	/*
	 * This method tracks the text field input and limits its range to 3 characters.
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
