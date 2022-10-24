package com.nekrashevich.b1.task2.entity;

import javax.persistence.*;

@Entity
@Table(name = "data")
public class DBData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private int id;
    @Column(name = "bank_account")
    private String bankAccount;
    @Column(name = "opening_balance_asset")
    private String openingBalanceAsset;
    @Column(name = "opening_balance_liability")
    private String openingBalanceLiability;
    @Column(name = "turnover_debit")
    private String turnoverDebit;
    @Column(name = "turnover_credit")
    private String turnoverCredit;
    @Column(name = "export_balance_asset")
    private String exportBalanceAsset;
    @Column(name = "export_balance_liability")
    private String exportBalanceLiability;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="class_id")
    private BalanceSheetClass balanceSheetClass;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="file_id")
    private BalanceSheetFile balanceSheetFile;

    public DBData() {
    }

    public DBData(int id, String bankAccount, String openingBalanceAsset,
                  String openingBalanceLiability, String turnoverDebit, String turnoverCredit,
                  String exportBalanceAsset, String exportBalanceLiability, BalanceSheetClass balanceSheetClass,
                  BalanceSheetFile balanceSheetFile) {
        this.id = id;
        this.bankAccount = bankAccount;
        this.openingBalanceAsset = openingBalanceAsset;
        this.openingBalanceLiability = openingBalanceLiability;
        this.turnoverDebit = turnoverDebit;
        this.turnoverCredit = turnoverCredit;
        this.exportBalanceAsset = exportBalanceAsset;
        this.exportBalanceLiability = exportBalanceLiability;
        this.balanceSheetClass = balanceSheetClass;
        this.balanceSheetFile = balanceSheetFile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getOpeningBalanceAsset() {
        return openingBalanceAsset;
    }

    public void setOpeningBalanceAsset(String openingBalanceAsset) {
        this.openingBalanceAsset = openingBalanceAsset;
    }

    public String getOpeningBalanceLiability() {
        return openingBalanceLiability;
    }

    public void setOpeningBalanceLiability(String openingBalanceLiability) {
        this.openingBalanceLiability = openingBalanceLiability;
    }

    public String getTurnoverDebit() {
        return turnoverDebit;
    }

    public void setTurnoverDebit(String turnoverDebit) {
        this.turnoverDebit = turnoverDebit;
    }

    public String getTurnoverCredit() {
        return turnoverCredit;
    }

    public void setTurnoverCredit(String turnoverCredit) {
        this.turnoverCredit = turnoverCredit;
    }

    public String getExportBalanceAsset() {
        return exportBalanceAsset;
    }

    public void setExportBalanceAsset(String exportBalanceAsset) {
        this.exportBalanceAsset = exportBalanceAsset;
    }

    public String getExportBalanceLiability() {
        return exportBalanceLiability;
    }

    public void setExportBalanceLiability(String exportBalanceLiability) {
        this.exportBalanceLiability = exportBalanceLiability;
    }

    public BalanceSheetClass getBalanceSheetClass() {
        return balanceSheetClass;
    }

    public void setBalanceSheetClass(BalanceSheetClass balanceSheetClass) {
        this.balanceSheetClass = balanceSheetClass;
    }

    public BalanceSheetFile getBalanceSheetFile() {
        return balanceSheetFile;
    }

    public void setBalanceSheetFile(BalanceSheetFile balanceSheetFile) {
        this.balanceSheetFile = balanceSheetFile;
    }
}
