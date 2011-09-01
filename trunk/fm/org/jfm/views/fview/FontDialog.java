package org.jfm.views.fview;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class FontDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private JPanel buttonsPanel = new JPanel();
  private JButton okButton = new JButton();
  private JButton cancelButton = new JButton();
  private JPanel panel = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JScrollPane scroll = new JScrollPane();
  private JList fontList = new JList();
  private JLabel sample = new JLabel();
  private JLabel jLabel1 = new JLabel();
  private JCheckBox boldCheckBox = new JCheckBox();
  private JCheckBox italicCheckBox = new JCheckBox();
  private JLabel jLabel2 = new JLabel();
  private JTextField sizeField = new JTextField();
  private boolean cancelled=false;
  
  public boolean getCancelled() {
    return this.cancelled;
  }
  public void setCancelled(boolean b) {
    this.cancelled = b;
  }
      
  public FontDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
//      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public FontDialog() {
    this(null, "", false);
  }
  void jbInit() throws Exception {
    this.setSize(new Dimension(382, 344));
    this.setResizable(false);
    panel1.setLayout(borderLayout1);
    okButton.setText("OK");
    sizeField.setText("10");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okButton_actionPerformed(e);
      }
    });
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    panel.setLayout(null);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    sample.setText("Sample font selection");

    jLabel1.setText("Fonts:");
    jLabel1.setBounds(new Rectangle(3, 8, 162, 15));
    boldCheckBox.setText("Bold");
    boldCheckBox.setBounds(new Rectangle(228, 30, 132, 17));
    italicCheckBox.setText("Italic");
    italicCheckBox.setBounds(new Rectangle(228, 56, 133, 17));
    jLabel2.setText("Size");
    jLabel2.setBounds(new Rectangle(228, 85, 107, 21));
    sizeField.setBounds(new Rectangle(228, 124, 144, 22));
    scroll.setBounds(new Rectangle(3, 23, 216, 215));
    sample.setBounds(new Rectangle(3, 253, 366, 33));
    
    getContentPane().add(panel1);
    panel1.add(buttonsPanel, BorderLayout.SOUTH);
    buttonsPanel.add(okButton, null);
    buttonsPanel.add(cancelButton, null);
    panel1.add(panel, BorderLayout.CENTER);
    panel.add(scroll, null);
    scroll.getViewport().add(fontList, null);
    panel.add(jLabel1, null);
    panel.add(sizeField, null);
    panel.add(boldCheckBox, null);
    panel.add(italicCheckBox, null);
    panel.add(jLabel2, null);
    panel.add(sample, null);
    buttonsPanel.getRootPane().setDefaultButton(okButton);
    fontList.setModel(new DefaultListModel());
    fontList.setSelectionMode(fontList.getSelectionModel().SINGLE_SELECTION);
    
    fontList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()) return;
        setSampleFont();
      }
    });
    
    boldCheckBox.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
         setSampleFont();     
      }
    });
    
    italicCheckBox.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
         setSampleFont();     
      }
    });
    sizeField.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setSampleFont();
      }
    });
    
    sample.setVisible(true);
    
    loadFonts();
  }
  
  private void setSampleFont(){
        int style=Font.PLAIN;
        if(boldCheckBox.isSelected()) style=style|Font.BOLD;
        if(italicCheckBox.isSelected()) style=style|Font.ITALIC;
        int size=10;
        try{
          size=Integer.parseInt(sizeField.getText());
        }catch(NumberFormatException ex){
          JOptionPane.showMessageDialog(this,sizeField.getText()+" is not a number. Please enter a number.","Error",JOptionPane.ERROR_MESSAGE);
          size=10;
        }
        sample.setFont(new Font((String)fontList.getSelectedValue(),style,size));
        sample.revalidate();            
  }
  
  private void loadFonts(){
    String[] sfonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    for(int i=0;i<sfonts.length;i++){
      ((DefaultListModel)fontList.getModel()).addElement(sfonts[i]);      
    }  
  }
  
  public Font getSelectedFont(){
    return sample.getFont(); 
  }

  void okButton_actionPerformed(ActionEvent e) {
    setCancelled(false);
    this.dispose();
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    setCancelled(true);
    this.dispose();
  }      
}