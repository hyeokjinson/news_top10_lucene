package com.saltlux.edu.lucene;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class searchTest {


	public static void main(String[] args) throws Exception {
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File("C:\\Users\\user\\Desktop\\Edu2020\\temp\\news")));
		
		IndexSearcher searcher = new IndexSearcher(reader);

		BooleanQuery bquery = new BooleanQuery();
		Query query1 = new TermQuery(new Term("TITLE", "오늘"));
		bquery.add(query1, Occur.MUST);
		
		Sort sort = new Sort(new SortField("DATE", Type.INT, true));
		
		TopDocs tds = searcher.search(bquery, 10, sort);
		long totalcount = tds.totalHits;
		System.out.println("total document = " + totalcount);
		ScoreDoc[] sds = tds.scoreDocs;
		
		for(int i = 0 ; i < sds.length; i++) {
			float score = sds[i].score;
			if (Float.isNaN(score)) {
				score = 0;
			}
			int docid = sds[i].doc;
			Document doc = reader.document(docid);
			String title = doc.get("TITLE");
			String content = doc.get("DATE");
			
			System.out.println(score + "\t" + title + "\t" + content);
		}
	
		reader.close();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
