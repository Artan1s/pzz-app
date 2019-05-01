package by.mikemladinskiy.pzz.core.backend.internal.dto

internal class PzzResponseDto<T>(var response: PzzInnerResponseDto<T>)

internal class PzzInnerResponseDto<T>(var data: T)
