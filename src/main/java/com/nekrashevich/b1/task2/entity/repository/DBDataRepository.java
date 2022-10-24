package com.nekrashevich.b1.task2.entity.repository;

import com.nekrashevich.b1.task2.consts.FileConsts;
import com.nekrashevich.b1.task2.consts.PathConsts;
import com.nekrashevich.b1.task2.entity.DBData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface DBDataRepository extends CrudRepository<DBData, Integer> {
    @Query(value = "LOAD DATA INFILE '" + PathConsts.OUTPUT_PATH + FileConsts.DATA_FILE_NAME + "' INTO TABLE task2_db.data\n" +
            "FIELDS TERMINATED BY ','", nativeQuery = true)
    @Modifying
    @Transactional
    void importDataToDB();
}
