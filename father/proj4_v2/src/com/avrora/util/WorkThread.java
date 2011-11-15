package com.avrora.util;

import com.avrora.GUI.MainWindow;

import com.avrora.common.CommonVariables;

public class WorkThread implements Runnable {
    private Thread thread;
    private static MainWindow win;
    private static final WorkThread INSTANSE = new WorkThread();
    Float[] array = new Float[11];
    //  private byte oldState;

    private WorkThread() {
        //  oldState = -1;

    }

    public static WorkThread getInstanse() {
        return INSTANSE;
    }

    public void StartWork() {
        System.out.println("WorkThread.StartWork");
        if ((thread == null) || (!thread.isInterrupted())) {
            CommonVariables.INSTANSE.setNEXT_TASK_TIME(System
                    .currentTimeMillis());
            thread = new Thread(this);
            thread.start();

        }


    }


    public void run() {
        while (true) {
            if (System.currentTimeMillis() > CommonVariables.INSTANSE.getNEXT_TASK_TIME()) {
                //    System.out.println("����� "
                //           + new Date(System.currentTimeMillis()));
                System.out.println("__________________________________________");
                win.addData();
                long timeForNewTask = CommonVariables.INSTANSE.getNEXT_TASK_TIME() + 3000;
                //	+ CommonConstants.Intervals[CommonVariables.INSTANSE.getInterval()];
                CommonVariables.INSTANSE.setNEXT_TASK_TIME(timeForNewTask);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }

        }
    }


    public static void setWin(MainWindow wind) {
        win = wind;
    }
}
