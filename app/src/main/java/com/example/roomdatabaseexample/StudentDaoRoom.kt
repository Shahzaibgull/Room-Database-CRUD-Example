package com.example.roomdatabaseexample

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Dao
interface StudentDaoRoom {
    @PrimaryKey(autoGenerate = true)
    @Query("SELECT * FROM student_table1")
    fun getAll(): List<StudentRoom>

    @Query("SELECT * FROM student_table1 WHERE roll_no LIKE :roll LIMIT 1")
    suspend fun findByRoll(roll: Int): StudentRoom

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(studentRoom: StudentRoom)

    @Delete
    suspend fun delete(studentRoom: StudentRoom)

    @Query("DELETE FROM student_table1")
    suspend fun deleteALL()

    /*@Query("DELETE FROM sqlite_sequence WHERE name='student_table'")
    suspend fun repeatPrimaryKey()*/

    @Query("UPDATE student_table1 SET first_name1=:firstName, last_Name1=:lastName WHERE roll_no LIKE :roll")
    suspend fun update(firstName: String, lastName: String, roll: Int)
}