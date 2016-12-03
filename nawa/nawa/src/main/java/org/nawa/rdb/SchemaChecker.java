package org.nawa.rdb;

import java.util.HashSet;
import java.util.Set;

import org.nawa.common.Conf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.BadSqlGrammarException;

/**
 * @description ???? ?????? ?????????? ????? ???? ???θ? ?????? ?????? ???? ???????.
 * @author leejy
 * @date 2015. 01. 24
 */
public class SchemaChecker {
	private static final Logger logger=LoggerFactory.getLogger(SchemaChecker.class);
	
	public static String createUserInfo="create table user_info ("
			+ "email varchar(50) primary key,"
			+ "passwd varchar(128) not null,"
			+ "name varchar(20) not null,"
			+ "gender tinyint not null,"
			+ "regdate datetime not null,"
			+ "facebook_access_token varchar(100) ) "
			+ "ENGINE=MYISAM DEFAULT CHARSET=utf8";
	
	public static String createProjectInfo="create table project_info ("
			+ "id varchar(36) primary key,"
			+ "title varchar(200) not null,"
			+ "description text,"
			+ "description_title varchar(200),"
			+ "leader_email varchar(50) not null,"
			+ "regdate datetime not null,"
			+ "place varchar(100),"
			+ "meeting_date date,"
			+ "meeting_time varchar(30),"
			+ "max_user_count tinyint ,"
			+ "recruit_due_date datetime, "
			+ "long_project tinyint not null) ENGINE=MYISAM DEFAULT CHARSET=utf8";
		
	public static String createUserParticipatedProject="create table user_participated_project ("
			+ "project_id varchar(36) not null,"
			+ "user_email varchar(50) not null,"
			+ "regdate varchar(50) not null,"
			+ "leader_accept tinyint) ENGINE = MYISAM DEFAULT CHARSET=utf8";
	
	public static String createProjectReply="create table project_reply ("
			+ "seq bigint not null,"
			+ "project_id varchar(36) not null, "
			+ "user_email varchar(50) not null, "
			+ "depth varchar(100), "
			+ "regdate datetime not null,"
			+ "content text,"
			+ "constraint pk primary key (seq, project_id) ) ENGINE = InnoDB DEFAULT CHARSET=utf8";
	
	public static String createUserFriend = "create table user_friend ("
			+ "user varchar(50) not null,"
			+ "to_user varchar(50) not null ) ENGINE=MYISAM DEFAULT CHARSET=utf8";
	
	public static String createNotification = "create table notification ("
			+ "noti_id varchar(36) primary key, "
			+ "type tinyint not null, "
			+ "owner varchar(50) not null, "
			+ "is_read tinyint not null, "
			+ "regdate datetime not null, "
			+ "additional_data1 varchar(200),"
			+ "additional_data2 varchar(200),"
			+ "additional_data3 varchar(200),"
			+ "additional_data4 varchar(200),"
			+ "additional_data5 varchar(200) ) ENGINE=MYISAM DEFAULT CHARSET=utf8";
	
	public static String createProjectInvitaion = "create table project_invitation ("
			+ "host_email varchar(50) not null, "
			+ "guest_email varchar(50) not null, "
			+ "project_id varchar(36) not null, "
			+ "regdate datetime not null,"
			+ "constraint pk primary key (host_email, guest_email, project_id) ) ENGINE=MYISAM DEFAULT CHARSET=utf8";
	
	public static void check(){
		String database = Conf.getDbProps().getProperty("database");
		logger.info("start to check tables for database : " + database);
		String[][] result=DbHandler.select(String.format("select table_name from information_schema.tables where table_schema='%s'", database));
		
		Set<String> existingTables=new HashSet<String>();
		if(result!=null && result.length!=0)
			for(String[] row : result)
				existingTables.add(row[0].toLowerCase());
		
		if(existingTables.contains("user_info")==false)
			DbHandler.update(createUserInfo);
		if(existingTables.contains("project_info")==false)
			DbHandler.update(createProjectInfo);
		if(existingTables.contains("user_participated_project")==false)
			DbHandler.update(createUserParticipatedProject);
		if(existingTables.contains("project_reply")==false)
			DbHandler.update(createProjectReply);
		if(existingTables.contains("user_friend")==false)
			DbHandler.update(createUserFriend);
		if(existingTables.contains("notification")==false)
			DbHandler.update(createNotification);
		if(existingTables.contains("project_invitation")==false)
			DbHandler.update(createProjectInvitaion);
		
		logger.info("finish to check tables");
	} //check
	
	public static void dropAllTables(){
		String[] queries = new String[]{
		"drop table user_info",
		"drop table project_info",
		"drop table user_participated_project",
		"drop table project_reply",
		"drop table user_friend",
		"drop table notification",
		"drop table project_invitation"};
		
		for(String query : queries){
			try{
				ConnectionSource.getJdbcTemplate().update(query);
			} catch(BadSqlGrammarException e){}
		} //for query
	} //dropAllTables
	
	public static void main(String[] args) {
		check();
	} //main
} //class
