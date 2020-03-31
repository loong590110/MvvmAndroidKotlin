package com.aaron.kotlindemo

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path(val value: String)