package dslab.crawler.apple;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dslab.crawler.pack.Crawler;
import dslab.crawler.pack.CrawlerPack;

public class AppleCrawler extends Crawler{
	
	@Override
	public void customerProcessNewsList(String tag, String url, String date, String dirPath, Document contain) throws IOException{
		if (tag.equals("�a���J�I"))
			processNewsContain(houseNewsParseProcess(contain), date, dirPath);
		else if (tag.equals("�в���") || tag.equals("�a�~��") || tag.equals("���v��") || tag.equals("�a����"))
			processNewsContain(housekingNewsParseProcess(contain), date, dirPath);
		else
			processNewsContain(commentNewsParseProcess(contain), date, dirPath);
	}
	
	@Override
	public void customerRunProcess(){
		url = "http://www.appledaily.com.tw/appledaily/archive/" + pastday;
    	newsLinks = CrawlerPack.start().getFromHtml(url);

		for (Element elem : newsLinks.select("h2.nust.clearmen")) {
			newsTag = elem.text();
			for (Element elem2 : elem.nextElementSibling().select("a[href]")) {
				if (elem2.attr("href").contains("http:"))
					addNewsLinkList(elem2.attr("href"), newsTag, pastday);
				else
					addNewsLinkList("http://www.appledaily.com.tw" + elem2.attr("href"), newsTag, pastday);
			}
		}
	}
	
	/**
	 * �@��s�D���γB�z
	 * 
	 * @param elem
	 * @return
	 */
	private String[] commentNewsParseProcess(Document contain){
		String[] newscontent = {"",""};
		
		for (Element elem : contain.select("article#maincontent.vertebrae")) {
			// �I���s�D���D�B���e
			newscontent[0] = elem.select("header").select("hgroup").text();
			newscontent[1] = elem.select("p").text();
		}
		return newscontent;
	}
	
	/**
	 * �в����B�a�~���B���v���B�a�����s�D���γB�z
	 * 
	 * @param elem
	 * @return
	 */
	private String[] housekingNewsParseProcess(Document contain){
		String[] newscontent = {"",""};
		
		for (Element elem : contain.select("article#maincontent.vertebrae")) {
			// �I���s�D���D�B���e
			newscontent[0] = elem.select("div.ncbox_cont").select("h1").text();
			newscontent[1] = elem.select("div.articulum").select("p").text();
		}
		return newscontent;
	}
	
	/**
	 * �в��s�D���γB�z
	 * 
	 * @param elem
	 * @return
	 */
	private String[] houseNewsParseProcess(Document contain){
		String[] newscontent = {"",""};
		
		for (Element elem : contain.select("article#maincontent.vertebrae")) {
			// �I���s�D���D�B���e
			newscontent[0] = elem.select("div.ncbox_cont").select("h1").text();
			newscontent[1] = elem.select("p").text();
		}
		return newscontent;
	}

}
