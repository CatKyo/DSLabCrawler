package dslab.crawler.ltn;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dslab.crawler.pack.Crawler;
import dslab.crawler.pack.CrawlerPack;

public class LtnCrawler extends Crawler{
	
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
			
			dirPath = "./�ۥѮɳ�/" + tag;
			dir = new File(dirPath);
			dir.mkdirs();			
			
			Document contain = CrawlerPack.getFromXml(url);
			
			if (contain != null) {
				if(tag.equals("����") || tag.equals("�ۥѼs��") || tag.equals("�ۥѽ�"))
					saveNewsToFile(editorialNewsParseProcess(contain), date, dirPath);
				else if(tag.equals("�v���J�I"))
					saveNewsToFile(entertainmentNewsParseProcess(contain), date, dirPath);
				else if(tag.equals("��|�s�D") || tag.equals("�B�ʱm��"))
					saveNewsToFile(sportNewsParseProcess(contain), date, dirPath);
				else if(tag.equals("����"))
					saveNewsToFile(talkNewsParseProcess(contain), date, dirPath);
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
	 * @throws IOException 
	 */
	private String[] commentNewsParseProcess(Document contain) throws IOException{
		String[] newscontent = {"",""};
		for (Element elem : contain.select("div.content")) {
			// �I���s�D���D�B���e
			newscontent[0] = elem.select("h1").text();
			for (Element elem2 : elem.select("div#newstext.text.boxTitle")) {
				newscontent[1] += elem2.select("p").text();
			}
		}
		return newscontent;
	}
	
	/**
	 * �v���J�I�s�D���γB�z
	 * 
	 * @param elem
	 * @return
	 */
	private String[] entertainmentNewsParseProcess(Document contain) throws IOException{
		String[] newscontent = {"",""};
		for (Element elem : contain.select("div.content").select("div.news_content")) {
			// �I���s�D���D�B���e
			newscontent[0] = elem.select("h1").text();
			newscontent[1] += elem.select("p").text();
		}
		return newscontent;
	}
	
	/**
	 * ��|�s�D�B�B�ʱm�����γB�z
	 * 
	 * @param elem
	 * @return
	 */
	private String[] sportNewsParseProcess(Document contain) throws IOException{
		String[] newscontent = {"",""};
		for (Element elem : contain.select("div.content").select("div.news_content")) {
			// �I���s�D���D�B���e
			newscontent[0] = elem.select("div.Btitle").text();
			newscontent[1] += elem.select("p").text();
		}
		return newscontent;
	}
	
	/**
	 * ���סB�ۥѼs���B�ۥѽͷs�D���γB�z
	 * 
	 * @param elem
	 * @return
	 */
	private String[] editorialNewsParseProcess(Document contain) throws IOException{
		String[] newscontent = {"",""};
		for (Element elem : contain.select("div.content.page-name")) {
			// �I���s�D���D�B���e
			newscontent[0] = elem.select("h2").text();
			for (Element elem2 : elem.select("div.cont")) {
				newscontent[1] += elem2.select("p").text();
			}
			for (Element elem2 : elem.select("div.conbox")) {
				newscontent[1] += elem2.select("p").text();
			}
		}
		return newscontent;
	}
	
	/**
	 * ���򶰷s�D���γB�z
	 * 
	 * @param elem
	 * @return
	 */
	private String[] talkNewsParseProcess(Document contain) throws IOException{
		String[] newscontent = {"",""};
		for (Element elem : contain.select("div#rightmain.rightmain_c").select("div.content.page-name")) {
			// �I���s�D���D�B���e
			newscontent[0] = elem.select("h2").text();
			for (Element elem2 : elem.select("div.cont")) {
				newscontent[1] += elem2.select("p").text();
			}
		}
		return newscontent;
	}
		
	@SuppressWarnings("static-access")
	public void run() throws IOException{
		
		//�s�D����
		String[] category = {"focus", "politics", "society", "local", "life", "opinion", "world", "business", "sports", "entertainment", "consumer", "supplement"};
		
		List<String> urlList = new ArrayList<String>();
		
		dateProcess();
				
	    while(Integer.parseInt(pastday) < Integer.parseInt(today)){
	    	pastdayOfYear = String.format("%04d", C.get(Calendar.YEAR));
	    	pastdayOfMonth = String.format("%02d", C.get(Calendar.MONTH) + 1);
	    	pastdayOfdate = String.format("%02d", C.get(Calendar.DAY_OF_MONTH));
	    	pastday = pastdayOfYear + pastdayOfMonth + pastdayOfdate;	
	    	
	    	for (int i = 0; i < category.length; i++) {
				urlList = new ArrayList<String>();
				url = "http://news.ltn.com.tw/newspaper/" + category[i] + "/" + pastday;
				
				newsLinks = CrawlerPack.getFromXml(url);
				
				//���o������s�D�s��������
				urlList.add(url);
				for (Element elem : newsLinks.select("div#page.boxTitle.boxText").select("a[href]")) {
					urlList.add("http://news.ltn.com.tw" + elem.attr("href"));
				}

				//����s��
				for (String urllink : urlList) {
					newsLinks = CrawlerPack.getFromXml(urllink);
					for (Element elem : newsLinks.select("ul#newslistul.boxTitle").select("li.lipic")) {
						newsTag = elem.select("span").text();
						addNewsLinkList("http://news.ltn.com.tw" + elem.select("a[href]").attr("href"), newsTag);
					}
				}
			}
	    	// �x�s�s�D���e
	    	processNewsList(pastday);
	    	
	    	C.add(C.DATE, Integer.parseInt("1"));
	    }
	}
}
