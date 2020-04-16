package org.itniorwings.aivita.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import org.itniorwings.aivita.AppConfig;
import org.itniorwings.aivita.api.CmsService;
import org.itniorwings.aivita.api.ContentsApi;
import org.itniorwings.aivita.api.ContentsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ContentsRepository {

    @NonNull
    static ContentsRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final ContentsRepository INSTANCE = new ContentsRepository();
    }


    private ContentsApi contentsApi;

    private ContentsRepository() {
        contentsApi = CmsService.createContentsService(AppConfig.API_URL);
    }

    MutableLiveData<ContentsResponse> getContents(String apiKey) {
        MutableLiveData<ContentsResponse> contents = new MutableLiveData<>();

        contentsApi.getContents(apiKey).enqueue(new Callback<ContentsResponse>() {

            @Override
            public void onResponse(@Nullable Call<ContentsResponse> call, @NonNull Response<ContentsResponse> response) {

                if (response.isSuccessful()) {
                    contents.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@Nullable Call<ContentsResponse> call, @NonNull Throwable t) {
                contents.setValue(null);
            }
        });
        return contents;
    }
}
