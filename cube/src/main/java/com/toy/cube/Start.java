package com.toy.cube;

import com.toy.cube.lql.Analyize;
import com.toy.cube.lql.Compile;
import com.toy.cube.lql.Exe;
import com.toy.cube.lql.ReadFile;

import java.io.IOException;
import java.sql.SQLException;

public class Start {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        if (args.length < 1)
            throw new RuntimeException("not input file");
        ReadFile readFile = new ReadFile(args[0]);
        Compile analyize = new Compile(readFile);
        Exe exe = analyize.compile();
        Analyize analyize1 = new Analyize(exe);
        analyize1.analyizeExe();
    }
}
