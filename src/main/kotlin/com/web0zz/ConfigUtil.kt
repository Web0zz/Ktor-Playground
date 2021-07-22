package com.web0zz

import io.ktor.config.*

class ConfigUtil constructor(config: ApplicationConfig) {
    val SECRET_KEY = config.property("key.secret").getString()
}