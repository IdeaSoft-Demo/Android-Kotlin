package com.famer.sport.ui.coach.fragment.dashboard.workouts

import com.famer.sport.base.BasePresenter
import com.famer.sport.base.Transformer
import com.famer.sport.database.dao.WorkoutDao
import com.famer.sport.server.FamerApi
import com.famer.sport.utils.extensions.getServerErrorMessage

class CoachWorkoutsPresenter :
    BasePresenter<CoachWorkoutsContract.View>(),
    CoachWorkoutsContract.Presenter {

    private val workoutDao by lazy { WorkoutDao() }

    override fun detach() {
        super.detach()
        workoutDao.close()
    }

    override fun getWorkouts(page: Int) {
        if (page == 1) workoutDao.getCoachLibraryWorkouts { view?.showWorkouts(it) }

        addSubscription(
            FamerApi.getInstance().getCoachWorkouts(page)
                .compose(Transformer())
                .subscribe({ response ->
                    if (page == 1) view?.initPagination()
                    workoutDao.insertCoachLibraryWorkouts(page, response.data)
                }, { error ->
                    view?.showErrorMessage(error.getServerErrorMessage())
                })
        )
    }
}