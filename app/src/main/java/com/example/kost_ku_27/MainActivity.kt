package com.example.kost_ku_27

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kost_ku_27.room.Constant
import com.example.kost_ku_27.room.Kost
import com.example.kost_ku_27.room.KostDb
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy  {KostDb(this) }


    lateinit var kostAdapter : KostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListener()
        setupRecyclerview()
    }



    override fun onStart() {
        super.onStart()
    loadkost()
    }

    fun loadkost(){
        CoroutineScope(Dispatchers.IO).launch {
            val kost = db.kostDao().getkost()
            Log.d("MainAvtivity", "dbResponse: $kost")
            withContext(Dispatchers.Main) {
                kostAdapter.setData(kost)
            }
        }
    }

    fun setupListener() {
        add_movie.setOnClickListener{
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(kostId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, AddActivity::class.java)
                .putExtra("intent_id",kostId)
                .putExtra("intent_type",intentType)
        )
    }

    private fun setupRecyclerview(){
        kostAdapter = KostAdapter(arrayListOf(), object : KostAdapter.OnAdapterListener{
            override fun onClick(kost: Kost) {
                // read detail kost
                intentEdit(kost.id,Constant.TYPE_READ)
            }

            override fun onUpdate(kost: Kost) {
                intentEdit(kost.id,Constant.TYPE_UPDATE)
            }

            override fun onDelete(kost: Kost) {
                deleteDialog(kost)
            }

        })
        rv_movie.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = kostAdapter
        }
    }

    private fun deleteDialog(kost: Kost) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin mau dihapus nih? ${kost.title}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.kostDao().deleteKost(kost)
                    loadkost()
                }
            }
        }
        alertDialog.show()
    }
}