package com.nir.apod.data

import android.content.Context
import android.net.Uri
import androidx.room.Room
import com.google.gson.Gson
import com.nir.apod.database.APODDatabase
import com.nir.apod.database.APODEntity
import com.nir.apod.model.APOD
import com.nir.apod.util.API_KEY
import com.nir.apod.util.BASE_URL
import com.nir.apod.util.DB_NAME
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

class APODRepository(private val context: Context) : IAPODRepository{

    override suspend fun getAPOD(): APOD? {
        // URL end point creation for apod
        val url = Uri.parse(BASE_URL).buildUpon()
            .appendQueryParameter("api_key", API_KEY)
            .build()
            .toString()
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        // Room db initialization
        val db = Room.databaseBuilder(
            context,
            APODDatabase::class.java, DB_NAME
        ).build()

        runCatching { client.newCall(request).execute() }
            .map { response ->
                return if (response.isSuccessful) {
                    val resp = response.body?.string()
                    val apod = Gson().fromJson(resp, APOD::class.java)
                    // Store the apod inside Room db
                    db.apodDao().insert(APODEntity(apod.date, apod.title, apod.explanation, apod.url))
                    apod
                } else {
                    if(db.apodDao().getAll().isEmpty()) throw Exception("No APOD found")
                    // Retrieves the last stored apod from database
                    val lastEntry = db.apodDao().getAll().last()
                    APOD(
                        date = lastEntry.date,
                        title = lastEntry.title,
                        explanation = lastEntry.explanation,
                        url = lastEntry.url
                    )
                }
            }
            .getOrElse {
                if(db.apodDao().getAll().isEmpty()) throw Exception("No APOD found")
                // Retrieves the last stored apod from database
                val lastEntry = db.apodDao().getAll().last()
                return APOD(
                    date = lastEntry.date,
                    title = lastEntry.title,
                    explanation = lastEntry.explanation,
                    url = lastEntry.url
                )
            }
    }
}