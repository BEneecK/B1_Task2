package com.nekrashevich.b1.task2.controller;

import com.nekrashevich.b1.task2.entity.BalanceSheetClass;
import com.nekrashevich.b1.task2.entity.BalanceSheetFile;
import com.nekrashevich.b1.task2.entity.repository.BalanceSheetClassRepository;
import com.nekrashevich.b1.task2.entity.repository.BalanceSheetFileRepository;
import com.nekrashevich.b1.task2.service.ImportDataToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomePageController {

    private final BalanceSheetFileRepository balanceSheetFileRepository;
    private final BalanceSheetClassRepository balanceSheetClassRepository;
    private final ImportDataToFile importDataToFile;

    @Autowired
    public HomePageController(BalanceSheetFileRepository balanceSheetFileRepository,
                              BalanceSheetClassRepository balanceSheetClassRepository, ImportDataToFile importDataToFile) {
        this.balanceSheetFileRepository = balanceSheetFileRepository;
        this.balanceSheetClassRepository = balanceSheetClassRepository;
        this.importDataToFile = importDataToFile;
    }

    @GetMapping("/")
    public String uploadedFiles(Model model) {
        Iterable<BalanceSheetFile> balanceSheetFiles = balanceSheetFileRepository.findAll();
        model.addAttribute("files", balanceSheetFiles);
        return "homePage";
    }

    @GetMapping("/save-to-file/{id}")
    public String saveFile(@PathVariable(value = "id") int balanceSheetFileId, Model model) {
        Iterable<BalanceSheetClass> balanceSheetClasses = balanceSheetClassRepository.findAll();
        List<BalanceSheetClass> list = new ArrayList<>();
        balanceSheetClasses.forEach(list::add);
        importDataToFile.importData(list, balanceSheetFileId);
        return "createFilePage";
    }
}
