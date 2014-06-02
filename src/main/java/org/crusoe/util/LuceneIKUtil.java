package org.crusoe.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.google.common.collect.Lists;

import org.crusoe.dto.FieldDTO;
import org.crusoe.dto.fulltextSearch.SearchResultDTO;
import org.crusoe.entity.workflow.governmentInformationDisclosure.*;

@Component
public class LuceneIKUtil {
	private Directory directory;
	private Analyzer analyzer;
	private String indexFilePath = "/IK";
	IndexWriterConfig indexWriterConfig;
	IndexWriter indexWriter;

	public LuceneIKUtil() {

		try {
			directory = FSDirectory.open(new File(indexFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		analyzer = new IKAnalyzer();
		indexWriterConfig = new IndexWriterConfig(Version.LUCENE_47, analyzer);
		try {
			indexWriter = new IndexWriter(directory, indexWriterConfig);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public LuceneIKUtil(String indexFilePath) {
		this.indexFilePath = indexFilePath;
		try {
			directory = FSDirectory.open(new File(indexFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		analyzer = new IKAnalyzer();
	}

	@PostConstruct
	public void postConstructLuceneIKUtil() {
		// System.out.println("postConstruct1");
	}

	public void createIndex() throws IOException {

		indexWriter.deleteAll();

	}

	public Document addDocument(String processInstanceId, Long id,
			String title, String content) {
		Document doc = new Document();
		doc.add(new StringField("processInstanceId", String
				.valueOf(processInstanceId), Field.Store.YES));
		doc.add(new StringField("id", String.valueOf(id), Field.Store.YES));
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new TextField("content", content, Field.Store.YES));
		return doc;

	}

	public Document addDocument(List<Field> fields) {
		Document doc = new Document();
		for (Field field : fields) {

			doc.add(field);

		}
		return doc;
	}

	public void updateIndex(String processInstanceId, Long id, String title,
			String content) {
		try {

			Document doc = addDocument(processInstanceId, id, title, content);
			Term term = new Term("id", String.valueOf(id));
			indexWriter.updateDocument(term, doc);
			indexWriter.close();
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public void addIndex(String processInstanceId, Long id, String title,
			String content) {
		try {

			Document doc = addDocument(processInstanceId, id, title, content);
			// Term term = new Term("id", String.valueOf(id));
			indexWriter.addDocument(doc);
			indexWriter.close();
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public void addIndex(List<FieldDTO> fieldDTOList) {
		try {

			List<Field> fields = Lists.newArrayList();
			for (FieldDTO fieldDTO : fieldDTOList) {
				Field field;
				if (fieldDTO.isCompleteMatch())
					field = new StringField(fieldDTO.getFieldName(),
							fieldDTO.getFieldContent(), fieldDTO.getStore());
				else
					field = new TextField(fieldDTO.getFieldName(),
							fieldDTO.getFieldContent(), fieldDTO.getStore());
				fields.add(field);
			}
			Document doc = addDocument(fields);
			indexWriter.addDocument(doc);
			indexWriter.commit();
			// indexWriter.close();
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public void updateIndex(List<FieldDTO> fieldDTOList) {
		try {

			List<Field> fields = Lists.newArrayList();
			String id = "";
			for (FieldDTO fieldDTO : fieldDTOList) {
				Field field;
				if (fieldDTO.isCompleteMatch())
					field = new StringField(fieldDTO.getFieldName(),
							fieldDTO.getFieldContent(), fieldDTO.getStore());
				else
					field = new TextField(fieldDTO.getFieldName(),
							fieldDTO.getFieldContent(), fieldDTO.getStore());
				if (fieldDTO.getFieldName() == "id")
					id = fieldDTO.getFieldName();
			}
			Document doc = addDocument(fields);
			Term term = new Term("id", String.valueOf(id));
			indexWriter.updateDocument(term, doc);
			indexWriter.commit();
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public HashMap<String, Object> search(String[] fields, String keyword,
			int start, int size) {
		IndexSearcher indexSearcher = null;
		List<SearchResultDTO> result = Lists.newArrayList();
		HashMap<String, Object> rets = new HashMap<String, Object>();

		int currentPage = start / size;
		try {
			IndexReader indexReader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(indexReader);
			MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
					Version.LUCENE_47, fields, analyzer);
			Query query = queryParser.parse(keyword);
			TopDocs topDocs = indexSearcher.search(query, size);
			ScoreDoc scoreDoc = null;
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			if (start > 0) {
				scoreDoc = scoreDocs[start - 1];
			}
			topDocs = indexSearcher.searchAfter(scoreDoc, query, size);
			int totalCount = topDocs.totalHits;
			for (ScoreDoc scDoc : scoreDocs) {
				SearchResultDTO fsrDTO = new SearchResultDTO();
				Document document = indexSearcher.doc(scDoc.doc);
				Long id = Long.parseLong(document.get("id"));
				String processInstanceId = document.get("processInstanceId");
				fsrDTO.setId(id);
				fsrDTO.setProcessInstanceId(processInstanceId);
				result.add(fsrDTO);
			}

			rets.put("count", totalCount);
			rets.put("result", result);
			indexReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rets;
	}
}
