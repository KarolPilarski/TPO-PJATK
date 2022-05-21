package zad1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;

public class WeatherPanel extends JPanel {
    Service s;
    String cityInfo;
    JLabel topLabel;
    JLabel temperatureLabel;
    JLabel weatherLabel;
    JLabel weatherDescriptionLabel;
    JLabel pressureLabel;
    JLabel humidityLabel;


    public WeatherPanel(Service s,String cityInfo) {
        this.s = s;
        this.cityInfo = cityInfo;

        String weatherAPI=s.getWeather(cityInfo);
        JSONObject weatherObj=null;
        try {
            JSONParser jparserer= new JSONParser();
            weatherObj= (JSONObject) (jparserer.parse(weatherAPI));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        topLabel = new JLabel("<html>Current weather in <p style=\"color:white\">"+cityInfo+","+s.countryName+"</p><br></html>");
        topLabel.setFont(new Font("Arial", Font.PLAIN,28));
        topLabel.setPreferredSize(new Dimension(180,300));
        add(topLabel);

        temperatureLabel=new JLabel("<html><p style=\"color:#66a0ff\">"+(Math.round(((Double)(((JSONObject)(weatherObj.get("main"))).get("temp")))-272.2))+"°C</p><br></html>");
        temperatureLabel.setFont(new Font("Arial", Font.PLAIN,56));
        add(temperatureLabel);

        weatherLabel=new JLabel("<html><p style=\"color:#3d86fc\">"+(String)((JSONObject)((JSONArray)(weatherObj.get("weather"))).get(0)).get("main")+"</p></html>");
        weatherLabel.setFont(new Font("Arial", Font.PLAIN,38));
        add(weatherLabel);

        weatherDescriptionLabel = new JLabel("<html><p style=\"color:#3a6aba\">"+(String)((JSONObject)((JSONArray)(weatherObj.get("weather"))).get(0)).get("description")+"</p><br><br></html>");
        weatherDescriptionLabel.setFont(new Font("Arial", Font.PLAIN,20));
        add(weatherDescriptionLabel);

        pressureLabel=new JLabel("<html>Pressure: <p style=\"color:red\">"+(((JSONObject)(weatherObj.get("main"))).get("pressure"))+"</p>hPa<br><br><br></html>");
        pressureLabel.setFont(new Font("Arial", Font.PLAIN,17));
        add(pressureLabel);

        humidityLabel=new JLabel("<html>Humidity:<p style=\"color:red\">"+(((JSONObject)(weatherObj.get("main"))).get("humidity"))+"</p>%</html>");
        humidityLabel.setFont(new Font("Arial", Font.PLAIN,17));
        add(humidityLabel);

        setPreferredSize(new Dimension(200,750));
        setBackground(Color.GRAY);

    }
}
