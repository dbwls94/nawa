package org.nawa.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Conf {
	private static final Logger logger=LoggerFactory.getLogger(Conf.class);
	private static Properties props = new Properties();
	private static Properties dbProps = new Properties();

	/**
	 * @description nawa.properties, db.properties �κ��� ���������� �ҷ��´�.
	 */
	static {
		String nawaHomeStr = System.getProperty("nawa.home");
		if(nawaHomeStr == null){
			try {
				InputStream input = Conf.class.getClassLoader().getResourceAsStream("nawa.properties");
				props.load(input);
				input.close();
				logger.info("nawa.properties loaded");

				String dbPropsFileName = "db.properties";
				if("true".equals(System.getProperty("is.unit.test")))
					dbPropsFileName = "test_db.properties";
				
				input = Conf.class.getClassLoader().getResourceAsStream(dbPropsFileName);
				dbProps.load(input);
				input.close();
				logger.info(dbPropsFileName + " loaded");
			} catch (IOException e) {
				logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName()), e);
			} //catch
		} else{
			try{
				File confPath = new File(nawaHomeStr, "conf/nawa");
				File nawaPropsFile = new File(confPath, "nawa.properties");
				if(nawaPropsFile.exists() == false){
					logger.error("failed to load nawa.properties");
				} else{
					props.load(new FileInputStream(nawaPropsFile));
				} //if

				File dbPropsFile = new File(confPath, "db.properties");
				if(dbPropsFile.exists() == false){
					logger.error("failed to load db.properties");
				} else{
					dbProps.load(new FileInputStream(dbPropsFile));
				} //if
			} catch(IOException e){
				logger.error(String.format("%s, errmsg : %s", e.getClass().getSimpleName(), e.getMessage()), e);
			} //catch
		} //if
	} // static

	public static String get(String key) {
		return props.getProperty(key);
	} // get

	public static String get(String key, String defaultValue) {
		String value = props.getProperty(key, defaultValue);
		return value.trim().length() == 0 ? defaultValue : value;
	} // get

	public static void set(String key, String value) {
		props.setProperty(key, value);
	} // set

	public static Properties getDbProps() {
		return dbProps;
	} // getDbProps

	public static Properties getProps(){
		return props;
	} //getProps

	public static File getPackagePath() {
		String jarPath = Conf.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		File jarFile = new File(jarPath);
		File packagePath = jarFile.getParentFile();
		return packagePath;
	} // getPackagePath

	public static File getUserPicPath() { 
		String userPicPath = Conf.get("user.img.path");
		if(userPicPath == null || userPicPath.trim().length() == 0)
			return new File(System.getProperty("java.io.tmpdir"));
		return new File(userPicPath);
	} //getUserPicPath

	public static File getProjectPicPath(){
		String projectPicPath = Conf.get("project.img.path");
		if(projectPicPath == null || projectPicPath.trim().length() == 0)
			return new File(System.getProperty("java.io.tmpdir"));
		return new File(projectPicPath);
	} //getProjectPicPath
} // class