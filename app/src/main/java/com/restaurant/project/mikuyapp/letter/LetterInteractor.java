package com.restaurant.project.mikuyapp.letter;

import com.restaurant.project.mikuyapp.domain.model.mikuy.request.ReservationRequestEntity;
import com.restaurant.project.mikuyapp.menutoday.MenuTodayPresenter;

public interface LetterInteractor {
    void requestListPlatesDb(LetterPresenter.Callback callback);

    void requestMakeReservation(ReservationRequestEntity entity, LetterPresenter.Callback callback);
}
