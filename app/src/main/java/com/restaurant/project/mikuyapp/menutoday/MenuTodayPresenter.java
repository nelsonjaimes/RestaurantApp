package com.restaurant.project.mikuyapp.menutoday;

import com.restaurant.project.mikuyapp.domain.model.mikuy.response.ReservationResponseEntity;
import com.restaurant.project.mikuyapp.menutoday.model.Plate;

import java.util.List;

public interface MenuTodayPresenter {

    void onAttach(MenuTodayView menuTodayView);

    void onDetach();

    void startLoadingPlates();

    void makeReservation(List<Plate> plateList);

    interface Callback {
        void onSuccessListPlate(List<Plate> list);

        void onSuccessMakeReservation(ReservationResponseEntity entity);

        void onError(String message);

        void onFailure();
    }
}
