package com.saltlux.edu.server;

import com.saltlux.dor.api.IN2TMSNETopicRank;
import com.saltlux.dor.api.IN2TMSNewCluster;
import com.saltlux.dor.api.common.query.IN2ParseQuery;

public class Search {
	public static void main(String[] args) {
		IN2TMSNewCluster cluster = new IN2TMSNewCluster();
		cluster.setServer("127.0.0.1", 10000); 
		cluster.setSearchCount(200); 
		cluster.setClusterLevel(2);
		cluster.setLanguage("KOR"); 
		cluster.setContentField("TMS_RAW_STREAM");
		cluster.setKeyField("_id");
		cluster.addIndex("news");
		IN2ParseQuery query = new IN2ParseQuery("TMS_RAW_STREAM", "야구", IN2TMSNETopicRank.TOKENIZER_KOR_TOPIC); 
		cluster.setQuery(query); System.out.println(cluster.getRequestData());
		boolean istrue = cluster.analyzeDocument();
		if(!istrue) { System.out.println(cluster.getLastErrorMessage()); 
		} 
		else {
		System.out.println(cluster.getClusterXmlData()); 
		String[][] aryCategory = cluster.getClusterInfo();
		
		String[] aryDocid = null;
		StringBuffer idOfdoc = new StringBuffer(); 
		StringBuffer result = new StringBuffer();
		
		for(int i=0; i < aryCategory.length; i++) {
			idOfdoc.setLength(0); aryDocid = cluster.getDocumentListofCluster(aryCategory[i][0]);
			for(int x=0; x< aryDocid.length; x++) { 
				if(x != 0) idOfdoc.append(","); 
				idOfdoc.append(aryDocid[x]); 
			}
			result.append("<cluster>");
			result.append("<id>" + aryCategory[i][0] + "</id>");
			result.append("<pid>" + aryCategory[i][1] + "</pid>");
			result.append("<name>" + aryCategory[i][2] + "</name>");
			result.append("<docnum>" + aryCategory[i][3] + "</docnum>");
			result.append("<level>" + aryCategory[i][4] + "</level>"); 
			result.append("<doclist>" + idOfdoc + "</doclist>");
			result.append("</cluster>");
			} 
		System.out.println(result);

		
	}
}
}
