package com.saltlux.edu.dor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonTest {

	public static final class NewsInfo {
		public String title;
		public String content;
		public int date;
		public String id;
	}
	
	public static void main(String[] args) {
		
		NewsInfo info  = new NewsInfo();
		info.title = "오늘 강의가 힘들어요.";
		info.content  ="내일은 조금 쉬울라나....";
		info.id = "23424";
		info.date = 20200918;
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String jsonStr = gson.toJson(info);
		System.out.println(jsonStr);
		
		NewsInfo info2 = gson.fromJson(jsonStr, NewsInfo.class);
		
		System.out.println(info2.content);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
