package org.nawa.rdb;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author m������
 *
 */
public class DbHandler {
	private static final Logger logger=LoggerFactory.getLogger(DbHandler.class);
	
	private DbHandler(){
	} //INIT
	
	/**
	 * @description insert, update, delete ������ �����Ѵ�.
	 * @param query ������ insert, update, delete ����
	 */
	public static int update(String query)
	{
		logger.info("query : {}", query);
		
		Connection conn = null;
		Statement stmt = null;		

		try
		{
			conn = (Connection) ConnectionSource.getConnection();
			stmt = (Statement) conn.createStatement();
			return stmt.executeUpdate(query); //���� ���ϰ��� �޾ƾ��Ѵ�.
		} 
		catch (Exception e)
		{
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
			e.printStackTrace();
			return -1; 
		}
		finally
		{
			try 
			{ 
				stmt.close(); 
			} 
			catch (Exception ignored) 
			{ }
			try 
			{ 
				conn.close(); 
			} 
			catch (Exception ignored) 
			{ } 
		}
	} //update, insert, delete

	/**
	 * @description select ������ ������ �� ��ȸ�� ����� String[][] Ÿ������ ��ȯ�Ѵ�.
	 * @param query ������ select ����
	 * @return
	 */
	public static String[][] select(String query)
	{
		logger.info("query : {}", query);
		
		Connection conn = null;
		Statement stmt = null;

		ArrayList<String[]> list=new ArrayList<String[]>();

		try
		{
			conn = (Connection) ConnectionSource.getConnection();
			stmt = conn.createStatement();

			//Ư�� ������ �������� ���ؼ�
			ResultSet rs = stmt.executeQuery(query);

			ResultSetMetaData meta= rs.getMetaData();
			int c = meta.getColumnCount();

			while(rs.next()) //�ش�Ǵ� �����Ͱ� �־ ������
			{
				String[] cols=new String[c];
				for(int i=0; i<c; i++)
				{
					cols[i] = rs.getString(i+1);
				}
				list.add(cols);
			}

			String[][] returnArray = new String[list.size()][];

			for (int i = 0; i < returnArray.length; i++)
			{
				returnArray[i]=list.get(i);
			}

			return returnArray;
		}
		catch (Exception e)
		{
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
			e.printStackTrace();
			return null;
		}
		finally
		{
			try 
			{ stmt.close(); } 
			catch (Exception ignored) 
			{ }
			try 
			{ conn.close(); } 
			catch (Exception ignored) 
			{ } 
		}
		//return returnArray;
	} //select
	
