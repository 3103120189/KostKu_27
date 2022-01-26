package com.example.kost_ku_27

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kost_ku_27.room.Constant
import com.example.kost_ku_27.room.Kost
import com.example.kost_ku_27.room.KostDb
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    val db by lazy {KostDb(this)}
    private var kostId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setupView()
        setupListener()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {

            }
            Constant.TYPE_READ -> {
                btn_save.visibility = View.GONE
                btn_update.visibility = View.GONE
                getKost()
            }
            Constant.TYPE_UPDATE -> {
                btn_save.visibility = View.GONE
                getKost()
            }

        }
    }

    fun setupListener(){
        btn_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.kostDao().addKost(
                    Kost(0, et_title.text.toString(),
                    et_description.text.toString())
                )
                finish()
            }
        }
        btn_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.kostDao().updateKost(
                    Kost(kostId, et_title.text.toString(),
                        et_description.text.toString())
                )
                finish()
            }
        }
    }
    fun getKost(){
        kostId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val kostt= db.kostDao().getkost( kostId )[0]
            et_title.setText( kostt.title)
            et_description.setText( kostt.desc)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}