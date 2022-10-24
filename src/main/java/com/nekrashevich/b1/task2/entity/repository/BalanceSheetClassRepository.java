package com.nekrashevich.b1.task2.entity.repository;

import com.nekrashevich.b1.task2.consts.FileConsts;
import com.nekrashevich.b1.task2.consts.PathConsts;
import com.nekrashevich.b1.task2.entity.BalanceSheetClass;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BalanceSheetClassRepository extends CrudRepository<BalanceSheetClass, Integer> {

    @Query(value = "LOAD DATA INFILE '" + PathConsts.OUTPUT_PATH + FileConsts.CLASSES_FILE_NAME + "' INTO TABLE task2_db.class\n" +
            "FIELDS TERMINATED BY '||'", nativeQuery = true)
    @Modifying
    @Transactional
    void importClassesToDB();
}
