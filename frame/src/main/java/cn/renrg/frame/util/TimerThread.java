package cn.renrg.frame.util;

import android.os.Handler;
import android.os.Message;

/**
 * Created by renruigang
 * <p>
 * 计时器功能-支持暂停和继续
 */

public class TimerThread extends Thread {

    private final Object lock = new Object();
    private boolean pause = false;
    private Handler handler;
    private int message_what;//handler接收处理的message类型
    private int sleep_time;//计时器间隔时间

    public TimerThread(Handler handler, int message_what, int sleep_time) {
        this.handler = handler;
        this.message_what = message_what;
        this.sleep_time = sleep_time;
    }

    /**
     * 调用这个方法实现暂停线程
     */
    public void pauseThread() {
        pause = true;
    }

    /**
     * 调用这个方法实现恢复线程的运行
     */
    public void resumeThread() {
        pause = false;
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    /**
     * 注意：这个方法只能在run方法里调用，不然会阻塞主线程，导致页面无响应
     */
    void onPause() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        super.run();
        try {
            while (true) {
                // 让线程处于暂停等待状态
                while (pause) {
                    onPause();
                }
                try {
                    Message msg = new Message();
                    msg.what = message_what;
                    handler.sendMessage(msg);
                    Thread.sleep(sleep_time);
                } catch (InterruptedException e) {
                    //捕获到异常之后，执行break跳出循环
                    break;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
