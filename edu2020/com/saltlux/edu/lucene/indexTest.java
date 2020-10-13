package com.saltlux.edu.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class indexTest {

  public static IndexWriterConfig createConfig() throws IOException {
    WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer();
    IndexWriterConfig wconf = new IndexWriterConfig(Version.LATEST, analyzer);
    wconf.setOpenMode(OpenMode.CREATE_OR_APPEND);
    return wconf;
  }
  
  public static void main(String[] args) throws Exception {
     IndexWriter writer = new IndexWriter(FSDirectory.open(new File("C:\\Users\\user\\Desktop\\Edu2020\\temp\\news")), createConfig());
    
     ///
     Document doc = new Document();
     
     doc.add(new TextField("CONTENT", "내일은 조금 쉬울라나....", Store.YES));
     doc.add(new TextField("TITLE", "내일 수업은 너무 어려워", Store.YES));
     doc.add(new StringField("ID", "2423", Store.YES));
     doc.add(new IntField("DATE", 20200920, Store.YES));
     
     writer.addDocument(doc);
     ////
     
     writer.close();
  }
}
