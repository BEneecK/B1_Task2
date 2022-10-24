package com.nekrashevich.b1.task2.controller;

import com.nekrashevich.b1.task2.consts.PathConsts;
import com.nekrashevich.b1.task2.entity.BalanceSheetFile;
import com.nekrashevich.b1.task2.entity.repository.BalanceSheetClassRepository;
import com.nekrashevich.b1.task2.entity.repository.BalanceSheetFileRepository;
import com.nekrashevich.b1.task2.entity.repository.DBDataRepository;
import com.nekrashevich.b1.task2.service.DeleteCSVFiles;
import com.nekrashevich.b1.task2.service.XLSToCSVImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/import-file")
public class LoadExcelFileController {

    private static final String ACCEPT_MESSAGE = "Данные загружены";

    private final XLSToCSVImport xlsToCSVImport;
    private final BalanceSheetClassRepository balanceSheetClassRepository;
    private final BalanceSheetFileRepository balanceSheetFileRepository;
    private final DBDataRepository dbDataRepository;
    private final DeleteCSVFiles deleteCSVFiles;

    @Autowired
    public LoadExcelFileController(XLSToCSVImport xlsToCSVImport, BalanceSheetClassRepository balanceSheetClassRepository,
                                   DBDataRepository dbDataRepository, DeleteCSVFiles deleteCSVFiles,
                                   BalanceSheetFileRepository balanceSheetFileRepository) {
        this.xlsToCSVImport = xlsToCSVImport;
        this.balanceSheetClassRepository = balanceSheetClassRepository;
        this.dbDataRepository = dbDataRepository;
        this.deleteCSVFiles = deleteCSVFiles;
        this.balanceSheetFileRepository = balanceSheetFileRepository;
    }

    @GetMapping
    public String importFileForm(Model model) {
        return "loadFile";
    }

    @PostMapping
    public String importFileToDB(@RequestParam("file") MultipartFile file, Model model) {

        String fileName = file.getOriginalFilename();
        try {
            file.transferTo(new File(PathConsts.LOAD_PATH + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int fileNumber = (int) balanceSheetFileRepository.count() + 1;
        //Запись в бд информации о файле
        BalanceSheetFile balanceSheetFile = new BalanceSheetFile(fileNumber, fileName);
        balanceSheetFileRepository.save(balanceSheetFile);

        xlsToCSVImport.importFile(fileName, fileNumber);
        //Если классы ведомости ещё не хранятся в бд, то записываем их
        if (balanceSheetClassRepository.count() == 0) {
            balanceSheetClassRepository.importClassesToDB();
        }
        dbDataRepository.importDataToDB();
        //Удаление временных .csv файлов
        deleteCSVFiles.deleteFiles();
        model.addAttribute("message", ACCEPT_MESSAGE);

        return "loadFile";
    }
}
