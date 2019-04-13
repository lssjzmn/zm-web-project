package com.lssj.zmn.server.app.utils.util;


/**
 * Created by dji on 15/12/18.
 */



public class GpsConvertUtils {
    public static final double ONE_METER_OFFSET = 0.00000899322;
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }




    public static boolean checkGpsCoordinate(double latitude, double longitude) {
        return (latitude > -90 && latitude < 90 && longitude > -180 && longitude < 180) && (latitude != 0f && longitude != 0f);
    }

    public static double Radian(double x){
        return  x * Math.PI / 180.0;
    }

    public static double Degree(double x){
        return  x * 180 / Math.PI ;
    }

    public static double cosForDegree(double degree) {
        return Math.cos(degree * Math.PI / 180.0f);
    }

    public static double calcLongitudeOffset(double latitude) {
        return ONE_METER_OFFSET / cosForDegree(latitude);
    }




    public static Double distance(double lat1,double lon1,double lat2,double lon2){
        Double distance=0.0;
        Double disLat=Math.abs(lat1-lat2)/ONE_METER_OFFSET;
        Double disLon=Math.abs(lon1-lon2)/(GpsConvertUtils.calcLongitudeOffset(lat1));
        distance=Math.sqrt(disLat*disLat+disLon*disLon);
        return distance;
    }

    public static double calLonDistance(double orgLon, double lon, double lat) {
        double disLon = 0.0;
        disLon = (lon - orgLon) / calcLongitudeOffset(lat);
        return disLon;
    }

    public static double calLatDistance(double orgLat, double lat) {
        double disLat = 0.0;
        disLat = (lat - orgLat) / ONE_METER_OFFSET;
        return disLat;
    }

    public  static Double calTheta(double lat1,double lon1,double lat2,double lon2){
        Double disLon= (lat2-lat1)/ONE_METER_OFFSET;
        Double disLat= (lon2-lon1)/GpsConvertUtils.calcLongitudeOffset(lat1);
        Double theta=Math.atan(disLon/disLat);
        return theta;
    }

//    public static GpsPoint calOffsetPoint(double offset, double theta, GpsPoint GpsPoint){
//        double deltaLat=(offset*Math.sin(Math.PI*theta/180.0f))* GpsConvertUtils.ONE_METER_OFFSET;
//        double deltaLon=(offset*Math.cos(Math.PI*theta/180.0f))*(GpsConvertUtils.calcLongitudeOffset(GpsPoint.latitude));
//        GpsPoint newPt=new GpsPoint(GpsPoint.latitude+deltaLat,GpsPoint.longitude+deltaLon);
//        return newPt;
//    }
//
//    public static List<GpsPoint> calOffsetPoints(GpsPoint pt1, GpsPoint pt2,double offset){
//        Double theta=calTheta(pt1.latitude, pt1.longitude,pt2.latitude,pt2.longitude);
//        Double theta1=theta+90*Math.PI/180;
//        Double theta2=theta-90*Math.PI/180;
//        GpsPoint offsetPt1=calOffsetPoint(offset,theta1,pt1);
//        GpsPoint offsetPt2=calOffsetPoint(offset,theta1,pt2);
//        GpsPoint offsetOtherPt1=calOffsetPoint(offset,theta2,pt1);
//        GpsPoint offsetOtherPt2=calOffsetPoint(offset,theta2,pt2);
//        List<GpsPoint> ptList=new ArrayList<>();
//        ptList.add(offsetPt1);
//        ptList.add(offsetPt2);
//        ptList.add(offsetOtherPt1);
//        ptList.add(offsetOtherPt2);
//        return ptList;
//    }
//
//    public static GpsPoint calPointOnLine(GpsPoint pt1, double theta,double distance){
//        double deltaLat=distance*Math.sin(theta)* GpsConvertUtils.ONE_METER_OFFSET;
//        double deltaLon=distance*Math.cos(theta)*GpsConvertUtils.calcLongitudeOffset(pt1.latitude);
//        GpsPoint pt=new GpsPoint(pt1.latitude+deltaLat,pt1.longitude+deltaLon);
//        return pt;
//    }
//
//    public static void main(String args[]) {
//        GpsPoint ptA=new GpsPoint(30.7404472557,103.9620996612);
//        GpsPoint ptB=new GpsPoint(30.7404472557,103.9629996612);
//
//    }



}
