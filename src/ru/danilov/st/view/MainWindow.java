package ru.danilov.st.view;

import ru.danilov.st.data.LoaderPairs;
import ru.danilov.st.data.Serializer;
import ru.danilov.st.trading.Asset;
import ru.danilov.st.trading.Pair;
import ru.danilov.st.utils.InternetConnection;
import ru.danilov.st.view.graphs.Plot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MainWindow extends JFrame {
    private JPanel rootPanel;
    private JPanel panel;
    private JTextField txDirectory;
    private JTabbedPane tabGraphs;
    private JPanel pnGrAssets;

    private JLabel status;

    private JButton btSerializer;
    private JButton makePairs;
    private JButton btGoodPairs;

    private JComboBox boxPairs;
    private JComboBox boxGoodPairs;
    private JTextField tfDay;
    private JTextField tfYear;
    private JTextField tfMonth;
    private JButton btTrading;

    private JLabel lForCorrel;
    private JLabel lCorrel;
    private JLabel lForADF;
    private JLabel lADF;
    private JLabel lForPValue;
    private JLabel lPValue;

    private Serializer serializer;
    private LoaderPairs loader;
    private Date dateStart;


    public MainWindow() {
        super("Pairs Finder 1.0");
        setContentPane(rootPanel);
        setSize(720, 480);
        makePairs.setEnabled(false);
        btGoodPairs.setEnabled(false);
        boxPairs.setEnabled(false);
        boxGoodPairs.setEnabled(false);
        btTrading.setEnabled(false);

        lCorrel.setVisible(false);
        lForCorrel.setVisible(false);
        lForADF.setVisible(false);
        lADF.setVisible(false);
        lForPValue.setVisible(false);
        lPValue.setVisible(false);

        dateStart = new Date();


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        initComponent();

    }

    void initComponent() {
        btSerializer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (InternetConnection.isConnection()) {
                    dateStart.setYear(Integer.parseInt(tfYear.getText()) - 1900);
                    dateStart.setMonth(Integer.parseInt(tfMonth.getText()) - 1);
                    dateStart.setDate(Integer.parseInt(tfDay.getText()));
                    serializer = new Serializer(txDirectory.getText(), dateStart);
                    if (serializer.getQuotes().size() == 0) {
                        status.setText("Неверный путь или файл пуст");
                    } else {
                        status.setText("Данные получены");
                    }
                    makePairs.setEnabled(true);
                } else {
                    status.setText("Нет соединения с сетью");
                }
            }
        });

        makePairs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loader = new LoaderPairs(serializer);
                if (!loader.getNamesPair().isEmpty()) {
                    boxPairs.setEnabled(true);
                    boxPairs.setModel(new DefaultComboBoxModel<>(loader.getNamesPair()));
                    status.setText("Пары составлены");
                    btGoodPairs.setEnabled(true);
                } else {
                    status.setText("Не удалось");
                }
            }
        });

        btGoodPairs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!loader.getNamesGoodPair().isEmpty()) {
                    boxGoodPairs.setEnabled(true);
                    boxGoodPairs.setModel(new DefaultComboBoxModel<>(loader.getNamesGoodPair()));
                    status.setText("Пары составлены");
                } else {
                    status.setText("Не удалось");
                    boxGoodPairs.setEnabled(false);
                }
            }
        });

        boxPairs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGraphs(loader.getPairs().get(boxPairs.getSelectedIndex()));

                lCorrel.setVisible(true);
                lForCorrel.setVisible(true);
                lForPValue.setVisible(true);
                lPValue.setVisible(true);
                lForADF.setVisible(true);
                lADF.setVisible(true);

                lCorrel.setText(String.valueOf(loader.getPairs().get(boxPairs.getSelectedIndex()).calcCorrelation()));
                lADF.setText(String.valueOf(loader.getPairs().get(boxPairs.getSelectedIndex()).getResidue().getStatistic()));
                lPValue.setText(String.valueOf(loader.getPairs().get(boxPairs.getSelectedIndex()).getResidue().getPValue()));
            }
        });

        boxGoodPairs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGraphs(loader.getGoodPairs().get(boxGoodPairs.getSelectedIndex()));
                btTrading.setEnabled(true);

                lCorrel.setVisible(true);
                lForCorrel.setVisible(true);
                lForPValue.setVisible(true);
                lPValue.setVisible(true);
                lADF.setVisible(true);
                lForADF.setVisible(true);

                lCorrel.setText(String.valueOf(loader.getGoodPairs().get(boxGoodPairs.getSelectedIndex()).calcCorrelation()));
                lADF.setText(String.valueOf(loader.getGoodPairs().get(boxGoodPairs.getSelectedIndex()).getResidue().getStatistic()));
                lPValue.setText(String.valueOf(loader.getGoodPairs().get(boxGoodPairs.getSelectedIndex()).getResidue().getPValue()));
            }
        });

        btTrading.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WindowTrading(loader.getGoodPairs().get(boxGoodPairs.getSelectedIndex()));
            }
        });
    }

    private void createGraphs(Pair pair) {
        tabGraphs.removeAll();
        tabGraphs.add("Графики активов", Plot.createPanel(" ", pair.getY(), pair.getX()));
        tabGraphs.add("Спред", Plot.createPanel("  ", pair.getResidue()));
    }
}
