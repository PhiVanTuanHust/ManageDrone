package com.tony.drawing;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;

/**
 * Created by Phí Văn Tuấn on 13/11/2018.
 */

public class FlyCtrl {
    public static final int ONEKEYFLIP = 8;
    public static final int ONEKEYFLYDOWN = 32;
    public static final int ONEKEYFLYUP = 16;
    public static final int ONEKEYJIAOZHENG = 128;
    public static final int ONEKEYSTOP = 64;
    public static final int ONTOUCED = 7013;
    public static final int ONTOUCHING = 7012;
    public static final int RECV_BATTERY0 = 7001;
    public static final int RECV_BATTERY100 = 7005;
    public static final int RECV_BATTERY25 = 7002;
    public static final int RECV_BATTERY50 = 7003;
    public static final int RECV_BATTERY75 = 7004;
    public static final int RECV_FILP = 7010;
    public static final int RECV_FILP_NO = 7011;
    public static final int RECV_HIGHT = 7006;
    public static final int RECV_LOW = 7009;
    public static final int RECV_SHAKE = 7007;
    public static final int RECV_SHAKE_CANCEL = 7008;
    public static final int RECV_START = 7000;
    private static final String TAG = "FlyCtrl";
    public static int[] rudderdata = new int[5];
    public static byte[] serialdata = new byte[11];
    public static int trim_left_landscape = 0;
    public static int trim_right_landscape = 0;
    public static int trim_right_portrait = 0;
    private Handler handler;
    private boolean isFlipCtrl = false;
    private boolean isFlipOver = false;
    private boolean isJiaozheng = false;
    private boolean isNeedSendData = true;
    private boolean isOneKeyStop = false;
    private boolean isOver = true;
    private boolean isRecStop93 = false;
    private boolean isSendOneKeyDown = false;
    private boolean isSendOneKeyUp = false;
    private boolean isStop63 = false;
    private boolean isX = true;
    private Thread recThread63;
    private Thread recThread93;
    private int sendFlipTimes = 0;
    private long sendOneKeyDownTime = 0;
    private long sendOneKeyStopTime = 0;
    private long sendOneKeyUpTime = 0;
    private long sendOneKeyjiaozhengTime = 0;
    private Thread sendThread63;
    private Thread sendThread93;

    public FlyCtrl(Handler handler) {
        this.handler = handler;
        serialdata[0] = (byte) 102;
        serialdata[1] = (byte) 0;
        serialdata[2] = (byte) 0;
        serialdata[3] = (byte) 0;
        serialdata[4] = (byte) 0;
        serialdata[5] = (byte) 0;
        serialdata[6] = (byte) 0;
        serialdata[7] = (byte) 0;
        serialdata[8] = (byte) 0;
        serialdata[9] = checkSum(serialdata);
        serialdata[10] = (byte) -103;
        rudderdata[1] = 128;
        rudderdata[2] = 128;
        rudderdata[3] = 0;
        rudderdata[4] = 128;
        Log.i(TAG, "initialize the serial data.");
    }

    private byte checkSum(byte[] data) {
        return (byte) ((((((((data[1] ^ data[2]) ^ data[3]) ^ data[4]) ^ data[5]) ^ data[6]) ^ data[7]) ^ data[8]) & MotionEventCompat.ACTION_MASK);
    }

    public static byte checkSumRec(byte[] data) {
        return (byte) (((((data[1] ^ data[2]) ^ data[3]) ^ data[4]) ^ data[5]) & MotionEventCompat.ACTION_MASK);
    }

    public static String byteToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & MotionEventCompat.ACTION_MASK);
            if (hex.length() == 1) {
                hex = new StringBuilder(String.valueOf('0')).append(hex).toString();
            }
            sb.append(new StringBuilder(String.valueOf(hex)).append(" ").toString());
        }
        return sb.toString();
    }

