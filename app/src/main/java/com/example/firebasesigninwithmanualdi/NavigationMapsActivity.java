package com.example.firebasesigninwithmanualdi;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.maps.GoogleMap.CameraPerspective;
import com.google.android.libraries.navigation.ListenableResultFuture;
import com.google.android.libraries.navigation.NavigationApi;
import com.google.android.libraries.navigation.Navigator;
import com.google.android.libraries.navigation.RoutingOptions;
import com.google.android.libraries.navigation.SupportNavigationFragment;
import com.google.android.libraries.navigation.Waypoint;

public class NavigationMapsActivity extends AppCompatActivity {

    private static final String TAG = NavigationMapsActivity.class.getSimpleName();
    private Navigator mNavigator;
    private SupportNavigationFragment mNavFragment;
    private RoutingOptions mRoutingOptions;

    private static String DESTINATION_PLACE_ID = "ChIJ3S-JXmauEmsRUcIaWtf4MzE";

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_navigation);

        DESTINATION_PLACE_ID = getIntent().getStringExtra("destPlaceId");
        // Initialize the Navigation SDK.
        initializeNavigationSdk();
    }

    private void initializeNavigationSdk() {
        if (ContextCompat.checkSelfPermission(
                this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (!mLocationPermissionGranted) {
            displayMessage(
                    "Error loading Navigation SDK: " + "The user has not granted location permission.");
            return;
        }

        // Get a navigator.
        NavigationApi.getNavigator(
                this,
                new NavigationApi.NavigatorListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onNavigatorReady(Navigator navigator) {
                        displayMessage("Navigator ready.");
                        mNavigator = navigator;
                        mNavFragment =
                                (SupportNavigationFragment)
                                        getSupportFragmentManager().findFragmentById(R.id.main_map_fragment);

                        mNavFragment.getMapAsync(
                                googleMap -> googleMap.followMyLocation(CameraPerspective.TILTED));

                        mRoutingOptions = new RoutingOptions();
                        mRoutingOptions.travelMode(RoutingOptions.TravelMode.DRIVING);

                        navigateToPlace(DESTINATION_PLACE_ID, mRoutingOptions);
                    }
                    @Override
                    public void onError(@NavigationApi.ErrorCode int errorCode) {
                        switch (errorCode) {
                            case NavigationApi.ErrorCode.NOT_AUTHORIZED:
                                displayMessage(
                                        "Error loading Navigation SDK: Your API key is "
                                                + "invalid or not authorized to use the Navigation SDK.");
                                break;
                            case NavigationApi.ErrorCode.TERMS_NOT_ACCEPTED:
                                displayMessage(
                                        "Error loading Navigation SDK: User did not accept "
                                                + "the Navigation Terms of Use.");
                                break;
                            case NavigationApi.ErrorCode.NETWORK_ERROR:
                                displayMessage("Error loading Navigation SDK: Network error.");
                                break;
                            case NavigationApi.ErrorCode.LOCATION_PERMISSION_MISSING:
                                displayMessage(
                                        "Error loading Navigation SDK: Location permission " + "is missing.");
                                break;
                            default:
                                displayMessage("Error loading Navigation SDK: " + errorCode);
                        }
                    }
                });
    }
    private void navigateToPlace(String placeId, RoutingOptions travelMode) {
        Waypoint destination;
        try {
            destination = Waypoint.builder().setPlaceIdString(placeId).build();
        } catch (Waypoint.UnsupportedPlaceIdException e) {
            displayMessage("Error starting navigation: Place ID is not supported.");
            return;
        }
        ListenableResultFuture<Navigator.RouteStatus> pendingRoute =
                mNavigator.setDestination(destination, travelMode);

        pendingRoute.setOnResultListener(
                code -> {
                    switch (code) {
                        case OK:
                            if (getActionBar() != null) {
                                getActionBar().hide();
                            }
                            mNavigator.setAudioGuidance(Navigator.AudioGuidance.VOICE_ALERTS_AND_GUIDANCE);

//                                // Simulate vehicle progress along the route for demo/debug builds.
//                                if (BuildConfig.DEBUG) {
//                                    mNavigator
//                                            .getSimulator()
//                                            .simulateLocationsAlongExistingRoute(
//                                                    new SimulationOptions().speedMultiplier(5));
//                                }
                            mNavigator.startGuidance();
                            break;

                        case NO_ROUTE_FOUND:
                            displayMessage("Error starting navigation: No route found.");
                            break;
                        case NETWORK_ERROR:
                            displayMessage("Error starting navigation: Network error.");
                            break;
                        case ROUTE_CANCELED:
                            displayMessage("Error starting navigation: Route canceled.");
                            break;
                        default:
                            displayMessage("Error starting navigation: " + String.valueOf(code));
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is canceled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
    }

    private void displayMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        Log.d(TAG, errorMessage);
    }
}