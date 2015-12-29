package dslab.crawler.ltn;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dslab.crawler.pack.CrawlerPack;

public class LtnCrawler {
	
	private final static Date todayDate = Calendar.getInstance().getTime();
	private final static DateFormat dateFormate = new SimpleDateFormat("yyyyMMdd");
	private static ArrayList<String> newsLinkList = new ArrayList<String>();
	private static ArrayList<String[]> newsTagLinkList = new ArrayList<String[]>();
	
	/**
	 * �[�J�s�D�s����C
	 * 
	 * @param url �s�D�s��
	 * @param newsTag �s�D�s������
	 */
	private static void addNewsLinkList(String url, String newsTag){
		String[] link = new String[2];
		link[0] = newsTag;
		link[1] = url;
		if (!newsLinkList.contains(link[1])){
			newsLinkList.add(link[1]);
			newsTagLinkList.add(link);
		}
	}
	
	/**
	 * �B�z�s�D�s���M��
	 * 
	 * @param date
	 * @throws IOException
	 */
	private static void processNewsList(String date) throws IOException{
		
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
	private static String[] commentNewsParseProcess(Document contain) throws IOException{
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
	private static String[] entertainmentNewsParseProcess(Document contain) throws IOException{
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
	private static String[] sportNewsParseProcess(Document contain) throws IOException{
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
	private static String[] editorialNewsParseProcess(Document contain) throws IOException{
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
	private static String[] talkNewsParseProcess(Document contain) throws IOException{
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
	
	/**
	 * �ഫ���ѳB�z
	 * 
	 * @param dirPath
	 * @param num
	 * @param url
	 * @throws IOException
	 */
	private static void transferFail(String dirPath, int num, String url) throws IOException{
		File f = null;
		OutputStream out = null;
		System.err.println("�ഫ����");
		f = new File(dirPath + "/�ഫ����" + num);
		out = new FileOutputStream(f.getAbsolutePath());
		out.write(url.getBytes());
		out.close();
	}
	
	/**
	 * �x�s�s�D���e��txt�ɡA���|�G./���/����/���+�s�D�W��.txt
	 * 
	 * @param newscontent
	 * @param date
	 * @param dirPath
	 * @throws IOException
	 */
	private static void saveNewsToFile(String[] newscontent, String date, String dirPath) throws IOException{
		
		File f = null;
		String filePath = null;
		OutputStream out = null;
		
		// ���ɮצW��(�ɶ�+�s�D���D)
		filePath = date + newscontent[0] + ".txt";
		f = new File(dirPath + "/" + filePath.replaceAll("[\\\\/:*?\"<>|  ]", "-"));
		out = new FileOutputStream(f.getAbsolutePath());

		// �g�J���e���ɮ�
		out.write(newscontent[0].getBytes());
		out.write("\n".getBytes());
		out.write(newscontent[1].getBytes());
		out.close();
	}
		
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException {
		
		String today = dateFormate.format(todayDate).toString();
		String pastdayOfYear = "2010";
		String pastdayOfMonth = "01";
		String pastdayOfdate = "01";
		String pastday = pastdayOfYear + pastdayOfMonth + pastdayOfdate;		
		String url = null;
		String newsTag = null;
		Document newsLinks;
		
		//�s�D����
		String[] category = {"focus", "politics", "society", "local", "life", "opinion", "world", "business", "sports", "entertainment", "consumer", "supplement"};
		
		List<String> urlList = new ArrayList<String>();
		
	    Calendar C = Calendar.getInstance();
	    C.set(Integer.parseInt(pastdayOfYear),Integer.parseInt(pastdayOfMonth)-1,Integer.parseInt(pastdayOfdate));
		
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
