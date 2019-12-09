package studio.oldblack.wpflightcontrol.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import studio.oldblack.wpflightcontrol.repository.RepositoryMain

class ViewModelAuth(application: Application): AndroidViewModel(application) {
    val mAuthRepo = RepositoryMain(application)
}