	public static void preparedSelect(ResultSetMapper mapper, String query, Object... parameters){
		logger.info("query : {}, parameters : {}", query, parameters);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = (Connection) ConnectionSource.getConnection();
			stmt = conn.prepareStatement(query);
	
			for (int i = 0; i < parameters.length; i++) {
				Class<?> paramType=parameters[i].getClass();
				Object paramValue=parameters[i];
				
				if(paramType == Byte.class){
					stmt.setByte(i+1, (byte) paramValue);
				} else if(paramType == Date.class){
					stmt.setDate(i+1, (Date) paramValue);
				} else if(paramType == Double.class){
					stmt.setDouble(i+1, (double) paramValue);
				} else if(paramType == Float.class){
					stmt.setFloat(i+1, (float) paramValue);
				} else if(paramType == Integer.class){
					stmt.setInt(i+1, (int) paramValue);
				} else if(paramType == Long.class){
					stmt.setLong(i+1, (long) paramValue);
				} else {
					stmt.setString(i+1, paramValue.toString());
				} //if
			} //for i
			
			//Ư�� ������ �������� ���ؼ�
			rs = stmt.executeQuery();
			mapper.map(rs);
		}
		catch (Exception e) {
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
		}
		finally {
			if(rs!=null)
				try { rs.close(); } catch (Exception ignored) {}
			if(stmt!=null)
				try { stmt.close(); } catch (Exception ignored) { }
			if(conn!=null)
				try { conn.close(); } catch (Exception ignored) { } 
		}
		//return returnArray;	
		
	} //preparedSelect

	public static String[][] preparedSelect(String query, Object... parameters){
		logger.info("query : {}, parameters : {}", query, parameters);
		
		Connection conn = null;
		PreparedStatement stmt = null;

		ArrayList<String[]> list=new ArrayList<String[]>();

		try {
			conn = (Connection) ConnectionSource.getConnection();
			stmt = conn.prepareStatement(query);
	
			for (int i = 0; i < parameters.length; i++) {
				Class<?> paramType=parameters[i].getClass();
				Object paramValue=parameters[i];
				
				if(paramType == Byte.class){
					stmt.setByte(i+1, (byte) paramValue);
				} else if(paramType == Date.class){
					stmt.setDate(i+1, (Date) paramValue);
				} else if(paramType == Double.class){
					stmt.setDouble(i+1, (double) paramValue);
				} else if(paramType == Float.class){
					stmt.setFloat(i+1, (float) paramValue);
				} else if(paramType == Integer.class){
					stmt.setInt(i+1, (int) paramValue);
				} else if(paramType == Long.class){
					stmt.setLong(i+1, (long) paramValue);
				} else {
					stmt.setString(i+1, paramValue.toString());
				} //if
			} //for i
			
			//Ư�� ������ �������� ���ؼ�
			ResultSet rs = stmt.executeQuery();

			ResultSetMetaData meta= rs.getMetaData();
			int c = meta.getColumnCount();

			while(rs.next()) //�ش�Ǵ� �����Ͱ� �־ ������
			{
				String[] cols=new String[c];
				for(int i=0; i<c; i++)
				{
					cols[i] = rs.getString(i+1);
				}
				list.add(cols);
			}

			String[][] returnArray = new String[list.size()][];

			for (int i = 0; i < returnArray.length; i++)
			{
				returnArray[i]=list.get(i);
			}

			return returnArray;
		}
		catch (Exception e)
		{
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
			e.printStackTrace();
			return null;
		}
		finally
		{
			try 
			{ stmt.close(); } 
			catch (Exception ignored) 
			{ }
			try 
			{ conn.close(); } 
			catch (Exception ignored) 
			{ } 
		}
		//return returnArray;	
	} //preparedSelect
	
	public static boolean preparedUpdate(String query, Object... parameters){
		logger.info("query : {}, parameters : {}", query, parameters);
		
		Connection conn = null;
		PreparedStatement stmt = null;

		try{
			conn = ConnectionSource.getConnection();
			stmt = conn.prepareStatement(query);		
			
			for (int i = 0; i < parameters.length; i++) {
				if(parameters[i]==null){
					stmt.setNull(i+1, Types.VARCHAR);
					continue;
				} //if
				
				Class<?> paramType=parameters[i].getClass();
				Object paramValue=parameters[i];
				
				if(paramType == Byte.class){
					stmt.setByte(i+1, (byte) paramValue);
				} else if(paramType == java.util.Date.class){
//					stmt.setDate(i+1, (Date) paramValue);
					java.util.Date dateParamValue=(java.util.Date) paramValue;
					Timestamp timestampParamValue=new Timestamp(dateParamValue.getTime());
					stmt.setTimestamp(i+1, timestampParamValue);
				} else if(paramType == Double.class){
					stmt.setDouble(i+1, (double) paramValue);
				} else if(paramType == Float.class){
					stmt.setFloat(i+1, (float) paramValue);
				} else if(paramType == Integer.class){
					stmt.setInt(i+1, (int) paramValue);
				} else if(paramType == Long.class){
					stmt.setLong(i+1, (long) paramValue);
				} else {
					stmt.setString(i+1, paramValue.toString());
				} //if
			} //for i
			
			stmt.executeUpdate();
			return true;
		} catch(Exception e){
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
			return false;
		} finally{
			if(stmt!=null){
				try { stmt.close(); } catch (SQLException e) { }
			} //if
			if(conn!=null){
				try { conn.close(); } catch (SQLException e) { }
			} //if
		} //finally
	} //preparedUpdate
} //class