package dslab.crawler.pack;

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

public class Crawler {
	
	public ArrayList<String> newsLinkList = new ArrayList<String>();
	public ArrayList<String[]> newsTagLinkList = new ArrayList<String[]>();
	public String today;
	public String pastdayOfYear;
	public String pastdayOfMonth;
	public String pastdayOfdate;
	public String pastday;
	public String url;
	public String newsTag;
	public Document newsLinks;
	public Calendar C;
	
	/**
	 * ����ܼƫإ�
	 */
	public void dateProcess(){
		pastday = pastdayOfYear + pastdayOfMonth + pastdayOfdate;
		Date todayDate = Calendar.getInstance().getTime();
		DateFormat dateFormate = new SimpleDateFormat("yyyyMMdd");
		today = dateFormate.format(todayDate).toString();
		C = Calendar.getInstance();
		C.set(Integer.parseInt(pastdayOfYear), Integer.parseInt(pastdayOfMonth) - 1, Integer.parseInt(pastdayOfdate));
	}
	
	/**
	 * �[�J�s�D�s����C
	 * 
	 * @param url �s�D���}
	 * @param newsTag �s�D����
	 * @param pastday �s�D���
	 */
	public void addNewsLinkList(String url, String newsTag, String pastday){
		String[] link = new String[3];
		link[0] = newsTag;
		link[1] = url;
		link[2] = pastday;
		if (!newsLinkList.contains(link[1])){
			newsLinkList.add(link[1]);
			newsTagLinkList.add(link);
			System.out.println(link[1]);
		}
	}
	
	/**
	 * �x�s�s�D���e��txt�ɡA���|�G./���/����/���+�s�D�W��.txt
	 * 
	 * @param newscontent �s�D���e
	 * @param date �s�D���
	 * @param dirPath �x�s���|
	 * @throws IOException
	 */
	public void saveNewsToFile(String[] newscontent, String date, String dirPath) throws IOException{
		
		File f = null;
		String filePath = null;
		OutputStream out = null;
		
		// ���ɮצW��(�ɶ�+�s�D���D)
		filePath = date + newscontent[0] + ".txt";
		f = new File(dirPath + "/" + filePath.replaceAll("[\\\\/:*?\"<>| ]", "-"));
		out = new FileOutputStream(f.getAbsolutePath());
		
		System.out.println(date);
		System.out.println(newscontent[0]);

		// �g�J���e���ɮ�
		out.write(newscontent[0].getBytes());
		out.write("\n".getBytes());
		out.write(newscontent[1].getBytes());
		out.close();
	}
	
	/**
	 * �ഫ���ѳB�z
	 * 
	 * @param dirPath �x�s���|
	 * @param num ���~�s��
	 * @param url �s�D���}
	 * @throws IOException
	 */
	public void transferFail(String dirPath, int num, String url) throws IOException{
		File f = null;
		OutputStream out = null;
		System.err.println("�ഫ����");
		f = new File(dirPath + "/�ഫ����" + num);
		out = new FileOutputStream(f.getAbsolutePath());
		out.write(url.getBytes());
		out.close();
	}
	
	/**
	 * �B�z�s�D�s���M��
	 * 
	 * @param newsName �s�D�W��
	 * @throws IOException
	 */
	public void processNewsList(String newsName) throws IOException{
		
		String dirPath = null;
		String tag = null;
		String url = null;
		String date = null;
		File dir;
		
		for (int i = 0; i < newsTagLinkList.size(); i++) {

			//�ظ��|��Ƨ�(�ɶ�/�s�D����)
			tag = newsTagLinkList.get(i)[0];
			url = newsTagLinkList.get(i)[1].toString();
			date = newsTagLinkList.get(i)[2].toString();
			
			dirPath = newsName + tag;
			dir = new File(dirPath);
			dir.mkdirs();			
			
			customerProcessNewsList(tag, url, date, dirPath, i);
		}
	}
	
	/**
	 * �̷Ӥ��P�s�D�Ȼs�ƳB�z
	 * 
	 * @param tag
	 * @param url
	 * @param date
	 * @param dirPath
	 * @param tmp
	 * @throws IOException
	 */
	public void customerProcessNewsList(String tag, String url, String date, String dirPath, int tmp) throws IOException{
		
	}
	
	@SuppressWarnings("static-access")
	public void run() throws IOException{
		
		dateProcess();
		
	    while(Integer.parseInt(pastday) < Integer.parseInt(today)){
	    	pastdayOfYear = String.format("%04d", C.get(Calendar.YEAR));
	    	pastdayOfMonth = String.format("%02d", C.get(Calendar.MONTH) + 1);
	    	pastdayOfdate = String.format("%02d", C.get(Calendar.DAY_OF_MONTH));
	    	pastday = pastdayOfYear + pastdayOfMonth + pastdayOfdate;	
	    	
	    	customerRunProcess();
	    	
			C.add(C.DATE, Integer.parseInt("1"));
	    }
	}
	
	public void customerRunProcess(){

	}

}
