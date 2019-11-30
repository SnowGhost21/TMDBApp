package com.arctouch.codechallenge.view.databinding

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class ReactiveAdapter<T, R : ViewDataBinding> : RecyclerView.Adapter<ReactiveAdapter<T, R>.ViewHolder>() {

    private var items: ArrayList<T> = ArrayList<T>()

    fun setItems(newItems: List<T>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun setItems(newItems: ArrayList<T>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(getBinding(parent.context, parent, viewType))
    }

    abstract fun getBinding(context: Context, parent: ViewGroup, viewType: Int): R

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItems(position: Int): T {
        return items[position]
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        bind(holder.binding, item)
    }

    abstract fun bind(binding: R, item: T)

    inner class ViewHolder(binding: R) : BindingViewHolder<R>(binding)
}