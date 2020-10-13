package com.saltlux.edu.dor;


import com.saltlux.dor.api.IN2TMSSubjectSearch;
import com.saltlux.dor.api.common.query.IN2ParseQuery;


public class TestSubjectSearch {
	public static void main(String[] args) {
		
		IN2TMSSubjectSearch searcher=new IN2TMSSubjectSearch();
		searcher.setServer("127.0.0.1",10000);
		searcher.addIndex("NEWS");
		searcher.setSearchCount(200);
		searcher.setResultCount(20);
		searcher.setLanguage("KOR");
		searcher.setContentField("TMS_RAW_STREAM");
		
		 IN2ParseQuery query=new IN2ParseQuery("CONTENT", "금연", IN2TMSSubjectSearch.TOKENIZER_KOR_BIGRAM);
		 searcher.setQuery(query);
		 
		 if(!searcher.analyzeDocument()) {
			 System.out.println(searcher.getLastErrorMessage());
		 }
		 else {
			 String[][]resultlist=searcher.getSubjectInfo();
			 System.out.println(resultlist.length);
			 for (int i=0; i<resultlist.length; i++) {
				 System.out.println(resultlist[i][0]+"{wgt"+resultlist[i][1]+"}");
			 }
					 
		 }
	}
}
