package einfoplanet.com.ipl.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import einfoplanet.com.ipl.R;

/**
 * InternetConnection is a util class which is used to check the internet connectivity
 * and also show the alert dialog if there isn't a internet connection.
 */

public class InternetConnection {
    public static void noInternetAlertDialog(final Context context){
        new AlertDialog.Builder(context)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("No Internet Connection")
                .setMessage("Please turn on Internet")
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                        context.startActivity(intent);
                    }

                })
                .setNegativeButton("Cancel",null)
                .show();
    }
    //checking the internetConnection
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
