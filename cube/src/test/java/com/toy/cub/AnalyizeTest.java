package com.toy.cub;

import com.toy.cube.lql.Analyize;
import com.toy.cube.lql.Compile;
import com.toy.cube.lql.Exe;
import com.toy.cube.lql.ReadFile;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by ljx on
 * 2017/8/5.
 */
public class AnalyizeTest {
    @Test
    public void analyizeTest() throws IOException, SQLException, ClassNotFoundException {
        ReadFile readFile = new ReadFile("E:\\ljx\\cube\\src\\main\\resources\\APP_CreateWarnAudit31.wql");
        Compile analyize = new Compile(readFile);
        Exe exe = analyize.compile();
        Analyize analyize1 = new Analyize(exe);
        analyize1.analyizeExe();
        System.out.print("aaaa");
    }
}
