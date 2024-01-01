package com.example.fruticion.view.fragments

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.fruticion.FruticionApplication
import com.example.fruticion.R
import com.example.fruticion.broadcastReceiver.AlarmReceiver
import com.example.fruticion.broadcastReceiver.AlarmReceiver.Companion.NOTIFICATION_ID
import com.example.fruticion.databinding.FragmentDetailBinding
import com.example.fruticion.util.FruitImagesMap
import com.example.fruticion.view.viewModel.DetailViewModel
import java.util.Calendar

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!//Esto es de Roberto

    private val args: DetailFragmentArgs by navArgs()//Esto tiene que ser val porque si no, peta

    private val detailViewModel: DetailViewModel by viewModels { DetailViewModel.Factory }

    private lateinit var fruitImagesMap: FruitImagesMap

    companion object {
        const val MY_CHANNEL_ID = "myChannel"
    }

    //Se crean los elementos de la pantalla (sin valores o con aquellos valores que no corran de nuestra cuenta)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    //El codigo de este metodo podria estar en onCreateView() pero es tecnicamente mas correcto si esta en onViewCreated()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Para poder usar el contexto de la aplicacion (application) hay que llamarlo desde la Activity
        val appContainer = (requireActivity().application as FruticionApplication).appContainer
        fruitImagesMap = appContainer.fruitImagesMap

        val fruitId = args.fruitId
        setUpUI(fruitId)
        setUpListeners(fruitId)

        createChannel()

    }

    private fun setUpUI(fruitId: Long) {

        detailViewModel.update(fruitId)

        detailViewModel.detailFruit.observe(viewLifecycleOwner) { fruit ->


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

            if (!detailViewModel.isFavorite)
                removeFavFruitIcon()
            else
                addFavFruitIcon()
        }

    }

    private fun setUpFruitImage(order: String) {

        if (fruitImagesMap.contieneClave(order))
            binding.imagenDetalleFruta.setImageResource(
                fruitImagesMap.obtenerValor(order)!!
            )
        else
            binding.imagenDetalleFruta.setImageResource(R.mipmap.ic_launcher_foreground)

    }

    private fun setUpListeners(fruitId: Long) {
        with(binding) {

            addFavourite.setOnClickListener {
                detailViewModel.onFavoriteButtonClick(fruitId)

                if (!detailViewModel.isFavorite) {
                    addFavFruitIcon()//cambia el aspecto del boton
                    val message = getString(R.string.add_fav_mes)
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                } else {
                    removeFavFruitIcon()//cambia el aspecto del boton
                    val message = getString(R.string.remove_fav_mes)
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }

            }

            addDailyButton?.setOnClickListener {
                detailViewModel.onAddDailyButtonClick(fruitId)

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
                        scheduleNotification(selectedHour, selectedMinute)
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
    private fun scheduleNotification(selectedHour: Int, selectedMinute: Int) {

        // Configura la hora seleccionada en el calendario
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
        calendar.set(Calendar.MINUTE, selectedMinute)
        calendar.set(Calendar.SECOND, 0)


            val fruit = detailViewModel.detailFruit.value

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
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_corazon)
        binding.addFavourite.setImageDrawable(drawable)
        detailViewModel.isFavorite = true
    }

    private fun removeFavFruitIcon() {
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_corazon_sin_fav)
        binding.addFavourite.setImageDrawable(drawable)
        detailViewModel.isFavorite = false
    }

    //Este metodo es SOLO para evitar posibles fugas de memoria.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }
}