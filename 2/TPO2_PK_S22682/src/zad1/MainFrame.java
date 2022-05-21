package zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    Service s;
    JPanel setCountry;
    JLabel countryLabel;
    JLabel cityLabel;
    JLabel currencyLabel;
    JTextField countryTextField;
    JTextField cityTextField;
    JTextField currencyTextField;
    JButton submit;
    String cityInfo;

    public MainFrame(Service s,String cityInfo) {
        this.s = s;
        this.cityInfo = cityInfo;


        getContentPane().setLayout(new BorderLayout());

        setCountry= new JPanel();
        setCountry.setLayout(new FlowLayout());
        setCountry.setPreferredSize(new Dimension(1200,50));
        setCountry.setBackground(Color.darkGray);

        countryTextField = new JTextField();
        countryLabel = new JLabel("Country:");
        countryLabel.setForeground(Color.white);
        setCountry.add(countryLabel);
        countryTextField.setPreferredSize(new Dimension(200,30));
        setCountry.add(countryTextField);

        cityLabel = new JLabel("City:");
        cityLabel.setForeground(Color.white);
        setCountry.add(cityLabel);
        cityTextField = new JTextField();
        cityTextField.setPreferredSize(new Dimension(200,30));
        setCountry.add(cityTextField);

        currencyLabel = new JLabel("Currency to compare:");
        currencyLabel.setForeground(Color.white);
        setCountry.add(currencyLabel);
        currencyTextField = new JTextField();
        currencyTextField.setPreferredSize(new Dimension(200,30));
        setCountry.add(currencyTextField);

        submit = new JButton();
        submit.setPreferredSize(new Dimension(200,30));
        submit.setText("Submit");
        setCountry.add(submit);
        final WikiPanel[] wikiPanel = {new WikiPanel(s)};
        final CurrencyPanel[] currencyPanel = {new CurrencyPanel(s,"USD")};
        final WeatherPanel[] weatherPanel = {new WeatherPanel(s,cityInfo)};

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Service temp=new Service(countryTextField.getText());
                getContentPane().remove(wikiPanel[0]);
                wikiPanel[0] = new WikiPanel(temp);
                add(wikiPanel[0],BorderLayout.EAST);

                getContentPane().remove(weatherPanel[0]);
                weatherPanel[0] = new WeatherPanel(temp,cityTextField.getText());
                add(weatherPanel[0],BorderLayout.WEST);

                getContentPane().remove(currencyPanel[0]);
                currencyPanel[0] = new CurrencyPanel(temp,currencyTextField.getText());
                add(currencyPanel[0],BorderLayout.CENTER);

            }
        });
        add(setCountry,BorderLayout.NORTH);
        add(weatherPanel[0],BorderLayout.WEST);
        add(currencyPanel[0],BorderLayout.CENTER);
        add(wikiPanel[0],BorderLayout.EAST);

        setVisible(true);
        setSize(1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
