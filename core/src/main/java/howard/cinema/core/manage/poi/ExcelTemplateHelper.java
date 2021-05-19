package howard.cinema.core.manage.poi;

import howard.cinema.core.manage.common.CommonAbstract;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelTemplateHelper extends CommonAbstract
{

	/**
	 * 使用指定模板及填充数据生成excel(xlsx)文件
	 * 
	 * @param is
	 *            模板信息及生成文件信息，参见注释
	 * @param data
	 *            用于填充的数据
	 * @param handler
	 *            填充数据的handler
	 * @return
	 * @throws Exception
	 */
	public static <E> ByteArrayOutputStream generateExcel(InputStream is, boolean isXlsx, E data,
			WorkbookWithDataHandler<E> handler) throws Exception
	{
		InputStream in = null;
		Workbook workbook = null;
		ByteArrayOutputStream op = new ByteArrayOutputStream();
		try
		{
			workbook = createWorkbook(is, isXlsx);
			handler.fillWorkbook(workbook, data);
			workbook.write(op);
			op.flush();
		}
		finally
		{
			closeQuietly(op);
			closeQuietly(in);
			closeQuietly(workbook);
		}
		return op;
	}

	/**
	 * 从excel中读取数据
	 * 
	 * @param info
	 *            读取的参数配置
	 * @param handler
	 *            行数据处理器
	 * @return 读取的数据列表
	 * @throws IOException
	 */
	public static <E> List<E> readData(PoiExcelInfo info, ExcelReadRowHandler<E> handler) throws Exception
	{
		List<E> rs = new ArrayList<E>();
		InputStream in = info.getExcelInput();
		Workbook workbook = null;
		try
		{
			workbook = createWorkbook(in, info.isXlsx());
			Sheet sheet = workbook.getSheetAt(info.getSheetIndex());
			if (sheet == null) return rs;
			int fromRow = info.getFromRow() < 0 ? 0 : info.getFromRow();
			int toRow = info.getToRow() < 0 ? Integer.MAX_VALUE : info.getToRow();
			if (toRow < fromRow) throw new IllegalArgumentException("结束行号不能小于开始行号.");
			for (int i = 0; i <= toRow; i++)
			{
				if (i < fromRow) continue;
				Row row = sheet.getRow(i);
				if (info.isBreakOnRowNull() && row == null) break;
				if (row == null) continue;// 空行不退出时，忽略空行
				Map<Integer, Cell> rowData = new HashMap<Integer, Cell>();
				for (int j = 0; j < info.getColSize(); j++)
				{
					Cell cell = row.getCell(j);
					// 为空时，new一个空cell
					rowData.put(j, cell == null ? getCell(sheet, i, j) : cell);
				}
				E e = handler.row2Model(i, rowData);
				if (e != null) rs.add(e);
			}
		}
		finally
		{
			closeQuietly(in);
			closeQuietly(workbook);
		}
		return rs;
	}

	private static Workbook createWorkbook(InputStream in, boolean xlsx) throws IOException
	{
		return xlsx ? new XSSFWorkbook(in) : new HSSFWorkbook(new POIFSFileSystem(in));
	}

	/**
	 * 获取指定单元格，存在返回值，不存在新建单元格
	 * 
	 * @param sheet
	 *            sheet页
	 * @param row
	 *            行号
	 * @param col
	 *            列号
	 * @return 单元格
	 */
	public static Cell getCell(Sheet sheet, int row, int col)
	{
		Row sheetRow = sheet.getRow(row);
		if (sheetRow == null) sheetRow = sheet.createRow(row);
		Cell cell = sheetRow.getCell(col);
		if (cell == null) cell = sheetRow.createCell(col);
		return cell;
	}

	/**
	 * 取得指定单元格的值，不存在返回null
	 * 
	 * @param sheet
	 *            sheet页
	 * @param row
	 *            行号
	 * @param col
	 *            列号
	 * @return 单元格
	 */
	public static Cell getCellValue(Sheet sheet, int row, int col)
	{
		Row sheetRow = sheet.getRow(row);
		if (sheetRow == null) return null;
		Cell cell = sheetRow.getCell(col);
		return cell == null ? null : cell;
	}
}
