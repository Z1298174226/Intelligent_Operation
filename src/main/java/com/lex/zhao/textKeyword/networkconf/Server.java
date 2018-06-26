package com.lex.zhao.textKeyword.networkconf;

/**
 * Created by qtfs on 2018/6/20.
 */

//服务器
public class Server {
    private String ip;
    public Server(String ip) {
        this.ip = ip;
    }
    public Port port;

    public String toString() {
        return "server [" + ip + "] servers at [" + port + "] port";
    }
//端口
    public class Port{
        int port;
        String protocol;
        public Port(int port) {
            this.port = port;
        }
        public boolean access = true;
        public String toString() {
            return String.valueOf(port);
        }
    }
}
