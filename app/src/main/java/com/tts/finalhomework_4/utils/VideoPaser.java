package com.tts.finalhomework_4.utils;

import com.tts.finalhomework_4.beans.Videos;

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

public class VideoPaser extends DefaultHandler {

    private List<Videos> videosList;
    private Videos videos;

    private String videoname;
    private String videosdate;
    private String videoaddress;

    private String nodeName;

    public List<Videos> parseXML(InputStream in) throws
            ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(in,this);
        return videosList;
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equals("Videos")){
            videosList = new ArrayList<Videos>();
            videoname="";
            videosdate="";
            videoaddress="";
        }else if(localName.equals("videos")) {
            videos = new Videos();
            String id = attributes.getValue(0);
            videos.setId(id);
        }
        nodeName=localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equals("videos")){
            videosList.add(videos);
        }else if(localName.equals("videoname")){
            videos.setVideoname(videoname.trim());
            videoname="";
        }else if(localName.equals("videosdate")){
            videos.setVideosdate(videosdate.trim());
            videosdate="";
        }else if(localName.equals("videoaddress")){
            videos.setVideoaddress(videoaddress.trim());
            videoaddress="";
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws
            SAXException {
        super.characters(ch, start, length);
        if(nodeName.equals("videoname")){
            videoname += String.valueOf(ch,start,length);
        }else if(nodeName.equals("videosdate")){
            videosdate += String.valueOf(ch,start,length);
        }else if(nodeName.equals("videoaddress")){
            videoaddress += String.valueOf(ch,start,length);
        }
    }
}
