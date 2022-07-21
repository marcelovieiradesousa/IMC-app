package me.mcl.imc

import android.animation.LayoutTransition
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import me.mcl.imc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.v("lifecycle", "CREATE - estou criando a tela")
        binding.linearLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        setListeners()
    }

    private fun setListeners() {
        binding.etAltura.doAfterTextChanged { text -> }
        binding.etPeso.doAfterTextChanged { text -> }
        binding.btnCalcular.setOnClickListener {
            calculaImc(binding.etPeso.text.toString(), binding.etAltura.text.toString())
        }
    }

    private fun calculaImc(peso: String, altura: String) {
        val peso = peso.toFloatOrNull()
        val altura = altura.toFloatOrNull()
        val permission = checkNullabilityTwoValor(peso, altura)
        if (permission == 1) {
            val result = peso!! / (altura!! * altura!!)
            binding.tvIMC.text = "IMC é\n %.2f".format(result)
            CategoryIMC(result.toInt())
        } else {
            binding.tvIMC.text = "Insira um valor válido"
        }
    }

    private fun checkNullabilityTwoValor(x: Float?, y: Float?): Int {
        val valor1 = x
        val valor2 = y
        if (valor1 != null && valor2 != null
            && valor1.toInt() != 0 && valor2.toInt() != 0
        ) return 1 else return 0
    }

    private fun CategoryIMC(numero: Int) {
        binding.tvClassifica.visibility = View.VISIBLE
        when (numero) {
            in 1..16 -> {
                binding.linearLayout.setBackgroundColor(Color.parseColor("#1B5E20"))
                binding.tvClassifica.text = "Muito abaixo do peso"
            }
            in 17..19 -> {
                binding.linearLayout.setBackgroundColor(Color.parseColor("#8BC34A"))
                binding.tvClassifica.text = "Abaixo do peso"
            }
            in 20..25 -> {
                binding.linearLayout.setBackgroundColor(Color.parseColor("#B2FF59"))
                binding.tvClassifica.text = "Peso normal"
            }
            in 26..30 -> {
                binding.linearLayout.setBackgroundColor(Color.parseColor("#FDD835"))
                binding.tvClassifica.text = "Acima do peso"
            }
            in 31..35 -> {
                binding.linearLayout.setBackgroundColor(Color.parseColor("#FF9800"))
                binding.tvClassifica.text = "Obesidade I"
            }
            in 36..40 -> {
                binding.linearLayout.setBackgroundColor(Color.parseColor("#E65100"))
                binding.tvClassifica.text = "Obesidade II"
            }
            in 41..999 -> {
                binding.linearLayout.setBackgroundColor(Color.parseColor("#D32F2F"))
                binding.tvClassifica.text = "Obesidade III"
            }
        }
    }

    //    CRIAÇÃO DE VISUALIZAÇÃO DE CICLO DE VIDA
    override fun onStart() {
        super.onStart()
        Log.v("lifecycle", "START - deixei a tela visível para você")
    }

    override fun onResume() {
        super.onResume()
        Log.v("lifecycle", "RESUME - tela interativa")
    }

    override fun onPause() {
        super.onPause()
        Log.v("lifecycle", "STOP - tela está invisível, mas ainda existe")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("lifecycle", "DESTROY - tela finalizando seu ciclo")
    }

    override fun onRestart() {
        super.onRestart()
        Log.v("lifecycle", "RESTART - tela sendo retomada")
    }
}


