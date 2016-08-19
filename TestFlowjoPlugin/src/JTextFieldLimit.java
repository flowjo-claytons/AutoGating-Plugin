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
