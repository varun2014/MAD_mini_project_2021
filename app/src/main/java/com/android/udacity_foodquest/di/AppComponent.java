package com.android.udacity_foodquest.di;

import com.android.udacity_foodquest.FoodQuestApp;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class})
public interface AppComponent extends AndroidInjector<FoodQuestApp>{
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<FoodQuestApp> {}
}
