package com.feng.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * excel的工具类
 *
 * @author fhn
 * @create 2021/7/19
 * @software idea
 */
public class ExcelUtil {


    public static void exportExcelUser(HttpServletResponse response, String fileName, List<String> columnList, List<List<String>> dataList) {
//        声明输出流
        OutputStream os = null;
//        设置响应头
        setResponseHeader(response, fileName);
        try {
//             初始化输出流
            os = response.getOutputStream();
//            内存保留1000条数据，以免内存溢出，其余写入硬盘
            SXSSFWorkbook wb = new SXSSFWorkbook(1000);
//            获取该工作区的第一个sheet
            Sheet sheet = wb.createSheet("人员表");
            int excelRow = 0;
//            创建标题行
            Row titleRow = sheet.createRow(excelRow++);
            for (int i = 0; i < columnList.size(); i++) {
//               创建该行下的每一列，并写入标题数据
                Cell cell = titleRow.createCell(i);
                cell.setCellValue(columnList.get(i));
            }
//           设置内容行
            if (!StringUtil.isNullOrBlank(dataList) && dataList.size() > 0) {
//                序号是从一开始的
                int count = 1;
//                外层for循环创建行
                for (List<String> strings : dataList) {
                    Row dataRow = sheet.createRow(excelRow++);
                    //内层for循环创建每行对应的列，并赋值
                    for (int j = 0; j < strings.size(); j++) {//由于多了一列序号列所以内层循环从-1开始
                        Cell cell = dataRow.createCell(j);
//                        if (j == -1) {//第一列是序号列，不是在数据库中读取的数据，因此手动递增赋值
//                            cell.setCellValue(count++);
//                        } else {//其余列是数据列，将数据库中读取到的数据依次赋值
                        cell.setCellValue(strings.get(j));
//                        }
                    }
                }
            }
//            将整理好的excel数据写入流中
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置浏览器下载响应头
     *
     * @param response
     * @param fileName
     */
    private static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            fileName = new String(fileName.getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
    }
}
