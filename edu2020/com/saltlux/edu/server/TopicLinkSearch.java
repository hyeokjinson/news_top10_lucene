package com.saltlux.edu.server;



import com.saltlux.dor.api.IN2TMSNewCluster;
import com.saltlux.dor.api.IN2TMSOldOwlimSearch;
import com.saltlux.dor.api.common.query.IN2BooleanQuery;
import com.saltlux.dor.api.common.query.IN2ParseQuery;


public class TopicLinkSearch {
	public static void main(String[] args) {
		IN2TMSOldOwlimSearch topicRank = new IN2TMSOldOwlimSearch();
		IN2BooleanQuery bQuery1=new IN2BooleanQuery();
		topicRank.setServer("127.0.0.1", 10000); 
		topicRank.addIndex("NEWS");
		topicRank.setSearchCount(200);
		topicRank.setLanguage("KOR");
		topicRank.setContentField("TMS_RAW_STREAM");
		topicRank.setQuery(new IN2ParseQuery(
				"TMS_RAW_STREAM", "금연", IN2TMSNewCluster.TOKENIZER_KOR_TOPIC));
		boolean istrue = topicRank.analyzeDocument();
		if(!istrue) { 
			System.out.println(topicRank.getLastErrorMessage());
		} 
		else { 
			System.out.println(topicRank.getTopicRank()); 
		}
	}
}
