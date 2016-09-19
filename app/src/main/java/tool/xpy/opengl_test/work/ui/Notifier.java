package tool.xpy.opengl_test.work.ui;

/**
 * Created by root on 15-10-14.
 */
public class Notifier {
    /**
     * handler主要接受子线程发送的数据， 并用此数据配合主线程更新UI。
     * 具体参考:http://mobile.51cto.com/aprogram-442833.htm
     */
    public static MainActivity.MyHandler handler;

    public static void write(final String text){
        handler.showMessage(text);
    }
}
