package studio.oldblack.wpflightcontrol.database

import androidx.lifecycle.LiveData
import androidx.room.*
import studio.oldblack.wpflightcontrol.model.Me

@Dao
interface MeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMe(me: Me)

    @Query("Select * from wpfc_proto_me")
    fun getMe(): LiveData<List<Me>>

    @Update
    fun updateMe(me: Me)
}