package org.nawa.daomapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.nawa.rdb.ConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoMapper<T> extends AbstractMapper{
	private static final Logger logger = LoggerFactory.getLogger(DaoMapper.class);
	
	public T select(Class<T> clazz, String query, Object... parameters){
		logger.info("query : {}, parameters : {}", query, parameters);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = (Connection) ConnectionSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt = fillPreparedStatementParameters(pstmt, parameters);
			rs = pstmt.executeQuery();
			return mapDao(rs, clazz);
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
	
	public List<T> selectList(Class<T> clazz, String query, Object... parameters){
		logger.info("query : {}, parameters : {}", query, parameters);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = (Connection) ConnectionSource.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt = fillPreparedStatementParameters(pstmt, parameters);
			rs = pstmt.executeQuery();
			return mapDaoList(rs, clazz);
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
	} //selectList

	private T mapDao(ResultSet rs, Class<T> clazz) throws DaoMapperException {
		try {
			if (rs.next() == false)
				return null;
		} catch (SQLException e) {
			throw new DaoMapperException(e);
		} // catch

		try {
			return mapSingleRow(rs, clazz);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SQLException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new DaoMapperException(e);
		} // catch
	} // map

	private List<T> mapDaoList(ResultSet rs, Class<T> clazz) throws DaoMapperException {
		List<T> daoList = new ArrayList<T>();

		try {
			while(rs.next())
				daoList.add(mapSingleRow(rs, clazz));
		} catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | SQLException | InvocationTargetException | NoSuchMethodException e) {
			throw new DaoMapperException(e);
		} //catch

		return daoList;
	} // mapDaoList

	private T mapSingleRow(ResultSet rs, Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, SQLException, InvocationTargetException, NoSuchMethodException, SecurityException{
		T obj = clazz.getConstructor().newInstance();
		for (Field field : clazz.getDeclaredFields()) {
			Mapped mapped = field.getAnnotation(Mapped.class);
			if (mapped == null)
				continue;

			field.setAccessible(true);
			String colName = mapped.column();
			Class<?> fieldType = field.getType();

			if (fieldType == Byte.class || fieldType == byte.class) {
				field.set(obj, rs.getByte(colName));
			} else if (fieldType == String.class) {
				field.set(obj, rs.getString(colName));
			} else if (fieldType == Date.class) {
				Timestamp timestamp = rs.getTimestamp(colName);
				field.set(obj, new Date(timestamp.getTime()));
			} else if (fieldType == Double.class || fieldType == double.class) {
				field.set(obj, rs.getDouble(colName));
			} else if (fieldType == Float.class || fieldType == float.class) {
				field.set(obj, rs.getFloat(colName));
			} else if (fieldType == Integer.class || fieldType == int.class) {
				field.set(obj, rs.getInt(colName));
			} else if (fieldType == Long.class || fieldType == long.class) {
				field.set(obj, rs.getLong(colName));
			} // if
		} // for field
		return obj;
	} //mapSingleRow
} // class