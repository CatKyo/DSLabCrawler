package dslab.crawler.apple;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dslab.crawler.pack.CrawlerPack;

public class AppleCrawler {
	
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
	private static void processNewsList(String year, String date) throws IOException{
		
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
			
			dirPath = "./ī�G���" + year + "/" + date + "/" + tag;
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
	private static String[] commentNewsParseProcess(Document contain){
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
	private static String[] housekingNewsParseProcess(Document contain){
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
	private static String[] houseNewsParseProcess(Document contain){
		String[] newscontent = {"",""};
		for (Element elem : contain.select("article#maincontent.vertebrae")) {
			// �I���s�D���D�B���e
			newscontent[0] = elem.select("div.ncbox_cont").select("h1").text();
			newscontent[1] = elem.select("p").text();
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
		
	    Calendar C = Calendar.getInstance();
	    C.set(Integer.parseInt(pastdayOfYear),Integer.parseInt(pastdayOfMonth)-1,Integer.parseInt(pastdayOfdate));
		
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
			processNewsList(pastdayOfYear, pastday);

			C.add(C.DATE, Integer.parseInt("1"));
	    }
		
	}
}
