/**
 *
 *  @author Pilarski Karol S22682
 *
 */

package zad1;


import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class Tools {
    static Options createOptionsFromYaml(String fileName) {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;

        FileInputStream in = null;
        try {
            inputStream = new FileInputStream(new File(fileName));
            Map<String, Object> map = yaml.load(inputStream);
            Map<String, List<String>> clientsMap= (Map<String,List<String>>) (map.get("clientsMap"));
            return new Options(map.get("host").toString(),Integer.parseInt(map.get("port").toString()),Boolean.parseBoolean(map.get("concurMode").toString()),Boolean.parseBoolean(map.get("showSendRes").toString()), (Map<String, List<String>>) clientsMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
