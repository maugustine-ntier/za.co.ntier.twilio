package za.co.ntier.twilio.models;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.osgi.service.component.annotations.Component;

@Component(

		 property= {"service.ranking:Integer=2"},
		 service = org.adempiere.base.IModelFactory.class
		 )
public class TwilioModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		if (tableName.equals(X_TW_Message.Table_Name)) {
			return X_TW_Message.class;
		}
		
			
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		if (tableName.equals(X_TW_Message.Table_Name)) {
			return new X_TW_Message(Env.getCtx(),Record_ID,trxName);
		}
		
		
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		if (tableName.equals(X_TW_Message.Table_Name)) {
			return new X_TW_Message(Env.getCtx(),rs,trxName);
		}
		
			
		return null;
	}

}
