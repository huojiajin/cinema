package howard.cinema.manage.manage.common;

import howard.cinema.core.manage.poi.ExcelTemplateHelper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * @name: AbstractExcelManager
 * @description: 涉及Excel导入导出通用方法的Manager
 * @author: huojiajin
 * @time: 2020/11/12 15:42
 */
public abstract class AbstractExcelManager extends AbstractManager{

    /**
     * @Name getCellValue
     * @Author HuoJiaJin
     * @Description 获取单元格的值
     * @Date 2021/2/1 15:39
     * @Param [rowData, index]
     * @return java.lang.String
     **/
    public String getCellValue(Map<Integer, Cell> rowData, int index) throws InterruptedException {
        Cell cell = rowData.get(index);
        switch (cell.getCellType()) {
            case NUMERIC:
                DecimalFormat df = new DecimalFormat("0");
                return String.valueOf(df.format(cell.getNumericCellValue()));
            case STRING:
                return cell.getStringCellValue();
            case BLANK:
                throw new InterruptedException("必填单元格为空");
            default:
                throw new InterruptedException("格式不正确");
        }
    }

    /**
     * @Name regionCells
     * @Author HuoJiaJin
     * @Description 合并单元格
     * @Date 2021/2/1 15:39
     * @Param [workbook, sheet, cell, cellValue, firstRow, lastRow, firstCol, lastCol]
     * @return void
     **/
    public void regionCells(Workbook workbook, Sheet sheet, Cell cell, String cellValue,
                            int firstRow, int lastRow, int firstCol, int lastCol){
        //创建其余空白表格，不创建会报错
        for (int i = firstRow; i <= lastRow; i++) {
            for (int j = firstCol; j <= lastCol; j++) {
                cell = ExcelTemplateHelper.getCell(sheet, i, j);
                if (i == firstRow && j == firstCol){
                    cell.setCellValue(cellValue);
                }
            }
        }

        CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(region);
        CellStyle cellType = setCellStyle(cell, workbook, true, false);
        for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
            Row row = sheet.getRow(i);
            for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                cell = row.getCell(j);
                cell.setCellStyle(cellType);
            }
        }
    }

    /**
     * @Name setCellValueAndStyle
     * @Author HuoJiaJin
     * @Description 设置单元格的值
     * @Date 2021/2/1 15:40
     * @Param [cell, workbook, value, bold]
     * @return void
     **/
    public void setCellValueAndStyle(Cell cell, Workbook workbook, String value, boolean bold) {
        CellStyle cellStyle = setCellStyle(cell, workbook, bold, false);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }
    
    /**
     * @Name setCellStyle
     * @Author HuoJiaJin
     * @Description 设置单元格样式
     * @Date 2021/2/1 15:40
     * @Param [cell, workbook, bold, error]
     * @return org.apache.poi.ss.usermodel.CellStyle
     **/
    public CellStyle setCellStyle(Cell cell, Workbook workbook, boolean bold, boolean error){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(cell.getCellStyle());
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        Font font = workbook.createFont();
        font.setBold(bold);
        cellStyle.setFont(font);
        //设置边框线
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        if (error) {
            cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        }
        return cellStyle;
    }
}
