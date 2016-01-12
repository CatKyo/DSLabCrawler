package dslab.crawler.pack;

import java.io.IOException;

import dslab.crawler.apple.AppleCrawler;
import dslab.crawler.chinatimes.BusinessTimes;
import dslab.crawler.chinatimes.ChinaElectronicsNews;
import dslab.crawler.chinatimes.ChinatimesCrawler;
import dslab.crawler.chinatimes.DogCrawler;
import dslab.crawler.ltn.LtnCrawler;

public class Launcher {
	
	private static void apple(String[] args) throws IOException{
		AppleCrawler apple = new AppleCrawler();
		apple.pastdayOfYear = args[0];
		apple.pastdayOfMonth = args[1];
		apple.pastdayOfdate = args[2];
		apple.today = args[3];
		apple.run();
		apple.processNewsList("./ī�G���/");
	}
	
	private static void ltn(String[] args) throws IOException{
		LtnCrawler ltn = new LtnCrawler();
		ltn.pastdayOfYear = args[0];
		ltn.pastdayOfMonth = args[1];
		ltn.pastdayOfdate = args[2];
		ltn.today = args[3];
		ltn.run();
		ltn.processNewsList("./�ۥѮɳ�/");
	}
	
	private static void chinatimes(String[] args) throws IOException{
		ChinatimesCrawler chinatimes = new ChinatimesCrawler();
		chinatimes.pastdayOfYear = args[0];
		chinatimes.pastdayOfMonth = args[1];
		chinatimes.pastdayOfdate = args[2];
		chinatimes.today = args[3];
		chinatimes.run();
		chinatimes.processNewsList("./����ɳ�/");
	}
	
	private static void businesstimes(String[] args) throws IOException{
		BusinessTimes businesstimes = new BusinessTimes();
		businesstimes.pastdayOfYear = args[0];
		businesstimes.pastdayOfMonth = args[1];
		businesstimes.pastdayOfdate = args[2];
		businesstimes.today = args[3];
		businesstimes.run();
		businesstimes.processNewsList("./�u�Ӯɳ�/");
	}
	
	private static void dog(String[] args) throws IOException{
		DogCrawler dog = new DogCrawler();
		dog.pastdayOfYear = args[0];
		dog.pastdayOfMonth = args[1];
		dog.pastdayOfdate = args[2];
		dog.today = args[3];
		dog.run();
		dog.processNewsList("./����/");
	}
	
	private static void chinaelectrontimes(String[] args) throws IOException{
		ChinaElectronicsNews chinaelectrontimes = new ChinaElectronicsNews();
		chinaelectrontimes.pastdayOfYear = args[0];
		chinaelectrontimes.pastdayOfMonth = args[1];
		chinaelectrontimes.pastdayOfdate = args[2];
		chinaelectrontimes.today = args[3];
		chinaelectrontimes.run();
		chinaelectrontimes.processNewsList("./���ɹq�l��/");
	}
	
	public static void main(String[] args) throws IOException {
		Integer year = 0;
		Integer currentday = 0;

		for (Integer i = 1; i <= 6; i++) {
			apple(args);
			ltn(args);
			chinatimes(args);
			businesstimes(args);
			dog(args);
			chinaelectrontimes(args);

			year = Integer.parseInt(args[0]) + 1;
			args[0] = year.toString();
			currentday = Integer.parseInt(args[3]) + 10000;
			args[3] = currentday.toString();
		}
	}
}
