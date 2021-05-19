package howard.cinema.core.manage.tools.httpclient;

import org.apache.http.Header;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public interface FileDownloadHandler<T>
{
	/**
	 * 文件下载的回调接口
	 * 
	 * @param is
	 *            返回的流信息
	 * @param fileName
	 *            文件名
	 * @param headers
	 *            HTTP Header
	 * @param uri
	 *            本次请求的
	 * @return 下载完成返回的信息
	 * @throws IOException
	 */
	public T handle(InputStream is, String fileName, Header[] headers, URI uri) throws IOException;
}
