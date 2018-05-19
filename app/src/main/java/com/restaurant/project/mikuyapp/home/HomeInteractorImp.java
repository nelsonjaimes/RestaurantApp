package com.restaurant.project.mikuyapp.home;

import android.content.Context;
import android.support.annotation.NonNull;

import com.restaurant.project.mikuyapp.domain.api.ApiMikuyInterface;
import com.restaurant.project.mikuyapp.domain.api.ApiMikuyManager;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.ListPlateResponseEntity;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.MikuyException;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeInteractorImp implements HomeInteractor {
    private PlatesRepository platesRepository;
    private Call<ListPlateResponseEntity> call;

    HomeInteractorImp(Context context) {
        platesRepository = new PlatesRepositoryImp(context);
    }

    @Override
    public void requestDownLoadPlatesList(final HomePresenter.Callback callback) {
        ApiMikuyInterface apiMikuyInterface = ApiMikuyManager.getInstance();
        call = apiMikuyInterface.requestPlatesList();
        call.enqueue(new Callback<ListPlateResponseEntity>() {
            @Override
            public void onResponse(@NonNull Call<ListPlateResponseEntity> call,
                                   @NonNull Response<ListPlateResponseEntity> response) {
                if (response.isSuccessful()) {
                    ListPlateResponseEntity listPlateResponseEntity = response.body();
                    List<ListPlateResponseEntity.PlateResponseEntity> list =
                            listPlateResponseEntity.getPlateList();
                    platesRepository.savePlatesListSqlLite(list);
                    if (callback != null) callback.onSuccessDownloadPlatesList();
                } else {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        MikuyException mikuyException = MikuyException.parseError(responseBody);
                        if (callback != null) callback.onErrorService(mikuyException.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListPlateResponseEntity> call,
                                  @NonNull Throwable t) {
                if (callback != null) callback.onFailure();
            }
        });
    }

    @Override
    public void onDetach() {
        if (call != null) call.cancel();
        platesRepository.closeConnection();
    }
}
