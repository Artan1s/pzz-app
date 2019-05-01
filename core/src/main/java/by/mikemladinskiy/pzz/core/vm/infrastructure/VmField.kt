package by.mikemladinskiy.pzz.core.vm.infrastructure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject



fun <T: Any> vmField(value: T): VmField<T> {
    return VmField(value)
}

interface IReadonlyVmField<T: Any> {
    val value: T
    fun live(): LiveData<T>
    fun rx(): Observable<T>
}

class VmField<T: Any>(value: T) : IReadonlyVmField<T>, Disposable {
    private val valueLive: MutableLiveData<T>
    private val valueSubject: Subject<T>
    private val disposableBag = CompositeDisposable()

    override var value: T
        get() = valueLive.value!!
        set(value) = this.valueSubject.onNext(value)

    init {
        valueLive = MutableLiveData()
        valueLive.value = value
        valueSubject = BehaviorSubject.createDefault(value)
        disposableBag.add(valueSubject.subscribe { valueLive.setValue(it) })
    }

    override fun live(): MutableLiveData<T> {
        return valueLive
    }

    override fun rx(): Observable<T> {
        return valueSubject
    }

    override fun dispose() {
        disposableBag.dispose()
    }

    override fun isDisposed(): Boolean {
        return disposableBag.isDisposed
    }
}
