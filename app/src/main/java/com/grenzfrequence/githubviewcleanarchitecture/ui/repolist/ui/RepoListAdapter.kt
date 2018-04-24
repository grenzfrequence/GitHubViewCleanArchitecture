package com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.grenzfrequence.githubviewcleanarchitecture.R
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.RepoModel
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.Extensions.hide
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.Extensions.inflate
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.Extensions.show
import kotlinx.android.synthetic.main.repo_list_item.view.*
import org.joda.time.DateTime

class RepoListAdapter(
        val repoList: RepoList = ArrayList(),
        val onClickListener: (selectedRepo: RepoModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_ITEM_VIEW_HOLDER = 1
    private val TYPE_PROGRESS_BAR_VIEW_HOLDER = 2

    private var showProgressBar: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            if (viewType == TYPE_ITEM_VIEW_HOLDER) {
                ItemViewHolder(parent.inflate(R.layout.repo_list_item), onClickListener)
            } else {
                ProgressBarViewHolder(parent.inflate(R.layout.progress_bar))
            }

    override fun getItemCount(): Int = with(repoList) {
        if (size == 0) 0 else size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            holder is ItemViewHolder -> holder.bind(repoList[position])
            holder is ProgressBarViewHolder -> holder.setVisibility(showProgressBar)
        }
    }

    override fun getItemViewType(position: Int): Int =
            if (position != 0 && position == itemCount - 1) TYPE_PROGRESS_BAR_VIEW_HOLDER else TYPE_ITEM_VIEW_HOLDER

    override fun getItemId(position: Int): Long =
            if (position != 0 && position == itemCount - 1) -1 else repoList[position].hashCode().toLong()

    fun showOnLoadMore() {
        showProgressBar = true
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View, private val onClickListener: (selectedRepo: RepoModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(repoModel: RepoModel) {
            with(itemView) {
                tv_repo_name.text = repoModel.repoName
                tv_repo_updated_at.text = DateTime(repoModel.repoUpdatedAt).toString("dd.MM.yyyy") ?: "";
                tv_repo_description.text = repoModel.repoDescription
                setOnClickListener { onClickListener(repoModel) }
            }
        }
    }

    class ProgressBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setVisibility(show: Boolean) {
            if (show) itemView.show() else itemView.hide()
        }
    }
}
