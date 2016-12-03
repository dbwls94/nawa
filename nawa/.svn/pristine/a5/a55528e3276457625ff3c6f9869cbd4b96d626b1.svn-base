package org.nawa.daomapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nawa.rdb.ConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonMapper extends AbstractMapper{
	private static final Logger logger = LoggerFactory.getLogger(JsonMapper.class);
	
	public JSONObject select(String query, Object parameters){
		logger.info("query : {}, parameters : {}", query, parameters);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = (Connection) ConnectionSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt = fillPreparedStatementParameters(pstmt, parameters);
			rs = pstmt.executeQuery();
			return mapJson(rs);
		} catch (Exception e) {
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
			return null;
		} finally {
			if(rs!=null)
				try { rs.close(); } catch (Exception ignored) {}
			if(pstmt!=null)
				try { pstmt.close(); } catch (Exception ignored) { }
			if(conn!=null)
				try { conn.close(); } catch (Exception ignored) { } 
		} //finally
	} //select
	
	public JSONArray selectList(String query, Object... parameters){
		logger.info("query : {}, parameters : {}", query, parameters);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = (Connection) ConnectionSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt = fillPreparedStatementParameters(pstmt, parameters);
			rs = pstmt.executeQuery();
			return mapJsonArray(rs);
		} catch (Exception e) {
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
			return null;
		} finally {
			if(rs!=null)
				try { rs.close(); } catch (Exception ignored) {}
			if(pstmt!=null)
				try { pstmt.close(); } catch (Exception ignored) { }
			if(conn!=null)
				try { conn.close(); } catch (Exception ignored) { } 
		} //finally
	} //select
	
	private JSONArray mapJsonArray(ResultSet rs) throws DaoMapperException{
		JSONArray retJsonArray = new JSONArray();

		try {
			while(rs.next())
				retJsonArray.put(mapSingleRow(rs));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SQLException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new DaoMapperException(e);
		} //catch
		
		return retJsonArray;
	} //mapJsonArray
	
	private JSONObject mapJson(ResultSet rs) throws DaoMapperException {
		try {
			if (rs.next() == false)
				return null;
		} catch (SQLException e) {
			throw new DaoMapperException(e);
		} // catch

		try {
			return mapSingleRow(rs);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SQLException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new DaoMapperException(e);
		} // catch
	} // map
	
	private JSONObject mapSingleRow(ResultSet rs) throws InstantiationException, IllegalAccessException, IllegalArgumentException, SQLException, InvocationTargetException, NoSuchMethodException, SecurityException{
		JSONObject retJsonObject = new JSONObject();
		ResultSetMetaData meta = rs.getMetaData();
		
		for (int i = 0; i < meta.getColumnCount(); i++) {
			String columnName = meta.getColumnLabel(i+1);
			retJsonObject.put(columnName, rs.getObject(i+1));
		} //for i
		
		return retJsonObject;
	} //mapSingleRow
} //class