package dslab.crawler.apple;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dslab.crawler.pack.Crawler;
import dslab.crawler.pack.CrawlerPack;

public class AppleCrawler extends Crawler{
	
	/**
	 * �B�z�s�D�s���M��
	 * 
	 * @param date
	 * @throws IOException
	 */
	private void processNewsList(String date) throws IOException{
		
		String dirPath = null;
		String tag = null;
		String url = null;
		File dir;
		
		for (int i = 0; i < newsTagLinkList.size(); i++) {

			//�ظ��|��Ƨ�(�ɶ�/�s�D����)
			tag = newsTagLinkList.get(i)[0];
			url = newsTagLinkList.get(i)[1].toString();
			System.out.println(tag);	
			System.out.println(url);
			
			dirPath = "./ī�G���/" + tag;
			dir = new File(dirPath);
			dir.mkdirs();			
			
			Document contain = CrawlerPack.getFromXml(url);
			
			if (contain != null) {
				if(tag.equals("�a���J�I"))
					saveNewsToFile(houseNewsParseProcess(contain), date, dirPath);
				else if(tag.equals("�в���") || tag.equals("�a�~��") || tag.equals("���v��") || tag.equals("�a����"))
					saveNewsToFile(housekingNewsParseProcess(contain), date, dirPath);
				else
					saveNewsToFile(commentNewsParseProcess(contain), date, dirPath);
			} else{
				transferFail(dirPath, i, url);
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
	
	@SuppressWarnings("static-access")
	public void run() throws IOException{
		
		dateProcess();
		
	    while(Integer.parseInt(pastday) < Integer.parseInt(today)){
	    	pastdayOfYear = String.format("%04d", C.get(Calendar.YEAR));
	    	pastdayOfMonth = String.format("%02d", C.get(Calendar.MONTH) + 1);
	    	pastdayOfdate = String.format("%02d", C.get(Calendar.DAY_OF_MONTH));
	    	pastday = pastdayOfYear + pastdayOfMonth + pastdayOfdate;	
	    	
	    	url = "http://www.appledaily.com.tw/appledaily/archive/" + pastday;
	    	newsLinks = CrawlerPack.getFromHtml(url);

			for (Element elem : newsLinks.select("h2.nust.clearmen")) {
				newsTag = elem.text();
				for (Element elem2 : elem.nextElementSibling().select("a[href]")) {
					if (elem2.attr("href").contains("http:"))
						addNewsLinkList(elem2.attr("href"), newsTag);
					else
						addNewsLinkList("http://www.appledaily.com.tw" + elem2.attr("href"), newsTag);
				}
			}

			// �x�s�s�D���e
			processNewsList(pastday);

			C.add(C.DATE, Integer.parseInt("1"));
	    }
	}
}
