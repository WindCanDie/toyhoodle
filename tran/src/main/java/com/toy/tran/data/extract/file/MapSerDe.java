package com.toy.tran.data.extract.file;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.toy.tran.data.extract.SerDe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

/**
 * Created by Administrator on
 * 2017/8/14.
 */
public class MapSerDe implements SerDe {
    private String clomeSplie;
    private String vlaueSplie;

    @Override
    public void initialize(Properties pro) {
        clomeSplie = pro.getProperty("clomeSplie", ";");
        vlaueSplie = pro.getProperty("vlaueSplie", "=");
    }

    @Override
    public ByteBuffer serialize(final Object obj) {
        String value = (String) obj;
        String[] clomes = value.split(clomeSplie);
        try (DataOutputStream outputStream = new DataOutputStream(new ByteOutputStream())) {


        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public Object deserialize(DataInputStream buffer) {
        return null;
    }
}
