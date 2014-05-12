package org.crusoe.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.google.common.collect.Lists;

import org.crusoe.entity.workflow.governmentInformationDisclosure.*;

public class LuceneIKUtil<T> {
	private Directory directory;
	private Analyzer analyzer;

	public LuceneIKUtil(String indexFilePath) {

		try {
			directory = FSDirectory.open(new File(indexFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		analyzer = new IKAnalyzer();
	}

	public void createIndex() throws IOException {
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
				Version.LUCENE_47, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		indexWriter.deleteAll();

	}

	public Document addDocument(Long id, String title, String content) {
		Document doc = new Document();
		doc.add(new StringField("id", String.valueOf(id), Field.Store.YES));
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new TextField("content", content, Field.Store.YES));
		return doc;

	}

	public void updateIndex(Long id, String title, String content) {
		try {
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
					Version.LUCENE_47, analyzer);
			IndexWriter indexWriter = new IndexWriter(directory,
					indexWriterConfig);
			Document doc = addDocument(id, title, content);
			Term term = new Term("id", String.valueOf(id));
			indexWriter.updateDocument(term, doc);
			indexWriter.close();
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public List<T> search(String[] fields, String keyword) {
		IndexSearcher indexSearcher = null;
		List<T> result = Lists.newArrayList();
		T obj;

		try {
			IndexReader indexReader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(indexReader);
			MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
					Version.LUCENE_47, fields, analyzer);
			Query query = queryParser.parse(keyword);
			TopDocs topDocs = indexSearcher.search(query, 10);
			int totalCount = topDocs.totalHits;
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc scDoc : scoreDocs) {
				Document document = indexSearcher.doc(scDoc.doc);
				Long id = Long.parseLong(document.get("id"));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (List<T>) result;
	}
}
