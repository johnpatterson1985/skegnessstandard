package co.uk.saltais.skegnessstandard;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends Activity {
    

		
		WebView web;
        ProgressDialog mProgress;
		private WebView webView;
        

        @Override
        public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
            this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        	//this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        	setContentView(R.layout.web_view);

            // Get ActionBar if min SDK 11
            //ActionBar actionBar = getActionBar();
            //actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayShowTitleEnabled(true);

            // Get & Show Loading progress bar
        	getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        	web = new WebView(this);
        	web = (WebView) findViewById(R.id.webview);
        	web.getSettings().setSupportZoom(true); 
            web.getSettings().setBuiltInZoomControls(true); //Enable Multitouch if supported by ROM
		    WebSettings settings = web.getSettings();
        	settings.setJavaScriptEnabled(true);
        	web.getSettings().setDefaultFontSize(16);
        	//web.getSettings().setPluginsEnabled(true);
            web.getSettings().setAllowFileAccess(true);
            
        	// the init state of progress dialog
        	mProgress = ProgressDialog.show(this, "Connecting to Skegness...", "Getting latest news stories...");
        	
        	
        // add a WebViewClient for WebView, which actually handles loading data from web
        web.setWebViewClient(new WebViewClient() {
                // load url
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                		view.loadUrl(url);
                        return true;
                }

                // when finish loading page
                public void onPageFinished(WebView view, String url) {
                        if(mProgress.isShowing()) {
                                mProgress.dismiss();
                        }
                }
                
                public void onReceivedError(WebView view, int errorCode,
                		String description, String failingUrl) {
                		
                		Toast.makeText(WebViewActivity.this,
                		"oops! Check data connection or try refreshing the page." 
                		//+ description + "\n" 
                		//+ failingUrl
                		, Toast.LENGTH_LONG).show();
                }
        });
        // set url for webview to load
        web.loadUrl("http://m.skegnessstandard.co.uk");
        
        final Activity WebViewActivity = this;
        web.setWebChromeClient(new WebChromeClient() {
        	public void onProgressChanged(WebView view, int progress)
        	{
        		WebViewActivity.setTitle("Loading...");
        		WebViewActivity.setProgress(progress * 100);
        		
        		if (progress == 100)
        			WebViewActivity.setTitle(R.string.blank_string);
        	}
        	
        });
        
        
    } // end onCreate
    
    //Handle "Back" key when pressed to go back to previous page/screen.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
			web.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Show changelog if it is new
		WelcomeDialogTrigger cl = new WelcomeDialogTrigger(this);
		if (cl.firstRun())
			cl.getWelcomeDialog().show();
		
	}
	
	
	
	
	private void sharePage() {
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_TEXT, web.getUrl() + "\n\n" + "Shared via Skegness Standard for Android");
		share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		startActivity(Intent.createChooser(share, "Share with..."));
	}
	
	
	// Menu //////////////////////////////////////////////////////
		@Override
		public boolean onCreateOptionsMenu(Menu menu)
		{
	    	MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.menu_main, menu);
			return true;
		}
		@Override
		public boolean onOptionsItemSelected(MenuItem item)
		{
			switch (item.getItemId()) {


            case R.id.menu_refresh_page:
                web.loadUrl( "javascript:window.location.reload( true )" );
                Toast.makeText(getApplicationContext(), R.string.toast_refresh, Toast.LENGTH_SHORT).show();
            return true;

			case R.id.menu_share:
				sharePage();
	        return true;





			case R.id.menu_gohome:
				web.loadUrl( "http://m.skegnessstandard.co.uk" );
	            return true;
	            
			case R.id.menu_news:
				web.loadUrl( "http://m.skegnessstandard.co.uk/news" );
	            return true;
	            
			case R.id.menu_sport:
				web.loadUrl( "http://m.skegnessstandard.co.uk/sport" );
	            return true;
	            
			case R.id.menu_lifestyle:
				web.loadUrl( "http://m.skegnessstandard.co.uk/lifestyle" );
	            return true;
	            
			case R.id.menu_community:
				web.loadUrl( "http://m.skegnessstandard.co.uk/community" );
	            return true;
	            
			/////More items/////
	        case R.id.menu_more:
				return true;
	            
			case R.id.menu_login:
				web.loadUrl( "http://m.skegnessstandard.co.uk/login" );
	            return true;
	            
			case R.id.menu_advertise:
				web.loadUrl( "http://m.skegnessstandard.co.uk/advertise-with-us" );
	            return true;


	        
			

	            
			case R.id.menu_email : //Feedback
					Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
					String aEmailList[] = { getResources()
							.getString(R.string.email_address) };
					String aEmailCCList[] = { getResources().getString(
							R.string.email_address_cc) };
					//String aEmailBCCList[] = { getResources().getString(
					//		R.string.email_address_bcc) };
					emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
					emailIntent.putExtra(android.content.Intent.EXTRA_CC, aEmailCCList);
					//emailIntent.putExtra(android.content.Intent.EXTRA_BCC, aEmailBCCList);
					emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
							 "Feedback - "
							+ getResources().getString(R.string.app_name)
							+ " build "
							+ getResources().getString(R.string.versionName));
					
					emailIntent.setType("plain/text");
					emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
							getResources().getString(R.string.email_message)
							+ " Device details - "
							+ android.os.Build.MANUFACTURER
							+ ", "
							+ android.os.Build.MODEL
							+ " ("
							+ android.os.Build.PRODUCT
							+ ") ");
					startActivity(emailIntent);
				return true;
				
			case R.id.menu_tos:
				super.onResume();
				// Show Terms and Policys
				TosDialogTrigger cl = new TosDialogTrigger(this);
				cl.getLogDialog().show();
	            return true;
				///////////////
			//////////////////

                case R.id.menu_welcome:
                    super.onResume();
                    // Show Terms and Policys
                    WelcomeDialogTrigger wd = new WelcomeDialogTrigger(this);
                    if (wd.firstRun())
                        wd.getWelcomeDialog().show();
                    else
                        wd.getWelcomeDialog().show();
                    return true;

			case R.id.menu_exit : // Exit App
				finish();
	          	return true;
	          	  
				default:
					NavUtils.navigateUpFromSameTask(this);
					return super.onOptionsItemSelected(item);
					
			} // Ens switch
		}// End Menu ///////////////////////////////////
	
	
}