package studio.oldblack.wpflightcontrol.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import studio.oldblack.wpflightcontrol.model.Me

@Database(entities = arrayOf(Me::class), version = 1, exportSchema = false)
abstract class WPFlightControlDatabase: RoomDatabase() {
    abstract fun MeDAO(): MeDao

    companion object {
        // Singleton prevents multiple instances of DB opening at the same time
        // This is from https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#6
        @Volatile
        private var INSTANCE: WPFlightControlDatabase? = null

        fun getDatabase(context: Context): WPFlightControlDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WPFlightControlDatabase::class.java,
                    "wpfc_proto_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}