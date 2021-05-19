package howard.cinema.core.manage.poi;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Map;

public interface ExcelReadRowHandler<E>
{
	/**
	 * 根据行信息生成model
	 * 
	 * @param row
	 *            行号
	 * @param rowData
	 *            行数据
	 * @return
	 */
	public E row2Model(int row, Map<Integer, Cell> rowData) throws Exception;
}
