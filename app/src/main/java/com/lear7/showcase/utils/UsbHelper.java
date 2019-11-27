package com.lear7.showcase.utils;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import java.util.HashMap;
import java.util.Iterator;

public class UsbHelper {

    public static UsbDevice findUSB(Context mContext, int VENDORID, int PRODUCTID) {
        UsbDevice usbDevice;
        UsbManager usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            if (device.getVendorId() == VENDORID && device.getProductId() == PRODUCTID) {
                usbDevice = device;
                return usbDevice;
            }
        }
        return null;
    }

}
