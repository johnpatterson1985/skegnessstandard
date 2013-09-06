package co.uk.saltais.skegnessstandard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import co.uk.saltais.skegnessstandard.R;

public class TosDialogTrigger extends Activity{
    private static final String TAG = "What's New Dialog Opened";
    
    private final Context context;
    private String lastVersion, thisVersion;

    //  VERSION_KEY : storing the versionName in SharedPreferences
    private static final String VERSION_KEY = "PREFS_VERSION_KEY";
    
    
    public TosDialogTrigger(Context context) {
        this(context, PreferenceManager.getDefaultSharedPreferences(context));
    }
        
    
    public TosDialogTrigger(Context context, SharedPreferences sp) {
        this.context = context;

        // get version versionCode
        this.lastVersion = sp.getString(VERSION_KEY, "");
        Log.d(TAG, "lastVersion: " + lastVersion);
        try {
			this.thisVersion = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			this.thisVersion = "?";
			Log.e(TAG, "error reading version name from AndroidManifest file");
			e.printStackTrace();
		}
        Log.d(TAG, "appVersion: " + this.thisVersion);
        
        // saving new versionCode to prefs
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(VERSION_KEY, this.thisVersion);
        editor.commit();
    }
    
    
    public String getLastVersion() {
    	return  this.lastVersion;
    }
    
    
    public String getThisVersion() {
    	return  this.thisVersion;
    }

    
    public boolean firstRun() {
        return  ! this.lastVersion.equals(this.thisVersion);
    }

    
    public boolean firstRunEver() {
        return  "".equals(this.lastVersion);
    }

    
    public AlertDialog getLogDialog() {
        return  this.getDialog(false);
    }

    
    public AlertDialog getFullLogDialog() {
        return  this.getDialog(true);
    }
    
    private AlertDialog getDialog(boolean full) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view               = inflater.inflate(R.layout.dialog_tos, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        
        builder.setView(view)
        //.setTitle(R.string.changelog_full_title)
        //.setIcon(R.drawable.ic_launcher)
        .setCancelable(false)
        
        
        .setNegativeButton(R.string.button_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return  builder.create();   
    }

    
    void setLastVersion(String lastVersion) {
    	this.lastVersion = lastVersion;
    }
}