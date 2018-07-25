package com.lex.zhao.textKeyword.underCommunication;

import com.lex.zhao.textKeyword.ParameterMap;
import com.lex.zhao.textKeyword.StrategyChoice;
import com.lex.zhao.textKeyword.intentionCollection.TextKeyWord;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
/**
 * Created by qtfs on 2018/7/24.
 */
public class IntendReceiveServerHandler extends ChannelInboundHandlerAdapter {
    private int byteRead;
    private volatile int start = 0;
    private String file_dir = "src\\main\\resources";
    String path;
    File file;
    public static BufferedReader bufread;
    //指定文件路径和名称

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FileUploadFile) {
            FileUploadFile ef = (FileUploadFile) msg;
            byte[] bytes = ef.getBytes();
            byteRead = ef.getEndPos();
            System.out.println("endPos : " + byteRead);
            String md5 = ef.getFile_md5();
            path = file_dir + File.separator + md5;
            file = new File(path);
            //文件清空
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();

            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(start);

            randomAccessFile.write(bytes);
        //    IntendReceiveServer.readTxtFile(file);
            randomAccessFile.close();
            start = start + byteRead;
            if (byteRead > 0) {
                ctx.writeAndFlush(start);
                /*=================================读取用户意图=========================*/
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "unicode"));
                String content = reader.readLine();
                System.out.println(content);
                List<String> wordList = new TextKeyWord().getKeyword("", content);
                Map<String, Integer> resultMap = new ParameterMap().parameterMap(wordList);
                new StrategyChoice().strategyChoice(resultMap);
               /*=======================================================================*/
                randomAccessFile.close();
                ctx.close();
            } else {
                randomAccessFile.close();
                ctx.close();
            }
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
