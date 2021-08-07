package com.android.udacity_foodquest.di;

import android.app.Application;
import androidx.room.Room;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.udacity_foodquest.BuildConfig;
import com.android.udacity_foodquest.FoodQuestApp;
import com.android.udacity_foodquest.data.local.FoodQuestDatabase;
import com.android.udacity_foodquest.data.local.dao.FavRestaurantDao;
import com.android.udacity_foodquest.data.remote.ApiService;
import com.android.udacity_foodquest.data.remote.RequestInterceptor;
import com.android.udacity_foodquest.util.Constants;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {ViewModelModule.class})
class AppModule {

    @Provides
    @Singleton
    Application provideContext(FoodQuestApp application) {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            okHttpClient.addNetworkInterceptor(new StethoInterceptor());
            okHttpClient.addInterceptor(httpLoggingInterceptor);
        }
        okHttpClient.addInterceptor(new RequestInterceptor());
        return okHttpClient.build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    FoodQuestDatabase provideFoodQuestDatabase(Application application) {
        return Room.databaseBuilder(application, FoodQuestDatabase.class, "foodquest.db").build();
    }

    @Provides
    @Singleton
    FavRestaurantDao provideFavRestaurantDao(FoodQuestDatabase database) {
        return database.favRestaurantDao();
    }
}
