package a123.vaidya.nihal.foodcrunchserver.Common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import a123.vaidya.nihal.foodcrunchserver.Model.Request;
import a123.vaidya.nihal.foodcrunchserver.Model.User;
import a123.vaidya.nihal.foodcrunchserver.Remote.APIService;
import a123.vaidya.nihal.foodcrunchserver.Remote.FCMRetrofitClient;
import a123.vaidya.nihal.foodcrunchserver.Remote.RetrofitClient;
import a123.vaidya.nihal.foodcrunchserver.Remote.iGeoCoordinates;

/**
 * Created by nnnn on 26/12/2017.
 */

public class Common {
    public static User currentUser;
    public static Request currentRequest;
    public static String topicName = "News";
    public static String PHONE_TEXT = "userPhone";
    public static final String UPDATE = "UPDATE";
    public static final String DIRECTIONS = "DIRECTIONS";
    public static final String DELETE = "DELETE";
    public static final String DETAILS = "DETAILS";
    //update and delete master witch
    public static String convertCodeToStatus(String code)
    {
        switch (code) {
            case "0":
                return "Placed";
            case "1":
                return "Your food is on the way ";
            default:
                return "Shipped!!";
        }
    }
    public static String convertstaffToStatus(String code)
    {
        switch (code) {
            case "0":
                return "Placed";
            case "1":
                return "Your food is on the way ";
            default:
                return "Shipped!!";
        }
    }

    public static final String baseUrl = "https://maps.googleapis.com";

    public static final String fcmUrl = "https://fcm.googleapis.com";

    public static APIService getFCMClient()
    {
        return FCMRetrofitClient.getClient(fcmUrl).create(APIService.class);
    }

    public static iGeoCoordinates getGeoCodeService()
    {
        return RetrofitClient.getClient(baseUrl).create(iGeoCoordinates.class);
    }


    public static final int PICK_IMAGE_REQUEST= 71;
    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";
    public static Bitmap scaleBitmap (Bitmap bitmap,int newWidth,int newHeight) {
        Bitmap scaleBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        float scaleX = newWidth/(float)bitmap.getWidth();
        float scaleY = newHeight/(float)bitmap.getHeight();
        float pivotX=0,pivotY=0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX,scaleY,pivotX,pivotY);

        Canvas canvas = new Canvas(scaleBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap,0,0,new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaleBitmap;


    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if(info != null)
            {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED)
                        return true;

                }
            }
        }

        return false;
    }

}
