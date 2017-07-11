package com.lxb.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: lxb.
 * idea-user: Administrator.
 * time:   2017/7/11.
 */
public class IndexSearch {

    private void doSearch(Query query) {
        // 创建IndexSearcher
        // 指定索引库的地址
        try {
//          File indexFile = new File("D:\\Lpj\\Eclipse\\lecencedemo\\");
//          Directory directory = FSDirectory.open(indexFile);
            // 1、创建Directory
            //JDK 1.7以后 open只能接收Path
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("D:\\lxb\\lucenceIndex1\\"));
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            // 通过searcher来搜索索引库
            // 第二个参数：指定需要显示的顶部记录的N条
            TopDocs topDocs = searcher.search(query, 10);

            // 根据查询条件匹配出的记录总数
            int count = topDocs.totalHits;
            System.out.println("匹配出的记录总数:" + count);
            // 根据查询条件匹配出的记录
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            for (ScoreDoc scoreDoc : scoreDocs) {
                // 获取文档的ID
                int docId = scoreDoc.doc;

                // 通过ID获取文档
                Document doc = searcher.doc(docId);
                System.out.println("商品ID：" + doc.get("id"));
                System.out.println("商品名称：" + doc.get("name"));
                System.out.println("商品价格：" + doc.get("price"));
                System.out.println("商品图片地址：" + doc.get("pic"));
                System.out.println("==========================");
                 System.out.println("商品描述：" + replaceBlank(doc.get("description")));
            }
            // 关闭资源
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void indexSearch() throws Exception {
        // 创建query对象
        Analyzer analyzer = new StandardAnalyzer();
        // 使用QueryParser搜索时，需要指定分词器，搜索时的分词器要和索引时的分词器一致
        // 第一个参数：默认搜索的域的名称
        QueryParser parser = new QueryParser("description", analyzer);

        // 通过queryparser来创建query对象
        // 参数：输入的lucene的查询语句(关键字一定要大写)
        Query query = parser.parse("description:solr");

        doSearch(query);

    }
    public  String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static void main(String[] args) {
//        IndexManager indexManager=new IndexManager();
//        try{
//            indexManager.createIndex();
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }

        IndexSearch indexSearch=new IndexSearch();
        try{
            indexSearch.indexSearch();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
