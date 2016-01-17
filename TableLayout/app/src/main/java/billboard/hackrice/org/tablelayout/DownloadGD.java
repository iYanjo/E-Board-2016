package billboard.hackrice.org.tablelayout;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.OpenFileActivityBuilder;

/*www.101apps.co.za*/
public class DownloadGD extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "drive";
    private static final int REQUEST_CODE_SELECT = 102;
    private static final int REQUEST_CODE_RESOLUTION = 103;
    private GoogleApiClient googleApiClient;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        you can check if play services installed and up to date - returns integer value
        Log.i(TAG, "Is Google Play Services available and up to date? "
                + GooglePlayServicesUtil.isGooglePlayServicesAvailable(this));

        buildGoogleApiClient();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /*connect client to Google Play Services*/
    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Log.i(TAG, "In onStart() - connecting...");
        googleApiClient.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DownloadGD Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://billboard.hackrice.org.tablelayout/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    /*close connection to Google Play Services*/
    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DownloadGD Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://billboard.hackrice.org.tablelayout/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        if (googleApiClient != null) {
            Log.i(TAG, "In onStop() - disConnecting...");
            googleApiClient.disconnect();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    /*Connection callback - on successful connection*/
    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "in onConnected() - we're connected, let's do the work in the background...");
//        build an intent that we'll use to start the open file activity
        IntentSender intentSender = Drive.DriveApi
                .newOpenFileActivityBuilder()
//                these mimetypes enable these folders/files types to be selected
                .setMimeType(new String[]{DriveFolder.MIME_TYPE, "text/plain", "image/png"})
                .build(googleApiClient);
        try {
            startIntentSenderForResult(
                    intentSender, REQUEST_CODE_SELECT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            Log.i(TAG, "Unable to send intent", e);
        }
    }

    /*Connection callback - Called when the client is temporarily in a disconnected state*/
    @Override
    public void onConnectionSuspended(int i) {
        switch (i) {
            case 1:
                Log.i(TAG, "Connection suspended - Cause: " + "Service disconnected");
                break;
            case 2:
                Log.i(TAG, "Connection suspended - Cause: " + "Connection lost");
                break;
            default:
                Log.i(TAG, "Connection suspended - Cause: " + "Unknown");
                break;
        }
    }

    /*connection failed callback - Called when there was an error connecting the client to the service*/
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed - result: " + result.toString());
        if (!result.hasResolution()) {
//            display error dialog
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }

        try {
            Log.i(TAG, "trying to resolve the Connection failed error...");
//            tries to resolve the connection failure by trying to restart this activity
            result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
        } catch (IntentSender.SendIntentException e) {
            Log.i(TAG, "Exception while starting resolution activity", e);
        }
    }

    /*build the google api client*/
    private void buildGoogleApiClient() {
        Log.i(TAG, "Building the client");
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }

    /* receives returned result - called by the open file activity when it's exited
    by user pressing Select. This passes the request code, result code and data back
    which is received here*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "in onActivityResult() - triggered on pressing Select");
        switch (requestCode) {
            case REQUEST_CODE_SELECT:
                if (resultCode == RESULT_OK) {
                    /*get the selected item's ID*/
                    DriveId driveId = (DriveId) data.getParcelableExtra(
                            OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);//this extra contains the drive id of the selected file
                    Log.i(TAG, "Selected folder's ID: " + driveId.encodeToString());
                    Log.i(TAG, "Selected folder's Resource ID: " + driveId.getResourceId());

//                    selected file (can also be a folder)
                    DriveFile selectedFile = Drive.DriveApi.getFile(googleApiClient, driveId);
                    PendingResult selectedFileMetadata = selectedFile.getMetadata(googleApiClient);

//                    fetch the selected item's metadata asynchronously using a pending result
                    selectedFileMetadata.setResultCallback(new ResultCallback() {
                        @Override
                        public void onResult(DriveResource.MetadataResult metadataResult) {
//                            get the metadata out of the result
                            Metadata fileMetadata = metadataResult.getMetadata();
//                            get the details out of the metadata object
                            Log.i(TAG, "File title: " + fileMetadata.getTitle());
                            Log.i(TAG, "File size: " + fileMetadata.getFileSize());
                            Log.i(TAG, "File mime type: " + fileMetadata.getMimeType());
                        }
                    });
                }
                finish();
                break;
            case REQUEST_CODE_RESOLUTION:
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "in onActivityResult() - resolving connection, connecting...");
                    googleApiClient.connect();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}