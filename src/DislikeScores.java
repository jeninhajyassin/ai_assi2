import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DislikeScores {
    public static double[][] readDislikeScores(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        int numRows = sheet.getPhysicalNumberOfRows();
        int numCols = sheet.getRow(0).getPhysicalNumberOfCells();

        double[][] dislikeMatrix = new double[numRows - 1][numCols - 1];

        for (int i = 1; i < numRows; i++) {
            Row row = sheet.getRow(i);
            for (int j = 1; j < numCols; j++) {
                dislikeMatrix[i - 1][j - 1] = row.getCell(j).getNumericCellValue();
            }
        }

        workbook.close();
        return dislikeMatrix;
    }
}
