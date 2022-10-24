package com.nekrashevich.b1.task2.service.impl;

import com.nekrashevich.b1.task2.consts.FileConsts;
import com.nekrashevich.b1.task2.consts.PathConsts;
import com.nekrashevich.b1.task2.service.DeleteCSVFiles;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DeleteCSVFilesImpl implements DeleteCSVFiles {
    @Override
    public void deleteFiles() {
        File file = new File(PathConsts.OUTPUT_PATH + FileConsts.CLASSES_FILE_NAME);
        file.delete();
        file = new File(PathConsts.OUTPUT_PATH + FileConsts.DATA_FILE_NAME);
        file.delete();
    }
}
