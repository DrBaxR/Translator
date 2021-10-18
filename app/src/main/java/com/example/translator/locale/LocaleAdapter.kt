package com.example.translator.locale

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.translator.R
import java.util.*

class LocaleAdapter(private val mContext: Context, private val locales: List<Locale>) :
    ArrayAdapter<Locale>(mContext, 0, locales) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItem = convertView ?: LayoutInflater.from(mContext)
            .inflate(R.layout.locale_list_item, parent, false)

        listItem.findViewById<TextView>(R.id.tvLocale).text = locales[position].displayLanguage

        return listItem
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItem = convertView ?: LayoutInflater.from(mContext)
            .inflate(R.layout.locale_list_item, parent, false)

        listItem.findViewById<TextView>(R.id.tvLocale).text = locales[position].displayLanguage

        return listItem
    }

}