//    public void startSendDataThread93() {
//        if (this.sendThread93 == null || !this.sendThread93.isAlive()) {
//            this.sendThread93 = new Thread() {
//                public void run() {
//                    FlyCtrl.this.isNeedSendData = true;
//                    LeweiLib.LW93InitUdpSocket();
//                    while (FlyCtrl.this.isNeedSendData) {
//                        if (!Applications.isAllCtrlHide) {
//                            FlyCtrl.this.updateSendData();
//                            LeweiLib.LW93SendUdpData(FlyCtrl.serialdata, FlyCtrl.serialdata.length);
//                        }
//                        if (Applications.isStartDrawing) {
//                            FlyCtrl.this.updateSendData();
//                            LeweiLib.LW93SendUdpData(FlyCtrl.serialdata, FlyCtrl.serialdata.length);
//                        }
//                        Log.e(FlyCtrl.TAG, "Data: " + FlyCtrl.byteToHex(FlyCtrl.serialdata));
//                        try {
//                            Thread.sleep(50);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    LeweiLib.LW93CloseUdpSocket();
//                }
//            };
//            this.sendThread93.start();
//        }
//    }
//
//    public void receiveData93() {
//        this.recThread93 = new Thread(new Runnable() {
//            public void run() {
//                FlyCtrl.this.isRecStop93 = false;
//                while (!FlyCtrl.this.isRecStop93) {
//                    try {
//                        byte[] recvBuf = LeweiLib.LW93RecvUdpData();
//                        if (recvBuf.length >= 8 && recvBuf[0] == (byte) 102 && recvBuf[7] == (byte) -103 && recvBuf[6] == FlyCtrl.checkSumRec(recvBuf)) {
//                            if ((recvBuf[4] & MotionEventCompat.ACTION_MASK) == 128) {
//                                FlyCtrl.this.sendHandlerMessage(FlyCtrl.RECV_FILP, null);
//                                Log.e("", "1221212");
//                            } else {
//                                FlyCtrl.this.sendHandlerMessage(FlyCtrl.RECV_FILP_NO, null);
//                            }
//                            int channel = Integer.parseInt(Integer.toString(recvBuf[1] & MotionEventCompat.ACTION_MASK));
//                            FlyCtrl.this.sendHandlerMessage(FlyCtrl.RECV_START, Integer.valueOf(channel));
//                            if ((recvBuf[1] & MotionEventCompat.ACTION_MASK) >= 0 && (recvBuf[1] & MotionEventCompat.ACTION_MASK) <= 9) {
//                                FlyCtrl.this.sendHandlerMessage(FlyCtrl.RECV_BATTERY0, Integer.valueOf(channel));
//                            } else if ((recvBuf[1] & MotionEventCompat.ACTION_MASK) >= 10 && (recvBuf[1] & MotionEventCompat.ACTION_MASK) <= 29) {
//                                FlyCtrl.this.sendHandlerMessage(FlyCtrl.RECV_BATTERY25, Integer.valueOf(channel));
//                            } else if ((recvBuf[1] & MotionEventCompat.ACTION_MASK) >= 30 && (recvBuf[1] & MotionEventCompat.ACTION_MASK) <= 59) {
//                                FlyCtrl.this.sendHandlerMessage(FlyCtrl.RECV_BATTERY50, Integer.valueOf(channel));
//                            } else if ((recvBuf[1] & MotionEventCompat.ACTION_MASK) >= 60 && (recvBuf[1] & MotionEventCompat.ACTION_MASK) <= 89) {
//                                FlyCtrl.this.sendHandlerMessage(FlyCtrl.RECV_BATTERY75, Integer.valueOf(channel));
//                            } else if ((recvBuf[1] & MotionEventCompat.ACTION_MASK) >= 90 && (recvBuf[1] & MotionEventCompat.ACTION_MASK) <= 100) {
//                                FlyCtrl.this.sendHandlerMessage(FlyCtrl.RECV_BATTERY100, Integer.valueOf(channel));
//                            }
//                            if ((recvBuf[1] & MotionEventCompat.ACTION_MASK) < 0 || (recvBuf[1] & MotionEventCompat.ACTION_MASK) > 15) {
//                                FlyCtrl.this.sendHandlerMessage(FlyCtrl.RECV_SHAKE_CANCEL, null);
//                            } else {
//                                FlyCtrl.this.sendHandlerMessage(FlyCtrl.RECV_SHAKE, null);
//                            }
//                            FlyCtrl.this.sendHandlerMessage(FlyCtrl.RECV_HIGHT, Double.valueOf(new BigDecimal(((double) ((recvBuf[2] << 8) + (recvBuf[3] & MotionEventCompat.ACTION_MASK))) / 100.0d).setScale(1, 4).doubleValue()));
//                        }
//                    } catch (Exception e) {
//                    }
//                    try {
//                        Thread.sleep(20);
//                    } catch (InterruptedException e2) {
//                        e2.printStackTrace();
//                    }
//                }
//            }
//        });
//        this.recThread93.start();
//    }
//
//    public void startSendDataThread63() {
//        if (this.sendThread63 == null || !this.sendThread63.isAlive()) {
//            this.sendThread63 = new Thread(new Runnable() {
//                public void run() {
//                    try {
//                        Log.e(FlyCtrl.TAG, "start send serial data");
//                        FlyCtrl.this.isNeedSendData = true;
//                        Thread.sleep(100);
//                        while (FlyCtrl.this.isNeedSendData && LeweiLib63.LW63GetClientSize() != 1) {
//                            Thread.sleep(2000);
//                        }
//                        while (FlyCtrl.this.isNeedSendData) {
//                            if (LeweiLib63.LW63GetLogined()) {
//                                if (!LeweiLib63.LW63GetSerialState()) {
//                                    LeweiLib63.LW63StartSerial(19200);
//                                }
//                                if (!Applications.isAllCtrlHide) {
//                                    FlyCtrl.this.updateSendData();
//                                    LeweiLib63.LW63SendSerialData(FlyCtrl.serialdata, FlyCtrl.serialdata.length);
//                                }
//                                if (Applications.isStartDrawing) {
//                                    FlyCtrl.this.updateSendData();
//                                    LeweiLib63.LW63SendSerialData(FlyCtrl.serialdata, FlyCtrl.serialdata.length);
//                                }
//                                Thread.sleep(50);
//                            } else if (LeweiLib63.LW63GetSerialState()) {
//                                LeweiLib63.LW63StopSerial();
//                            }
//                        }
//                        Thread.sleep(20);
//                        Log.e(FlyCtrl.TAG, "stop send serial data");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            this.sendThread63.start();
//        }
//    }

    private void updateSendData() {
        int rotate = rudderdata[4] + (trim_left_landscape * 2);
        if (rotate < 0) {
            rotate = 1;
        } else if (rotate >= MotionEventCompat.ACTION_MASK) {
            rotate = MotionEventCompat.ACTION_MASK;
        }
        int ail = rudderdata[1];
        int ele = rudderdata[2];
        if (ail < 0) {
            ail = 1;
        } else if (ail >= MotionEventCompat.ACTION_MASK) {
            ail = MotionEventCompat.ACTION_MASK;
        }
        if (ele < 0) {
            ele = 1;
        } else if (ele >= MotionEventCompat.ACTION_MASK) {
            ele = MotionEventCompat.ACTION_MASK;
        }
        checkSendOneKeyTime();
        checkSendOneKeyStopTime();
        checkSendOneKeyjiaozhengTime();
        serialdata[1] = (byte) ail;
        serialdata[2] = (byte) ele;
        serialdata[3] = (byte) rudderdata[3];
        if (serialdata[3] == Byte.MAX_VALUE) {
            serialdata[3] = Byte.MIN_VALUE;
        }
        serialdata[4] = (byte) rotate;
        if (serialdata[1] == Byte.MIN_VALUE && serialdata[2] == Byte.MIN_VALUE && serialdata[3] == Byte.MIN_VALUE) {
            sendHandlerMessage(ONTOUCED, null);
        } else {
            sendHandlerMessage(ONTOUCHING, null);
            Applications.isShowWaring = true;
        }
        serialdata[5] = (byte) ((trim_right_portrait * 2) + 128);
        serialdata[6] = (byte) ((trim_right_landscape * 2) + 128);
        serialdata[7] = (byte) ((trim_left_landscape * 2) + 128);
        serialdata[9] = checkSum(serialdata);
    }

    public void stopSendDataThread() {
        this.isNeedSendData = false;
    }

    public void stopRecDataThread() {
        this.isRecStop93 = true;
    }

    private void checkSendOneKeyStopTime() {
        long nowtime = System.currentTimeMillis();
        if (!this.isOneKeyStop) {
            return;
        }
        byte[] bArr;
        if (nowtime - this.sendOneKeyStopTime > 1200) {
            this.isOneKeyStop = false;
            bArr = serialdata;
            bArr[8] = (byte) (bArr[8] & -65);
            if (this.handler != null) {
                this.handler.sendEmptyMessage(131);
            }
        } else if ((serialdata[8] & 64) <= 0) {
            bArr = serialdata;
            bArr[8] = (byte) (bArr[8] | 64);
        }
    }

    private void checkSendOneKeyjiaozhengTime() {
        long nowtime = System.currentTimeMillis();
        if (!this.isJiaozheng) {
            return;
        }
        byte[] bArr;
        if (nowtime - this.sendOneKeyjiaozhengTime > 1200) {
            this.isJiaozheng = false;
            bArr = serialdata;
            bArr[5] = (byte) (bArr[5] & -129);
            if (this.handler != null) {
                this.handler.sendEmptyMessage(Applications.FLYCTRL_JIAO_ZHENG);
            }
        } else if ((serialdata[5] & 128) <= 0) {
            bArr = serialdata;
            bArr[5] = (byte) (bArr[5] | 128);
        }
    }

    private void checkSendOneKeyTime() {
        byte[] bArr;
        long nowtime = System.currentTimeMillis();
        if (this.isSendOneKeyUp) {
            if (nowtime - this.sendOneKeyUpTime > 1200) {
                this.isSendOneKeyUp = false;
                bArr = serialdata;
                bArr[8] = (byte) (bArr[8] & -17);
            } else if ((serialdata[8] & 16) <= 0) {
                bArr = serialdata;
                bArr[8] = (byte) (bArr[8] | 16);
            }
        }
        if (!this.isSendOneKeyDown) {
            return;
        }
        if (nowtime - this.sendOneKeyDownTime > 1200) {
            this.isSendOneKeyDown = false;
            bArr = serialdata;
            bArr[8] = (byte) (bArr[8] & -33);
        } else if ((serialdata[8] & 32) <= 0) {
            bArr = serialdata;
            bArr[8] = (byte) (bArr[8] | 32);
        }
    }

    public void setFlip(boolean isFlip) {
        byte[] bArr;
        if (isFlip) {
            this.isFlipCtrl = true;
            this.isFlipOver = false;
            bArr = serialdata;
            bArr[5] = (byte) (bArr[5] | 8);
            return;
        }
        bArr = serialdata;
        bArr[5] = (byte) (bArr[5] & -9);
    }

    private void checkFlip(int x, int y) {
        if (this.isFlipCtrl) {
            int divideOver;
            int divedeLimit;
            if (Applications.speed_level == 1) {
                divideOver = 32;
                divedeLimit = 16;
            } else if (Applications.speed_level == 2) {
                divideOver = 48;
                divedeLimit = 21;
            } else {
                divideOver = 96;
                divedeLimit = 32;
            }
            int diffX = x - 128;
            int diffY = y - 128;
            if (Math.abs(diffX) > divideOver && Math.abs(diffY) < divedeLimit) {
                this.isFlipCtrl = false;
                this.sendFlipTimes = 5;
                this.isX = true;
                if (diffX > 0) {
                    this.isOver = true;
                } else {
                    this.isOver = false;
                }
            }
            if (Math.abs(y - 128) > divideOver && Math.abs(x - 128) < divedeLimit) {
                this.isFlipCtrl = false;
                this.sendFlipTimes = 5;
                this.isX = false;
                if (diffY > 0) {
                    this.isOver = true;
                } else {
                    this.isOver = false;
                }
            }
        }
        if (!this.isFlipOver) {
            int i = this.sendFlipTimes;
            this.sendFlipTimes = i - 1;
            if (i > 0) {
                if (this.isX) {
                    if (this.isOver) {
                        rudderdata[1] = MotionEventCompat.ACTION_MASK;
                    } else {
                        rudderdata[1] = 1;
                    }
                } else if (this.isOver) {
                    rudderdata[2] = MotionEventCompat.ACTION_MASK;
                } else {
                    rudderdata[2] = 1;
                }
                if (this.sendFlipTimes == 0) {
                    if (this.handler != null) {
                        this.handler.sendEmptyMessage(132);
                    }
                    rudderdata[1] = 128;
                    rudderdata[2] = 128;
                    byte[] bArr = serialdata;
                    bArr[5] = (byte) (bArr[5] & -9);
                }
            }
        }
    }

    public void setOneKeyUp() {
        this.isSendOneKeyUp = true;
        this.sendOneKeyUpTime = System.currentTimeMillis();
    }

    public void setOenKeyStop() {
        this.isOneKeyStop = true;
        this.sendOneKeyStopTime = System.currentTimeMillis();
    }

    public void setOenKeyJiaozheng() {
        this.isJiaozheng = true;
        this.sendOneKeyjiaozhengTime = System.currentTimeMillis();
    }

    public void setOneKeyDown() {
        this.isSendOneKeyDown = true;
        this.sendOneKeyDownTime = System.currentTimeMillis();
    }

    private void sendHandlerMessage(int type, Object obj) {
        if (this.handler != null) {
            Message message = Message.obtain();
            message.what = type;
            message.obj = obj;
            this.handler.sendMessage(message);
        }
    }

    public static String bytesToHexStringLength(byte[] src, int length) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0 || length <= 0) {
            return null;
        }
        for (int i = 0; i < length; i++) {
            String hv = Integer.toHexString(src[i] & MotionEventCompat.ACTION_MASK);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

}
