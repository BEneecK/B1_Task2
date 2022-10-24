package com.nekrashevich.b1.task2.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "class")
public class BalanceSheetClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name")
    private String className;
    @OneToMany(mappedBy = "balanceSheetClass", cascade = CascadeType.ALL)
    private List<DBData> data = new ArrayList<>();

    public BalanceSheetClass() {
    }

    public BalanceSheetClass(int id, String className, List<DBData> data) {
        this.id = id;
        this.className = className;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<DBData> getData() {
        return data;
    }

    public void setData(List<DBData> data) {
        this.data = data;
    }
}
