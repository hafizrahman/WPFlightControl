package studio.oldblack.wpflightcontrol.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wpfc_proto_me")
data class Me (
    @PrimaryKey @ColumnInfo(name = "user_id")      val user_id: Int,
    @ColumnInfo(name = "display_name")          val meDisplayName: String,
    @ColumnInfo(name = "username")              val meUsername: String,
    @ColumnInfo(name = "email")                 val meEmail: String,
    @ColumnInfo(name = "language")              val meLanguage: String,
    @ColumnInfo(name = "site_count")            val meSiteCount: Int,
    @ColumnInfo(name = "primary_blog")          val mePrimaryBlogId: Int
)