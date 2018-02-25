package statistical.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import static statistical.model.Graph.DataSet;
import static statistical.model.Graph.Graph;

public class Main_GUI extends JFrame implements ActionListener {

    //strips value for calculation
    int stripsValue = 1000;
    //Declaration of buttons 
    JRadioButton aboveRadio = new JRadioButton("Upper Cumulative");
    public static JRadioButton belowRadio = new JRadioButton("Lower Cumulative");
    JRadioButton betweenRadio = new JRadioButton("Inner Cumulative");
    JRadioButton outsideRadio = new JRadioButton("Outer Cumulative");
    ButtonGroup radioGroup = new ButtonGroup();
    JButton calculateB = new JButton("Calculate");
    JButton zTableButton = new JButton("Z Table");
    JButton clearButton = new JButton("Clear");
    //Combo Box
    JComboBox ruleComboBox = new JComboBox();
    //Textfields
    public static JTextField meanField;
    public static JTextField standardDField;
    public static JTextField aboveField = new JTextField(3);
    public static JTextField belowField = new JTextField(3);
    public static JTextField betweenXField = new JTextField(1);
    public static JTextField betweenYField = new JTextField(1);
    public static JTextField outsideXField = new JTextField(2);
    public static JTextField outsideYField = new JTextField(2);
    //Labels
    JLabel meanLabel = new JLabel("Mean: ");
    JLabel standardDLabel = new JLabel("Standard Deviation: ");
    JLabel useLabel = new JLabel("Use one of the Rules; ");
    JLabel area = new JLabel("Area: ");
    // only numbers allowed document filter class
    DocumentFilter filter = new OnlyNumbersFilter();
    //Calling z table class
    ZTable ztable;
    //font 
    Font font = new Font("Cambria", Font.BOLD, 15);
    //xyseries for the shading
    public static XYSeries belowG = new XYSeries("belowG");
    public static XYSeries aboveG = new XYSeries("aboveG");
    public static XYSeries betweenG = new XYSeries("betweenG");
    public static XYSeries outsideG = new XYSeries("outsideG");

    public Main_GUI() {
        //Gui settings
        setBounds(300, 200, 750, 700);
        setTitle("Statistical Model");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        //Placements within the frame

        add(aboveRadio);
        aboveRadio.setBounds(80, 400, 150, 20);
        aboveRadio.setBackground(Color.gray);
        aboveRadio.setForeground(Color.white);
        aboveRadio.setFont(font);
        add(aboveField);
        aboveField.setBounds(235, 400, 80, 25);
        aboveField.setText("1");
        ((AbstractDocument) aboveField.getDocument()).setDocumentFilter(filter);
        //mean label and mean text field 
        getContentPane().add(meanLabel);
        meanLabel.setBounds(100, 350, 100, 20);
        meanLabel.setFont(font);
        meanLabel.setForeground(Color.white);
        meanField = new JTextField();
        meanField.setText("0");
        getContentPane().add(meanField);
        meanField.setBounds(235, 350, 80, 25);
        ((AbstractDocument) meanField.getDocument()).setDocumentFilter(filter);
        //standard deviation label and text field
        standardDField = new JTextField();
        add(standardDLabel);
        standardDLabel.setBounds(405, 350, 150, 20);
        standardDLabel.setFont(font);
        standardDLabel.setForeground(Color.white);
        getContentPane().add(standardDField);
        standardDField.setBounds(565, 350, 80, 25);
        standardDField.setText("1");
        ((AbstractDocument) standardDField.getDocument()).setDocumentFilter(filter);
        // below image, below radio button and below textfield
        add(belowRadio);
        belowRadio.setBounds(400, 400, 160, 20);
        belowRadio.setBackground(Color.gray);
        belowRadio.setForeground(Color.white);
        belowRadio.setFont(font);
        add(belowField);
        belowField.setBounds(565, 400, 80, 25);
        belowField.setText("1");
        ((AbstractDocument) belowField.getDocument()).setDocumentFilter(filter);
        //between image, between radio button and between 2 text fields , between x for the upper and y for the lower value
        add(betweenRadio);
        betweenRadio.setBounds(80, 450, 150, 20);
        betweenRadio.setBackground(Color.gray);
        betweenRadio.setForeground(Color.white);
        betweenRadio.setFont(font);
        add(betweenXField);
        betweenXField.setBounds(235, 450, 80, 25);
        betweenXField.setText("-2");
        ((AbstractDocument) betweenXField.getDocument()).setDocumentFilter(filter);
        add(betweenYField);
        betweenYField.setBounds(235, 480, 80, 25);
        betweenYField.setText("2");
        ((AbstractDocument) betweenYField.getDocument()).setDocumentFilter(filter);
        //outside image, radio button and 2 text fields,  x for the upper and y for the lower value
        add(outsideRadio);
        outsideRadio.setBounds(400, 450, 150, 20);
        outsideRadio.setBackground(Color.gray);
        outsideRadio.setForeground(Color.white);
        outsideRadio.setFont(font);
        add(outsideXField);
        outsideXField.setBounds(565, 450, 80, 25);
        outsideXField.setText("-2");
        ((AbstractDocument) outsideXField.getDocument()).setDocumentFilter(filter);
        add(outsideYField);
        outsideYField.setBounds(565, 480, 80, 25);
        outsideYField.setText("2");
        ((AbstractDocument) outsideYField.getDocument()).setDocumentFilter(filter);
        //radio buttons group adding and giving them event handlers
        radioGroup.add(aboveRadio);
        radioGroup.add(belowRadio);
        radioGroup.add(betweenRadio);
        radioGroup.add(outsideRadio);
        aboveRadio.addActionListener(this);
        belowRadio.addActionListener(this);
        betweenRadio.addActionListener(this);
        outsideRadio.addActionListener(this);
        //adding calculate button 
        add(calculateB);
        calculateB.setBounds(515, 530, 130, 30);
        calculateB.addActionListener(this);
        //adding z tabel button
        add(zTableButton);
        zTableButton.setBounds(515, 570, 130, 30);
        zTableButton.addActionListener(this);
        //adding clear button 
        add(clearButton);
        clearButton.setBounds(370, 570, 130, 30);
        clearButton.addActionListener(this);
        //Adding Combo box and items
        add(ruleComboBox);
        ruleComboBox.setBounds(350, 530, 130, 20);
        ruleComboBox.addItem("Rectangular Rule");
        ruleComboBox.addItem("Trapezodial Rule");
        ruleComboBox.addItem("Simpsons Rule");
        //area label 
        add(area);
        area.setBounds(100, 530, 300, 100);
        area.setToolTipText("Area of the Graph");
        area.setFont(font);
        area.setForeground(Color.white);
        getContentPane().setBackground(Color.GRAY);

        getContentPane().add(graphPanel());

    }

