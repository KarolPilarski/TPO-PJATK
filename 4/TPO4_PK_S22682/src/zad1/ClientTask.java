/**
 *
 *  @author Pilarski Karol S22682
 *
 */

package zad1;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientTask implements Runnable {
    Client client;
    List<String> reqList;
    boolean showRes;
    LinkedList<String> serverLog;
    String userLog;



    public ClientTask(Client client, List<String> reqList, boolean showRes) {
        this.client = client;
        this.reqList = reqList;
        this.showRes = showRes;

    }

    public static ClientTask create(Client client, List<String> reqList, boolean showRes) {

        return new ClientTask(client,reqList,showRes);
    }

    public String get() throws ExecutionException, InterruptedException {
        Thread.sleep(100);
        return userLog;
    }

    @Override
    public void run() {
        client.connect();
        client.send("login "+client.id);
        for(String s: reqList){
            String result=client.send(s);
            if(showRes) System.out.println(result);
        }
        userLog = client.send("bye and log transfer");
    }
}
