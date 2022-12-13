package id.skaynix.ecommerce.controller;

import id.skaynix.ecommerce.service.ReportExcelExporter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class OrderController {

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());
        String fileName = "report_" + currentDateTime + ".xlsx";

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;

        response.setHeader(headerKey,headerValue);

        ReportExcelExporter excelExporter = new ReportExcelExporter();
        excelExporter.export(response);
    }

}