    //Graph class panel 
    public static JPanel graphPanel() {
        //panel settings
        JFreeChart chart = Graph(DataSet());
        ChartPanel panel = new ChartPanel(chart);
        panel.setBounds(70, 30, 600, 300);
        //zoom in and out with a mouse drag
        panel.setMouseWheelEnabled(true);
        panel.revalidate();
        panel.repaint();
        return panel;
    }

    // method to get the text values of the text field 
    public static String getMeanText() {
        return meanField.getText();
    }

    public static String getAboveText() {
        return aboveField.getText();
    }

    public static String getStandardDText() {
        return standardDField.getText();
    }

    public static String getBelowText() {
        return belowField.getText();
    }

    public static String getOutsideXText() {
        return outsideXField.getText();
    }

    public static String getOutsideYText() {
        return outsideYField.getText();
    }

    public static String getBetweenXText() {
        return betweenXField.getText();
    }

    public static String getBetweenYText() {
        return betweenYField.getText();
    }

    //class for only numbers, negative and a dot
    class OnlyNumbersFilter extends DocumentFilter {

        final int maxCharacters = 10;

        @Override
        public void insertString(DocumentFilter.FilterBypass filterBypass, int offset, String string,
                AttributeSet attributeSet) throws BadLocationException {

            String text = filterBypass.getDocument().getText(0,
                    filterBypass.getDocument().getLength());
            text += string;
            //if input is smaller than 10 numbers and matches the ones mentioned, insert them
            if ((filterBypass.getDocument().getLength() + string.length()) <= maxCharacters
                    && text.matches("([+-]{0,1})?[\\d]*[.]{0,1}?[\\d]*")) {
                super.insertString(filterBypass, offset, string, attributeSet);
            } else {
            }
        }

        @Override
        public void replace(DocumentFilter.FilterBypass filterBypass, int offset, int length, String string,
                AttributeSet attributeSet) throws BadLocationException {

            String text = filterBypass.getDocument().getText(0,
                    filterBypass.getDocument().getLength());
            text += string;
            //if input doesnt match then replace with nothing 
            if ((filterBypass.getDocument().getLength() + string.length() - length) <= maxCharacters
                    && text.matches("([+-]{0,1})?[\\d]*[.]{0,1}?[\\d]*")) {
                super.replace(filterBypass, offset, length, string, attributeSet);
            } else {
                JOptionPane.showMessageDialog(null, "Only numbers ! ");
            }

        }

    }

