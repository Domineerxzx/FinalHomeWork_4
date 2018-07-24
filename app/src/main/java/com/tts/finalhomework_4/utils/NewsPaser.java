package com.tts.finalhomework_4.utils;

import android.content.Intent;

import com.tts.finalhomework_4.beans.News;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by 37444 on 2017/12/14.
 */

public class NewsPaser extends DefaultHandler {

    private List<News> newsList;
    private News news;

    private String newstitle;
    private String assess;
    private String newsdate;
    private String newscontent;
    private String nodeName;
    private String newsimage;

    public List<News> parseXML(InputStream in) throws
            ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(in,this);
        return newsList;
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equals("News")){
            newsList = new ArrayList<News>();
            newstitle="";
            assess="";
            newsdate="";
            newscontent="";
            newsimage="";
        }else if(localName.equals("news")) {
            news = new News();
            String id = attributes.getValue(0);
            news.setId(id);
        }
        nodeName=localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equals("news")){
            newsList.add(news);
        }else if(localName.equals("newstitle")){
            news.setNewstitle(newstitle.trim());
            newstitle="";
        }else if(localName.equals("assess")){
            news.setAssess(assess.trim());
            assess="";
        }else if(localName.equals("newsdate")){
            news.setNewsdate(newsdate.trim());
            newsdate="";
        }else if(localName.equals("newscontent")){
            news.setNewscontent(newscontent.trim());
            newscontent="";
        } else if(localName.equals("newsimage")){
            news.setNewsimage(newsimage.trim());
            newsimage="";
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws
            SAXException {
        super.characters(ch, start, length);
        if(nodeName.equals("newstitle")){
            newstitle += String.valueOf(ch,start,length);
        }else if(nodeName.equals("assess")){
            assess += String.valueOf(ch,start,length);
        }else if(nodeName.equals("newsdate")){
            newsdate += String.valueOf(ch,start,length);
        }else if(nodeName.equals("newscontent")){
            newscontent += String.valueOf(ch,start,length);
        } else if(nodeName.equals("newsimage")){
            newsimage += String.valueOf(ch,start,length);
        }
    }
}
