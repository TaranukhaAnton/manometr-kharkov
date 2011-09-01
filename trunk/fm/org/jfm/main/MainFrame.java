package org.jfm.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jfm.event.*;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @@author Giurgiu Sergiu
 * @@version 1.0
 */

public class MainFrame extends JFrame {
  private JPanel contentPane;

//  private JMenuBar jMenuBar1 = new JMenuBar();
//  private JMenu jMenuFile = new JMenu();
 // private JMenuItem jMenuFileExit = new JMenuItem();
 // private JMenuItem jMenuFileSearch =new JMenuItem("Search");
//  private JMenu jMenuHelp = new JMenu();
//  private JMenuItem jMenuHelpAbout = new JMenuItem();
//  private JMenu jMenuView=new JMenu("View");
  
//  private JMenu jMenuViewLookandFeel = new JMenu("Look&Feel");
    
//  private ButtonGroup viewGroup=new ButtonGroup();
  //private JRadioButtonMenuItem jMenuViewFull=new JRadioButtonMenuItem("Full");
//  private JRadioButtonMenuItem jMenuViewTree=new JRadioButtonMenuItem("Tree");
//  private JRadioButtonMenuItem jMenuViewFile=new JRadioButtonMenuItem("File");
  
//  private JToolBar jToolBar = new JToolBar();
//  private JButton jButton1 = new JButton();
//  private JButton jButton2 = new JButton();
//  private JButton jButton3 = new JButton();
//  private ImageIcon image1;
//  private ImageIcon image2;
//  private ImageIcon image3;
 // private JLabel statusBar = new JLabel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private MainPanel mainPanel=new MainPanel();
  
  

  /**Construct the frame*/
  public MainFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**Component initialization*/
  private void jbInit() throws Exception  {
    Options.setMainFrame(this);
//    image1 = new ImageIcon(org.jfm.main.MainFrame.class.getResource("openFile.gif"));
//    image2 = new ImageIcon(org.jfm.main.MainFrame.class.getResource("closeFile.gif"));
//    image3 = new ImageIcon(org.jfm.main.MainFrame.class.getResource("help.gif"));
    //setIconImage(Toolkit.getDefaultToolkit().createImage(MainFrame.class.getResource("[Your Icon]")));
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout1);
    //statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
    this.setSize(new Dimension(800, 600));

    this.setTitle("Java File Manager");

   // statusBar.setText(" ");
//    jMenuFile.setText("File");
//    jMenuFileExit.setText("Exit");
//    jMenuFileExit.addActionListener(new ActionListener()  {
//      public void actionPerformed(ActionEvent e) {
//        jMenuFileExit_actionPerformed(e);
//      }
//    });
//    jMenuHelp.setText("Help");
//    jMenuHelpAbout.setText("About");
//    jMenuHelpAbout.addActionListener(new ActionListener()  {
//      public void actionPerformed(ActionEvent e) {
//        jMenuHelpAbout_actionPerformed(e);
//      }
//    });

//    jMenuViewFull.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//           ChangeViewEvent ev=new ChangeViewEvent();
//           ev.setSource(MainFrame.this);
//           ev.setNewView(ViewportOptions.FILE_BROWSER);
//           Broadcaster.notifyChangeViewListeners(ev);
//      }
//    });

//    jMenuViewTree.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//           ChangeViewEvent ev=new ChangeViewEvent();
//           ev.setSource(MainFrame.this);
//           ev.setNewView(ViewportOptions.TREE_BROWSER);
//           Broadcaster.notifyChangeViewListeners(ev);
//      }
//    });
//
//    jMenuViewFile.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//           ChangeViewEvent ev=new ChangeViewEvent();
//           ev.setSource(MainFrame.this);
//           ev.setNewView(ViewportOptions.FILE_VIEWER);
//           Broadcaster.notifyChangeViewListeners(ev);
//      }
//    });

//    jMenuFileSearch.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        search();
//      }
//    });
    
//    jMenuViewFull.setSelected(true);
//    jMenuViewTree.setSelected(false);
//    jMenuViewFile.setSelected(false);

//    jButton1.setIcon(image1);
//    jButton1.setToolTipText("Open File");
//    jButton2.setIcon(image2);
//    jButton2.setToolTipText("Close File");
//    jButton3.setIcon(image3);
//    jButton3.setToolTipText("Help");
//    jToolBar.add(jButton1);
//    jToolBar.add(jButton2);
//    jToolBar.add(jButton3);

//    jMenuView.add(jMenuViewFull);
//    jMenuView.add(jMenuViewTree);
//    jMenuView.add(jMenuViewFile);
//    jMenuView.addSeparator();
//    jMenuView.add(jMenuViewLookandFeel);
//    addLookandFeels();
//
//    viewGroup.add(jMenuViewFull);
//    viewGroup.add(jMenuViewTree);
//    viewGroup.add(jMenuViewFile);
//
//    jMenuFile.add(jMenuFileSearch);
//    jMenuFile.add(jMenuFileExit);
//    jMenuHelp.add(jMenuHelpAbout);
//    jMenuBar1.add(jMenuFile);
//    jMenuBar1.add(jMenuView);
//    jMenuBar1.add(jMenuHelp);
//
//    jMenuFileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_MASK));
//    jMenuFileSearch.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK));
//    this.setJMenuBar(jMenuBar1);
   // contentPane.add(jToolBar, BorderLayout.NORTH);
  //  contentPane.add(statusBar, BorderLayout.SOUTH);
    contentPane.add(mainPanel,BorderLayout.CENTER);
  }
  
/*  private void addLookandFeels(){
    final UIManager.LookAndFeelInfo[] lfs=  UIManager.getInstalledLookAndFeels();
    //java.util.Arrays.sort(lfs);
    if(lfs!=null){
      for(int i=0;i<lfs.length;i++){
        JMenuItem look=new JMenuItem(lfs[i].getName());
        final String className=lfs[i].getClassName();        
        look.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
              try{
                UIManager.setLookAndFeel(className);
                SwingUtilities.updateComponentTreeUI(MainFrame.this);
              }catch(Exception exception){exception.printStackTrace();}
            }
        });
//        jMenuViewLookandFeel.add(look);
      }
    }
  }*/
  
/*  *//**File | Seach action*//*
  private void search(){
    SearchDialog dialog=new SearchDialog(this,"Search...",false);
    dialog.setLocationRelativeTo(this);
    dialog.show();
  }*/

  //**File | Exit action performed*//*
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }
//  /**Help | About action performed*/
//  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
//    MainFrame_AboutBox dlg = new MainFrame_AboutBox(this);
//    Dimension dlgSize = dlg.getPreferredSize();
//    Dimension frmSize = getSize();
//    Point loc = getLocation();
//    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
//    dlg.setModal(true);
//    dlg.show();
//  }
  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jMenuFileExit_actionPerformed(null);
    }
  }
}