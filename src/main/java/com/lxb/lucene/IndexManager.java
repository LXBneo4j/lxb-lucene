package com.lxb.lucene;

import com.lxb.lucene.book.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

/**
 * author: lxb.
 * idea-user: Administrator.
 * time:   2017/7/11.
 */
public class IndexManager {

    public List<Book> initBook(){
        List<Book> list =new ArrayList<Book>();
        Book book1=new Book();
        book1.setId(1);
        book1.setPrice(324.2f);
        book1.setName("Lucene");
        book1.setPic("jkkjljkl接口接口六角恐龙就开了");
        book1.setDescription("Lucene\n" +
                "是非常优秀的成熟的\n" +
                "开源\n" +
                "的\n" +
                "免费\n" +
                "的\n" +
                "纯\n" +
                "java\n" +
                "语言\n" +
                "的全文索引检索工\n" +
                "具包。\n" +
                "\uF0B2\n" +
                "全文检索\n" +
                "\uF0E0\n" +
                "是指计算机索引程序通过扫描文章中的每一个词，对每\n" +
                "一个词建立一个索引，指明该词在文章中出现的次数和位置，当用\n" +
                "户查询时，检索程序就根据事先建立的索引进行查找，并将查找的\n" +
                "结果反馈给用户的检索方式。\n" +
                "\uF0B3\n" +
                "Lucene\n" +
                "是一个高性能、可伸缩的信息搜索\n" +
                "(IR)\n" +
                "库。\n" +
                "Information Retrieval \n" +
                "(IR) library.\n" +
                "它使你可以为你的应用程序添加索引和搜索能力。\n" +
                "\uF0B3\n" +
                "Lucene\n" +
                "的作者\n" +
                "Doug Cutting\n" +
                "是资深的全文索引\n" +
                "/\n" +
                "检索专家，最开始发布在\n" +
                "他本人的主页上，\n" +
                "2001\n" +
                "年\n" +
                "10\n" +
                "月贡献给\n" +
                "APACHE\n" +
                "，成为\n" +
                "APACHE\n" +
                "基金的一\n" +
                "个子项目。\n" +
                "\uF0B2\n" +
                "http://jakarta.apache.org/lucene/\n" +
                "\uF0B3\n" +
                "Lucene\n" +
                "是一个\n" +
                "IR\n" +
                "库而不是现成的产品，\n" +
                "当然也不是\n" +
                "Lucene\n" +
                "的初识者常常认为的\n" +
                "web\n" +
                "爬行器");
        list.add(book1);
        Book book2=new Book();
        book2.setId(2);
        book2.setName("Solr");
        book2.setPrice(431f);
        book2.setPic("就开了就开了dd 订单");
        book2.setDescription("Apache Solr 是一个开源的搜索服务器，Solr 使用 Java 语言开发，主要基于 HTTP 和 Apache Lucene 实现。定制 Solr 索引的实现方法很简单，用 POST\n" +
                "\t方法向 Solr 服务器发送一个描述所有 Field 及其内容的 XML 文档就可以了。定制搜索的时候只需要发送 HTTP GET 请求即可，然后对 Solr 返回的信息进行重\n" +
                "\t新布局，以产生利于用户理解的页面内容布局");

        list.add(book2);
        return list;
    }


    public void createIndex() throws Exception {
        // 采集数据
        List<Book> list=initBook();
        // 将采集到的数据封装到Document对象中
        List<Document> docList = new ArrayList<Document>();
        Document document;
        for (Book book : list) {
            document = new Document();
            // store:如果是yes，则说明存储到文档域中
            // 图书ID
            // Field id = new TextField("id", book.getId().toString(), Store.YES);

            Field id = new TextField("id", Integer.toString(book.getId()), Field.Store.YES);
            // 图书名称
            Field name = new TextField("name", book.getName(), Field.Store.YES);
            // 图书价格
            Field price = new TextField("price", Float.toString(book.getPrice()), Field.Store.YES);
            // 图书图片地址
            Field pic = new TextField("pic", book.getPic(), Field.Store.YES);
            // 图书描述
            Field description = new TextField("description", book.getDescription(), Field.Store.YES);

            // 将field域设置到Document对象中
            document.add(id);
            document.add(name);
            document.add(price);
            document.add(pic);
            document.add(description);

            docList.add(document);
        }
        //JDK 1.7以后 open只能接收Path/////////////////////////////////////////////////////

        // 创建分词器，标准分词器
        Analyzer analyzer = new StandardAnalyzer();

        // 创建IndexWriter
        // IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_6_5_0,analyzer);
        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);

        // 指定索引库的地址
//         File indexFile = new File("D:\\L\a\Eclipse\\lecencedemo\\");
//         Directory directory = FSDirectory.open(indexFile);
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("D:\\lxb\\lucenceIndex1\\"));

        IndexWriter writer = new IndexWriter(directory, cfg);
        writer.deleteAll(); //清除以前的index
        // 通过IndexWriter对象将Document写入到索引库中
        for (Document doc : docList) {
            writer.addDocument(doc);
        }

        // 关闭writer
        writer.close();
    }
}
