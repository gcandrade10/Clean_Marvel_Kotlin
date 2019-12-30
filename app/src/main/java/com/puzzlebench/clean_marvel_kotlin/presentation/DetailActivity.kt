package com.puzzlebench.clean_marvel_kotlin.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.getImageByUrl
import com.puzzlebench.cmk.domain.model.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA = "extra"
        fun getIntent(context: Context, character: Character): Intent {
            val intent = Intent(context,DetailActivity::class.java)
            intent.putExtra(EXTRA,character)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent){
        val character:Character = intent.extras[EXTRA] as Character
        image_detail.getImageByUrl("${character.thumbnail.path}.${character.thumbnail.extension}", this)
        if(character.description.isNotEmpty()){
            text_detail.text=character.description
        }
    }


}
