package android.example.rpgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.TextView

private var cBUppercase = false
private var cBLowercase = false
private var cBNumbers = false
private var cBSymbols = false

class MainActivity : AppCompatActivity() {

    private lateinit var textViewPassword : TextView
    private lateinit var textViewSeekBar : TextView

    private var length = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkBoxUppercase = findViewById<CheckBox>(R.id.checkBoxUppercase)
        val checkBoxLowercase = findViewById<CheckBox>(R.id.checkBoxLowercase)
        val checkBoxNumbers = findViewById<CheckBox>(R.id.checkBoxNumbers)
        val checkBoxSymbols = findViewById<CheckBox>(R.id.checkBoxSymbols)

        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        textViewPassword = findViewById(R.id.textViewPassword)
        textViewSeekBar = findViewById(R.id.textViewSeekBar)

        checkBoxUppercase.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked){
                cBUppercase = true
            }
        }

        checkBoxLowercase.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked){
                cBLowercase = true
            }
        }

        checkBoxNumbers.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked){
                cBNumbers = true
            }
        }

        checkBoxSymbols.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked){
                cBSymbols = true
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                length = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                textViewSeekBar.setText(seekBar.getProgress().toString())
            }

        })
    }

    fun onClickRepeat(view: android.view.View) {
        //make password
        var myPassword = MakePassword()
        var newPassword : String = myPassword.generatePassword(cBLowercase, cBUppercase, cBNumbers, cBSymbols, length)
        Log.d("password", "$newPassword")
        textViewPassword.setText(newPassword)
    }
}
