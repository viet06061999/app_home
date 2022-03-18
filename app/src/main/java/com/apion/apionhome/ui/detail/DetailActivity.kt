package com.apion.apionhome.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import com.apion.apionhome.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        println( intent.extras)
        findNavController(R.id.navHostActivityDetail)
            .setGraph(R.navigation.detail_graph, intent.extras)
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }
}