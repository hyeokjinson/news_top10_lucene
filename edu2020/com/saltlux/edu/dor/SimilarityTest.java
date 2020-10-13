package com.saltlux.edu.dor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class SimilarityTest {
	public static double EachOtherSimilarity(Map<String,Double> res1,Map<String,Double> res2) {
		if(res1==null || res1.size()==0 ||res2==null|| res2.size()==0) return 0.0;
		
		Set<String>read=new HashSet<String>();
		double norm1=0.0, norm2=0.0,child=0.0;
		
		for (Entry<String,Double>e:res1.entrySet()) {
			norm1+=e.getValue()*e.getValue();
			Double value2=res2.get(e.getKey());
			if(value2!=null) child+=e.getValue()*value2;
			read.add(e.getKey());
		}
		for(Entry<String,Double>e:res2.entrySet()) {
			norm2+=e.getValue()*e.getValue();
			if(!read.contains(e.getKey())) {
				Double value1=res1.get(e.getKey());
				if(value1!=null) child+=value1*e.getValue();
			}
		}
		read.clear();
		return child/(Math.sqrt(norm1)*Math.sqrt(norm2));
	}
	public static Map<String,Double> createTermMap(String rest){
		Map<String,Double>resMap=new HashMap<>();
		for(String tm:rest.split(" ")) {
			Double score=resMap.get(tm);
			if(score==null) {
				score=1.0;
				
			}else {
				score=score+1.0;
			}
			resMap.put(tm, score);
		}
		return resMap;
	}
	public static void main(String[] args) {
		String res1t="니 이름이 뭐니?";
		String res2t="너 이름이 뭐니?";
		Map<String,Double>res1Map=createTermMap(res1t);
		Map<String,Double>res2Map=createTermMap(res2t);
		double cosscore=SimilarityTest.EachOtherSimilarity(res1Map,res2Map);
		
		System.out.println(cosscore);
	}
}
