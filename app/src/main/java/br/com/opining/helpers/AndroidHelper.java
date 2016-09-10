package br.com.opining.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.opining.R;
import br.com.opining.model.Token;

/**
 * Created by Juan on 16/06/2016.
 */
public class AndroidHelper {

    private static final String TOKEN = "token";

    @Nullable
    public static String loadToken(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(TOKEN, null);
    }

    public static void saveToken(Context context, Token token) {
        //TODO PreferenceManager.getDefaultSharedPreferences(context).edit().putString(TOKEN, token.getToken()).apply();
    }

    public static void showSnackbar(Activity activity, String msg) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snackbar.getView();
        //TODO group.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.snack));
        snackbar.show();
    }


    public static void showSnackbar(Activity activity, int msg) {
        showSnackbar(activity, activity.getApplicationContext().getString(msg));
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int msg) {
        showToast(context, context.getString(msg));
    }


}
