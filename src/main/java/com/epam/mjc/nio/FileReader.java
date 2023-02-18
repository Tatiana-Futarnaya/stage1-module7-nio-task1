package com.epam.mjc.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;


public class FileReader {

    public Profile getDataFromFile(File file) {
        Profile profile=new Profile();
        List<String> list=new ArrayList<>();
        try(RandomAccessFile aFile = new RandomAccessFile(file.getAbsolutePath(), "r");
            FileChannel channel=aFile.getChannel()){
            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
            String l="";
            String[] arr=new String[buffer.limit()];
            while (channel.read(buffer)>0){
                buffer.flip();

                for (int i=0; i<buffer.limit(); i++) {
                    l=l+(char)buffer.get();

                }

            }

            for (String item: l.split("(\\w*:\\s)|(\r\n)")) {
                if(item!=""){
                    list.add(item);
                }
            }
            buffer.clear();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        profile.setName(list.get(0));
        profile.setAge(Integer.valueOf(list.get(1)));
        profile.setEmail(list.get(2));
        profile.setPhone(Long.valueOf(list.get(3)));

        return profile;
    }
}
