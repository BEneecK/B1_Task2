package com.nekrashevich.b1.task2.service.impl;

import com.nekrashevich.b1.task2.consts.FileConsts;
import com.nekrashevich.b1.task2.consts.PathConsts;
import com.nekrashevich.b1.task2.service.XLSToCSVImport;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
public class XLSToCSVImportImpl implements XLSToCSVImport {

    private Iterator<Row> rowIterator;

    private static final int countOfCell = 7;
    private static int currentNumberOfClass = 1;
    private static int fileId;
    private final StringBuilder classes = new StringBuilder();
    private final StringBuilder data = new StringBuilder();
    private Row row;
    private int dataID = 1;

    @Override
    public void importFile(String fileName, int id) {
        File pkg = new File(PathConsts.LOAD_PATH + fileName);
        try (HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(pkg))) {
            setFileId(id);
            rowIterator = wb.getSheetAt(0).iterator();
            goToFirstClassDeclaration();
            appendClass();
            iterateThroughFileAndSave();
            clearBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setFileId(int id) {
        fileId = id;
    }

    //Отделение шапки от данных
    private void goToFirstClassDeclaration() {
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            if (isStartClass()) {
                break;
            }
        }
    }

    private void appendClass() {
        Cell cell = row.cellIterator().next();
        classes.append(currentNumberOfClass).append("||").append(cell.getStringCellValue()).append("\n");
    }

    private void iterateThroughFileAndSave() throws IOException {
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            if (isStartClass()) {
                appendClass();
            } else {
                appendCurrentLine();
                if (isEndClass()) {
                    currentNumberOfClass++;
                } else if (isEndDoc()) {
                    classes.append(currentNumberOfClass).append("||").append("Итого");
                    saveData();
                    saveClasses();
                    break;
                }
            }
        }
    }

    //Парсинг числа в нужный формат
    private String parseValue(Cell cell) {
        double value = cell.getNumericCellValue();
        return String.format("%.2f", value).replace(",", ".");
    }

    //Проверка является ли строка последней в данном классе
    private boolean isEndClass() {
        Cell cell = row.cellIterator().next();
        return cell.getCellType() == CellType.STRING && cell.getStringCellValue().contains("ПО КЛАССУ");
    }

    //Проверка конец ли документа
    private boolean isEndDoc() {
        Cell cell = row.cellIterator().next();
        return cell.getCellType() == CellType.STRING && cell.getStringCellValue().contains("БАЛАНС");
    }

    //Запись названия класса в StringBuilder
    private boolean isStartClass() {
        Cell cell = row.cellIterator().next();
        return cell.getCellType() == CellType.STRING && cell.getStringCellValue().contains("КЛАСС ");
    }

    //Вставка в стрингбилдер ткущей строки
    private void appendCurrentLine() {
        Iterator<Cell> cellIterator = row.cellIterator();
        data.append(dataID++).append(",");
        for (int iterator = 0, columnNumber = 1; iterator < countOfCell; ++columnNumber, ++iterator) {
            Cell cell = cellIterator.next();
            switch (cell.getCellType()) {
                case NUMERIC -> data.append(parseValue(cell)).append(",");
                case STRING -> data.append(cell.getStringCellValue()).append(",");
            }
        }
        data.append(currentNumberOfClass).append(",").append(fileId).append('\n');
    }

    private void saveData() throws IOException {
        String fileName = PathConsts.OUTPUT_PATH + "output.csv";
        byte[] bytes = data.toString().getBytes();
        saveBytes(fileName, bytes);
    }

    private void saveClasses() throws IOException {
        String fileName = PathConsts.OUTPUT_PATH + FileConsts.CLASSES_FILE_NAME;
        byte[] bytes = classes.toString().getBytes();
        saveBytes(fileName, bytes);
    }

    private void saveBytes(String fileName, byte[] bytes) throws IOException {
        File outputFileClass = new File(fileName);
        FileOutputStream fosForClassFile = new FileOutputStream(outputFileClass);
        fosForClassFile.write(bytes);
        fosForClassFile.close();
    }

    private void clearBuffer() {
        classes.setLength(0);
        data.setLength(0);
        currentNumberOfClass = 1;
    }
}