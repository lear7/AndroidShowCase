package com.lear7.showcase.utils.shell;

import android.content.Context;
import android.text.TextUtils;

/**
 * @author Lear
 * @description
 * @date 2019/12/18 13:41
 */
public class BoardHelper {

    private static BoardHelper INSTANCE;

    public static final String TAG = "BoardHelper";

    public synchronized BoardHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (BoardHelper.class) {
                INSTANCE = new BoardHelper();
            }
        }
        return INSTANCE;
    }

//    private static final String BOARD1_CMD = "cat /proc/mcu | grep board_id";
//    private static final String BOARD1_TOKEN = "board_id = 4";

    private static final String BOARD2_CMD = "getprop ro.hardinfo";
    private static final String BOARD2_TOKEN = "QJ-iCON39-180528-WHNE73-031609";

    public static boolean isAuthorizedDevice(Context context, String expectedToken) {
        // test command line
//        CommandResult cmd1 = Shell.SH.run(BOARD1_CMD);
//        if (cmd1.isSuccessful()) {
//            String res = cmd1.getStdout();
//            if (TextUtils.equals(res, BOARD1_TOKEN)) {
//                return true;
//            }
//        }
        CommandResult cmd2 = Shell.SH.run(BOARD2_CMD);
        if (cmd2.isSuccessful()) {
            String res = cmd2.getStdout();
            if (TextUtils.equals(res, expectedToken)) {
                return true;
            }
        }
        return false;
    }

}
