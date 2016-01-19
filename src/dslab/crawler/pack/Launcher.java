package dslab.crawler.pack;

import java.io.IOException;
import java.util.Calendar;

import dslab.crawler.apple.AppleCrawler;
import dslab.crawler.chinatimes.BusinessTimes;
import dslab.crawler.chinatimes.ChinaElectronicsNews;
import dslab.crawler.chinatimes.ChinatimesCrawler;
import dslab.crawler.chinatimes.DogCrawler;
import dslab.crawler.ltn.LtnCrawler;
import dslab.crawler.ptt.PttGossipingCrawler;

public class Launcher {
	
	public static String pastdayOfYear;
	public static String pastdayOfMonth;
	public static String pastdayOfdate;
	public static String pastday;
	public static String today;
	public static Calendar C;
	
	private static void apple(AppleCrawler apple, String[] dateAry) throws IOException{
		apple.pastdayOfYear = dateAry[0];
		apple.pastdayOfMonth = dateAry[1];
		apple.pastdayOfdate = dateAry[2];
		apple.pastday = pastday;
		apple.today = today;
		apple.customerRunProcess();
		apple.processNewsList("./ī�G���/");
	}
	
	private static void ltn(LtnCrawler ltn, String[] dateAry) throws IOException{
		ltn.pastdayOfYear = dateAry[0];
		ltn.pastdayOfMonth = dateAry[1];
		ltn.pastdayOfdate = dateAry[2];
		ltn.pastday = pastday;
		ltn.today = today;
		ltn.customerRunProcess();
		ltn.processNewsList("./�ۥѮɳ�/");
	}
	
	private static void chinatimes(ChinatimesCrawler chinatimes, String[] dateAry) throws IOException{
		chinatimes.pastdayOfYear = dateAry[0];
		chinatimes.pastdayOfMonth = dateAry[1];
		chinatimes.pastdayOfdate = dateAry[2];
		chinatimes.pastday = pastday;
		chinatimes.today = today;
		chinatimes.customerRunProcess();
		chinatimes.processNewsList("./����ɳ�/");
	}
	
	private static void businesstimes(BusinessTimes businesstimes, String[] dateAry) throws IOException{
		businesstimes.pastdayOfYear = dateAry[0];
		businesstimes.pastdayOfMonth = dateAry[1];
		businesstimes.pastdayOfdate = dateAry[2];
		businesstimes.pastday = pastday;
		businesstimes.today = today;
		businesstimes.customerRunProcess();
		businesstimes.processNewsList("./�u�Ӯɳ�/");
	}
	
	private static void dog(DogCrawler dog, String[] dateAry) throws IOException{
		dog.pastdayOfYear = dateAry[0];
		dog.pastdayOfMonth = dateAry[1];
		dog.pastdayOfdate = dateAry[2];
		dog.pastday = pastday;
		dog.today = today;
		dog.customerRunProcess();
		dog.processNewsList("./����/");
	}
	
	private static void chinaelectrontimes(ChinaElectronicsNews chinaelectrontimes, String[] dateAry) throws IOException{
		chinaelectrontimes.pastdayOfYear = dateAry[0];
		chinaelectrontimes.pastdayOfMonth = dateAry[1];
		chinaelectrontimes.pastdayOfdate = dateAry[2];
		chinaelectrontimes.pastday = pastday;
		chinaelectrontimes.today = today;
		chinaelectrontimes.customerRunProcess();
		chinaelectrontimes.processNewsList("./���ɹq�l��/");
	}
	
	private static void pttgossiping(Integer startIdx) throws IOException{
		PttGossipingCrawler ptt = new PttGossipingCrawler();
		ptt.startIdx = startIdx.toString();
		ptt.run();
		ptt.processNewsList("./PttGossiping/");
	}
	
	/**
	 * ����ܼƫإ�
	 */
	private static void dateProcess(String[] args) {
		pastdayOfYear = args[0];
		pastdayOfMonth = args[1];
		pastdayOfdate = args[2];
		today = args[3];
		pastday = pastdayOfYear + pastdayOfMonth + pastdayOfdate;
		C = Calendar.getInstance();
		C.set(Integer.parseInt(pastdayOfYear), Integer.parseInt(pastdayOfMonth) - 1, Integer.parseInt(pastdayOfdate));
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException {
		
		AppleCrawler apple = new AppleCrawler();
		LtnCrawler ltn = new LtnCrawler();
		ChinatimesCrawler chinatimes = new ChinatimesCrawler();
		BusinessTimes businesstimes = new BusinessTimes();
		DogCrawler dog = new DogCrawler();
		ChinaElectronicsNews chinaelectrontimes = new ChinaElectronicsNews();
		
		String[] dateAry = new String[4];
		dateProcess(args);
		
		while (Integer.parseInt(pastday) < Integer.parseInt(today)) {
			dateAry[0] = String.format("%04d", C.get(Calendar.YEAR));
			dateAry[1] = String.format("%02d", C.get(Calendar.MONTH) + 1);
			dateAry[2] = String.format("%02d", C.get(Calendar.DAY_OF_MONTH));
			pastday = dateAry[0] + dateAry[1] + dateAry[2];
			
			apple(apple, dateAry);
			ltn(ltn, dateAry);
			chinatimes(chinatimes, dateAry);
			businesstimes(businesstimes, dateAry);
			dog(dog, dateAry);
			chinaelectrontimes(chinaelectrontimes, dateAry);
			
			C.add(C.DATE, Integer.parseInt("1"));
		}
		
//		for(Integer i = Integer.parseInt(args[0]); i <= Integer.parseInt(args[1]); i++){
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			pttgossiping(i);
//			System.out.println("���ơG" + i);
//		}
		
		System.out.println("�������!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
}
