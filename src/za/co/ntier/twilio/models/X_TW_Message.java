/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package za.co.ntier.twilio.models;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for TW_Message
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="TW_Message")
public class X_TW_Message extends PO implements I_TW_Message, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20240530L;

    /** Standard Constructor */
    public X_TW_Message (Properties ctx, int TW_Message_ID, String trxName)
    {
      super (ctx, TW_Message_ID, trxName);
      /** if (TW_Message_ID == 0)
        {
			setName (null);
			setTW_Message_ID (0);
			setTwilio_Message_Type (null);
        } */
    }

    /** Standard Constructor */
    public X_TW_Message (Properties ctx, int TW_Message_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, TW_Message_ID, trxName, virtualColumns);
      /** if (TW_Message_ID == 0)
        {
			setName (null);
			setTW_Message_ID (0);
			setTwilio_Message_Type (null);
        } */
    }

    /** Standard Constructor */
    public X_TW_Message (Properties ctx, String TW_Message_UU, String trxName)
    {
      super (ctx, TW_Message_UU, trxName);
      /** if (TW_Message_UU == null)
        {
			setName (null);
			setTW_Message_ID (0);
			setTwilio_Message_Type (null);
        } */
    }

    /** Standard Constructor */
    public X_TW_Message (Properties ctx, String TW_Message_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, TW_Message_UU, trxName, virtualColumns);
      /** if (TW_Message_UU == null)
        {
			setName (null);
			setTW_Message_ID (0);
			setTwilio_Message_Type (null);
        } */
    }

    /** Load Constructor */
    public X_TW_Message (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_TW_Message[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Message.
		@param Message Message
	*/
	public void setMessage (String Message)
	{
		set_Value (COLUMNNAME_Message, Message);
	}

	/** Get Message.
		@return Message
	  */
	public String getMessage()
	{
		return (String)get_Value(COLUMNNAME_Message);
	}

	/** Set Name.
		@param Name Alphanumeric identifier of the entity
	*/
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName()
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Send Message.
		@param Send_Message Send Message
	*/
	public void setSend_Message (String Send_Message)
	{
		set_Value (COLUMNNAME_Send_Message, Send_Message);
	}

	/** Get Send Message.
		@return Send Message	  */
	public String getSend_Message()
	{
		return (String)get_Value(COLUMNNAME_Send_Message);
	}

	/** Set Twilio Message.
		@param TW_Message_ID Twilio Message
	*/
	public void setTW_Message_ID (int TW_Message_ID)
	{
		if (TW_Message_ID < 1)
			set_ValueNoCheck (COLUMNNAME_TW_Message_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_TW_Message_ID, Integer.valueOf(TW_Message_ID));
	}

	/** Get Twilio Message.
		@return Twilio Message	  */
	public int getTW_Message_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TW_Message_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set TW_Message_UU.
		@param TW_Message_UU TW_Message_UU
	*/
	public void setTW_Message_UU (String TW_Message_UU)
	{
		set_Value (COLUMNNAME_TW_Message_UU, TW_Message_UU);
	}

	/** Get TW_Message_UU.
		@return TW_Message_UU	  */
	public String getTW_Message_UU()
	{
		return (String)get_Value(COLUMNNAME_TW_Message_UU);
	}

	/** SMS = S */
	public static final String TWILIO_MESSAGE_TYPE_SMS = "S";
	/** Whatsapp = W */
	public static final String TWILIO_MESSAGE_TYPE_Whatsapp = "W";
	/** Set Twilio Message Type.
		@param Twilio_Message_Type Twilio Message Type
	*/
	public void setTwilio_Message_Type (String Twilio_Message_Type)
	{

		set_Value (COLUMNNAME_Twilio_Message_Type, Twilio_Message_Type);
	}

	/** Get Twilio Message Type.
		@return Twilio Message Type	  */
	public String getTwilio_Message_Type()
	{
		return (String)get_Value(COLUMNNAME_Twilio_Message_Type);
	}
}