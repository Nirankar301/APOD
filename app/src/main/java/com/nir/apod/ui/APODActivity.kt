package com.nir.apod.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.nir.apod.R
import com.nir.apod.isNetworkAvailable
import com.nir.apod.model.APOD
import com.nir.apod.viewmodel.APODViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class APODActivity : AppCompatActivity() {

    private val viewModel: APODViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apod)
        registerObservers()
        viewModel.getAPOD()
    }

    private fun registerObservers() {
        viewModel.apodLiveData.observe(this, registerAPODObserver())
        viewModel.errorLiveData.observe(this, registerErrorObserver())
        viewModel.isLoading.observe(this, {
            findViewById<View>(R.id.loadingView).visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.dateMismatchLiveData.observe(this, {
            if (it && !this.isNetworkAvailable()) {
                Toast.makeText(this, R.string.error_show_last_apod, Toast.LENGTH_LONG).show()
            }
        })
    }

    /**
     * Observer to retrieve updated apod from view model
     */
    private fun registerAPODObserver() = Observer<APOD> { apod ->
        Glide.with(this)
            .asBitmap()
            .load(apod.url)
            .into(findViewById(R.id.icon))
        findViewById<TextView>(R.id.title).text = apod.title
        findViewById<TextView>(R.id.content).text = apod.explanation
    }

    /**
     * Handles error scenarios when no apod found
     */
    private fun registerErrorObserver() = Observer<Boolean> { isError ->
        if (isError) {
            Toast.makeText(this, R.string.error_no_apod, Toast.LENGTH_LONG).show()
        }
    }
}