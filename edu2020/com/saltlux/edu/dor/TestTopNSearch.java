package com.saltlux.edu.dor;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.saltlux.dor.api.IN2TopNSearch;

import com.saltlux.dor.api.common.query.IN2Query;
import com.saltlux.dor.api.common.query.IN2RangeQuery;

public class TestTopNSearch {
	public static void createCSV(String[][] keyword, String st, String et)
	{
		int count = 0;
		
		try {
			BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(st + "~" + et + ".csv"), "MS949"));
			
			for (int i = 0; i < keyword.length; i++)
			{
				fw.write(keyword[i][0] + "," + keyword[i][1]);
				fw.newLine();
				count++;
				
				System.out.println(keyword[i][0] + "\t" + keyword[i][1]);
				
				if (count % 100 == 0)
				{
					System.out.println("100 Complete");
				}
			}
			
			fw.flush();
			fw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void search() {
		IN2TopNSearch searcher=new IN2TopNSearch();
		String [] date= {"202001","202002","202003","202004","202005","202007","202008"};
		
		searcher.setServer("127.0.0.1",10000);
		
		searcher.addIndex("NEWS");
		searcher.setSearchCount(200);
		searcher.setLanguage("KOR");
		searcher.setContentField("TMS_RAW_STREAM");
		searcher.setQuery(IN2Query.MatchingAllDocQuery());
		
		
		for(int k=0;k<(date.length)-1;k++) {
		IN2RangeQuery query=IN2RangeQuery.newStringRange("DATE", date[k], date[k+1], false, true);
		searcher.setQuery(query);
		boolean flag=searcher.analyzeDocument();
		
		if(flag) {
			String [][] keywords=searcher.getSubjectInfo();
			for(int i=0; i<keywords.length;i++) {
				
				System.out.println(keywords[i][0]+"\t"+keywords[i][1]);
				createCSV(keywords,date[k],date[k+1]);
				
		
		        }
			
		}
			}
		}
	public static void main(String[] args) {
		search();
	}

}
