package com.saltlux.edu.dor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.saltlux.dor.api.IN2StdIndexer;
import com.saltlux.dor.api.common.GeoPoint;

public class GeoIndexer {
	private String severIp;
	private int serverPort;
	
	public void setServer(String Ip,int Port) {
		this.severIp=Ip;
		this.serverPort=Port;
	}
	public void index(String IndexName,String FileName) {
		BufferedReader reader=null;
		
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(FileName),"UTF-8"));
			
			String line=reader.readLine();
			int i=0;
			while((line=reader.readLine())!=null) {
				String[] sp=line.split("\t");
				if(sp.length!=5) continue;
				
				IN2StdIndexer indexer=new IN2StdIndexer();
				indexer.setServer(severIp, serverPort);
				indexer.newDocument();
				
				indexer.setIndex(IndexName);
				
				indexer.addSource("ID", sp[0],IN2StdIndexer.SOURCE_TYPE_TEXT);
				indexer.addSource("NAME", sp[1],IN2StdIndexer.SOURCE_TYPE_TEXT);
				indexer.addSource("ADDRESS", sp[2],IN2StdIndexer.SOURCE_TYPE_TEXT);
				
				indexer.addFieldFTR("ID", "ID", IN2StdIndexer.TOKENIZER_TERM, true, true);
				indexer.addFieldFTR("NAME", "NAME", IN2StdIndexer.TOKENIZER_BIGRAM, true, true);
				indexer.addFieldFTR("ADDRESS", "ADDRESS", IN2StdIndexer.TOKENIZER_TERM, true, true);
			
				indexer.setGeospatial(new GeoPoint(Double.parseDouble(sp[4]),Double.parseDouble(sp[3])));
				i++;
				
				if(!indexer.addDocument()) {
					System.out.println(indexer.getLastErrorMessage());
					break;
				}
				else {
					String warningMessage=indexer.getLastErrorMessage();
					System.out.println("ADD DOCUMENT"+i);
					if(warningMessage.length()>0) System.out.println(warningMessage);
				}
			}
			reader.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		GeoIndexer runner=new GeoIndexer();
		runner.setServer("127.0.0.1", 10200);
		runner.index("GEO", "edudata/geoinfo.txt");
	}
}
