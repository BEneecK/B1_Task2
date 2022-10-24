package com.nekrashevich.b1.task2.service.impl;

import com.nekrashevich.b1.task2.consts.PathConsts;
import com.nekrashevich.b1.task2.entity.BalanceSheetClass;
import com.nekrashevich.b1.task2.entity.DBData;
import com.nekrashevich.b1.task2.service.ImportDataToFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ImportDataToFileImpl implements ImportDataToFile {
    private static String FILE_NAME;
    private static String HEADER;
    private static final StringBuilder stringBuilder = new StringBuilder();

    static class MakeHeader {
        private static final StringBuilder stringBuilder = new StringBuilder();
        private static final String BANK_ACCOUNT = "Б/сч";
        private static final String OPENING_BALANCE = "ВХОДЯЩЕЕ САЛЬДО";
        private static final String TURNOVER = "ОБОРОТЫ";
        private static final String EXPORT_BALANCE = "ИСХОДЯЩЕЕ САЛЬДО";
        private static final String ASSET = "Актив";
        private static final String LIABILITY = "Пассив";
        private static final String CREDIT = "Дебет";
        private static final String DEBIT = "Кредит";

        private static void formatHeader() {
            stringBuilder.append(String.format("%40s", OPENING_BALANCE));
            stringBuilder.append(String.format("%40s", TURNOVER));
            stringBuilder.append(String.format("%45s", EXPORT_BALANCE)).append("\n");
            stringBuilder.append(String.format("%9s", BANK_ACCOUNT));
            stringBuilder.append(String.format("%22s", ASSET));
            stringBuilder.append(String.format("%22s", LIABILITY));
            stringBuilder.append(String.format("%22s", DEBIT));
            stringBuilder.append(String.format("%22s", CREDIT));
            stringBuilder.append(String.format("%22s", ASSET));
            stringBuilder.append(String.format("%22s", LIABILITY)).append("\n");
            HEADER = stringBuilder.toString();
        }
    }

    @Override
    public void importData(List<BalanceSheetClass> list, int fileId) {
        createFileName(fileId);
        File file = new File(FILE_NAME);
        MakeHeader.formatHeader();
        stringBuilder.append(HEADER);
        try (FileWriter fileWriter = new FileWriter(file)) {
            //Цикл по классам ведомости
            for (BalanceSheetClass balanceSheetClass : list) {
                appendClassName(balanceSheetClass);
                //Цикл по данным каждого класса
                for (DBData data : balanceSheetClass.getData()) {
                    if (data.getBalanceSheetFile().getId() == fileId) {
                        appendData(data);
                    }
                }
            }
            fileWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        clearBuffer();
    }

    private void createFileName(int fileId) {
        FILE_NAME = PathConsts.LOAD_PATH + "Balance sheet" + fileId + ".txt";
    }

    private void appendClassName(BalanceSheetClass balanceSheetClass) {
        if (!balanceSheetClass.getClassName().equals("Итого")) {
            stringBuilder.append("\n").append(balanceSheetClass.getClassName());
            stringBuilder.append("\n\n");
        }
    }

    private void appendData(DBData data) {
        stringBuilder.append(String.format("%9s", data.getBankAccount())).append("||");
        stringBuilder.append(String.format("%20s", data.getOpeningBalanceAsset())).append("||");
        stringBuilder.append(String.format("%20s", data.getOpeningBalanceLiability())).append("||");
        stringBuilder.append(String.format("%20s", data.getTurnoverCredit())).append("||");
        stringBuilder.append(String.format("%20s", data.getTurnoverDebit())).append("||");
        stringBuilder.append(String.format("%20s", data.getExportBalanceAsset())).append("||");
        stringBuilder.append(String.format("%20s", data.getExportBalanceLiability())).append("||");
        stringBuilder.append("\n");
    }

    private void clearBuffer() {
        HEADER = "";
        stringBuilder.setLength(0);
        MakeHeader.stringBuilder.setLength(0);
    }
}
