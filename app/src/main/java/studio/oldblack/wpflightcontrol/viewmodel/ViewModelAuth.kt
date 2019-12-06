package studio.oldblack.wpflightcontrol.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import studio.oldblack.wpflightcontrol.repository.RepositoryAuth


class ViewModelAuth(application: Application): AndroidViewModel(application) {
    val mAuthRepo = RepositoryAuth(application)
}