/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.energyhelmet.energyhelmetc;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.BeanManager;
import com.punchthrough.bean.sdk.message.Acceleration;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.Callback;
import com.punchthrough.bean.sdk.message.DeviceInfo;
import com.punchthrough.bean.sdk.message.ScratchBank;

import org.apache.cordova.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends CordovaActivity {

    public class BeansData {
        double x, y, z;
        boolean haveData;

        public BeansData(double x, double y, double z, boolean haveData) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.haveData = haveData;
        }
    }

    public class BeansInterfaceClass {
        Context context;

        public BeansInterfaceClass(Context c) {
            this.context = c;
        }

        @JavascriptInterface
        public double getBeansX() {
            return data.x;
        }

        @JavascriptInterface
        public double getBeansY() {
            return data.y;
        }

        @JavascriptInterface
        public double getBeansZ() {
            return data.z;
        }

        @JavascriptInterface
        public boolean getBeansHaveData() {
            return data.haveData;
        }
    }

    final List<Bean> beans = new ArrayList<>();
    final Context self = this;

    BeansData data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set by <content src="index.html" /> in config.xml
        loadUrl(launchUrl);

        data = new BeansData(0.0, 0.0, 0.0, false);

        BeansInterfaceClass bic = new BeansInterfaceClass(self);

        WebView wv = (WebView) appView.getEngine().getView();
        wv.addJavascriptInterface(bic, "beansInterface");

        BeanDiscoveryListener listener = new BeanDiscoveryListener() {
            @Override
            public void onBeanDiscovered(Bean bean, int rssi) {
                beans.add(bean);
            }

            @Override
            public void onDiscoveryComplete() {
                for (final Bean bean : beans) {
                    Log.w(TAG, bean.getDevice().getName());       // "Bean"              (example)
                    Log.w(TAG, bean.getDevice().getAddress());    // "B4:99:4C:1E:BC:75" (example)

                    BeanListener beanListener = new BeanListener() {
                        @Override
                        public void onConnected() {
                            Log.i(TAG, "connected to Bean!");
                            bean.readDeviceInfo(new Callback<DeviceInfo>() {
                                @Override
                                public void onResult(DeviceInfo deviceInfo) {
                                    Log.w(TAG, deviceInfo.hardwareVersion());
                                    Log.w(TAG, deviceInfo.firmwareVersion());
                                    Log.w(TAG, deviceInfo.softwareVersion());
                                }
                            });

                            new Timer().scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    bean.readAcceleration(new Callback<Acceleration>() {
                                        @Override
                                        public void onResult(Acceleration result) {
                                            data.x = result.x();
                                            data.y = result.y();
                                            data.z = result.z();
                                            data.haveData = true;
                                            //Log.w(TAG, "onResult: " + data.x + " | " + data.y + " | " + data.z);
                                        }
                                    });
                                }
                            }, 0, 250);

                        }

                        @Override
                        public void onConnectionFailed() {

                        }

                        @Override
                        public void onDisconnected() {
                            Log.w(TAG, "Bean disconnected!");
                        }

                        @Override
                        public void onSerialMessageReceived(byte[] data) {

                        }

                        @Override
                        public void onScratchValueChanged(ScratchBank bank, byte[] value) {

                        }

                        @Override
                        public void onError(BeanError error) {

                        }

                        @Override
                        public void onReadRemoteRssi(int rssi) {

                        }
                    };

                    bean.connect(self, beanListener);
                }
            }
        };

        BeanManager.getInstance().startDiscovery(listener);
    }
}
