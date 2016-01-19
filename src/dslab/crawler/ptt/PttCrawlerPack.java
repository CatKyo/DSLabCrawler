package dslab.crawler.ptt;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.provider.http.HttpFileSystemConfigBuilder;
import dslab.crawler.pack.CrawlerPack;

public class PttCrawlerPack extends CrawlerPack{
	/**
	 * �z�L Apache Common VFS �M�� ���^���ݪ��귽
	 *
	 * ��ϥΪ���w�ѦҡG
	 * 
	 * @see "https://commons.apache.org/proper/commons-vfs/filesystems.html"
	 */
	public static String getFromRemote(String url) {
		try {
			// �z�L Apache VFS ���^���w�����ݸ��
			FileSystemManager fsManager = VFS.getManager();
			FileSystemOptions fsOption = new FileSystemOptions();
			
			Cookie cookie = new Cookie("www.ptt.cc", "over18", "1", "/", null, false);
			
			HttpFileSystemConfigBuilder.getInstance().setCookies(fsOption, new Cookie[]{cookie});
			FileObject file = fsManager.resolveFile(url, fsOption);
			
			return IOUtils.toString(file.getContent().getInputStream(), "UTF-8");
			
		} catch (Exception ex) {
			if(ex.getMessage().contains("because it is a not a file.")){
				System.out.println(ex.getMessage());
				return null;
			}
			System.out.println(ex.getMessage());
//			ex.printStackTrace();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return getFromRemote(url);
		}
	}
	/**
	 * ���o���ݮ榡�� XML �����
	 *
	 * @param url
	 *            required Apache Common VFS supported file systems
	 * @return Jsoup Document
	 */
	public static org.jsoup.nodes.Document getFromXml(String url) {
		// ���^��ơA����Ƭ�XML�榡
		String xml = getFromRemote(url);

		// Custom code here

		// ��Ƭ� Jsoup ����
		return xmlToJsoupDoc(xml);
	}
}
