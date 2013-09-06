package co.uk.saltais.skegnessstandard;



import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WelcomeDialog extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.dialog_welcome,
				container, false);
		
		return layout;
	}
	
	public void ensureList() {
	}
}