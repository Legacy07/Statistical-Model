package statistical.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class ZTable extends JFrame implements ActionListener {

    //TextField
    JTextField startTexF = new JTextField(4);
    JTextField endTextF = new JTextField(4);
    JLabel areaLabel = new JLabel("Not yet calculated the area!");
    JLabel startLabel = new JLabel("Start point:");
    JLabel endLabel = new JLabel("End point:");
    //Button
    JButton produceButton = new JButton("Produce");
    //combo box
    JComboBox caseComboBox = new JComboBox();
    //Table
    DefaultTableModel tableM = new DefaultTableModel();
    JTable table = new JTable(tableM);
    JScrollPane scrollPane;
    DocumentFilter filter = new OnlyNumbersFilter();

    Main_GUI main;

    public ZTable() {
        //setting the frame 
        setBounds(300, 200, 950, 500);
        setTitle("Statistical Model - Z Table");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        //adding panels 
        JPanel bottom = new JPanel();
        JPanel middle = new JPanel();
        JPanel west = new JPanel();
        JPanel east = new JPanel();
        JPanel top = new JPanel();
        //Setting the size of the table with scroll pane 
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 400));
        middle.add(scrollPane);
        //table settings
        table.setGridColor(Color.blue);
        scrollPane.getViewport().setBackground(Color.gray);
        table.setForeground(Color.white);
        table.setBackground(Color.gray);
        table.setFont(new Font("Cambria", Font.BOLD, 15));
        //adding area label
        bottom.add(areaLabel);
        areaLabel.setFont(new Font("Cambria", Font.BOLD, 15));
        areaLabel.setForeground(Color.white);
        //adding combo box 
        bottom.add(caseComboBox);
        caseComboBox.addItem("Upper Cumulative");
        caseComboBox.addItem("Lower Cumulative");
        caseComboBox.addItem("Inner Cumulative");
        caseComboBox.addItem("Outer Cumulative");
        caseComboBox.setToolTipText("Choose one of the cases to Produce Z Table");
        //adding the textFields and labels
        bottom.add(startLabel);
        startLabel.setForeground(Color.white);
        bottom.add(startTexF);
        bottom.add(endLabel);
        endLabel.setForeground(Color.white);
        bottom.add(endTextF);
        startTexF.setText("0");
        ((AbstractDocument) startTexF.getDocument()).setDocumentFilter(filter);
        endTextF.setText("1");
        ((AbstractDocument) endTextF.getDocument()).setDocumentFilter(filter);
        //adding button 
        bottom.add(produceButton);
        produceButton.addActionListener(this);
        //adding columns to the table  
        tableM.addColumn("Z");
        tableM.addColumn("0.00");
        tableM.addColumn("0.01");
        tableM.addColumn("0.02");
        tableM.addColumn("0.03");
        tableM.addColumn("0.04");
        tableM.addColumn("0.05");
        tableM.addColumn("0.06");
        tableM.addColumn("0.07");
        tableM.addColumn("0.08");
        tableM.addColumn("0.09");
        //Cells are not editable
        table.setEnabled(false);
        //columns are not moveable nor resizable 
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        //adding panels 
        add("Center", middle);
        add("South", bottom);
        add("North", top);
        add("East", east);
        add("West", west);
        //background colour of the panels 
        bottom.setBackground(Color.gray);
        top.setBackground(Color.gray);
        east.setBackground(Color.gray);
        west.setBackground(Color.gray);

    }

    //class for only numbers, negative and a dot
    class OnlyNumbersFilter extends DocumentFilter {

        final int maxCharacters = 7;

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

    @Override
    public void actionPerformed(ActionEvent e) {

        if (startTexF.getText().equals("") || endTextF.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Fill out both of the Fields!");
        }

        //parsing String to double to get the values within textfields
        double start = Double.parseDouble(startTexF.getText());
        double end = Double.parseDouble(endTextF.getText());
        double mean = Double.parseDouble(Main_GUI.getMeanText());
        double standardD = Double.parseDouble(Main_GUI.getStandardDText());
        //addRowValue for Z column to represent the x value 
        double addRowValue = 0.0;
        //Number format for decimal representation
        NumberFormat decimalFormat = new DecimalFormat("#0.000000");

        //declaring these to store values for each row which will be passed into .addrow property
        String zeroColumn = "";
        String firstColumn = "";
        String secondColumn = "";
        String thirdColumn = "";
        String fourthColumn = "";
        String fifthColumn = "";
        String sixthColumn = "";
        String seventhColumn = "";
        String eightColumn = "";
        String ninthColumn = "";

        if (e.getSource() == produceButton) {
            tableM.setRowCount(0);
            //loop for the Z Column to start from the start variable which the user inputs and the rows are added by 0.1 . 
            for (double i = start; i <= end; i += 0.1) {
                //will be added in z column rows that will only add by 0.1
                String startPoint = Double.toString(((double) Math.round((start + addRowValue) * 10) / 10));

                if (caseComboBox.getSelectedItem().equals("Upper Cumulative")) {
                    //calculations for upper cumulative case using simpsons rule
                    zeroColumn = decimalFormat.format(Calculations.aboveSimpsons((start + addRowValue + 0), 1000, mean, standardD));
                    firstColumn = decimalFormat.format(Calculations.aboveSimpsons((start + addRowValue + 0.1), 1000, mean, standardD));
                    secondColumn = decimalFormat.format(Calculations.aboveSimpsons((start + addRowValue + 0.2), 1000, mean, standardD));
                    thirdColumn = decimalFormat.format(Calculations.aboveSimpsons((start + addRowValue + 0.3), 1000, mean, standardD));
                    fourthColumn = decimalFormat.format(Calculations.aboveSimpsons((start + addRowValue + 0.4), 1000, mean, standardD));
                    fifthColumn = decimalFormat.format(Calculations.aboveSimpsons((start + addRowValue + 0.5), 1000, mean, standardD));
                    sixthColumn = decimalFormat.format(Calculations.aboveSimpsons((start + addRowValue + 0.6), 1000, mean, standardD));
                    seventhColumn = decimalFormat.format(Calculations.aboveSimpsons((start + addRowValue + 0.7), 1000, mean, standardD));
                    eightColumn = decimalFormat.format(Calculations.aboveSimpsons((start + addRowValue + 0.8), 1000, mean, standardD));
                    ninthColumn = decimalFormat.format(Calculations.aboveSimpsons((start + addRowValue + 0.9), 1000, mean, standardD));
                    //adding all the calculations in correct rows under columns in String  
                    tableM.addRow(new String[]{startPoint, zeroColumn, firstColumn, secondColumn, thirdColumn, fourthColumn, fifthColumn, sixthColumn, seventhColumn, eightColumn, ninthColumn});
                } else if (caseComboBox.getSelectedItem().equals("Lower Cumulative")) {
                    //calculations for lower cumulative case using simpsons rule
                    zeroColumn = decimalFormat.format(Calculations.belowSimpsons((start + addRowValue + 0), 1000, mean, standardD));
                    firstColumn = decimalFormat.format(Calculations.belowSimpsons((start + addRowValue + 0.1), 1000, mean, standardD));
                    secondColumn = decimalFormat.format(Calculations.belowSimpsons((start + addRowValue + 0.2), 1000, mean, standardD));
                    thirdColumn = decimalFormat.format(Calculations.belowSimpsons((start + addRowValue + 0.3), 1000, mean, standardD));
                    fourthColumn = decimalFormat.format(Calculations.belowSimpsons((start + addRowValue + 0.4), 1000, mean, standardD));
                    fifthColumn = decimalFormat.format(Calculations.belowSimpsons((start + addRowValue + 0.5), 1000, mean, standardD));
                    sixthColumn = decimalFormat.format(Calculations.belowSimpsons((start + addRowValue + 0.6), 1000, mean, standardD));
                    seventhColumn = decimalFormat.format(Calculations.belowSimpsons((start + addRowValue + 0.7), 1000, mean, standardD));
                    eightColumn = decimalFormat.format(Calculations.belowSimpsons((start + addRowValue + 0.8), 1000, mean, standardD));
                    ninthColumn = decimalFormat.format(Calculations.belowSimpsons((start + addRowValue + 0.9), 1000, mean, standardD));

                    tableM.addRow(new String[]{startPoint, zeroColumn, firstColumn, secondColumn, thirdColumn, fourthColumn, fifthColumn, sixthColumn, seventhColumn, eightColumn, ninthColumn});
                } else if (caseComboBox.getSelectedItem().equals("Inner Cumulative")) {
                    //calculations for inner cumulative case using simpsons rule
                    zeroColumn = decimalFormat.format(Calculations.betweenSimpsons((start + addRowValue + 0), (end + addRowValue + 0), 1000, mean, standardD));
                    firstColumn = decimalFormat.format(Calculations.betweenSimpsons((start + addRowValue + 0.1), (end + addRowValue + 0.1), 1000, mean, standardD));
                    secondColumn = decimalFormat.format(Calculations.betweenSimpsons((start + addRowValue + 0.2), (end + addRowValue + 2), 1000, mean, standardD));
                    thirdColumn = decimalFormat.format(Calculations.betweenSimpsons((start + addRowValue + 0.3), (end + addRowValue + 0.3), 1000, mean, standardD));
                    fourthColumn = decimalFormat.format(Calculations.betweenSimpsons((start + addRowValue + 0.4), (end + addRowValue + 0.4), 1000, mean, standardD));
                    fifthColumn = decimalFormat.format(Calculations.betweenSimpsons((start + addRowValue + 0.5), (end + addRowValue + 0.5), 1000, mean, standardD));
                    sixthColumn = decimalFormat.format(Calculations.betweenSimpsons((start + addRowValue + 0.6), (end + addRowValue + 0.6), 1000, mean, standardD));
                    seventhColumn = decimalFormat.format(Calculations.betweenSimpsons((start + addRowValue + 0.7), (end + addRowValue + 0.7), 1000, mean, standardD));
                    eightColumn = decimalFormat.format(Calculations.betweenSimpsons((start + addRowValue + 0.8), (end + addRowValue + 0.8), 1000, mean, standardD));
                    ninthColumn = decimalFormat.format(Calculations.betweenSimpsons((start + addRowValue + 0.9), (end + addRowValue + 0.9), 1000, mean, standardD));

                    tableM.addRow(new String[]{startPoint, zeroColumn, firstColumn, secondColumn, thirdColumn, fourthColumn, fifthColumn, sixthColumn, seventhColumn, eightColumn, ninthColumn});
                } else if (caseComboBox.getSelectedItem().equals("Outer Cumulative")) {
                    //calculations for outer cumulative case using simpsons rule
                    zeroColumn = decimalFormat.format(Calculations.outsideSimpsons((end + addRowValue + 0), (start + addRowValue + 0), 1000, mean, standardD));
                    firstColumn = decimalFormat.format(Calculations.outsideSimpsons((end + addRowValue + 0.1), (start + addRowValue + 0.1), 1000, mean, standardD));
                    secondColumn = decimalFormat.format(Calculations.outsideSimpsons((end + addRowValue + 0.2), (start + addRowValue + 0.2), 1000, mean, standardD));
                    thirdColumn = decimalFormat.format(Calculations.outsideSimpsons((end + addRowValue + 0.3), (start + addRowValue + 0.3), 1000, mean, standardD));
                    fourthColumn = decimalFormat.format(Calculations.outsideSimpsons((end + addRowValue + 0.4), (start + addRowValue + 0.4), 1000, mean, standardD));
                    fifthColumn = decimalFormat.format(Calculations.outsideSimpsons((end + addRowValue + 0.5), (start + addRowValue + 0.5), 1000, mean, standardD));
                    sixthColumn = decimalFormat.format(Calculations.outsideSimpsons((end + addRowValue + 0.6), (start + addRowValue + 0.6), 1000, mean, standardD));
                    seventhColumn = decimalFormat.format(Calculations.outsideSimpsons((end + addRowValue + 0.7), (start + addRowValue + 0.7), 1000, mean, standardD));
                    eightColumn = decimalFormat.format(Calculations.outsideSimpsons((end + addRowValue + 0.8), (start + addRowValue + 0.8), 1000, mean, standardD));
                    ninthColumn = decimalFormat.format(Calculations.outsideSimpsons((end + addRowValue + 0.9), (start + addRowValue + 0.9), 1000, mean, standardD));

                    tableM.addRow(new String[]{startPoint, zeroColumn, firstColumn, secondColumn, thirdColumn, fourthColumn, fifthColumn, sixthColumn, seventhColumn, eightColumn, ninthColumn});
                }
                //adding 0.1 every time the loop completes, to get the values for every other column.
                addRowValue += 0.1;

            }

        }

    }

}
