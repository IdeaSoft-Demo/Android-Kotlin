package com.famer.sport.ui.coach.fragment.dashboard.workouts

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.famer.sport.Constants.PER_PAGE
import com.famer.sport.R
import com.famer.sport.base.BaseMvpFragment
import com.famer.sport.database.entity.Workout
import com.famer.sport.ui.coach.fragment.workout.CoachWorkoutFragment
import com.famer.sport.utils.extensions.showOrHide
import com.famer.sport.widget.ScrollListener
import kotlinx.android.synthetic.main.fragment_coach_workouts.*

class CoachWorkoutsFragment :
    BaseMvpFragment<CoachWorkoutsContract.View, CoachWorkoutsContract.Presenter>(),
    CoachWorkoutsContract.View {

    private val coachWorkoutsAdapter by lazy { WorkoutsAdapter() }

    override fun createPresenter() = CoachWorkoutsPresenter()

    override fun getLayoutId() = R.layout.fragment_coach_workouts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        presenter?.getWorkouts(1)
    }

    private fun initRecyclerView() {
        coachWorkoutsAdapter.onWorkoutClick = {
            parentActivity?.switchFragment(CoachWorkoutFragment.createInstance(it))
        }
        coachWorkoutsRecyclerView.adapter = coachWorkoutsAdapter
    }

    override fun initPagination() {
        val layoutManager = coachWorkoutsRecyclerView.layoutManager as LinearLayoutManager
        coachWorkoutsRecyclerView.addOnScrollListener(
            object : ScrollListener(layoutManager, PER_PAGE) {
                override fun onLoadMore(page: Int) {
                    coachWorkoutsAdapter.addProgress()
                    presenter?.getWorkouts(page)
                }
            }
        )
    }

    override fun showWorkouts(workouts: List<Workout>) {
        noWorkoutsTextView.showOrHide(workouts.isEmpty())
        coachWorkoutsAdapter.removeProgress()
        coachWorkoutsAdapter.init(workouts)
    }

    companion object {
        fun createInstance() = CoachWorkoutsFragment()
    }
}
