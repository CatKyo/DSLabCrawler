package dslab.crawler.ltn;

import java.io.IOException;

public class LtnLauncher {

	public static void main(String[] args) throws IOException {
		
		LtnCrawler ltn = new LtnCrawler();
		
//		pastdayOfYear = args[0];
//		pastdayOfMonth = args[1];
//		pastdayOfdate = args[2];
		
		ltn.pastdayOfYear = "2010";
		ltn.pastdayOfMonth = "01";
		ltn.pastdayOfdate = "01";
		
		ltn.today = "20110101";
		
		// ����s�D�s��
		ltn.run();
		
		// �x�s�s�D���e
		ltn.processNewsList("./�ۥѮɳ�/");
	}

}
