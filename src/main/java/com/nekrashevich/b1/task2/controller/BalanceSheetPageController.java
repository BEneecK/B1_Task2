package com.nekrashevich.b1.task2.controller;

import com.nekrashevich.b1.task2.entity.BalanceSheetClass;
import com.nekrashevich.b1.task2.entity.DBData;
import com.nekrashevich.b1.task2.entity.repository.BalanceSheetClassRepository;
import com.nekrashevich.b1.task2.entity.repository.DBDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/classes")
public class BalanceSheetPageController {

    private final BalanceSheetClassRepository balanceSheetClassRepository;
    private final DBDataRepository dbDataRepository;

    @Autowired
    public BalanceSheetPageController(BalanceSheetClassRepository balanceSheetClassRepository, DBDataRepository dbDataRepository) {
        this.balanceSheetClassRepository = balanceSheetClassRepository;
        this.dbDataRepository = dbDataRepository;
    }

    @GetMapping("/{id}")
    public String balanceSheetPage(@PathVariable(value = "id") int balanceSheetFileId, Model model) {
        Iterable<BalanceSheetClass> balanceSheetClasses = balanceSheetClassRepository.findAll();
        Iterable<DBData> dbDataList = dbDataRepository.findAll();
        /*Создаём мапу, где в качестве ключей хранится список данных,
        в качестве значений названия классов ведомости для дальнейшего вывода таблицы*/
        Map<List<DBData>, BalanceSheetClass> map = new LinkedHashMap<>();
        for (BalanceSheetClass balanceSheetClass : balanceSheetClasses) {
            List<DBData> dataList = new ArrayList<>();
            for (DBData dbData : dbDataList) {
                if (dbData.getBalanceSheetFile().getId() == balanceSheetFileId
                        && dbData.getBalanceSheetClass() == balanceSheetClass) {
                    dataList.add(dbData);
                }
            }
            map.put(dataList, balanceSheetClass);
        }
        model.addAttribute("data", map);
        return "balanceSheet";
    }
}
