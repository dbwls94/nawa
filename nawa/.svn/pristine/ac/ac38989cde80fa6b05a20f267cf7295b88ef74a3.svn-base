package org.nawa.common;

import java.util.ArrayList;
import java.util.List;

public class MysqlUtil {
	public static String makeRegexpKeyword(String keyword) {
		String[] splitedKeywords = keyword.split(" ");
		StringBuilder keywordSb = new StringBuilder();
		for (int i = 0; i < splitedKeywords.length; i++) {
			keywordSb.append(splitedKeywords[i]);
			if (i != splitedKeywords.length - 1)
				keywordSb.append("|");
		} // for i
		return keywordSb.toString();
	} // makeRegexpKeyword
	
	public static List<String> makeConditionString4Search(String keyword){
		String[] splitedKeywords = keyword.split(" ");
		List<String> returnWords = new ArrayList<String>();
		
		for (String splitedKeyword : splitedKeywords)
			returnWords.add("%" + splitedKeyword + "%");
		
		return returnWords;
	} //makeConditionString4Search
} // class