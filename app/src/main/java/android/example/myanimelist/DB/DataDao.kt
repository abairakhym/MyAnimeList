package android.example.myanimelist.DB

import android.example.myanimelist.Model.Data
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(data: Data): Long

    @Query("SELECT * FROM datas")
    fun getAllDatas(): LiveData<List<Data>>

    @Delete
    suspend fun deleteData(data: Data)
}