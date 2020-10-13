package com.saltlux.edu.dor;

import com.saltlux.dor.api.IN2StdSearcher;
import com.saltlux.dor.api.common.GeoPoint;
import com.saltlux.dor.api.common.filter.IN2Filter;
import com.saltlux.dor.api.common.filter.IN2GeoSpatialFilter;
import com.saltlux.dor.api.common.query.IN2Query;
import com.saltlux.dor.api.common.sort.IN2GeoDistanceSort;

public class GeoSearch {
	public static void main(String[] args) throws Exception{
		IN2StdSearcher searcher=new IN2StdSearcher();
		searcher.setServer("127.0.0.1", 10000);
		searcher.newQuery();
		searcher.addIndex("GEO");
		
		searcher.setReturnPositionCount(0, 10);
		searcher.setQuery(IN2Query.MatchingAllDocQuery());
		GeoPoint p1=new GeoPoint(126.9860491,37.5729734);
		double km=GeoPoint.MileToKm(0.01);
		IN2Filter filter=new IN2GeoSpatialFilter(p1,km);
		searcher.setFilter(filter);
		searcher.addReturnField(new String[] {"[DOCUMENT_NO]","[SCORE]","[GEO]","NAME"});
		searcher.setSort(new IN2GeoDistanceSort(p1,km,true));
		
		searcher.searchDocument();
		
		long currCount=searcher.getDocumentCount();
		for(int i=0;i<currCount;i++) {
			String id=searcher.getValueInDocument(i, "[DOCUMENT_NO]");
			String name=searcher.getValueInDocument(i,"NAME");
			String geo=searcher.getValueInDocument(i, "[GEO]");
			String distance=Double.toString(GeoPoint.getDistance(p1,geo));
			String score=searcher.getValueInDocument(i, "[SCORE]");
			System.out.println(id+"\t"+name+"\t"+geo+"\t"+distance+"\t"+score);
		}
		
	}
}
