package com.example.bitfit

import com.example.bitfit.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DividerItemDecoration
import android.widget.Button
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {

    private val foodList = mutableListOf<DisplayFood>()
    private lateinit var foodRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        foodRecyclerView = findViewById<RecyclerView>(R.id.foodListRV)

        val foodAdapter = FoodAdapter(this, foodList)
        foodRecyclerView.adapter = foodAdapter

        lifecycleScope.launch {
            (application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayFood(
                        entity.foodName,
                        entity.caloriesNumber,
                        entity.caloriesText
                    )
                }.also { mappedList ->
                    foodList.addAll(mappedList)
                    foodAdapter.notifyDataSetChanged()
                }
            }
        }


        foodRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            foodRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        val addButton = findViewById<Button>(R.id.addNewFood)
        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            startActivity(intent)

        }

    }
}