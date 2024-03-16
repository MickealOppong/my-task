package com.opp.todo.impl;

import com.opp.todo.interfaces.TodoImageUtil;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Component
public class TodoImageUtilImpl implements TodoImageUtil {
    @Override
    public byte[] compress(byte[] data) throws DataFormatException {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];

        while(!deflater.finished()){
            int size = deflater.deflate(data);
            outputStream.write(tmp,0,size);
        }
        return outputStream.toByteArray();
    }

    @Override
    public byte[] deCompress(byte[] data) throws DataFormatException {

        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];

        while(!inflater.finished()){
            int size = inflater.inflate(data);
            outputStream.write(tmp,0,size);
        }
        return outputStream.toByteArray();
    }
}
