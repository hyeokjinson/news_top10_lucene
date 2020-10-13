package com.saltlux.edu.dor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.saltlux.dor.api.IN2StdIndexer;

public class NewsIndex {

  private String indexerIp;
  private int indexerPort;
  
  public void setServer(String indexerIp, int indexerPort) {
    this.indexerIp = indexerIp;
    this.indexerPort = indexerPort;
  }
  
  public class NewsInfo {
    StringBuilder createDate = new StringBuilder();
    StringBuilder title = new StringBuilder();
    StringBuilder content = new StringBuilder();
  }
  
  private void indexDocument(NewsInfo info) {
    if (info == null) {
      return;
    }
    
    IN2StdIndexer indexer = new IN2StdIndexer();
    indexer.setServer(indexerIp, indexerPort);
    
    indexer.setIndex("NEWS");
    indexer.addSource("DATE", info.createDate.toString(), IN2StdIndexer.SOURCE_TYPE_TEXT);
    indexer.addSource("TITLE", info.title.toString(), IN2StdIndexer.SOURCE_TYPE_TEXT);
    indexer.addSource("CONTENT", info.content.toString(), IN2StdIndexer.SOURCE_TYPE_TEXT);
    
    indexer.addFieldFTR("DATE", "DATE", IN2StdIndexer.TOKENIZER_TERM, true, true);
    indexer.addFieldFTR("TITLE", "TITLE", IN2StdIndexer.TOKENIZER_KOR_BIGRAM, true, true);
    indexer.addFieldFTR("CONTENT", "CONTENT", IN2StdIndexer.TOKENIZER_KOR_BIGRAM, true, true);
    
    indexer.addFieldTMS("TMS", "KOR", "CONTENT", true, 30);
    
    boolean flag = indexer.addDocument();
    if (flag == false) {
      System.out.println(indexer.getLastErrorMessage());
    }
  }
  
  public void index(String filename) throws Exception {
    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
    
    String line = null;
    NewsInfo info = null;
    int contentCase = -1;
    while((line = reader.readLine()) != null) {
      line = line.trim();
      
      if (line.length() == 0) {
        continue;
      }
      
      if (line.equals("<CREATE_DATE>")) {
        indexDocument(info);
        info = new NewsInfo();
        contentCase = 1;
      } else if (line.equals("<TITLE>")) {
        contentCase = 2;
      } else if (line.equals("<CONTENT>")) {
        contentCase = 3;
      } else {
        if (contentCase == 1) {
          info.createDate.append(line);
        } else if (contentCase == 2) {
          info.title.append(line);
        } else {
          info.content.append(line).append('\n');
        }
      }
    }
    indexDocument(info);
    
    reader.close();
  }
  
  public static void main(String[] args) throws Exception {
    
    String filename = "C:\\Users\\user\\Desktop\\Edu2020\\Edu2020\\temp\\news_sample.dat";
    
    NewsIndex runner = new NewsIndex();
    runner.setServer("127.0.0.1", 10200);
    
    runner.index(filename);
  }
}
