package studio.oldblack.wpflightcontrol.viewmodel
import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import studio.oldblack.wpflightcontrol.repository.RepositoryMain

class ViewModelAuth(application: Application): AndroidViewModel(application) {
    val mAuthRepo = RepositoryMain(application)

    fun getAppAuthRequestIntent(context: Context) : Intent {
        return mAuthRepo.getAppAuthRequestIntent(context)
    }
}