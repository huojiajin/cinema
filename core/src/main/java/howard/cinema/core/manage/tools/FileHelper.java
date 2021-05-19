package howard.cinema.core.manage.tools;

import com.google.common.collect.Maps;
import howard.cinema.core.manage.common.CommonAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public abstract class FileHelper extends CommonAbstract
{
	private static Logger logger = LoggerFactory.getLogger(FileHelper.class);
	private static Map<String, AtomicBoolean> floderMap = Maps.newConcurrentMap();

	/**
	 * 删除某个文件,文件不存在返回false.
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath)
	{
		if (filePath == null) throw new IllegalArgumentException("被删文件的路径名不能为空");
		File file = new File(filePath);
		if (file.exists() && file.isFile())
		{
			return file.delete();
		}
		return false;
	}

	/**
	 * 删除文件，如果删除失败抛出异常
	 * 
	 * @param file
	 *            被删除文件，null或者文件不存在，不执行操作。
	 */
	public static void deleteFile(File file)
	{
		if (file == null || !(file.exists())) return;
		if (!file.delete()) throw new RuntimeException("Delete file failed: " + file.getAbsolutePath());
	}

	/**
	 * 删除整个文件夹
	 * 
	 * @param floderPath
	 * @return
	 */
	public static boolean deleteDirectory(String floderPath)
	{
		return deleteDirectory(floderPath, true, 0);
	}

	public static boolean deleteDirectory(String floderPath, long holdTime)
	{
		if (holdTime <= 0) throw new IllegalArgumentException("hold time must > 0");
		return deleteDirectory(floderPath, false, holdTime);
	}

	/**
	 * 删除整个文件夹
	 * 
	 * @param floderPath
	 *            文件夹路径
	 * @param includeSelf
	 *            是否包含文件夹自身，如果文件保留时间>0永远不会删除自身
	 * @param holdTime
	 *            文件夹下文件的保留时间，毫秒
	 * @return
	 */
	public static boolean deleteDirectory(String floderPath, boolean includeSelf, long holdTime)
	{
		if (floderPath == null) throw new IllegalArgumentException("被删文件夹的路径名不能为空");
		File floder = new File(floderPath);
		if (!floder.isDirectory()) throw new RuntimeException("该路径不是文件夹:" + floderPath);
		if (isFloderDeleting(floderPath))
		{
			logger.info("删除文件夹({})的任务正在执行.", floder);
			return false;
		}
		try
		{
			floderMap.get(floderPath).set(true);
			Path root = Paths.get(floderPath);
			Files.walkFileTree(root, new DeleteFileVisitor(holdTime, root, includeSelf));
		}
		catch (IOException e)
		{
			logger.error("", e);
			return false;
		}
		finally
		{
			floderMap.remove(floderPath);
		}
		return true;
	}

	private static boolean isFloderDeleting(String floderPath)
	{
		AtomicBoolean ab = floderMap.get(floderPath);
		if (ab == null)
		{
			floderMap.put(floderPath, new AtomicBoolean(false));
			return false;
		}
		return ab.get();
	}

	private static class DeleteFileVisitor extends SimpleFileVisitor<Path>
	{
		private final long holdTime;
		private final Path root;
		private final boolean deleteRoot;

		protected DeleteFileVisitor(long holdTime, Path root, boolean deleteRoot)
		{
			super();
			this.holdTime = holdTime;
			this.root = root;
			this.deleteRoot = deleteRoot;
		}

		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
		{
			long current = System.currentTimeMillis();
			if (attrs.isRegularFile())
			{
				long existTime = current - attrs.lastModifiedTime().toMillis();
				if (holdTime < 0 || existTime > holdTime) deleteSilence(file.toFile());
			}
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
		{
			if (dir.equals(root))
			{
				if (holdTime > 0 || !deleteRoot) return FileVisitResult.CONTINUE;
			}
			try (Stream<Path> stream = Files.list(dir))
			{
				if (stream.count() == 0) Files.delete(dir);
			}
			return FileVisitResult.CONTINUE;
		}
	}

	public static void deleteSilence(File file)
	{
		if (file == null || !file.exists() || file.isDirectory())
		{
			logger.error("File does not exist: {}", file);
			return;
		}
		if (!file.delete()) logger.error("Unable to delete file:{} " + file);
	}
}