    public static void main(String[] args) {
        new Main_GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double belowShade = Double.parseDouble(getMeanText()) - 4.1 * Double.parseDouble(getStandardDText());
        double aboveShade = Double.parseDouble(getMeanText()) + 4.1 * Double.parseDouble(getStandardDText());

        //outputs a message when one of the textfield is empty
        if (aboveRadio.isSelected()
                || belowRadio.isSelected() || betweenRadio.isSelected() || outsideRadio.isSelected() || e.getSource() == calculateB) {
            if (meanField.getText().toString().equals("") || standardDField.getText().toString().equals("") || aboveField.getText().toString().equals("") || belowField.getText().toString().equals("")
                    || betweenXField.getText().toString().equals("") || betweenYField.getText().toString().equals("") || outsideXField.getText().toString().equals("") || outsideYField.getText().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill out the Fields!");

            }
        }
        //parsing String to double for the textfields so we can calculate the inputs
        double above = Double.parseDouble(aboveField.getText());
        double below = Double.parseDouble(belowField.getText());
        double betweenX = Double.parseDouble(betweenXField.getText());
        double betweenY = Double.parseDouble(betweenYField.getText());
        double outsideX = Double.parseDouble(outsideXField.getText());
        double outsideY = Double.parseDouble(outsideYField.getText());
        double mean = Double.parseDouble(meanField.getText());
        double standardD = Double.parseDouble(standardDField.getText());

        //to open z table gui 
        if (e.getSource()
                == zTableButton) {
            ZTable zTable = new ZTable();
            //if one of the cases being chosen, it inserts in a label in ztable gui with what the area is. 
            if (aboveRadio.isSelected()) {
                zTable.areaLabel.setText("Calculated " + aboveRadio.getText() + " " + area.getText());
            } else if (belowRadio.isSelected()) {
                zTable.areaLabel.setText("Calculated " + belowRadio.getText() + " " + area.getText());
            } else if (betweenRadio.isSelected()) {
                zTable.areaLabel.setText("Calculated " + betweenRadio.getText() + " " + area.getText());
            } else if (outsideRadio.isSelected()) {
                zTable.areaLabel.setText("Calculated " + outsideRadio.getText() + " " + area.getText());
            }

        }

        //to reset everything back to normal 
        if (e.getSource()
                == clearButton) {
            radioGroup.clearSelection();
            area.setText("Area :");
            meanField.setText("0");
            standardDField.setText("1");
            aboveField.setText("1");
            belowField.setText("1");
            betweenXField.setText("0");
            betweenYField.setText("2");
            outsideXField.setText("0");
            outsideYField.setText("2");
        }

        //////////////////////////////////////////////////////Rectangular Rule////////////////////////////////////////////////////////
        if (aboveRadio.isSelected()
                && ruleComboBox.getSelectedItem().equals("Rectangular Rule") && e.getSource() == calculateB) {
            //shading upper cumulative 
            aboveG.add(Double.parseDouble(getAboveText()), 0);
            aboveG.add(aboveShade, 0);
            area.setText("Area: " + Calculations.aboveRectangular(above, stripsValue, mean, standardD));
            graphPanel().revalidate();
            graphPanel().repaint();
        } else if (belowRadio.isSelected()
                && ruleComboBox.getSelectedItem().equals("Rectangular Rule") && e.getSource() == calculateB) {
            //shading lower cumulative
            belowG.add(Double.parseDouble(getBelowText()), 0);
            belowG.add(belowShade, 0);
            area.setText("Area: " + Calculations.belowRectangular(above, stripsValue, mean, standardD));
            graphPanel().revalidate();
            graphPanel().repaint();
        } else if (betweenRadio.isSelected()
                && ruleComboBox.getSelectedItem().equals("Rectangular Rule") && e.getSource() == calculateB) {
            //shading inner cumulative
            betweenG.add(Double.parseDouble(getBetweenXText()), 0);
            betweenG.add(Double.parseDouble(getBetweenYText()), 0);
            area.setText("Area: " + Calculations.betweenRectangular(betweenX, betweenY, stripsValue, mean, standardD));

        } else if (outsideRadio.isSelected()
                && ruleComboBox.getSelectedItem().equals("Rectangular Rule") && e.getSource() == calculateB) {
            //shading outer cumulative
            outsideG.add(belowShade, 0);
            outsideG.add(Double.parseDouble(getOutsideXText()), 0);
            outsideG.add(Double.parseDouble(getOutsideXText()), Calculations.aboveSimpsons(0, stripsValue, mean, standardD));
            outsideG.add(Double.parseDouble(getOutsideYText()), Calculations.aboveSimpsons(0, stripsValue, mean, standardD));
            outsideG.add(Double.parseDouble(getOutsideYText()), 0);
            outsideG.add(aboveShade, 0);
            area.setText("Area: " + Calculations.outsideRectangular(outsideY, outsideX, stripsValue, mean, standardD));

        } ////////////////////////////////////////////////////Trap rule////////////////////////////////////////////////////////////// 
        else if (aboveRadio.isSelected()
                && ruleComboBox.getSelectedItem().equals("Trapezodial Rule") && e.getSource() == calculateB) {

            aboveG.add(Double.parseDouble(getBelowText()), 0);
            aboveG.add(aboveShade, 0);
            area.setText("Area: " + Calculations.aboveTrap(above, stripsValue, mean, standardD));

        } else if (belowRadio.isSelected()
                && ruleComboBox.getSelectedItem().equals("Trapezodial Rule") && e.getSource() == calculateB) {
            belowG.add(Double.parseDouble(getBelowText()), 0);
            belowG.add(belowShade, 0);
            area.setText("Area: " + Calculations.belowTrap(below, stripsValue, mean, standardD));

        } else if (betweenRadio.isSelected()
                && ruleComboBox.getSelectedItem().equals("Trapezodial Rule") && e.getSource() == calculateB) {
            betweenG.add(Double.parseDouble(getBetweenXText()), 0);
            betweenG.add(Double.parseDouble(getBetweenYText()), 0);
            area.setText("Area: " + Calculations.betweenTrap(betweenX, betweenY, stripsValue, mean, standardD));

        } else if (outsideRadio.isSelected()
                && ruleComboBox.getSelectedItem().equals("Trapezodial Rule") && e.getSource() == calculateB) {
            outsideG.add(belowShade, 0);
            outsideG.add(Double.parseDouble(getOutsideXText()), 0);
            outsideG.add(Double.parseDouble(getOutsideXText()), Calculations.aboveSimpsons(0, stripsValue, mean, standardD));
            outsideG.add(Double.parseDouble(getOutsideYText()), Calculations.aboveSimpsons(0, stripsValue, mean, standardD));
            outsideG.add(Double.parseDouble(getOutsideYText()), 0);
            outsideG.add(aboveShade, 0);
            area.setText("Area: " + Calculations.outsideTrap(outsideY, outsideX, stripsValue, mean, standardD));

        } //////////////////////////////////////////////////////Simpsons Rule////////////////////////////////////////////////////////////// 
        else if (aboveRadio.isSelected()
                && ruleComboBox.getSelectedItem().equals("Simpsons Rule") && e.getSource() == calculateB) {

            aboveG.add(Double.parseDouble(getBelowText()), 0);
            aboveG.add(aboveShade, 0);
            area.setText("Area: " + Calculations.aboveSimpsons(above, stripsValue, mean, standardD));

        } else if (belowRadio.isSelected()
                && ruleComboBox.getSelectedItem().equals("Simpsons Rule") && e.getSource() == calculateB) {
            belowG.add(Double.parseDouble(getBelowText()), 0);
            belowG.add(belowShade, 0);
            area.setText("Area: " + Calculations.belowSimpsons(above, stripsValue, mean, standardD));

        } else if (betweenRadio.isSelected()
                && ruleComboBox.getSelectedItem().equals("Simpsons Rule") && e.getSource() == calculateB) {
            betweenG.add(Double.parseDouble(getBetweenXText()), 0);
            betweenG.add(Double.parseDouble(getBetweenYText()), 0);
            area.setText("Area: " + Calculations.betweenSimpsons(betweenX, betweenY, stripsValue, mean, standardD));

        } else if (outsideRadio.isSelected()
                && ruleComboBox.getSelectedItem().equals("Simpsons Rule") && e.getSource() == calculateB) {
            outsideG.add(belowShade, 0);
            outsideG.add(Double.parseDouble(getOutsideXText()), 0);
            outsideG.add(Double.parseDouble(getOutsideXText()), Calculations.aboveSimpsons(0, stripsValue, mean, standardD));
            outsideG.add(Double.parseDouble(getOutsideYText()), Calculations.aboveSimpsons(0, stripsValue, mean, standardD));
            outsideG.add(Double.parseDouble(getOutsideYText()), 0);
            outsideG.add(aboveShade, 0);
            area.setText("Area: " + Calculations.outsideSimpsons(outsideY, outsideX, stripsValue, mean, standardD));

        } //if none of the radio buttons have chosen then output a message
        else if (!aboveRadio.isSelected()
                && !belowRadio.isSelected() && !betweenRadio.isSelected() && !outsideRadio.isSelected() && e.getSource() == calculateB) {
            JOptionPane.showMessageDialog(null, "Please choose one of the Cases to Calculate!");
        }

    }
}
