package io.bionet.webbluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;

/**
 * Created by juul on 8/18/16.
 */
public class JavascriptInterface {
    private Activity activity;
    private WebView mWebView;
    private String jsCallbackName;

    public JavascriptInterface(Activity activity, WebView webView) {
        this.activity = activity;
        mWebView = webView;

    }



    @android.webkit.JavascriptInterface
    public void bluetoothLEScan(String callbackName) {
        jsCallbackName = callbackName;

        BluetoothManager bluetoothManager =
                (BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();

        BluetoothLeScanner scanner = mBluetoothAdapter.getBluetoothLeScanner();

        scanner.startScan(mScanCallback);
    }


    private ScanCallback mScanCallback =
            new ScanCallback() {
                public void onScanResult(int callbackType, ScanResult result) {
                    mWebView.loadUrl("javascript:"+jsCallbackName+"(null, \""+result.toString()+"\")");
                    System.out.println("found device: " + result.toString());
                };
                public void onScanFailed(int errorCode) {
                    mWebView.loadUrl("javascript:"+jsCallbackName+"("+errorCode+")");
                    System.out.println("scan error: " + errorCode);
                }
            };

}
