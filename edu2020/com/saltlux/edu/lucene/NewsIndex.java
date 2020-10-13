package com.saltlux.edu.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.saltlux.dor.api.IN2StdIndexer;

public class NewsIndex {

  public static IndexWriterConfig createConfig() throws IOException {
	    WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer();
	    IndexWriterConfig wconf = new IndexWriterConfig(Version.LATEST, analyzer);
	    wconf.setOpenMode(OpenMode.CREATE_OR_APPEND);
	    return wconf;
	  }
  
  public class NewsInfo {
    StringBuilder createDate = new StringBuilder();
    StringBuilder title = new StringBuilder();
    StringBuilder content = new StringBuilder();
  }

  private IndexWriter writer;
  public void init(String indexpath) throws Exception {
	  writer = new IndexWriter(FSDirectory.open(new File(indexpath)), createConfig());
  }
  
  public void close() throws Exception {
	  if (writer != null) {
		  writer.close();
	  }
  }
  
  private void indexDocument(int id, NewsInfo info) throws Exception  {
    if (info == null) {
      return;
    }
    Document doc = new Document();
    
    doc.add(new TextField("CONTENT", info.content.toString(), Store.YES));
    doc.add(new TextField("TITLE", info.title.toString(), Store.YES));
    doc.add(new StringField("ID", Integer.toString(id), Store.YES));
    doc.add(new IntField("DATE", Integer.parseInt(info.createDate.toString()), Store.YES));
    writer.addDocument(doc);
    
    if (id % 10000 == 0) {
    	System.out.println("index document ..." + id);
    }
  }
  
  public void index(String filename) throws Exception {
    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
    
    int id = 0;
    String line = null;
    NewsInfo info = null;
    int contentCase = -1;
    while((line = reader.readLine()) != null) {
      line = line.trim();
      
      if (line.length() == 0) {
        continue;
      }
      
      if (line.equals("<CREATE_DATE>")) {
        indexDocument(id++, info);
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
    indexDocument(id, info);
    
    reader.close();
  }
  
  public static void main(String[] args) throws Exception {
    
    String filename = "C:\\Users\\user\\Desktop\\Edu2020\\Edu2020\\temp\\news_sample.dat";
    String indexpath = "C:\\Users\\user\\Desktop\\Edu2020\\temp\\news";
    
    NewsIndex runner = new NewsIndex();
    runner.init(indexpath);
    
    runner.index(filename);
    
    runner.close();
    
  }
}
