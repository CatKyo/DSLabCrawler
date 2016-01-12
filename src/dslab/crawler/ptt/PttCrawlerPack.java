package dslab.crawler.ptt;

import java.nio.charset.StandardCharsets;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.provider.http.HttpFileSystemConfigBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.MyXmlTreeBuilder;
import org.jsoup.parser.Parser;

public class PttCrawlerPack{
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
			
			Cookie[] cookies = new Cookie[1];
			cookies[0] = new Cookie("/","over18","1");
			
			HttpFileSystemConfigBuilder.getInstance().setCookies(fsOption, cookies);
			FileObject file = fsManager.resolveFile(url, fsOption);
			
			return IOUtils.toString(file.getContent().getInputStream(), "UTF-8");
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}
	

	/**
	 * ���o���ݮ榡�� JSON �����
	 *
	 * @param url
	 *            required Apache Common VFS supported file systems
	 * @return Jsoup Document
	 * @throws JSONException
	 */
	public static org.jsoup.nodes.Document getFromJson(String url) throws JSONException {
		// ���^��ơA����Ƭ�XML�榡
		String json = getFromRemote(url);

		// �N json ��Ƭ� xml
		String xml = jsonToXml(json);

		// Custom code here

		// ��Ƭ� Jsoup ����
		return xmlToJsoupDoc(xml);
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

	/**
	 * HTML �P XML �B�z�Ҧ��ۦP
	 */
	public static org.jsoup.nodes.Document getFromHtml(String url) {
		return getFromXml(url);
	}

	/**
	 * �N json �ର XML
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static String jsonToXml(String json) throws JSONException {
		String xml = "";
		// �B�z�����H�}�C�}�Y��JSON�A�ë��w���� row �� tag
		if ("[".equals(json.substring(0, 1))) {
			xml = XML.toString(new JSONArray(json), "row");
		} else {
			xml = XML.toString(new JSONObject(json));
		}

		return xml;
	}

	// �����r���G�@�w�n�O a-zA-Z �}�Y���զX
	final static String prefix = "all-lower-case-prefix";

	/**
	 * �N XML ��Ƭ� Jsoup Document ����
	 *
	 * �p�G�I��Tag �W�٭��r���D a-zA-Z ���r���Ajsoup �|�ѪR������ �ҥH���ݥ��F�����m�J prefix �A��gxmlParse
	 * �b�^�Ǯɲ���prefix
	 *
	 */
	public static org.jsoup.nodes.Document xmlToJsoupDoc(String xml) {		
		if(xml != null){
			// Tag ���r���D a-zA-Z ����Ƭ����Ѫ����D
			xml = xml.replaceAll("<([^A-Za-z\\/][^\\/>]*)>", "<" + prefix.toLowerCase() + "$1>")
					.replaceAll("<\\/([^A-Za-z\\/][^\\/>]*)>", "</" + prefix.toLowerCase() + "$1>");

			// �N xml(html/html5) �ର jsoup Document ����
			Document jsoupDoc = Jsoup.parse(xml, "", new Parser(new MyXmlTreeBuilder(prefix.toLowerCase())));
			jsoupDoc.charset(StandardCharsets.UTF_8);

			return jsoupDoc;
		}
		return null;
	}
}
