package com.simplecalculator

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    val TAG="MainActivity"
    lateinit var entre1:TextInputEditText
    lateinit var entre2:TextInputEditText
    lateinit var btn:Button
    lateinit var sortie:TextInputEditText
    lateinit var categorie:AutoCompleteTextView
    lateinit var toolbar:Toolbar

    @SuppressLint("ResourceAsColor", "QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialisat_ion()
        toolbar.inflateMenu(R.menu.menu_app)
        toolbar.setOnMenuItemClickListener { menuItem->
           if ( menuItem.itemId == R.id.share){
               val intentSend = Intent(Intent.ACTION_SEND)
               intentSend.type= "text/plain"
               intentSend.putExtra(Intent.EXTRA_TEXT, "Number : ${entre1.text.toString()} ${categorie.text} " +
                       "Number : ${entre2.text.toString()} is equal to ${sortie.text.toString()}")
               if (intentSend.resolveActivity(packageManager)!=null){
                   startActivity(intentSend)
               }else{
                   Toast.makeText(this, "error handle not found!!", Toast.LENGTH_SHORT).show()
               }
           }
            true
        }
//
        val maList = listOf<String>("plus", "multiple", "division", "minus")
        val adapter = ArrayAdapter(this,R.layout.style_view,maList)
        categorie.setAdapter(adapter)
        btn.setOnClickListener {
            Log.d(TAG, "onCreate: $maList")

            if (entre1.text.toString().isNotEmpty() && entre2.text.toString().isNotEmpty()){
                calculateResult()
            }else{
               val snackbar = Snackbar.make(entre1,"enter value!!",Snackbar.LENGTH_SHORT)
                snackbar.show()
                snackbar.setAction("error"){

                }
//                snackbar.setActionTextColor(R.color.yellow)
                snackbar.setBackgroundTint(R.color.black)
                entre1.setError("notEmpty")
                entre2.setError("notEmpty")

            }
        }
    }
    private fun initialisat_ion(){
        entre1 = findViewById(R.id.entre1)
        entre2 = findViewById(R.id.entre2)
        btn = findViewById(R.id.btn)
        sortie = findViewById(R.id.sortie)
        categorie = findViewById(R.id.categorie)
        toolbar = findViewById(R.id.tolbar)
    }
    private fun calculateResult(){
        val x = entre1.text.toString().toDouble()
        val y = entre2.text.toString().toDouble()
        val operators = categorie.text.toString()

        val result=when(operators){
            "plus"->x.plus(y)
            "division"->x.div(y)
            "multiple"->x.times(y)
            "minus"   ->x.minus(y)
            else->"eroore"
        }
        val formatResult = String.format("%.2f", result)
        sortie.setText(formatResult)
    }
}




