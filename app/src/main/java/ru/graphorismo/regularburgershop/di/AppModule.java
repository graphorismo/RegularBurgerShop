package ru.graphorismo.regularburgershop.di;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import ru.graphorismo.regularburgershop.data.local.ILocalDataRepository;
import ru.graphorismo.regularburgershop.data.local.LocalDataRepository;
import ru.graphorismo.regularburgershop.data.local.room.ProductCartRoomDatabase;
import ru.graphorismo.regularburgershop.data.remote.IRemoteDataRepository;
import ru.graphorismo.regularburgershop.data.remote.RemoteDataRepository;
import ru.graphorismo.regularburgershop.data.remote.retrofit.IBurgershopApi;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    IRemoteDataRepository provideIRemoteDataRepository(RemoteDataRepository remoteDataRepository){
        return remoteDataRepository;
    }

    @Provides
    Retrofit providesRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8100/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Provides
    IBurgershopApi providesIBurgershopApi(Retrofit retrofit){
        return retrofit.create(IBurgershopApi.class);
    }

    @Provides
    ILocalDataRepository provideILocalDatabaseRepository(LocalDataRepository localDataRepository){
        return localDataRepository;
    }

    @Provides
    ProductCartRoomDatabase providesProductCartRoomDatabase(@ApplicationContext Context context){
        return Room.databaseBuilder(context,
                ProductCartRoomDatabase.class, "productCartRoomDatabase").build();
    }
}
