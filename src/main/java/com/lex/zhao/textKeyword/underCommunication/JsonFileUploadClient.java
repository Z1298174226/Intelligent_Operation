package com.lex.zhao.textKeyword.underCommunication;

import com.lex.zhao.textKeyword.topo.WeightedGraph;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by qtfs on 2018/7/10.
 */
public class JsonFileUploadClient {
    public static BufferedReader bufread;
    public void connect(int port, String host, final FileUploadFile fileUploadFile) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
                    ch.pipeline().addLast(new FileUploadClientHandler(fileUploadFile));
                }
            });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public  static void readTxtFile(File filename) {
        String read;
        FileReader fileread;
        try {
            fileread = new FileReader(filename);
            bufread = new BufferedReader(fileread);
            while ((read = bufread.readLine()) != null) {
                System.out.println(read);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
        }
    }

    public static void transmitJsonBuildPath() {
        try {
            FileUploadFile uploadFile = new FileUploadFile();
            File file = new File("src\\main\\resources\\buildPath.json");
            String fileMd5 = file.getName();
            uploadFile.setFile(file);
            uploadFile.setFile_md5(fileMd5);
            uploadFile.setStarPos(0);
            new JsonFileUploadClient().connect(4048, "10.108.49.220", uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void transmitJsonKeyWords() {
        try {
            FileUploadFile uploadFile = new FileUploadFile();
            File file = new File("src\\main\\resources\\keywords.json");
            String fileMd5 = file.getName();
            uploadFile.setFile(file);
            uploadFile.setFile_md5(fileMd5);
            uploadFile.setStarPos(0);
            new JsonFileUploadClient().connect(4048, "10.108.49.220", uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void transmitJsonPolicy() {
        try {
            FileUploadFile uploadFile = new FileUploadFile();
            File file = new File("src\\main\\resources\\policy.json");
            String fileMd5 = file.getName();
            uploadFile.setFile(file);
            uploadFile.setFile_md5(fileMd5);
            uploadFile.setStarPos(0);
            new JsonFileUploadClient().connect(4048, "10.108.49.220", uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
