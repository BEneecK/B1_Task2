package com.nekrashevich.b1.task2.service;

import com.nekrashevich.b1.task2.entity.BalanceSheetClass;

import java.util.List;

public interface ImportDataToFile {
    void importData(List<BalanceSheetClass> list, int fileId);
}
