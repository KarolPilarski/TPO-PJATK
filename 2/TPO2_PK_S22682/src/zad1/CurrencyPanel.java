package zad1;

import javax.swing.*;
import java.awt.*;

public class CurrencyPanel extends JPanel {
    Service s;
    JLabel topLabel;
    JLabel compareToCurrencyLabel;
    JLabel compareToPLNLabel;

    public CurrencyPanel(Service s, String compareTo) {
        this.s = s;

        setPreferredSize(new Dimension(200,750));
        setBackground(new Color(220,220,220));

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        topLabel = new JLabel("<html><br><br>Currency<br><br><br></html>");
        topLabel.setFont(new Font("Arial", Font.PLAIN,28));
        topLabel.setPreferredSize(new Dimension(180,300));
        add(topLabel);

        compareToCurrencyLabel=new JLabel("<html><p style=\"color:#66a0ff\">"+s.countryCurrency+"</p> to <p style=\"color:#66a0ff\">"+compareTo+"</p> rate: "+s.getRateFor(compareTo)+"<br><br><br></html>");
        compareToCurrencyLabel.setFont(new Font("Arial", Font.PLAIN,22));
        add(compareToCurrencyLabel);

        compareToPLNLabel=new JLabel("<html><p style=\"color:#66a0ff\"> PLN </p> to <p style=\"color:#66a0ff\">"+s.countryCurrency+"</p> rate: "+s.getNBPRate()+"<br></html>");
        compareToPLNLabel.setFont(new Font("Arial", Font.PLAIN,22));
        add(compareToPLNLabel);
    }
}
