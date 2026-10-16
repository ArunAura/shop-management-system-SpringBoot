package Excel;
import com.example.shopmanagement.model.Shop;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {


     //to check weather the uploaded file is Excel
    public static Boolean CheckExcelFormat(MultipartFile file){

        String ContentType = file.getContentType();
        if (ContentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            return true;

        }else {
            return false;
        }

    }

    //converts the Excel to the list of shops
    public static List<Shop> ConvertExcelToListOfShop(InputStream is){

        List<Shop> list = new ArrayList<>();

        try {

            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheets = workbook.getSheet("Shops");

            int rowNumber =0;
            Iterator<Row> iterator = sheets.iterator();
            while(iterator.hasNext()){

                Row row = iterator.next();
                if (rowNumber==0){
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();
                int cid = 0;

                Shop shop = new Shop();
                while (cells.hasNext()){
                    Cell cell = cells.next();
                    switch(cid){

                        case 0:
                            shop.setOwnername(cell.getStringCellValue());
                            break;
                        case 1:
                            shop.setShoptype(cell.getStringCellValue());
                            break;
                        case 2:
                            shop.setAdress(cell.getStringCellValue());
                            break;
                        case 3:
                            shop.setCountry(cell.getStringCellValue());
                            break;
                        default:
                            break;

                    }
                    cid++;

                }

                list.add(shop);


            }



        }catch (Exception e){
            e.printStackTrace();
        }


        return list;

    }
}
