package com.example.kalendarz

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.Calendar
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val PrzyciskK = findViewById<Button>(R.id.PrzyciskK)
        val DataRozp = findViewById<TextView>(R.id.DataRozp)
        val DataKonc = findViewById<TextView>(R.id.DataKonc)
        val DlgOut = findViewById<TextView>(R.id.DlgOut)
        val Kalendarz=Calendar.getInstance()
        val DataMin= Kalendarz.timeInMillis
        //Ustawienie na max 2lata od dzis
        val DataMax= Kalendarz.timeInMillis + 63113904000
        val Limit= CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .setStart(DataMin)
            .setEnd(DataMax)
            .build()

        PrzyciskK.setOnClickListener {
            var StartDate: Long
            var EndDate: Long
            val calendar = MaterialDatePicker.Builder.dateRangePicker()
                .setCalendarConstraints(Limit)
                .setTitleText("Wybierz date wyjazdu i przyjazdu")
                .build()
            calendar.show(supportFragmentManager, "Kalendarz")
            calendar.addOnPositiveButtonClickListener { datePicked->
                StartDate = datePicked.first
                EndDate = datePicked.second
                DataRozp.text="Data twojego wyjazdu to: "+ConvertDate(StartDate)
                DataKonc.text="Data twojego przyjazdu to: "+ConvertDate(EndDate)

                val StartDateSub = StartDate/1000/60/60/24
                val EndDateSub = EndDate/1000/60/60/24
                val DatesSub = EndDateSub-StartDateSub
                    DlgOut.text="Twój wyjazd będzie trwał " + DatesSub.toString() + " dni"
            }
        }
    }
    private fun ConvertDate(date: Long) :String{
        val datetoconvert=Date(date)
        val format=SimpleDateFormat(
            "dd-MM-yyyy"
        )
        return format.format(datetoconvert)
    }
}