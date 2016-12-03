package org.nawa.daomapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public abstract class AbstractMapper {
	protected PreparedStatement fillPreparedStatementParameters(PreparedStatement pstmt, Object... parameters) throws SQLException{
		for (int i = 0; i < parameters.length; i++) {
			Class<?> paramType=parameters[i].getClass();
			Object paramValue=parameters[i];

			if(paramType == Byte.class){
				pstmt.setByte(i+1, (byte) paramValue);
			} else if(paramType == Date.class){
				Timestamp timestamp = new Timestamp(((Date)paramValue).getTime());
				pstmt.setTimestamp(i+1, timestamp);
			} else if(paramType == Double.class){
				pstmt.setDouble(i+1, (double) paramValue);
			} else if(paramType == Float.class){
				pstmt.setFloat(i+1, (float) paramValue);
			} else if(paramType == Integer.class){
				pstmt.setInt(i+1, (int) paramValue);
			} else if(paramType == Long.class){
				pstmt.setLong(i+1, (long) paramValue);
			} else {
				pstmt.setString(i+1, paramValue.toString());
			} //if
		} //for i
		return pstmt;
	} //fillPreparedStatementParameters
} //class