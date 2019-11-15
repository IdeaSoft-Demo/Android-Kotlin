package com.balloon.poster.presentation.fragment.profile

import android.os.Bundle
import android.view.View
import com.balloon.poster.R
import com.balloon.poster.base.BaseMvpFragment
import com.balloon.poster.exstensions.loadPhoto
import com.balloon.poster.exstensions.setSafeOnClickListener
import com.balloon.poster.managers.PreferencesManager.getCurrUser
import com.balloon.poster.database.entity.User
import com.balloon.poster.presentation.activity.edit_profile.EditProfileActivity
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: BaseMvpFragment<ProfileContract.View, ProfileContract.Presenter>(), ProfileContract.View {

    private var user = getCurrUser()

    override fun createPresenter() = ProfilePresenter()

    override fun getLayoutId() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initUserInfo()
    }

    override fun onResume() {
        super.onResume()
        presenter?.getProfile()
    }

    private fun initUI() {
        menuButton.setSafeOnClickListener { EditProfileActivity.startActivity(activity) }
    }

    private fun initUserInfo() {
        loadUserPhoto()
        toolbar.setTitle(user?.name)
    }

    private fun loadUserPhoto() {
        val requestOptions = RequestOptions().placeholder(R.drawable.ic_user_placeholder)
        userPhotoImageView.loadPhoto(user?.avatar?.link, requestOptions)
    }

    override fun showProfile(userInfo: User) {
        user = userInfo
        initUserInfo()
    }

    companion object {
        fun createInstance() = ProfileFragment()
    }
}