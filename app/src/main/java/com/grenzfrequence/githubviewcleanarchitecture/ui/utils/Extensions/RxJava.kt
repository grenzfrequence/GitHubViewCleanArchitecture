package com.grenzfrequence.githubviewcleanarchitecture.ui.utils.Extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun CompositeDisposable.disposeSafety() {
    if (!this.isDisposed) {
        this.dispose()
    }
}

fun Disposable.disposeSafety() {
    if(!this.isDisposed) {
        this.dispose()
    }
}