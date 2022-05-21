/**
 *
 *  @author Pilarski Karol S22682
 *
 */

package zad1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Server extends Thread{

    String host;
    int port;
    LinkedList<String> serverLog;

    private ServerSocketChannel ssc;
    private Selector selector;
    private final Map<SocketChannel, Queue<ByteBuffer>>  pendingData = new HashMap<>();
    HashMap<SocketChannel,String> response = new HashMap<>();

    private final Map<SocketChannel, String>  name = new HashMap<>();
    private final Map<SocketChannel, StringBuilder>  userLog = new HashMap<>();


    public Server(String host,int port){
        this.host=host;
        this.port=port;
        serverLog=new LinkedList<>();
    }


    @Override
    public void run() {
        try {
            selector = Selector.open();
            ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(host, port));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (!this.isInterrupted()) {
                if (selector.select() > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext(); ) {

                        SelectionKey sk = it.next();
                        it.remove();

                        if (sk.isValid() && sk.isAcceptable()) {
                            SocketChannel sc = ssc.accept();

                            if (sc != null) {
                                sc.configureBlocking(false);
                                sc.register(selector, SelectionKey.OP_READ);

                                pendingData.put(sc, new LinkedList<ByteBuffer>());
                            }
                        } else if (sk.isValid() && sk.isReadable()) {
                            SocketChannel sc = (SocketChannel) sk.channel();
                            ByteBuffer bb = ByteBuffer.allocate(1000);

                            int read = sc.read(bb);
                            if (read == -1) {
                                pendingData.remove(sc);
                                sc.close();
                            } else if (read > 0) {
                                bb.flip();

                                String query = StandardCharsets.UTF_8.decode(bb).toString();


                                String responseString = "";

                                if(query.split(" ")[0].equals("login")){
                                    serverLog.add(query.split(" ")[1]+" logged in at "+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                                    name.put(sc,query.split(" ")[1]);
                                    userLog.put(sc,new StringBuilder());
                                    userLog.get(sc).append("=== "+query.split(" ")[1]+" log start ===\nlogged in\n");
                                    responseString="logged in";
                                }else if(query.equals("bye")){
                                    serverLog.add(name.get(sc)+" logged out at "+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                                    userLog.get(sc).append("logged out");
                                    responseString="logged out";
                                }else if(query.equals("bye and log transfer")){
                                    serverLog.add(name.get(sc)+" logged out at "+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                                    userLog.get(sc).append("logged out\n=== "+name.get(sc)+" log end ===");
                                    responseString=userLog.get(sc).toString();

                                }else if(query.split(" ").length==2){
                                    serverLog.add(name.get(sc)+" request at "+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))+": \""+query+"\"");
                                    userLog.get(sc).append("Request: "+query+"\nResult:\n"+Time.passed(query.split(" ")[0],query.split(" ")[1])+"\n");
                                    responseString=Time.passed(query.split(" ")[0],query.split(" ")[1]);
                                }

                                pendingData.get(sc).add(StandardCharsets.UTF_8.encode(CharBuffer.wrap(responseString)));

                                sk.interestOps(SelectionKey.OP_WRITE);
                            }
                        }else if (sk.isValid() && sk.isWritable()) {
                            SocketChannel sc = (SocketChannel) sk.channel();
                            Queue<ByteBuffer> queue = pendingData.get(sc);

                            while (!queue.isEmpty()) {
                                ByteBuffer bb = queue.peek();
                                int write = sc.write(bb);
                                if (write == -1) {
                                    pendingData.remove(sc);
                                    sc.close();
                                    return;
                                } else if (bb.hasRemaining()) {
                                    return;
                                }
                                queue.remove();
                            }
                            sk.interestOps(SelectionKey.OP_READ);
                        }
                    }
                }
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }


    public void startServer() {
        this.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void stopServer() {
        this.interrupt();
    }

    public String getServerLog() {
        StringBuilder result= new StringBuilder();
        for (int i=0;i<serverLog.size();i++){
            result.append(serverLog.get(i));
            if(i!=serverLog.size()-1) result.append("\n");
        }
        return result.toString();
    }
}
