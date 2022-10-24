package com.nekrashevich.b1.task2.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "file")
public class BalanceSheetFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "file_name")
    private String fileName;
    @OneToMany(mappedBy = "balanceSheetFile", cascade = CascadeType.ALL)
    private List<DBData> data = new ArrayList<>();

    public BalanceSheetFile() {
    }

    public BalanceSheetFile(int id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public BalanceSheetFile(int id, String fileName, List<DBData> data) {
        this.id = id;
        this.fileName = fileName;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<DBData> getData() {
        return data;
    }

    public void setData(List<DBData> data) {
        this.data = data;
    }
}
