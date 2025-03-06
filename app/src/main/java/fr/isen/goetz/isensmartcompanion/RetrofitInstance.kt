package fr.isen.goetz.isensmartcompanion

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.GET

//Singleton object to manage Retrofit instance
object RetrofitInstance {
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app/") //URL
            .addConverterFactory(GsonConverterFactory.create()) //Gson converter for JSON parsing
            .build()
    }

    //RetrofitService interface instance for making the API calls
    val retrofitService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }
}

interface RetrofitService {
    @GET("events.json") //My base URL and path for fetching events
    fun getEvents(): Call<List<Event>> //Will return a list of events
}
