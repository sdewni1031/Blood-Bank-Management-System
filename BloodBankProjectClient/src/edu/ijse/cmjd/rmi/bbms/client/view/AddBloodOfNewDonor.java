package edu.ijse.cmjd.rmi.bbms.client.view;

import AppPackage.AnimationClass;
import edu.ijse.cmjd.rmi.bbms.client.idgenerator.IdGenerator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import common.ijse.rmi.bloodbank.controller.BloodSampleController;
import edu.ijse.cmjd.rmi.bbms.client.serverconnector.ServerConnector;
import edu.ijse.cmjd.rmi.bbms.client.validation.Validation;
import common.ijse.rmi.bloodbank.controller.DonorController;
import common.ijse.rmi.bloodbank.model.BloodSample;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddBloodOfNewDonor extends JFrame {

    //----------panels
    private JPanel mainPanel;
    private JPanel redPanel;
    private JPanel grayPanel1;
    private JPanel redTitlePanel;
    private JPanel titlePanel1;
    private JPanel titlePanel2;
    private JPanel titlePanel3;
    private JPanel greenPanel;
    //----------labels
    private JLabel bloodBankLabel;
    private JLabel donorIdLabel;
    private JLabel donationDateLabel;
    private JLabel bloodBagLabel;
    private JLabel bloodGroupLabel;
    private JLabel bodyTempLabel;
    private JLabel donationTypeLabel;
    private JLabel pulseRateLabel;
    private JLabel rhLabel;
    private JLabel bloodPressureLabel;
    private JLabel bodyWeightLabel;
    private JLabel volumeLabel;
    private JLabel fLabel;
    private JLabel bpsLabel;
    private JLabel mmHgLabel;
    private JLabel kgLabel;
    private JLabel mlLabel;
    private JLabel donationInfoLabel;
    private JLabel testReportLabel;
    private JLabel testDateLabel;
    private JLabel hbsagLabel;
    private JLabel hivLabel;
    private JLabel hcvLabel;
    private JLabel malariyaLabel;
    private JLabel titleLabel;
    private JLabel reportNoLabel;
    private JLabel notificationLabel;
    //-----------textfields
    private JTextField donorIdText;
    private JTextField donationDateText;
    private JTextField bloodBagText;
    private JTextField bodyTempText;
    private JTextField pulseRateText;
    private JTextField bloodPressureText;
    private JTextField volumeText;
    private JTextField bodyWeightText;
    private JTextField testDateText;
    private JTextField reportNoText;
    private JTextField bloodGroupText;
    private JTextField rhFactorText;
    //----------comboboxes
    private JComboBox bloodCompCombo;
    private JComboBox hbsagCombo;
    private JComboBox hivCombo;
    private JComboBox hcvCombo;
    private JComboBox malariyaCombo;
    //----------buttons
    private JButton addButton;
    Validation validation = new Validation();
    AnimationClass ac = new AnimationClass();
    boolean isValidated = false;

    public AddBloodOfNewDonor(String donorId, String bloodGroup, String rhFactor) {
        setComponents();
        donorIdText.setText(donorId);
        bloodGroupText.setText(bloodGroup);
        rhFactorText.setText(rhFactor);
        setLocationRelativeTo(null);
        setdonateDate();
        setActions();
        addButton.setEnabled(false);
        bloodCompCombo.requestFocus();
        IdGenerator idGenerator = new IdGenerator();
        try {
            bloodBagText.setText(idGenerator.createBloodSampleId());
            reportNoText.setText(bloodBagText.getText());
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (NotBoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void setActions() {
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    BloodSampleController bloodSampleController = ServerConnector.getServerConnector().getBloodSampleController();
                    boolean res = bloodSampleController.addBloodSample(new BloodSample(
                            donorIdText.getText(),
                            bloodBagText.getText(),
                            donationDateText.getText(),
                            bloodGroupText.getText(),
                            bloodCompCombo.getSelectedItem().toString(),
                            rhFactorText.getText(),
                            bodyWeightText.getText(),
                            bodyTempText.getText(),
                            pulseRateText.getText(),
                            bloodPressureText.getText(),
                            volumeText.getText(),
                            testDateText.getText(),
                            reportNoText.getText(),
                            hbsagCombo.getSelectedItem().toString(),
                            hivCombo.getSelectedItem().toString(),
                            hcvCombo.getSelectedItem().toString(),
                            malariyaCombo.getSelectedItem().toString()));

                    if (res) {
                        JOptionPane.showMessageDialog(null, "BloodSample and Donor are added");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed");
                    }

                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (NotBoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (MalformedURLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        });

        bloodCompCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!bodyWeightText.getText().equals("") && !bodyTempText.getText().equals("") && !pulseRateText.getText().equals("") && !bloodPressureText.getText().equals("") && !volumeText.getText().equals("") && !testDateText.getText().equals("")) {
                    addButton.setEnabled(true);
                    addButton.requestFocus();
                } else {
                    bodyWeightText.requestFocus();
                }

            }
        });

        bodyWeightText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isValidated = validation.intnumberValidation(bodyWeightText.getText());
                if (isValidated) {
                    if (!bodyWeightText.getText().equals("") && !bodyTempText.getText().equals("") && !pulseRateText.getText().equals("") && !bloodPressureText.getText().equals("") && !volumeText.getText().equals("") && !testDateText.getText().equals("")) {
                        addButton.setEnabled(true);
                        addButton.requestFocus();
                    } else {
                        bodyTempText.requestFocus();
                    }
                } else {
                    notificationLabel.setText("Invalid integer input! Check it again");
                    ac.jLabelXLeft(910, 510, 5, 2, notificationLabel);
                }
            }
        });

        bodyTempText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isValidated = validation.intnumberValidation(bodyTempText.getText());
                if (isValidated) {
                    if (!bodyWeightText.getText().equals("") && !bodyTempText.getText().equals("") && !pulseRateText.getText().equals("") && !bloodPressureText.getText().equals("") && !volumeText.getText().equals("") && !testDateText.getText().equals("")) {
                        addButton.setEnabled(true);
                        addButton.requestFocus();
                    } else {
                        pulseRateText.requestFocus();
                    }
                } else {
                    notificationLabel.setText("Invalid integer input! Check it again");
                    ac.jLabelXLeft(910, 510, 5, 2, notificationLabel);
                }

            }
        });

        pulseRateText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isValidated = validation.intnumberValidation(pulseRateText.getText());
                if (isValidated) {
                    if (!bodyWeightText.getText().equals("") && !bodyTempText.getText().equals("") && !pulseRateText.getText().equals("") && !bloodPressureText.getText().equals("") && !volumeText.getText().equals("") && !testDateText.getText().equals("")) {
                        addButton.setEnabled(true);
                        addButton.requestFocus();
                    } else {
                        bloodPressureText.requestFocus();
                    }
                } else {
                    notificationLabel.setText("Invalid integer input! Check it again");
                    ac.jLabelXLeft(910, 510, 5, 2, notificationLabel);
                }

            }
        });

        bloodPressureText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isValidated = validation.intnumberValidation(bloodPressureText.getText());
                if (isValidated) {
                    if (!bodyWeightText.getText().equals("") && !bodyTempText.getText().equals("") && !pulseRateText.getText().equals("") && !bloodPressureText.getText().equals("") && !volumeText.getText().equals("") && !testDateText.getText().equals("")) {
                        addButton.setEnabled(true);
                        addButton.requestFocus();
                    } else {
                        volumeText.requestFocus();
                    }
                } else {
                    notificationLabel.setText("Invalid integer input! Check it again");
                    ac.jLabelXLeft(910, 510, 5, 2, notificationLabel);
                }

            }
        });

        volumeText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isValidated = validation.intnumberValidation(volumeText.getText());
                if (isValidated) {
                    if (!bodyWeightText.getText().equals("") && !bodyTempText.getText().equals("") && !pulseRateText.getText().equals("") && !bloodPressureText.getText().equals("") && !volumeText.getText().equals("") && !testDateText.getText().equals("")) {
                        addButton.setEnabled(true);
                        addButton.requestFocus();
                    } else {
                        testDateText.requestFocus();
                    }
                } else {
                    notificationLabel.setText("Invalid integer input! Check it again");
                    ac.jLabelXLeft(910, 510, 5, 2, notificationLabel);
                }

            }
        });

        testDateText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isValidated = validation.dateValidation(testDateText.getText());
                if (isValidated) {
                    if (!bodyWeightText.getText().equals("") && !bodyTempText.getText().equals("") && !pulseRateText.getText().equals("") && !bloodPressureText.getText().equals("") && !volumeText.getText().equals("") && !testDateText.getText().equals("")) {
                        addButton.setEnabled(true);
                        addButton.requestFocus();
                    } else {
                        hbsagCombo.requestFocus();
                    }
                } else {
                    notificationLabel.setText("Invalid date format! Check it again");
                    ac.jLabelXLeft(910, 510, 5, 2, notificationLabel);

                }

            }
        });

        hbsagCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hcvCombo.requestFocus();
            }
        });
        hcvCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hivCombo.requestFocus();
            }
        });
        hivCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                malariyaCombo.requestFocus();
            }
        });
        malariyaCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!bodyWeightText.getText().equals("") && !bodyTempText.getText().equals("") && !pulseRateText.getText().equals("") && !bloodPressureText.getText().equals("") && !volumeText.getText().equals("") && !testDateText.getText().equals("")) {
                    addButton.setEnabled(true);
                    addButton.requestFocus();
                }
            }
        });


        testDateText.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (testDateText.getText().equals("") == true) {
                    addButton.setEnabled(false);
                    ac.jLabelXRight(510, 910, 5, 2, notificationLabel);

                }
            }
        });
        volumeText.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (volumeText.getText().equals("") == true) {
                    addButton.setEnabled(false);
                    ac.jLabelXRight(510, 910, 5, 2, notificationLabel);

                }
            }
        });
        bloodPressureText.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (bloodPressureText.getText().equals("") == true) {
                    addButton.setEnabled(false);
                    ac.jLabelXRight(510, 910, 5, 2, notificationLabel);
                }
            }
        });

        pulseRateText.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (pulseRateText.getText().equals("") == true) {
                    addButton.setEnabled(false);
                    ac.jLabelXRight(510, 910, 5, 2, notificationLabel);
                }
            }
        });
        bodyWeightText.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (bodyWeightText.getText().equals("") == true) {
                    addButton.setEnabled(false);
                    ac.jLabelXRight(510, 910, 5, 2, notificationLabel);
                }
            }
        });
        bodyTempText.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (bodyTempText.getText().equals("") == true) {
                    addButton.setEnabled(false);
                    ac.jLabelXRight(510, 910, 5, 2, notificationLabel);
                }
            }
        });
    }

    private void setComponents() {
        //----------panels
        mainPanel = new JPanel();
        redPanel = new JPanel();
        grayPanel1 = new JPanel();
        redTitlePanel = new JPanel();
        titlePanel1 = new JPanel();
        titlePanel2 = new JPanel();
        titlePanel3 = new JPanel();
        greenPanel = new JPanel();

        //----------labels
        bloodBankLabel = new JLabel();
        donorIdLabel = new JLabel();
        donationDateLabel = new JLabel();
        bloodBagLabel = new JLabel();
        bloodGroupLabel = new JLabel();
        bodyTempLabel = new JLabel();
        donationTypeLabel = new JLabel();
        pulseRateLabel = new JLabel();
        rhLabel = new JLabel();
        bloodPressureLabel = new JLabel();
        bodyWeightLabel = new JLabel();
        volumeLabel = new JLabel();
        fLabel = new JLabel();
        bpsLabel = new JLabel();
        mmHgLabel = new JLabel();
        mlLabel = new JLabel();
        donationInfoLabel = new JLabel();
        testReportLabel = new JLabel();
        testDateLabel = new JLabel();
        reportNoLabel = new JLabel();
        hbsagLabel = new JLabel();
        hivLabel = new JLabel();
        hcvLabel = new JLabel();
        malariyaLabel = new JLabel();
        titleLabel = new JLabel();
        kgLabel = new JLabel();
        notificationLabel = new JLabel();

        //-----------textfields
        donorIdText = new JTextField();
        donationDateText = new JTextField();
        bloodBagText = new JTextField();
        bodyTempText = new JTextField();
        pulseRateText = new JTextField();
        bloodPressureText = new JTextField();
        volumeText = new JTextField();
        bodyWeightText = new JTextField();
        testDateText = new JTextField();
        reportNoText = new JTextField();
        bloodGroupText = new JTextField();
        rhFactorText = new JTextField();

        //----------comboboxes
        bloodCompCombo = new JComboBox();
        hbsagCombo = new JComboBox();
        hivCombo = new JComboBox();
        hcvCombo = new JComboBox();
        malariyaCombo = new JComboBox();

        //----------buttons
        addButton = new JButton();

        setSize(950, 640);
        setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(180, 180, 158), 8));

        //----------set panels
        mainPanel.setBackground(new Color(255, 255, 255));

        grayPanel1.setBackground(new Color(153, 153, 153));
        grayPanel1.setPreferredSize(new Dimension(700, 36));


        redPanel.setBackground(new Color(153, 0, 51));
        redPanel.setPreferredSize(new Dimension(120, 36));

        redTitlePanel.setBackground(new Color(153, 0, 51));
        redTitlePanel.setPreferredSize(new Dimension(20, 20));

        titlePanel1.setBackground(new Color(240, 240, 240));
        titlePanel1.setPreferredSize(new Dimension(690, 30));

        titlePanel2.setBackground(new Color(180, 180, 158));
        titlePanel2.setPreferredSize(new Dimension(690, 30));

        titlePanel3.setBackground(new Color(180, 180, 158));
        titlePanel3.setPreferredSize(new Dimension(690, 30));

        greenPanel.setBackground(new Color(0, 102, 102));
        greenPanel.setPreferredSize(new Dimension(730, 60));

        //-----------set main title	
        bloodBankLabel.setBackground(new Color(255, 255, 255));
        bloodBankLabel.setFont(new Font("Segoe UI", 1, 18));
        bloodBankLabel.setText("BLOOD BANK");

        //-----------set name of the application
        titleLabel.setFont(new Font("Segoe UI", 1, 14));
        titleLabel.setForeground(new Color(153, 153, 153));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Add Blood Sample");



        //----------set labels ,comboboxes and textfields

        donorIdLabel.setFont(new Font("Segoe UI", 0, 12));
        donorIdLabel.setText("Donor ID: ");
        donorIdLabel.setHorizontalAlignment(SwingConstants.CENTER);

        donorIdText.setEditable(false);
        donorIdText.setFont(new Font("Arial", 0, 12));
        donorIdText.setHorizontalAlignment(SwingConstants.CENTER);

        donationDateLabel.setFont(new Font("Segoe UI", 0, 12));
        donationDateLabel.setText("Donation date   : ");

        donationDateText.setEditable(false);
        donationDateText.setFont(new Font("Arial", 0, 12));
        donationDateText.setHorizontalAlignment(SwingConstants.CENTER);

        bloodBagLabel.setFont(new Font("Segoe UI", 0, 12));
        bloodBagLabel.setText("Blood Bag ID: ");

        bloodBagText.setEditable(false);
        bloodBagText.setFont(new Font("Arial", 0, 12));
        bloodBagText.setHorizontalAlignment(SwingConstants.CENTER);

        donationInfoLabel.setFont(new Font("Segoe UI", 1, 14));
        donationInfoLabel.setForeground(new Color(255, 255, 255));
        donationInfoLabel.setText("Donation Information");

        bloodGroupLabel.setFont(new Font("Segoe UI", 0, 12));
        bloodGroupLabel.setText("Blood Group :");

        bloodGroupText.setEditable(false);
        bloodGroupText.setFont(new Font("Arial", 0, 12));
        bloodGroupText.setHorizontalAlignment(SwingConstants.CENTER);

        bodyTempLabel.setFont(new Font("Segoe UI", 0, 12));
        bodyTempLabel.setText("Body Temperature :");

        bodyTempText.setFont(new Font("Arial", 0, 12));

        fLabel.setFont(new Font("Segoe UI", 0, 12));
        fLabel.setText("F");

        donationTypeLabel.setFont(new Font("Segoe UI", 0, 12));
        donationTypeLabel.setText("Donation Type :");

        bloodCompCombo.setFont(new Font("Arial", 1, 12));
        bloodCompCombo.setModel(new DefaultComboBoxModel(new String[]{"Whole Blood", "Red cells", "Platelets", "Plasma", "Cryoprecipitated AHF"}));
        bloodCompCombo.setPreferredSize(new Dimension(52, 23));

        pulseRateLabel.setFont(new Font("Segoe UI", 0, 12));
        pulseRateLabel.setText("Pulse Rate :");

        pulseRateText.setFont(new Font("Arial", 0, 12));

        bpsLabel.setFont(new Font("Segoe UI", 0, 12));
        bpsLabel.setText("bps");

        rhLabel.setFont(new Font("Segoe UI", 0, 12));
        rhLabel.setText("RH Factor :");

        rhFactorText.setEditable(false);
        rhFactorText.setFont(new Font("Arial", 0, 12));
        rhFactorText.setHorizontalAlignment(SwingConstants.CENTER);


        bloodPressureLabel.setFont(new Font("Segoe UI", 0, 12));
        bloodPressureLabel.setText("Blood Pressure :");

        bloodPressureText.setFont(new Font("Arial", 0, 12));

        mmHgLabel.setFont(new Font("Segoe UI", 0, 12));
        mmHgLabel.setText("bps");

        bodyWeightLabel.setFont(new Font("Segoe UI", 0, 12));
        bodyWeightLabel.setText("Body Weight :");

        bodyWeightText.setFont(new Font("Arial", 0, 12));

        kgLabel.setFont(new Font("Segoe UI", 0, 12));
        kgLabel.setText("Kg");

        volumeLabel.setFont(new Font("Segoe UI", 0, 12));
        volumeLabel.setText("Donated Volume/Qty :");

        volumeText.setFont(new Font("Arial", 0, 12));

        mlLabel.setFont(new Font("Segoe UI", 0, 12));
        mlLabel.setText("ml");

        testReportLabel.setFont(new Font("Segoe UI", 1, 14));
        testReportLabel.setForeground(new Color(255, 255, 255));
        testReportLabel.setText("Blood Test Report");

        testDateLabel.setFont(new Font("Segoe UI", 0, 12));
        testDateLabel.setText(" Date of Test :  ");

        testDateText.setFont(new Font("Arial", 0, 12));

        reportNoLabel.setFont(new Font("Segoe UI", 0, 12));
        reportNoLabel.setText("Report no.  : ");

        reportNoText.setEditable(false);
        reportNoText.setFont(new Font("Arial", 0, 12));

        hbsagLabel.setFont(new Font("Segoe UI", 0, 12));
        hbsagLabel.setText("HbSAg :");

        hbsagCombo.setFont(new Font("Arial", 1, 12));
        hbsagCombo.setModel(new DefaultComboBoxModel(new String[]{"Positive", "Negative"}));
        hbsagCombo.setPreferredSize(new Dimension(52, 23));

        hivLabel.setFont(new Font("Segoe UI", 0, 12));
        hivLabel.setText("HIV :");

        hivCombo.setFont(new Font("Arial", 1, 12));
        hivCombo.setModel(new DefaultComboBoxModel(new String[]{"Positive", "Negative"}));
        hivCombo.setPreferredSize(new Dimension(52, 23));

        hcvLabel.setFont(new Font("Segoe UI", 0, 12));
        hcvLabel.setText("HCV :");

        hcvCombo.setFont(new Font("Arial", 1, 12));
        hcvCombo.setModel(new DefaultComboBoxModel(new String[]{"Positive", "Negative"}));
        hcvCombo.setPreferredSize(new Dimension(52, 23));

        malariyaLabel.setFont(new Font("Segoe UI", 0, 12));
        malariyaLabel.setText("Malariya :");

        malariyaCombo.setFont(new Font("Arial", 1, 12));
        malariyaCombo.setModel(new DefaultComboBoxModel(new String[]{"Positive", "Negative"}));
        malariyaCombo.setPreferredSize(new Dimension(52, 23));


        notificationLabel.setFont(new Font("Segoe UI", 1, 13));
        notificationLabel.setBackground(new Color(190, 142, 140));
        notificationLabel.setOpaque(true);
        notificationLabel.setForeground(new Color(255, 255, 255));
        notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel2.add(notificationLabel);
        notificationLabel.setBounds(910, 1, 400, 30);

        //-----------buttons

        addButton.setBackground(new Color(151, 185, 185));
        addButton.setFont(new Font("Segoe UI", 1, 12));
        addButton.setText("Add");
        addButton.setBorder(BorderFactory.createLineBorder(new Color(153, 153, 153)));

        //-----------set panelLayouts

        //grayPanel1Layout
        grayPanel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        grayPanel1.add(redTitlePanel);
        grayPanel1.add(bloodBankLabel);

        //titlePanel1Layout
        GroupLayout titlePanel1Layout = new GroupLayout(titlePanel1);
        titlePanel1.setLayout(titlePanel1Layout);
        titlePanel1Layout.setHorizontalGroup(
                titlePanel1Layout.createParallelGroup()
                .addGroup(titlePanel1Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)));
        titlePanel1Layout.setVerticalGroup(
                titlePanel1Layout.createParallelGroup()
                .addGroup(titlePanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)));

        //titlePanel2Layout
        GroupLayout titlePanel2Layout = new GroupLayout(titlePanel2);
        titlePanel2.setLayout(titlePanel2Layout);
        titlePanel2Layout.setHorizontalGroup(
                titlePanel2Layout.createParallelGroup()
                .addGroup(titlePanel2Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(donationInfoLabel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        titlePanel2Layout.setVerticalGroup(
                titlePanel2Layout.createParallelGroup()
                .addComponent(donationInfoLabel, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE));

        //titlePanel3Layout
        GroupLayout titlePanel3Layout = new GroupLayout(titlePanel3);
        titlePanel3.setLayout(titlePanel3Layout);
        titlePanel3Layout.setHorizontalGroup(
                titlePanel3Layout.createParallelGroup()
                .addGroup(titlePanel3Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(testReportLabel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        titlePanel3Layout.setVerticalGroup(
                titlePanel3Layout.createParallelGroup()
                .addComponent(testReportLabel, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE));

        //mainPanelLayout
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup()
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(redPanel, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(grayPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(titlePanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(titlePanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(donationDateLabel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(donationDateText, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(donorIdLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(donorIdText, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(bloodBagLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(bloodBagText, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
                .addComponent(titlePanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(greenPanel)
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(mainPanelLayout.createParallelGroup()
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(donationTypeLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bloodCompCombo, 0, 160, Short.MAX_VALUE))
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(bodyWeightLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bodyWeightText, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(kgLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(bloodGroupLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bloodGroupText, GroupLayout.PREFERRED_SIZE, 180, Short.MAX_VALUE))
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(rhLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rhFactorText, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))
                .addGap(83, 83, 83)
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addGroup(mainPanelLayout.createParallelGroup()
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(bloodPressureLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bloodPressureText, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(mmHgLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(pulseRateLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pulseRateText, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(bpsLabel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(volumeLabel, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(volumeText, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(mlLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(bodyTempLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(bodyTempText, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(fLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(hbsagLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(hbsagCombo, GroupLayout.PREFERRED_SIZE, 200, Short.MAX_VALUE))
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(testDateLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(testDateText, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(hcvLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(hcvCombo, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))
                .addGap(80, 80, 80)
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(malariyaLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(malariyaCombo, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                .addGroup(GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                .addComponent(reportNoLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(reportNoText, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                .addGroup(GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                .addComponent(hivLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(hivCombo, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
                .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addButton, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)));
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup()
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addComponent(redPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(grayPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addComponent(titlePanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(bloodBagLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addComponent(donorIdText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(donorIdLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addComponent(donationDateText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(donationDateLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addComponent(bloodBagText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(titlePanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(bodyTempLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addComponent(bloodGroupLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addComponent(bodyTempText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(fLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(bloodGroupText, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(pulseRateLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addComponent(pulseRateText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(bpsLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(donationTypeLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addComponent(bloodCompCombo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(bloodPressureLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addComponent(bloodPressureText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(mmHgLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(rhLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addComponent(rhFactorText, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(volumeLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addComponent(volumeText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(mlLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(bodyWeightLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(bodyWeightText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(kgLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(titlePanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(mainPanelLayout.createParallelGroup()
                .addComponent(testDateLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(testDateText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(reportNoLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(reportNoText, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(mainPanelLayout.createParallelGroup()
                .addComponent(hbsagLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(hbsagCombo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(hivLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(hivCombo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(mainPanelLayout.createParallelGroup()
                .addComponent(hcvLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(hcvCombo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(malariyaLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(malariyaCombo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(greenPanel, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(addButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)));
        add(mainPanel);
        pack();


    }

    private void setdonateDate() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
        donationDateText.setText(dateformat.format(new Date()));
    }
}
