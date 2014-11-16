package safir.frame.dialogs;

import safir.data.Group;
import safir.data.Sensor;
import safir.frame.SafirFrame;
import safir.frame.dialogs.propertiesDialogs.SensorProperties;
import safir.rs232connect.SerialConnection;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User: anton
 * Date: 21.09.14
 */
public class PopupTrigger extends MouseAdapter {
    public static final int GROUP_LEVEL = 2;
    public static final int SENSOR_LEVEL = 3;
    private JTree tree;
    private SafirFrame application;
    private JPopupMenu popupSensor, popupGroup, popupTermocam;


    public PopupTrigger(JTree tree, SafirFrame application, JPopupMenu popupSensor, JPopupMenu popupGroup, JPopupMenu popupTermocam) {
        this.tree = tree;
        this.application = application;
        this.popupSensor = popupSensor;
        this.popupGroup = popupGroup;
        this.popupTermocam = popupTermocam;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        TreePath path = tree.getPathForLocation(x, y);
        int level = -1;
        if (path != null) {
            DefaultMutableTreeNode popupNode = (DefaultMutableTreeNode) path
                    .getLastPathComponent();
            level = popupNode.getLevel();
            if (level == GROUP_LEVEL) {
                int id = ((Group) popupNode).getGroupNum();
                application.setSelectedGroupId(id);


            }
            if (level == SENSOR_LEVEL) {
                int id = ((Sensor) popupNode).getGroup();
                application.setSelectedGroupId(id);

            }


        }


        if (e.getClickCount() == 2) {


            if (level == 3) {
                (new SensorProperties(application, (Sensor) path.getLastPathComponent())).setVisible(true);
            }

        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            int x = e.getX();
            int y = e.getY();
            TreePath path = tree.getPathForLocation(x, y);
            if (path != null) {
                application.setClickedPath(path);

                DefaultMutableTreeNode popupNode = (DefaultMutableTreeNode) path
                        .getLastPathComponent();
                int level = popupNode.getLevel();

                if (level == 1)
                    popupTermocam.show(tree, x, y);
                else if (level == 2) {

                    ((JMenuItem) popupGroup.getComponent(0))
                            .setEnabled(SerialConnection.INSTANSE.isOpen());
                    ((JMenuItem) popupGroup.getComponent(1))
                            .setEnabled(SerialConnection.INSTANSE.isOpen()
                                    & (popupNode.getChildCount() != 0));
                    ((JMenu) popupGroup.getComponent(3))
                            .setEnabled(SerialConnection.INSTANSE.isOpen()
                                    & (popupNode.getChildCount() != 0));
                    ((JMenuItem) popupGroup.getComponent(8))
                            .setEnabled(SerialConnection.INSTANSE.isOpen());
                    ((JMenuItem) popupGroup.getComponent(6)).setVisible(application.isLogin());
                    ((javax.swing.JPopupMenu.Separator) popupGroup.getComponent(7)).setVisible(application.isLogin());


                    popupGroup.show(tree, x, y);
                } else if (level == 3) {
                    Sensor sensor = (Sensor) popupNode;
                    ((JMenuItem) popupSensor.getComponent(0))
                            .setEnabled(SerialConnection.INSTANSE.isOpen());
                    ((JMenuItem) popupSensor.getComponent(1))
                            .setEnabled(SerialConnection.INSTANSE.isOpen());
                    ((JMenuItem) popupSensor.getComponent(2))
                            .setEnabled(SerialConnection.INSTANSE.isOpen());
                    ((JMenuItem) popupSensor.getComponent(4))
                            .setEnabled(SerialConnection.INSTANSE.isOpen());
                    ((JMenuItem) popupSensor.getComponent(5)).setEnabled(SerialConnection.INSTANSE.isOpen());
                    ((JMenuItem) popupSensor.getComponent(7)).setText((sensor.isEnabled()) ? "Выклють" : "Включить");



                    //todo права доступа поправить!
//                    ((JMenuItem) popupSensor.getComponent(10)).setVisible(application.isLogin());
//                    ((JMenuItem) popupSensor.getComponent(10)).setVisible(application.isLogin());
//                    ((JMenuItem) popupSensor.getComponent(11)).setVisible(application.isLogin());
//                    ((javax.swing.JPopupMenu.Separator) popupSensor.getComponent(12)).setVisible(application.isLogin());


//                    ((JMenuItem) popupSensor.getComponent(13)).setEnabled(SerialConnection.INSTANSE.isOpen());
                    popupSensor.show(tree, x, y);
                }

            }
        }
    }
}
