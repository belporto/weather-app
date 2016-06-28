package br.com.porto.isabel.weather.mvp.configuration.places;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import br.com.porto.isabel.weather.R;
import br.com.porto.isabel.weather.bus.BusProvider;
import br.com.porto.isabel.weather.model.user.UserCity;
import br.com.porto.isabel.weather.view.adapter.PlaceAutocompleteAdapter;


public class PlacesDialogFragment extends DialogFragment {


    private AppCompatDialog mDialog;

    protected GoogleApiClient mGoogleApiClient;

    private PlaceAutocompleteAdapter mPlaceAdapter;

    private AutoCompleteTextView mAutocompleteView;

    private UserCity mUserCity;

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    public static PlacesDialogFragment newInstance() {
        PlacesDialogFragment frag = new PlacesDialogFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .build();
        mGoogleApiClient.connect();

        mGoogleApiClient.connect();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_custom_view, null, false);

        mAutocompleteView = (AutoCompleteTextView) view.findViewById(R.id.autocomplete_places);
        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
        mAutocompleteView.setAdapter(mPlaceAdapter);

        builder.setTitle(R.string.dialog_title);
        builder.setView(view);
        builder.setPositiveButton(R.string.alert_dialog_ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {


                        if (mUserCity != null) {
                            BusProvider.getInstance().post(mUserCity);
                            dismiss();
                        }
                    }
                }
        )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                );
        mDialog = builder.create();

        return mDialog;


    }


    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                places.release();
                return;
            }

            final Place place = places.get(0);

            String name = place.getName().toString();
            Double lat = place.getLatLng().latitude;
            Double lon = place.getLatLng().longitude;
            String id = place.getId();
            mUserCity = new UserCity(name, lat, lon, id);

            places.release();
        }
    };

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final AutocompletePrediction item = mPlaceAdapter.getItem(position);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    @Override
    public void dismiss() {
        super.dismiss();
        mGoogleApiClient.disconnect();
    }
}
