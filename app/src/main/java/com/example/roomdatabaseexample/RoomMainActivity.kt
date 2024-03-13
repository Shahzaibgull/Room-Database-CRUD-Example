package com.example.roomdatabaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.roomdatabaseexample.databinding.ActivityRoomMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomMainBinding
    private lateinit var appDb: AppDatabaseRoom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRoomMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb=AppDatabaseRoom.getDatabase(this)

        binding.btnWriteData.setOnClickListener {
            writeData()
        }
        binding.btnReadData.setOnClickListener {
            readData()
        }
        binding.btnDeleteData.setOnClickListener {
            GlobalScope.launch {
                appDb.studentDao().deleteALL()
                Toast.makeText(this@RoomMainActivity, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                //appDb.clearAllTables()
                //appDb.studentDao().repeatPrimaryKey()
            }
        }
        binding.btnUpdateData.setOnClickListener {
            updateData()
        }
    }

    private fun updateData() {

        val firstName=binding.etFirstName.text.toString()
        val lastName=binding.etLastName.text.toString()
        val rollNo=binding.etRollNo.text.toString()

        if(firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty()){

            GlobalScope.launch(Dispatchers.IO) {
                appDb.studentDao().update(firstName,lastName,rollNo.toInt())
            }
            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etRollNo.text.clear()
            Toast.makeText(this@RoomMainActivity, "Successfully Update", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this@RoomMainActivity, "Please Enter Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun writeData(){
        val firstName=binding.etFirstName.text.toString()
        val lastName=binding.etLastName.text.toString()
        val rollNo=binding.etRollNo.text.toString()

        if(firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty()){
            val studentRoom= StudentRoom(
                null,firstName,lastName,rollNo.toInt()
            )
            GlobalScope.launch(Dispatchers.IO) {
                appDb.studentDao().insert(studentRoom)
            }

            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etRollNo.text.clear()
            Toast.makeText(this@RoomMainActivity, "Successfully Written", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this@RoomMainActivity, "Please Enter Data", Toast.LENGTH_SHORT).show()
        }

    }
    private fun readData(){

        val rollNo=binding.etRollNoRead.text.toString()
        if(rollNo.isNotEmpty()){
            lateinit var studentRoom: StudentRoom
            GlobalScope.launch {
                studentRoom=appDb.studentDao().findByRoll(rollNo.toInt())
                displayData(studentRoom)
            }
        }
    }
    private suspend fun displayData(studentRoom: StudentRoom) {

        withContext(Dispatchers.Main){
            binding.tvFirstName.text=studentRoom.firstName1
            binding.tvLastName.text=studentRoom.lastName1
            binding.tvRollNo.text=studentRoom.rollNo.toString()
        }

    }
}