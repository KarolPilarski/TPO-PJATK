/**
 *
 *  @author Pilarski Karol S22682
 *
 */

package zad1;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    String host;
    int port;
    String id;

    SocketChannel serverChannel;

    public Client(String host, int port, String id) {
        this.host=host;
        this.port=port;
        this.id=id;
    }

    public void connect() {
        try {
            serverChannel = SocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.connect(new InetSocketAddress("localhost", port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String send(String query) {
        byte[] message = new String(query).getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(message);
        try {
            while(!serverChannel.finishConnect()){
                Thread.sleep(10);
            }
            while(true) {
                serverChannel.write(buffer);
                ByteBuffer bb = ByteBuffer.allocate(1000);
                int read = serverChannel.read(bb);
                if (read == -1) {
                    serverChannel.close();
                } else if (read > 0) {
                    bb.flip();
                    String queryOut = StandardCharsets.UTF_8.decode(bb).toString();
                    return queryOut;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
