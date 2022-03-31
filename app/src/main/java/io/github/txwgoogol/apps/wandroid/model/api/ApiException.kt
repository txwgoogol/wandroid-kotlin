package io.github.txwgoogol.apps.wandroid.model.api

//异常处理
class ApiException(var code: Int, override var message: String) : RuntimeException()