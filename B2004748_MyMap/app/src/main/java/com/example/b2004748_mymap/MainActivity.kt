package com.example.b2004748_mymap

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.b2004748_mymap.databinding.ActivityMainBinding
import com.example.b2004748_mymap.models.Place
import com.example.b2004748_mymap.models.UserMap
import com.google.android.gms.maps.model.MarkerOptions

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userMaps: MutableList<UserMap>
    private lateinit var mapsAdapter: MapsAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //tao layout manager cho rcv
        binding.rcvMaps.layoutManager = LinearLayoutManager(this)

        //danh sach place
        userMaps = generateSimpleData().toMutableList()
        //tao adapter cho rcv
        mapsAdapter = MapsAdapter(this, userMaps, object: MapsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                Log.i(TAG, "onItemClick $position")
                val intent = Intent(this@MainActivity, DisplayMapsActivity::class.java).setAction("your.custom.action")
                intent.putExtra(Utils.EXTRA_USER_MAP, userMaps[position])
                startActivity(intent)
            }
        })
        binding.rcvMaps.adapter = mapsAdapter

        //nut click floatingActionBtn
        binding.btnAdd.setOnClickListener {
            val mapFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_map, null)
            AlertDialog.Builder(this).setTitle("Map Title")
                .setView(mapFormView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK") {
                        _,_ ->
                    val title = mapFormView.findViewById<EditText>(R.id.edt_title_map).text.toString()
                    if(title.trim().isEmpty()) {
                        Toast.makeText(this, "Fill out title", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    val intent = Intent(this@MainActivity, CreateMapsActivity::class.java)
                    intent.putExtra(Utils.EXTRA_MAP_TITLE, title)
                    getResult.launch(intent)
                }
                .show()
        }
    }

    //receiver
    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            val userMap = it.data?.getSerializableExtra(Utils.EXTRA_USER_MAP) as UserMap
            userMaps.add(userMap)
            mapsAdapter.notifyItemInserted(userMaps.size-1)
            Log.i(TAG, userMap.title)
        }
    }
    //ham sinh du lieu hien thi ra rcv
    private fun generateSimpleData(): List<UserMap> {
        return listOf(
            UserMap("Đại học Cần Thơ",
                listOf(
                    Place("Trường CNTT&TT", "thuộc ĐHCT", 10.0308541, 105.768986),
                    Place("Trường Nông Nghiệp", "thuộc ĐHCT", 10.0302655, 105.7679642),
                    Place("Hộ trường rùa", "thuộc ĐHCT", 10.0293402, 105.7690273),
                )
            ),
            UserMap("Ẩm thực",
                listOf(
                    Place("The 80's icafe", "Đường Mạc Thiên Tích", 10.0286827, 105.7732964),
                    Place("Trà sữa Tigon", "Đường Mạc Thiên Tích", 10.0278105, 105.7718373),
                    Place("Cafe Thủy Mộc", "Đường 3/2", 10.0273775, 105.7704913),
                )
            )
        )
    }
}