package dslab.crawler.pack;

import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;

import csist.c4isr.common.net.TcpLink;
import dslab.crawler.apple.AppleCrawler;
import dslab.crawler.chinatimes.BusinessTimesCrawlwe;
import dslab.crawler.chinatimes.ChinaElectronicsNewsCrawler;
import dslab.crawler.chinatimes.ChinatimesCrawler;
import dslab.crawler.chinatimes.DogCrawler;
import dslab.crawler.ltn.LtnCrawler;
import dslab.crawler.ptt.PttGossipingCrawler;
import dslab.crawler.udn.UdnCrawler;

public class Launcher {
	
	public static String pastdayOfYear;
	public static String pastdayOfMonth;
	public static String pastdayOfdate;
	public static String pastday;
	public static String today;
	public static Calendar C;
	
	private static void apple(AppleCrawler apple, String[] dateAry, TcpLink tcp) throws IOException{
		apple.pastdayOfYear = dateAry[0];
		apple.pastdayOfMonth = dateAry[1];
		apple.pastdayOfdate = dateAry[2];
		apple.pastday = pastday;
		apple.today = today;
		apple.tcpLink = tcp;
		apple.customerRunProcess();
		apple.processNewsList("./ī�G���/");
	}
	
	private static void ltn(LtnCrawler ltn, String[] dateAry, TcpLink tcp) throws IOException{
		ltn.pastdayOfYear = dateAry[0];
		ltn.pastdayOfMonth = dateAry[1];
		ltn.pastdayOfdate = dateAry[2];
		ltn.pastday = pastday;
		ltn.today = today;
		ltn.tcpLink = tcp;
		ltn.customerRunProcess();
		ltn.processNewsList("./�ۥѮɳ�/");
	}
	
	private static void chinatimes(ChinatimesCrawler chinatimes, String[] dateAry, TcpLink tcp) throws IOException{
		chinatimes.pastdayOfYear = dateAry[0];
		chinatimes.pastdayOfMonth = dateAry[1];
		chinatimes.pastdayOfdate = dateAry[2];
		chinatimes.pastday = pastday;
		chinatimes.today = today;
		chinatimes.tcpLink = tcp;
		chinatimes.customerRunProcess();
		chinatimes.processNewsList("./����ɳ�/");
	}
	
	private static void businesstimes(BusinessTimesCrawlwe businesstimes, String[] dateAry, TcpLink tcp) throws IOException{
		businesstimes.pastdayOfYear = dateAry[0];
		businesstimes.pastdayOfMonth = dateAry[1];
		businesstimes.pastdayOfdate = dateAry[2];
		businesstimes.pastday = pastday;
		businesstimes.today = today;
		businesstimes.tcpLink = tcp;
		businesstimes.customerRunProcess();
		businesstimes.processNewsList("./�u�Ӯɳ�/");
	}
	
	private static void dog(DogCrawler dog, String[] dateAry, TcpLink tcp) throws IOException{
		dog.pastdayOfYear = dateAry[0];
		dog.pastdayOfMonth = dateAry[1];
		dog.pastdayOfdate = dateAry[2];
		dog.pastday = pastday;
		dog.today = today;
		dog.tcpLink = tcp;
		dog.customerRunProcess();
		dog.processNewsList("./����/");
	}
	
	private static void chinaelectrontimes(ChinaElectronicsNewsCrawler chinaelectrontimes, String[] dateAry, TcpLink tcp) throws IOException{
		chinaelectrontimes.pastdayOfYear = dateAry[0];
		chinaelectrontimes.pastdayOfMonth = dateAry[1];
		chinaelectrontimes.pastdayOfdate = dateAry[2];
		chinaelectrontimes.pastday = pastday;
		chinaelectrontimes.today = today;
		chinaelectrontimes.tcpLink = tcp;
		chinaelectrontimes.customerRunProcess();
		chinaelectrontimes.processNewsList("./���ɹq�l��/");
	}
	
	private static void udn(UdnCrawler udn, String[] dateAry, TcpLink tcp) throws IOException{
		udn.pastdayOfYear = dateAry[0];
		udn.pastdayOfMonth = dateAry[1];
		udn.pastdayOfdate = dateAry[2];
		udn.pastday = pastday;
		udn.today = today;
		udn.tcpLink = tcp;
		udn.customerRunProcess();
		udn.processNewsList("./�p�X��/");
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
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		TcpLink tcp = new TcpLink();
//		tcp.setMode(TcpLink.DATA_MODE);
//		tcp.setHost("192.168.4.213");
//		tcp.setPort(9999);
//		tcp.connect();
//		tcp.start();
//		
//		int i = 0;
//		while(true){
//			tcp.writeBytes(("string\n").getBytes());
//			System.err.println("string" + i++);
//			Thread.sleep(500);
//		}
//		tcp.destroy();
		
		
		//�s�D����A��J�G�_�l�~��� �A���~���A�Ҧp�G2010 01 01 20110101
		AppleCrawler apple = new AppleCrawler();
		LtnCrawler ltn = new LtnCrawler();
		ChinatimesCrawler chinatimes = new ChinatimesCrawler();
		BusinessTimesCrawlwe businesstimes = new BusinessTimesCrawlwe();
		DogCrawler dog = new DogCrawler();
		ChinaElectronicsNewsCrawler chinaelectrontimes = new ChinaElectronicsNewsCrawler();
		UdnCrawler udn = new UdnCrawler();
		
		String[] dateAry = new String[4];
		dateProcess(args);
		
		while (Integer.parseInt(pastday) < Integer.parseInt(today)) {
			dateAry[0] = String.format("%04d", C.get(Calendar.YEAR));
			dateAry[1] = String.format("%02d", C.get(Calendar.MONTH) + 1);
			dateAry[2] = String.format("%02d", C.get(Calendar.DAY_OF_MONTH));
			pastday = dateAry[0] + dateAry[1] + dateAry[2];
			
//			apple(apple, dateAry, tcp);
			udn(udn, dateAry, tcp);
//			ltn(ltn, dateAry, tcp);
//			chinatimes(chinatimes, dateAry, tcp);
//			businesstimes(businesstimes, dateAry, tcp);
//			dog(dog, dateAry, tcp);
//			chinaelectrontimes(chinaelectrontimes, dateAry, tcp);
			
			C.add(C.DATE, Integer.parseInt("1"));
		}
		
//		//Ptt�K��������A��J�_�W�峹�s���A�Ҧp�G0 5000
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
