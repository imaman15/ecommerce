package id.skaynix.ecommerce.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReportExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

//    private List<Users> users;

//    List<Users> users
    public ReportExcelExporter() {
//        this.users = users;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Revenue");
    }

    private void writeHeaderRow() {
        Row row = sheet.createRow(0);
        Cell cell;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        style.setFont(font);

        int headCount = 7;
        for (int i = 0; i < headCount; i++) {
            cell = row.createCell(i);
            cell.setCellValue( (11 + i) + " Nov");
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
        }
    }

    private void writeDataRows() {
        Row rowOne = sheet.createRow(1);
        Row rowTwo = sheet.createRow(2);

        int[] dataOne = { 0, 10, 20, 25, 10, 50, 20 };
        int[] dataTwo = { 20, 10, 10, 18, 10, 22, 10 };

        int indexDataOne = 0;
        for (int item : dataOne){
            Cell cell = rowOne.createCell((short) indexDataOne);
            cell.setCellValue(item);
            sheet.autoSizeColumn(indexDataOne);
            indexDataOne++;
        }

        int indexDataTwo = 0;
        for (int item : dataTwo){
            Cell cell = rowTwo.createCell((short) indexDataTwo);
            cell.setCellValue(item);
            sheet.autoSizeColumn(indexDataTwo);
            indexDataTwo++;
        }

        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 4, 8, 20);

        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText("Revenue");
        chart.setTitleOverlay(false);

        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);

        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("Month");

        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("This Period & Last Period");

        XDDFDataSource<String> month = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(0, 0, 0, 6));
        XDDFNumericalDataSource<Double> lastPeriod = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, 1, 0, 6));
        XDDFNumericalDataSource<Double> thisPeriod = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(2, 2, 0, 6));

        XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis,leftAxis);

        XDDFLineChartData.Series series1 = (XDDFLineChartData.Series) data.addSeries(month, lastPeriod);
        series1.setTitle("Last Period", null);
        series1.setSmooth(true);
        series1.setMarkerStyle(MarkerStyle.SQUARE);

        XDDFLineChartData.Series series2 = (XDDFLineChartData.Series) data.addSeries(month, thisPeriod);
        series2.setTitle("This Period", null);
        series2.setSmooth(true);
        series2.setMarkerSize((short) 6);
        series2.setMarkerStyle(MarkerStyle.STAR);

        chart.plot(data);

    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
