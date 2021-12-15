package com.example.translator.locale

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.translator.R

class AutocompleteLocaleAdapter(
    private val mContext: Context,
    private var items: MutableList<AutocompleteLocale>
) :
    ArrayAdapter<AutocompleteLocale>(mContext, 0, items) {

    val suggestions = mutableListOf<AutocompleteLocale>()
    val tempItems = mutableListOf(*items.toTypedArray())
    private val filter = LanguageFilter()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if (convertView == null) {
            val inflater =
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.locale_list_item, parent, false)
        }

        val locale = items[position]
        val tvLocale = view?.findViewById<TextView>(R.id.tvLocale)
        if (tvLocale != null) {
            tvLocale.text = locale.language
        }

        return view!!
    }

    override fun getFilter(): Filter {
        return filter
    }

    inner class LanguageFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return if (constraint != null) {
                suggestions.clear()
                tempItems.forEach {
                    if (it.language.lowercase().contains(constraint.toString().lowercase())) {
                        suggestions.add(it)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = suggestions
                filterResults.count = suggestions.size
                filterResults
            } else {
                FilterResults()
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values != null) {
                val filterList = results.values as List<*>
                if (results.count > 0) {
                    clear()
                    filterList.forEach {
                        add(it as AutocompleteLocale)
                        notifyDataSetChanged()
                    }
                }
            }
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            return super.convertResultToString(resultValue)
        }
    }
}