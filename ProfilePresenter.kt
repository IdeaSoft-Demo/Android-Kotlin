package com.balloon.poster.presentation.fragment.profile

import com.balloon.poster.base.BasePresenter
import com.balloon.poster.base.Transformer
import com.balloon.poster.exstensions.getServerErrorMessage
import com.balloon.poster.managers.PreferencesManager.getCurrUser
import com.balloon.poster.managers.PreferencesManager.setCurrUser
import com.balloon.poster.server.ApiService

class ProfilePresenter :
    BasePresenter<ProfileContract.View>(),
    ProfileContract.Presenter {

    override fun getProfile() {
        val subscribe = ApiService.getInstance().getUserProfile(getCurrUser()?.id)
            .compose(Transformer())
            .subscribe({
                setCurrUser(it.data)
                view?.showProfile(it.data)
            }, { error ->
                view?.showErrorMessage(error.getServerErrorMessage())
            })
        addSubscription(subscribe)
    }
}