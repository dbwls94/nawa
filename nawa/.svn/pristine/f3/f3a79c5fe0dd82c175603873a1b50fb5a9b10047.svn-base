package org.nawa.rdb;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.nawa.common.Conf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionSource {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionSource.class);
	private static BasicDataSource dataSource = null;
	private static JsonJdbcTemplate jdbcTemplate = null;

	static {
		try {
			dataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(Conf.getDbProps());
			jdbcTemplate = new JsonJdbcTemplate(dataSource);
		} catch (Exception e) {
			logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
		} // catch
	} // static
	
	/**
	 * @description connection pool (dataSource)���� connection�� ���� ��ȯ�Ѵ�.
	 * @return	database connection
	 * @throws SQLException
	 */
	public static synchronized Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	} //getConnection
	
	public static JsonJdbcTemplate getJdbcTemplate(){
		return jdbcTemplate;
	} //getJdbcTemplate
} // class