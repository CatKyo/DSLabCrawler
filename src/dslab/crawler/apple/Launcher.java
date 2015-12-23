package dslab.crawler.apple;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dslab.crawler.pack.CrawlerPack;

public class Launcher {
	static List<String> headlineLinkList = new ArrayList<String>();
	static List<String> entertainmentLinkList = new ArrayList<String>();
	static List<String> internationalLinkList = new ArrayList<String>();
	static List<String> sportsLinkList = new ArrayList<String>();
	static List<String> financeLinkList = new ArrayList<String>();
	static List<String> houseLinkList = new ArrayList<String>();
	static List<String> supplementLinkList = new ArrayList<String>();
	static List<String> forumLinkList = new ArrayList<String>();
	
	private static void addHeadlineLinkList(String url){
		if (!headlineLinkList.contains(url)){
			headlineLinkList.add(url);
//			System.out.println(url);		
		}
	}
	
	private static void addEntertainmentLinkList(String url){
		if (!entertainmentLinkList.contains(url)){
			entertainmentLinkList.add(url);
//			System.out.println(url);		
		}
	}
	
	private static void addInternationalLinkList(String url){
		if (!internationalLinkList.contains(url)){
			internationalLinkList.add(url);
//			System.out.println(url);		
		}
	}
	private static void addSportsLinkList(String url){
		if (!sportsLinkList.contains(url)){
			sportsLinkList.add(url);
//			System.out.println(url);		
		}
	}
	private static void addFinanceLinkList(String url){
		if (!financeLinkList.contains(url)){
			financeLinkList.add(url);
//			System.out.println(url);		
		}
	}
	private static void addHouseLinkList(String url){
		if (!houseLinkList.contains(url)){
			houseLinkList.add(url);
//			System.out.println(url);		
		}
	}
	private static void addSupplementLinkList(String url){
		if (!supplementLinkList.contains(url)){
			supplementLinkList.add(url);
//			System.out.println(url);		
		}
	}
	private static void addForumLinkList(String url){
		if (!forumLinkList.contains(url)){
			forumLinkList.add(url);
//			System.out.println(url);		
		}
	}
	
	private static void saveNewsListText(List<String> newsList, String date, String newsTag) throws IOException{
		String topic = null;
		String introid = null;
		String bcontent = null;
		String filePath = null;
		File f = null;
		OutputStream out = null;
		
		//�ظ��|��Ƨ�(�ɶ�/�s�D����)
		String dirPath = "/" + date + "/" + newsTag;
		File dir = new File(dirPath);
		dir.mkdirs();
		
		for (int i = 0; i < newsList.size(); i++) {
			Document contain = CrawlerPack.getFromXml(newsList.get(i).toString());
			for (Element elem : contain.select("article#maincontent.vertebrae")) {
				//�I���s�D���D
				topic = elem.select("header").select("hgroup").text();
				//���ɮצW��(�ɶ�/�s�D���D)
				filePath = date + topic + ".txt";
				f = new File(dirPath + "/" + filePath.replace("/", "-"));
				out = new FileOutputStream(f.getAbsolutePath());
				introid = elem.select("p#introid").text();
				bcontent = elem.select("p#bcontent").text();
				//�g�J���e���ɮ�
				out.write(topic.getBytes());
				out.write("\n".getBytes());
				out.write(introid.getBytes());
				out.write("\n".getBytes());
				out.write(bcontent.getBytes());
				out.close();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		
		String date = "20151222";
		String url = "http://www.appledaily.com.tw/appledaily/archive/" + date;
		String href = null;	
		
		Document newsLinks = CrawlerPack.getFromHtml(url);
		
		//�إ߷s�D�s���C��
		for (Element elem : newsLinks.select("a[href]")) {
			if (elem.attr("href").contains(date)){
				href = elem.attr("href");
				//�Y���s�D
				if (href.contains("/appledaily/article/headline/"))
					addHeadlineLinkList("http://www.appledaily.com.tw" + href);
				//�T�ַs�D
				else if (href.contains("http://ent.appledaily.com.tw/enews/article/entertainment/"))
					addEntertainmentLinkList(href);
				//��ڷs�D
				else if (href.contains("/appledaily/article/international/"))
					addInternationalLinkList("http://www.appledaily.com.tw" + href);
				//��|�s�D
				else if (href.contains("/appledaily/article/sports/"))
					addSportsLinkList("http://www.appledaily.com.tw" + href);
				//�]�g�s�D
				else if (href.contains("/appledaily/article/finance/"))
					addFinanceLinkList("http://www.appledaily.com.tw" + href);
				//�a���s�D
				else if (href.contains("http://home.appledaily.com.tw/article/index/"))
					addHouseLinkList(href);
				//�ƥZ
				else if (href.contains("/appledaily/article/supplement/"))
					addSupplementLinkList("http://www.appledaily.com.tw" + href);
				//�׾»P�M��
				else if (href.contains("/appledaily/article/forum/"))
					addForumLinkList("http://www.appledaily.com.tw" + href);
			}
		}
		saveNewsListText(headlineLinkList, date, "headline");
		saveNewsListText(entertainmentLinkList, date, "entertainment");
		saveNewsListText(internationalLinkList, date, "international");
		saveNewsListText(sportsLinkList, date, "sports");
		saveNewsListText(financeLinkList, date, "finance");
		saveNewsListText(houseLinkList, date, "house");
		saveNewsListText(supplementLinkList, date, "supplement");
		saveNewsListText(forumLinkList, date, "forum");
	}
}
