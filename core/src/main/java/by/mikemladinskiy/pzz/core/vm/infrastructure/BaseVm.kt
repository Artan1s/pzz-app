package by.mikemladinskiy.pzz.core.vm.infrastructure

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

open class BaseVm: ViewModel() {

    private val baseVmCompositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun bind(disposable: Disposable) {
        baseVmCompositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        clearFields(
            this,
            clearClassAndMethods
        )
    }

    companion object {

        private var clearClassAndMethods: List<Pair<Class<*>, Method>>

        init {
            try {
                clearClassAndMethods = listOf(
                    Pair(Disposable::class.java, Disposable::class.java.getDeclaredMethod("dispose")),
                    Pair(ViewModel::class.java, ViewModel::class.java.getDeclaredMethod("onCleared")));
            } catch (e: NoSuchMethodException) {
                throw IllegalStateException(e);
            }
        }
    }
}

fun clearFields(
    target: Any,
    clearClassAndMethods: List<Pair<Class<*>, Method>>
) {
    val targetClass = target.javaClass
    try {
        val fields = targetClass.declaredFields
        for (field in fields) {
            field.isAccessible = true
            val fieldValue = field.get(target) ?: continue
            for (clearClassAndMethod in clearClassAndMethods) {
                val clearClass = clearClassAndMethod.first
                val clearMethod = clearClassAndMethod.second
                if (clearClass.isAssignableFrom(field.type)) {
                    clearMethod.setAccessible(true)
                    clearMethod.invoke(fieldValue)
                }
            }

        }
    } catch (e: IllegalAccessException) {
        throw IllegalStateException(e)
    } catch (e: InvocationTargetException) {
        throw IllegalStateException(e)
    }

}