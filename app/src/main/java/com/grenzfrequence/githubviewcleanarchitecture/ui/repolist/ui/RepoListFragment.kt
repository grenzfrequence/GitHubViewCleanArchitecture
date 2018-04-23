package com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.grenzfrequence.githubviewcleanarchitecture.R
import com.grenzfrequence.githubviewcleanarchitecture.ui.base.ui.BaseFragment
import com.grenzfrequence.githubviewcleanarchitecture.ui.base.ui.BaseViewModel
import com.grenzfrequence.githubviewcleanarchitecture.ui.common.ui.EndlessRecyclerViewScrollListener
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.AVATAR_PLACE_HOLDER
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.Extensions.hide
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.Extensions.load
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.Extensions.show
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.PAGE_FIRST_POSITION
import kotlinx.android.synthetic.main.repo_list_fragment.*

class RepoListFragment : BaseFragment<RepoListFragmentViewModel>() {

    private val repoListViewModel by lazy { injectViewModel() as RepoListFragmentViewModel }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUserName()
        initRecyclerView()
        initRefresh()
        initUiObserver()
    }

    private fun initUserName() {
        tv_user_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                repoListViewModel.onUserNameChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)

        rv_repo_list.layoutManager = linearLayoutManager
        rv_repo_list.addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                repoListViewModel.onLoadNextPage(page + 1)
            }
        })
    }

    private fun initRefresh() {
        srl_refresh.setOnRefreshListener { repoListViewModel.onLoadNextPage(PAGE_FIRST_POSITION) }
        srl_refresh.isRefreshing = false
    }

    private fun initUiObserver() {
        repoListViewModel.repoListUiModel.observe(this, Observer { repoListUiModel: RepoListUiModel? ->
            repoListUiModel?.let { uiModel: RepoListUiModel ->
                when (uiModel.loadingStatus) {
                    LoadingStatus.FirstPageLoading -> {
                        srl_refresh.isRefreshing = true
                        rv_repo_list.show()
                        ll_place_holder.hide()
                    }
                    is LoadingStatus.SuccessLoading -> {
                        srl_refresh.isRefreshing = false
                        rv_repo_list.show()
                        ll_place_holder.hide()
                        with(uiModel.loadingStatus) {
                            iv_avatar.load(data[0].repoOwner.avatarUrl, AVATAR_PLACE_HOLDER)
                            rv_repo_list.adapter = RepoListAdapter(data) { repoModel ->
                                // TODO("implement repoItem click listener")
                            }
                        }
                    }
                    LoadingStatus.NextPageLoading -> {
                        srl_refresh.isRefreshing = true
                        rv_repo_list.show()
                        ll_place_holder.hide()
                        (rv_repo_list.adapter as RepoListAdapter).showOnLoadMore()
                    }
                    is LoadingStatus.FailedLoading -> {
                        srl_refresh.isRefreshing = false
                        rv_repo_list.hide()
                        iv_avatar.load(placeholder = AVATAR_PLACE_HOLDER)
                        ll_place_holder.show()
                        with(uiModel.loadingStatus.errorState) {
                            iv_place_holder.load(placeholder = icon)
                            tv_error_message.text = context?.resources?.getText(subtitleId) ?: ""
                        }
                        rv_repo_list.adapter = RepoListAdapter(ArrayList(), {})
                    }
                }
            }
        })
    }

    override fun getLayoutId(): Int = R.layout.repo_list_fragment

    protected fun injectViewModel(): BaseViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(RepoListFragmentViewModel::class.java)

}