package howard.cinema.core.manage.poi;

import howard.cinema.core.dao.entity.common.BaseEntity;

import java.io.InputStream;

public class PoiExcelInfo extends BaseEntity
{
	private InputStream excelInput;// excel的文件流
	private int sheetIndex;// 读取的sheet标签页--from 0
	private int fromRow;// 从哪行开始读，为负数时从头开始读取(包含该行) from 0
	private int toRow;// 读取到哪行，为负数时读取到第一次碰到空行。(包含该行) from 0
	private int colSize;// 每行总共读取的列数 
	private boolean breakOnRowNull = true;// 当读取碰到空行时不再继续往下读取
	private boolean xlsx;// 是否是97以上的格式

	public boolean isXlsx()
	{
		return xlsx;
	}

	public PoiExcelInfo setXlsx(boolean xlsx)
	{
		this.xlsx = xlsx;
		return this;
	}

	public InputStream getExcelInput()
	{
		return excelInput;
	}

	public PoiExcelInfo setExcelInput(InputStream excelInput)
	{
		this.excelInput = excelInput;
		return this;
	}

	public int getSheetIndex()
	{
		return sheetIndex;
	}

	public PoiExcelInfo setSheetIndex(int sheetIndex)
	{
		this.sheetIndex = sheetIndex;
		return this;
	}

	public int getFromRow()
	{
		return fromRow;
	}

	public PoiExcelInfo setFromRow(int fromRow)
	{
		this.fromRow = fromRow;
		return this;
	}

	public int getToRow()
	{
		return toRow;
	}

	public PoiExcelInfo setToRow(int toRow)
	{
		this.toRow = toRow;
		return this;
	}

	public int getColSize()
	{
		return colSize;
	}

	public PoiExcelInfo setColSize(int colSize)
	{
		this.colSize = colSize;
		return this;
	}

	public boolean isBreakOnRowNull()
	{
		return breakOnRowNull;
	}

	public PoiExcelInfo setBreakOnRowNull(boolean breakOnRowNull)
	{
		this.breakOnRowNull = breakOnRowNull;
		return this;
	}

}
