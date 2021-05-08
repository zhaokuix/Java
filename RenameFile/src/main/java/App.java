import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kouzk
 */
public class App {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请将 编码-名称 的对照表Excel文件拖入此处");
        String codeName = scanner.nextLine();
        System.out.println("请将要修改文件所在的文件夹拖入此处");
        String isToRename = scanner.nextLine();
        //读取文件流
        InputStream inputStream = new FileInputStream(codeName);
        //封装为excel格式
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
        //读取第一个Sheet
        Sheet sheet = xssfWorkbook.getSheetAt(0);
        //读取文件中的数据
        Map<String, String> map = parseData(sheet);
        //读取要更改的文件夹
        File file = new File(isToRename);
        new Thread(new RenameThread(file,map)).start();
    }

    public static Map<String, String> parseData(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 1; i < lastRowNum + 1; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                //读取key
                Cell key = row.getCell(0);
                Cell value = row.getCell(1);
                map.put(getCellValue(key).toLowerCase(), getCellValue(value));
            }
        }
        return map;
    }
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        // 将cell全部作为string类型处理
        DataFormatter dataFormatter = new DataFormatter();
        return dataFormatter.formatCellValue(cell);
    }
}
