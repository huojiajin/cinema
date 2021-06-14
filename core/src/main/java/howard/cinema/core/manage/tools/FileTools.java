package howard.cinema.core.manage.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.UUID;


public abstract class FileTools
{
	protected static Logger logger = LoggerFactory.getLogger(FileTools.class);

	public static String extractSuffix(String filename)
	{
		int index = filename.lastIndexOf('.');
		return filename.substring(index + 1).toLowerCase();
	}
	
	public static String generateUUIDName(String fileName)
	{
		int index = fileName == null ? -1 : fileName.lastIndexOf(".");
		String type = index < 0 ? "" : fileName.substring(index);
		return UUID.randomUUID().toString().replace("-", "") + type;
	}

	/**
	 * 将Blob转换为文件
	 * 
	 * @param path
	 *            转换后文件存储的路径
	 * @param blob
	 *            被转换的blob，不能能空
	 * @param name
	 *            转换后的文件名
	 * @param alwaysNew
	 *            如果指定目录下已经存在指定文件是否重新转换
	 * @return 转换后的文件
	 * @throws IOException
	 * @throws SQLException
	 */
	public static File blob2File(String path, Blob blob, String name, boolean alwaysNew)
			throws IOException, SQLException
	{
		Assert.notNull(blob, "param blob can not be null.");
		return input2File(path, blob.getBinaryStream(), name, alwaysNew);
	}

	/**
	 * 将input转换为文件
	 * 
	 * @param path
	 *            转换后文件存储的路径
	 * @param in
	 *            输入的流
	 * @param name
	 *            转换后的文件名
	 * @param alwaysNew
	 *            如果指定目录下已经存在指定文件是否重新转换
	 * @return 转换后的文件
	 * @throws IOException
	 */
	public static File input2File(String path, InputStream in, String name, boolean alwaysNew)
			throws IOException
	{
		Assert.notNull(in, "param input stream can not be null.");
		if (!StringUtils.hasText(path) || !StringUtils.hasText(name))
			throw new IllegalArgumentException("path and name must has text.");
		StringBuilder filePath = new StringBuilder(path);
		filePath.append("/").append(name);
		File file = new File(filePath.toString());
		// 不生成新的，文件已经存在，直接返回
		if (file.exists() && !alwaysNew) return file;
		File pathFile = new File(path);
		pathFile.mkdirs();
		FileOutputStream fos = new FileOutputStream(file);
		FileCopyUtils.copy(in, fos);
		return file;
	}


	public static File input2File(String path, InputStream in, String name) throws IOException
	{
		return input2File(path, in, name, false);
	}

	public static File blob2File(String path, Blob blob, String name) throws IOException,
			SQLException
	{
		return blob2File(path, blob, name, false);
	}

	/**
	 * 保存文本文件
	 * 
	 * @param path
	 * @param content
	 * @param name
	 * @param alwaysNew
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public static File string2File(String path, String content, String name, boolean alwaysNew)
			throws IOException
	{
		Assert.notNull(content, "param blob can not be null.");

		if (!StringUtils.hasText(path) || !StringUtils.hasText(name))
			throw new IllegalArgumentException("path and name must has text.");
		File file = new File(path + "/" + name);
		// 不生成新的，文件已经存在，直接返回
		if (file.exists() && !alwaysNew) return file;
		File pathFile = new File(path);
		pathFile.mkdirs();
		FileCopyUtils.copy(content.getBytes("UTF-8"), file);
		return file;
	}

	/**
	 * @Name getResourcesFileInputStream
	 * @Author HuoJiaJin
	 * @Description 读取文件流
	 * @Date 2020/6/21
	 * @Param [fileName]
	 * @return java.io.InputStream
	 **/
	public static InputStream getResourcesFileInputStream(String fileName) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
	}

	/**
	 * @Name deleteDir
	 * @Author HuoJiaJin
	 * @Description 递归删除目录及其子文件
	 * @Date 2021/6/9 22:31
	 * @Param [dir]
	 * @Return boolean
	 **/
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			//递归删除目录中的子目录下
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
}
