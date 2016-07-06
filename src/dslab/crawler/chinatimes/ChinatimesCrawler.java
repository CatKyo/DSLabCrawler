package dslab.crawler.chinatimes;


import java.io.IOException;

import org.json.JSONException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dslab.crawler.pack.Crawler;
import dslab.crawler.pack.CrawlerPack;

public class ChinatimesCrawler extends Crawler {
	
	@Override
	public void customerProcessNewsList(String tag, String url, String date, String dirPath, Document contain) throws IOException, JSONException{
		
		processNewsContain(commentNewsParseProcess(contain));
	}
	
	@Override
	public void customerRunProcess(){
		int page = 0;
		String currentPage;
		
		setUrl();
    	newsLinks = CrawlerPack.start().getFromXml(url);
    	for (Element elem : newsLinks.select("div.pagination.clear-fix").select("li")){
    		if(elem.text().equals("最後一頁") && !elem.select("a[href]").attr("href").equals(""))
    			page = Integer.parseInt(elem.select("a[href]").attr("href").split("page=")[1]);
    	}
    	
    	for (Integer i = 1; i <= page; i++){
    		currentPage = url + "?page=" + i.toString();
    		newsLinks = CrawlerPack.start().getFromXml(currentPage);
			for (Element elem : newsLinks.select("section.np_alllist").select("div.listRight").select("li")) {
				newsCategory = elem.select("div.kindOf").text();
				addNewsLinkList("http://www.chinatimes.com" + elem.select("a[href]").attr("href"), newsCategory, pastday);
			}
    	}
	}	
	
	public void setUrl(){
		url = "http://www.chinatimes.com/history-by-date/" + pastdayOfYear + "-" + pastdayOfMonth + "-" + pastdayOfdate + "-2601";
	}
	
	private String[] commentNewsParseProcess(Document contain){
		String[] newscontent = {"",""};
		setSelectElement();
		for (Element elem : contain.select(elemString)) {
			newscontent[0] = elem.select("header").select("h1").text();
			newscontent[1] = elem.select("article.clear-fix").select("p").text();
		}
		return newscontent;
	}
	
	public void setSelectElement(){
		elemString = "div.page_container.stack.clear-fix.newspapers_ad";
	}
}
