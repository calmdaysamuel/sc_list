package com.calmday.scwishlist

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    lateinit var nameField: TextView
    lateinit var urlField: TextView
    lateinit var priceField: TextView
    lateinit var addButton: Button
    lateinit var cards: RecyclerView
    lateinit var cAdapter: ListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nameField = findViewById(R.id.name)
         urlField = findViewById(R.id.url)
         priceField = findViewById(R.id.price)
         addButton = findViewById(R.id.button)
         cards = findViewById(R.id.cards)
         cAdapter = ListItemAdapter()
        addButton.setOnClickListener {
            addItem();
        }

        cards.adapter = cAdapter
        cards.layoutManager = LinearLayoutManager(this)
    }


    private fun addItem() {
        cAdapter.addItem(ListItem(nameField.text.toString(), priceField.text.toString(), urlField.text.toString()))

        nameField.text = ""
        priceField.text = ""
        urlField.text = ""
    }

    private fun deleteItem() {

    }


}

class ListItem(val name: String, val price: String, val url: String)

class ListItemAdapter: RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val name = itemView.findViewById<TextView>(R.id.i_name)
        val price = itemView.findViewById<TextView>(R.id.i_price)
        val url = itemView.findViewById<TextView>(R.id.i_url)
    }
    var items = mutableListOf<ListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val itemView = inflater.inflate(R.layout.product_view, parent, false)
        // Return a new holder instance
        return ViewHolder(itemView)
    }


    fun addItem(item: ListItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun deleteItem(position: Int) {
        items.removeAt(position)
        notifyItemRangeRemoved(position, 1)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val item: ListItem = items.get(position)
        // Set item views based on your views and data model
        holder.price.text = item.price
        holder.name.text = item.name
        holder.url.text = item.url

        holder.itemView.setOnClickListener {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                ContextCompat.startActivity(it.context, browserIntent, null)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(it.context, "Invalid URL for " + item.name, Toast.LENGTH_LONG).show()
            }
        }

        holder.itemView.setOnLongClickListener {
            deleteItem(position);
            Toast.makeText(it.context, "Product Deleted", Toast.LENGTH_SHORT).show()

            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return  items.size;
    }
}

// https://www.amazon.com/Paddle-Detangling-Tangle-Hairbrush-Natural/dp/B07PPDFMJ5/ref=sr_1_1_sspa?keywords=comb&qid=1662683468&sr=8-1-spons&psc=1&smid=A3J3ZZOHW5DZSB
// https://www.amazon.com/Glass-Straws-Set-DWTS-DANWEITESI-Coffee/dp/B0B7WL9375/ref=sr_1_7?crid=2QM8HIZRMZM8I&keywords=cup&qid=1662683488&sprefix=cup%2Caps%2C74&sr=8-7&th=1