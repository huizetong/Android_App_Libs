package com.tonghz.app.util;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.tonghz.android.utils.LogUtils;

/**
 * 百度地图定位工具类
 * Created by TongHuiZe on 2016/8/13.
 */
public class LocationUtil {
    /**
     * 百度地图定位客户端
     */
    private LocationClient mClient;

    /**
     * LocationClientOption对象
     */
    private LocationClientOption mOption;

    /**
     * 经度、纬度
     */
    private static double latitude, longitude;

    /**
     * 城市
     */
    private static String city = "南京市";

    /**
     * 城市编码
     */
    private static String cityCode = "315";

    public LocationUtil(Context context) {
        synchronized (LocationUtil.class) {
            if (mClient == null) {
                mClient = new LocationClient(context);
                // 设置默认LocationClientOption
                mClient.setLocOption(getDefaultOption());
            }
        }
    }

    /**
     * 获取默认LocationClientOption
     *
     * @return 默认LocationClientOption
     */
    private LocationClientOption getDefaultOption() {
        if (mOption == null) {
            mOption = new LocationClientOption();
            mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(3000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true);// 可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(false);// 可选，设置是否需要设备方向结果
            mOption.setLocationNotify(false);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mOption.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mOption.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mOption.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        }
        return mOption;
    }

    /**
     * 注册默认的BDLocationListener
     */
    public void registerLocationListener() {
        if (mClient != null)
            mClient.registerLocationListener(mLocationListener);
    }

    /**
     * 注册BDLocationListener
     *
     * @param listener BDLocationListener对象
     */
    public void registerLocationListener(BDLocationListener listener) {
        if (mClient != null && listener != null)
            mClient.registerLocationListener(listener);
    }

    /**
     * 注销默认的BDLocationListener
     */
    public void unRegisterLocationListener() {
        if (mClient != null)
            mClient.unRegisterLocationListener(mLocationListener);
    }

    /**
     * 注销BDLocationListener
     *
     * @param listener BDLocationListener对象
     */
    public void unRegisterLocationListener(BDLocationListener listener) {
        if (mClient != null && listener != null)
            mClient.unRegisterLocationListener(listener);
    }

    /**
     * 开启定位
     */
    public void onStartLocation() {
        synchronized (LocationUtil.class) {
            if (mClient != null && !mClient.isStarted())
                mClient.start();
        }
    }

    /**
     * 停止定位
     */
    public void onStopLocation() {
        synchronized (LocationUtil.class) {
            if (mClient != null && mClient.isStarted())
                mClient.stop();
        }
    }

    /**
     * 定位结果回调监听
     */
    private BDLocationListener mLocationListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            /*
             * 显示定位信息
			 */
            showLocationMsg(location);

			/*
             * 保存经纬度、城市和城市Code码
			 */
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                setLongitude(location.getLongitude());// 经度
                setLatitude(location.getLatitude());// 纬度
                setCity(location.getCity());// 城市
                setCityCode(location.getCityCode());// 城市编码
            }
        }
    };

    /**
     * 显示定位信息
     *
     * @param location BDLocation对象
     */
    public void showLocationMsg(BDLocation location) {
        if (null != location && location.getLocType() != BDLocation.TypeServerError) {
            StringBuilder sb = new StringBuilder(256);
            sb.append("time : ");
            /*
			 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间； location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
			 */
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            sb.append("\nCountryCode : ");
            sb.append(location.getCountryCode());
            sb.append("\nCountry : ");
            sb.append(location.getCountry());
            sb.append("\ncitycode : ");
            sb.append(location.getCityCode());
            sb.append("\ncity : ");
            sb.append(location.getCity());
            sb.append("\nDistrict : ");
            sb.append(location.getDistrict());
            sb.append("\nStreet : ");
            sb.append(location.getStreet());
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append("\nDescribe: ");
            sb.append(location.getLocationDescribe());
            sb.append("\nDirection(not all devices have value): ");
            sb.append(location.getDirection());
            sb.append("\nPoi: ");
            if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                for (int i = 0; i < location.getPoiList().size(); i++) {
                    Poi poi = location.getPoiList().get(i);
                    sb.append(poi.getName()).append(";");
                }
            }
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：km/h
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                // 运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }

            // 打印输出
            LogUtils.i(LocationUtil.class.getSimpleName(), sb.toString());
        }
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        LocationUtil.city = city;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        LocationUtil.longitude = longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        LocationUtil.latitude = latitude;
    }

    public static String getCityCode() {
        return cityCode;
    }

    public static void setCityCode(String cityCode) {
        LocationUtil.cityCode = cityCode;
    }
}
