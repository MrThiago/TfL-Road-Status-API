/*
 * Created by Thiago Gouvea 15/4/2021.
 * Copyright (c) 2021. All rights reserved.
 */

package com.mrthiago.tflvalidroad.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mrthiago.tflvalidroad.R
import com.mrthiago.tflvalidroad.domain.RoadStatus

class RoadStatusAdapter(
    private val values: List<RoadStatus>
) : RecyclerView.Adapter<RoadStatusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.road_status_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.name.text = item.displayName
        holder.status.text = item.statusSeverity
        holder.statusDescription.text = item.statusSeverityDescription
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.cardView)
        val name: TextView = view.findViewById(R.id.displayName)
        val status: TextView = view.findViewById(R.id.statusSeverity)
        val statusDescription: TextView = view.findViewById(R.id.statusSeverityDescription)
    }
}