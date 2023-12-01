package com.example.fruticion.fragments

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.fruticion.R
import com.example.fruticion.activity.LoginActivity.Companion.currentUserId
import com.example.fruticion.activity.LoginActivity.Companion.fruitImagesMap
import com.example.fruticion.broadcastReceiver.AlarmReceiver
import com.example.fruticion.broadcastReceiver.AlarmReceiver.Companion.NOTIFICATION_ID
import com.example.fruticion.api.getNetworkService
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.database.Repository
import com.example.fruticion.databinding.FragmentDetailBinding
import com.example.fruticion.model.DailyIntake
import com.example.fruticion.model.Fruit
import com.example.fruticion.model.WeeklyIntake
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!//Esto es de Roberto

    private lateinit var db: FruticionDatabase
    private lateinit var repository: Repository

    private val args: DetailFragmentArgs by navArgs()//Esto tiene que ser val porque si no, peta

    companion object {
        const val MY_CHANNEL_ID = "myChannel"
    }

    //Se crean los elementos de la pantalla (sin valores o con aquellos valores que no corran de nuestra cuenta)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Se obtiene la instancia de la BD
        db = FruticionDatabase.getInstance(requireActivity().applicationContext)!!
        repository = Repository.getInstance(getNetworkService(), db)

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    //El codigo de este metodo podria estar en onCreateView() pero es tecnicamente mas correcto si esta en onViewCreated()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fruitId = args.fruitId
        setUpUI(fruitId)
        setUpListeners(fruitId)



        createChannel()

    }

    private fun setUpUI(fruitId: Long) {

        var fruit: Fruit?

        //llamada la a bd
        lifecycleScope.launch {
            fruit = repository.getFruitById(fruitId)

            with(binding) {//Recordar que with es para no poner binding delante de todas las lineas que tiene with dentro
                setUpFruitImage(fruit?.order.toString())

                textDetailName.text = fruit?.name

                //Familia, genero y orden
                valueDetailFamily.text = fruit?.family
                valueDetailOrder.text = fruit?.order
                valueDetailGenus.text = fruit?.genus
                //Informacion nutricional
                valueDetailCalories.text = fruit?.calories.toString()
                valueDetailFat.text = fruit?.fat.toString()
                valueDetailSugar.text = fruit?.sugar.toString()
                valueDetailCarbo.text = fruit?.carbohydrates.toString()
                valueDetailProtein.text = fruit?.protein.toString()
            }

            if (repository.getFavFruitByUser(fruitId) == null)
                removeFavFruitIcon()
            else
                addFavFruitIcon()

        }
    }

    private fun setUpFruitImage(order: String) {

        if (fruitImagesMap.contieneClave(order))
            binding.imagenDetalleFruta.setImageResource(
                fruitImagesMap.obtenerValor(order)!!)
        else
            binding.imagenDetalleFruta.setImageResource(R.mipmap.ic_launcher_foreground)

    }

    private fun setUpListeners(fruitId: Long) {
        with(binding) {

            addFavourite.setOnClickListener {
                lifecycleScope.launch {
                    if (repository.checkFruitIsFav(fruitId)) {
                        repository.addFavFruit(fruitId)


                        addFavFruitIcon()//cambia el aspecto del boton
                        val message = getString(R.string.add_fav_mes)
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    } else {
                        repository.deleteFavFruit(fruitId)

                        removeFavFruitIcon()//cambia el aspecto del boton
                        val message = getString(R.string.remove_fav_mes)
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            addDailyButton?.setOnClickListener {

                lifecycleScope.launch {
                    repository.insertDailyFruit(fruitId)

                    repository.insertWeeklyFruit(fruitId)
                }
                Toast.makeText(requireContext(), R.string.add_intake_mes, Toast.LENGTH_SHORT).show()
            }
            timePickerButton?.setOnClickListener {
                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)

                val timePickerDialog = TimePickerDialog(
                    requireContext(),
                    TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                        // Aquí puedes manejar la hora seleccionada
                        val selectedTime = "$selectedHour:$selectedMinute"
                        Toast.makeText(
                            requireContext(),
                            "Hora seleccionada: $selectedTime",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Lanza la notificación a la hora seleccionada
                        scheduleNotification(fruitId, selectedHour, selectedMinute)
                    },
                    hour,
                    minute,
                    true // true para el formato de 24 horas, false para el formato de 12 horas
                )

                timePickerDialog.show()
            }
        }
    }

    //--METODOS RECORDATORIO CON BROADCAST RECEIVER-------------------------------------------------------------------------------------------------
    private fun scheduleNotification(fruitId: Long, selectedHour: Int, selectedMinute: Int) {
        Log.d("DetailFragment", "Estamos dentro del scheduleNotification: ")

        // Configura la hora seleccionada en el calendario
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
        calendar.set(Calendar.MINUTE, selectedMinute)
        calendar.set(Calendar.SECOND, 0)
        var fruit: Fruit?

        lifecycleScope.launch {
            fruit = repository.getFruitById(fruitId)

            val intent = Intent(
                requireActivity().applicationContext,
                AlarmReceiver::class.java
            ).putExtra("FRUIT_NAME", fruit!!.name)
            val pendingIntent = PendingIntent.getBroadcast(
                requireActivity().applicationContext,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager =
                requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                "MySuperChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal para el recordatorio"
            }

            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    //--METODOS CAMBIO ICONO FAVORITOS-----------------------------------------------------------------------------------------------
    private fun addFavFruitIcon() {
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.corazon)
        binding.addFavourite.setImageDrawable(drawable)
    }

    private fun removeFavFruitIcon() {
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.corazon_sin_fav)
        binding.addFavourite.setImageDrawable(drawable)
    }

    //Este metodo es SOLO para evitar posibles fugas de memoria.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }
}