package com.githubsearcher.data.source.remote

import com.githubsearcher.data.UsersDetail
import com.githubsearcher.data.source.SearchLikeDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchLikeRemoteDataSource(
    private val retrofit: SearchLikeRemoteService
) : SearchLikeDataSource {

    override fun searchUserInfo(
        userID: String,
        onSuccess: (List<UsersDetail>) -> Unit,
        onFail: (Throwable) -> Unit
    ): Disposable = retrofit.getUserInfo(userID)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            val users = it.body()
            if (it.isSuccessful && users != null) {
                val usersInfo = users.items
                onSuccess(usersInfo)
            } else {
                onFail(IllegalStateException("Data is null"))
            }
        }, {
            onFail(it)
        })
}