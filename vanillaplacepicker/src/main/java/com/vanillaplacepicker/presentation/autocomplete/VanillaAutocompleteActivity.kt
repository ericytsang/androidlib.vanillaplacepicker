package com.vanillaplacepicker.presentation.autocomplete

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.vanillaplacepicker.data.VanillaAddress
import com.vanillaplacepicker.presentation.builder.VanillaConfig
import com.vanillaplacepicker.utils.KeyUtils

class VanillaAutocompleteActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // todo: delete unused resources & dependencies

        // start the autocomplete activity
        startActivityForResult(
            Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY,
                mutableListOf(
                    Place.Field.ADDRESS,
                    Place.Field.LAT_LNG))
                .build(this),
            4)

        // set the default result
        setResult(Activity.RESULT_CANCELED)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && Autocomplete.getStatusFromIntent(data) == com.google.android.gms.common.api.Status.RESULT_SUCCESS) {
            val pickedPlace = Autocomplete.getPlaceFromIntent(data)
            setResult(
                Activity.RESULT_OK,
                Intent().apply()
                {
                    putExtra(
                        KeyUtils.SELECTED_PLACE,
                        VanillaAddress(
                            pickedPlace.address,
                            pickedPlace.name,
                            pickedPlace.id,
                            pickedPlace.latLng?.latitude,
                            pickedPlace.latLng?.longitude,
                            /*todo: get from address components*/null,null,
                            /*todo: get*/null,
                            null,null))
                })
        }
        finish()
    }
}
