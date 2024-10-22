//package org.jeecg;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.CellType;
//import org.junit.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 读取txt文件，写入Excel文件
// */
//@SpringBootTest
//public class FileTest {
//
//
//    @Test
//    public void redTxt(){
//
//        try {
//            String encoding="utf-8";
//            File file = new File("C:\\Users\\devel\\Desktop\\bal.txt");
//
//            if(file.isFile() && file.exists()){ //判断文件是否存在
//                InputStreamReader read  = new InputStreamReader(new FileInputStream(file),encoding);
//                BufferedReader bufferedReader = new BufferedReader(read);
//
//                String lineTxt = null;
//
//                //表头
//                String title[] = {"流转单号","发卡机构","申请流水号","类型","卡号","品牌","产品","交易金额","明细状态","操作结果","充值途径","申请机构",
//                "申请操作员","申请日期","确认机构","充值操作员","充值日期","备注"};
//                createExcel("C:\\Users\\devel\\Desktop\\bal.xls","Sheet1",title);
//                List<Map> list = new ArrayList<Map>();
//                while ((lineTxt = bufferedReader.readLine()) != null){
//                    System.out.println(lineTxt);
//                    String[] lineStr = lineTxt.split(",");
//////
//                    for (int i = 0;i<lineStr.length;i++){
//                        System.out.println(lineStr[i]);
//                    }
//
//                    Map<String,String> map = new HashMap<String,String>();
//                    map.put("流转单号",lineStr[0]);
//                    map.put("发卡机构",lineStr[1]);
//                    map.put("申请流水号",lineStr[2]);
//                    map.put("类型",lineStr[2]);
//                    map.put("卡号",lineStr[2]);
//                    map.put("品牌",lineStr[2]);
//                    map.put("产品",lineStr[2]);
//                    map.put("交易金额",lineStr[2]);
//                    map.put("明细状态",lineStr[2]);
//                    map.put("操作结果",lineStr[2]);
//                    map.put("充值途径",lineStr[2]);
//                    map.put("申请机构",lineStr[2]);
//                    map.put("申请操作员",lineStr[2]);
//                    map.put("申请日期",lineStr[2]);
//                    map.put("确认机构",lineStr[2]);
//                    map.put("充值操作员",lineStr[2]);
//                    map.put("充值日期",lineStr[2]);
//                    map.put("备注",lineStr[2]);
//
//                    list.add(map);
//                }
//
////                System.out.println(fileExist("C:\\Users\\devel\\Desktop\\txt\\txn.xls"));
//                writeToExcel("C:\\Users\\devel\\Desktop\\bal.xls","Sheet1",list);
//            }else{
//                System.out.println("找不到指定文件");
//            }
//        }catch (Exception e){
//            System.out.println("出错了");
//            System.out.println(e.getMessage());
//            System.out.println(e.fillInStackTrace());
//        }
//    }
//
//    private static HSSFWorkbook workbook = null;
//
//    /**
//     * 判断文件是否存在.
//     * @param fileDir  文件路径
//     * @return
//     */
//    public static boolean fileExist(String fileDir){
//        boolean flag = false;
//        File file = new File(fileDir);
//        flag = file.exists();
//        return flag;
//    }
//    /**
//     * 判断文件的sheet是否存在.
//     * @param fileDir   文件路径
//     * @param sheetName  表格索引名
//     * @return
//     */
//    public static boolean sheetExist(String fileDir,String sheetName) throws Exception{
//        boolean flag = false;
//        File file = new File(fileDir);
//        if(file.exists()){    //文件存在
//            //创建workbook
//            try {
//                workbook = new HSSFWorkbook(new FileInputStream(file));
//                //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
//                HSSFSheet sheet = workbook.getSheet(sheetName);
//                if(sheet!=null)
//                    flag = true;
//            } catch (Exception e) {
//                throw e;
//            }
//
//        }else{    //文件不存在
//            flag = false;
//        }
//        return flag;
//    }
//    /**
//     * 创建新excel.
//     * @param fileDir  excel的路径
//     * @param sheetName 要创建的表格索引
//     * @param titleRow excel的第一行即表格头
//     */
//    public static void createExcel(String fileDir,String sheetName,String titleRow[]) throws Exception{
//        //创建workbook
//        workbook = new HSSFWorkbook();
//        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
//        HSSFSheet sheet1 = workbook.createSheet(sheetName);
//        //新建文件
//        FileOutputStream out = null;
//        try {
//            //添加表头
//            HSSFRow row = workbook.getSheet(sheetName).createRow(0);    //创建第一行
//            for(short i = 0;i < titleRow.length;i++){
//                HSSFCell cell = row.createCell(i);
//                cell.setCellValue(titleRow[i]);
//            }
//            out = new FileOutputStream(fileDir);
//            workbook.write(out);
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    /**
//     * 删除文件.
//     * @param fileDir  文件路径
//     */
//    public static boolean deleteExcel(String fileDir) {
//        boolean flag = false;
//        File file = new File(fileDir);
//        // 判断目录或文件是否存在
//        if (!file.exists()) {  // 不存在返回 false
//            return flag;
//        } else {
//            // 判断是否为文件
//            if (file.isFile()) {  // 为文件时调用删除文件方法
//                file.delete();
//                flag = true;
//            }
//        }
//        return flag;
//    }
//
//
//    /**
//     * 往excel中写入(已存在的数据无法写入).
//     * @param fileDir    文件路径
//     * @param sheetName  表格索引
//     * @param
//     * @throws Exception
//     */
//    public static void writeToExcel(String fileDir, String sheetName, List<Map> mapList) throws Exception{
//        //创建workbook
//        File file = new File(fileDir);
//        try {
//            workbook = new HSSFWorkbook(new FileInputStream(file));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //流
//        FileOutputStream out = null;
//        HSSFSheet sheet = workbook.getSheet(sheetName);
//        // 获取表格的总行数
//        // int rowCount = sheet.getLastRowNum() + 1; // 需要加一
//        // 获取表头的列数
//        int columnCount = sheet.getRow(0).getLastCellNum();
//        try {
//            // 获得表头行对象
//            HSSFRow titleRow = sheet.getRow(0);
//            if(titleRow!=null){
//                for(int rowId=0;rowId<mapList.size();rowId++){
//                    Map map = mapList.get(rowId);
//                    HSSFRow newRow=sheet.createRow(rowId+1);
//                    for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {  //遍历表头
//                        String mapKey = titleRow.getCell(columnIndex).toString().trim();
////                        System.out.println(mapKey);
//                        HSSFCell cell = newRow.createCell(columnIndex);
//                        cell.setCellType(CellType.forInt(HSSFCell.ENCODING_UTF_16));
//                        System.out.println(map.get(mapKey));
//                        cell.setCellValue(map.get(mapKey)==null ? null : map.get(mapKey).toString());
//                    }
//                }
//            }
//
//            out = new FileOutputStream(fileDir);
//            workbook.write(out);
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
