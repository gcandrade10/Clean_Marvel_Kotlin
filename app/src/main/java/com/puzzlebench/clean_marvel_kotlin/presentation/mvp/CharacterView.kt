package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import androidx.recyclerview.widget.GridLayoutManager
import android.view.View
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.presentation.DetailActivity
import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.adapter.CharacterAdapter
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.showToast
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference
import com.jakewharton.rxbinding3.view.clicks

class CharacterView(activity: MainActivity) {
    private val activityRef = WeakReference(activity)
    private val SPAN_COUNT = 1
    private var adapter = CharacterAdapter {
        character -> activity.startActivity(DetailActivity.getIntent(activity,character)) }

    lateinit var viewEventObservable: Observable<EventTypes>



    fun init() {
        val activity = activityRef.get()
        if (activity != null) {
            activity.recycleView.layoutManager = GridLayoutManager(activity, SPAN_COUNT)
            activity.recycleView.adapter = adapter
            showLoading()
            viewEventObservable = activity.fab.clicks().map<EventTypes> { Refresh }
        }

    }

    fun showToastNoItemToShow() {
        val activity = activityRef.get()
        if (activity != null) {
            val message = activity.baseContext.resources.getString(R.string.message_no_items_to_show)
            activity.applicationContext.showToast(message)

        }
    }

    fun showToastNetworkError(error: String) {
        activityRef.get()!!.applicationContext.showToast(error)
    }

    fun hideLoading() {
        activityRef.get()!!.progressBar.visibility = View.GONE
    }

    fun showCharacters(characters: List<Character>) {
        adapter.data = characters
    }

    fun showLoading() {
        activityRef.get()!!.progressBar.visibility = View.VISIBLE

    }
}
