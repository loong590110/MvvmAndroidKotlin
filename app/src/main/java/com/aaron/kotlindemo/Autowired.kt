package com.aaron.kotlindemo

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import java.io.Serializable
import java.lang.reflect.ParameterizedType

/**
 * Created by Developer Zailong Shi on 2020-01-07.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Autowired(val name: String = "")

object AutowiredHelper {
    fun inject(any: Any, intent: Intent?) = intent?.run {
        any::class.java.declaredFields.filter {
            it.isAnnotationPresent(Autowired::class.java)
        }.forEach {
            it.getAnnotation(Autowired::class.java)?.name.apply {
                val name = if (!TextUtils.isEmpty(this)) (this ?: "") else it.name
                it.isAccessible = true
                when {
                    it.type.isAssignableFrom(Boolean::class.java) -> {
                        it.set(any, getBooleanExtra(name, false))
                    }
                    it.type.isAssignableFrom(Byte::class.java) -> {
                        it.set(any, getByteExtra(name, 0))
                    }
                    it.type.isAssignableFrom(Short::class.java) -> {
                        it.set(any, getShortExtra(name, 0))
                    }
                    it.type.isAssignableFrom(Char::class.java) -> {
                        it.set(any, getCharExtra(name, ' '))
                    }
                    it.type.isAssignableFrom(Int::class.java) -> {
                        it.set(any, getIntExtra(name, 0))
                    }
                    it.type.isAssignableFrom(Integer::class.java) -> {
                        it.set(any, getIntExtra(name, 0))
                    }
                    it.type.isAssignableFrom(Long::class.java) -> {
                        it.set(any, getLongExtra(name, 0))
                    }
                    it.type.isAssignableFrom(Float::class.java) -> {
                        it.set(any, getFloatExtra(name, 0f))
                    }
                    it.type.isAssignableFrom(Double::class.java) -> {
                        it.set(any, getDoubleExtra(name, 0.0))
                    }
                    it.type.isAssignableFrom(String::class.java) -> {
                        it.set(any, getStringExtra(name))
                    }
                    it.type.isAssignableFrom(CharSequence::class.java) -> {
                        it.set(any, getCharSequenceExtra(name))
                    }
                    it.type.isAssignableFrom(Bundle::class.java) -> {
                        it.set(any, getBundleExtra(name))
                    }
                    it.type.isAssignableFrom(Parcelable::class.java) -> {
                        it.set(any, getParcelableExtra(name))
                    }
                    it.type.isAssignableFrom(Serializable::class.java) -> {
                        it.set(any, getSerializableExtra(name))
                    }
                    it.type.isAssignableFrom(BooleanArray::class.java) -> {
                        it.set(any, getBooleanArrayExtra(name))
                    }
                    it.type.isAssignableFrom(ByteArray::class.java) -> {
                        it.set(any, getByteArrayExtra(name))
                    }
                    it.type.isAssignableFrom(ShortArray::class.java) -> {
                        it.set(any, getShortArrayExtra(name))
                    }
                    it.type.isAssignableFrom(CharArray::class.java) -> {
                        it.set(any, getCharArrayExtra(name))
                    }
                    it.type.isAssignableFrom(IntArray::class.java) -> {
                        it.set(any, getIntArrayExtra(name))
                    }
                    it.type.isAssignableFrom(LongArray::class.java) -> {
                        it.set(any, getLongArrayExtra(name))
                    }
                    it.type.isAssignableFrom(FloatArray::class.java) -> {
                        it.set(any, getFloatArrayExtra(name))
                    }
                    it.type.isAssignableFrom(DoubleArray::class.java) -> {
                        it.set(any, getDoubleArrayExtra(name))
                    }
                    it.type.isAssignableFrom(Array<String>::class.java) -> {
                        it.set(any, getStringArrayExtra(name))
                    }
                    it.type.isAssignableFrom(Array<CharSequence>::class.java) -> {
                        it.set(any, getCharSequenceArrayExtra(name))
                    }
                    it.type.isAssignableFrom(Array<Parcelable>::class.java) -> {
                        it.set(any, getParcelableArrayExtra(name))
                    }
                    it.type.isAssignableFrom(ArrayList::class.java) -> {
                        if (it.genericType is ParameterizedType) {
                            //todo
                        }
                    }
                }
            }
        }
    }

    fun inject(any: Any, bundle: Bundle?) = bundle?.run {
        any::class.java.declaredFields.filter {
            it.isAnnotationPresent(Autowired::class.java)
        }.forEach {
            it.getAnnotation(Autowired::class.java)?.name.apply {
                val name = if (!TextUtils.isEmpty(this)) (this ?: "") else it.name
                it.isAccessible = true
                when {
                    it.type.isAssignableFrom(Boolean::class.java) -> {
                        it.set(any, getBoolean(name, false))
                    }
                    it.type.isAssignableFrom(Byte::class.java) -> {
                        it.set(any, getByte(name, 0))
                    }
                    it.type.isAssignableFrom(Short::class.java) -> {
                        it.set(any, getShort(name, 0))
                    }
                    it.type.isAssignableFrom(Char::class.java) -> {
                        it.set(any, getChar(name, ' '))
                    }
                    it.type.isAssignableFrom(Int::class.java) -> {
                        it.set(any, getInt(name, 0))
                    }
                    it.type.isAssignableFrom(Integer::class.java) -> {
                        it.set(any, getInt(name, 0))
                    }
                    it.type.isAssignableFrom(Long::class.java) -> {
                        it.set(any, getLong(name, 0))
                    }
                    it.type.isAssignableFrom(Float::class.java) -> {
                        it.set(any, getFloat(name, 0f))
                    }
                    it.type.isAssignableFrom(Double::class.java) -> {
                        it.set(any, getDouble(name, 0.0))
                    }
                    it.type.isAssignableFrom(String::class.java) -> {
                        it.set(any, getString(name))
                    }
                    it.type.isAssignableFrom(CharSequence::class.java) -> {
                        it.set(any, getCharSequence(name))
                    }
                    it.type.isAssignableFrom(Bundle::class.java) -> {
                        it.set(any, getBundle(name))
                    }
                    it.type.isAssignableFrom(Parcelable::class.java) -> {
                        it.set(any, getParcelable(name))
                    }
                    it.type.isAssignableFrom(Serializable::class.java) -> {
                        it.set(any, getSerializable(name))
                    }
                    it.type.isAssignableFrom(BooleanArray::class.java) -> {
                        it.set(any, getBooleanArray(name))
                    }
                    it.type.isAssignableFrom(ByteArray::class.java) -> {
                        it.set(any, getByteArray(name))
                    }
                    it.type.isAssignableFrom(ShortArray::class.java) -> {
                        it.set(any, getShortArray(name))
                    }
                    it.type.isAssignableFrom(CharArray::class.java) -> {
                        it.set(any, getCharArray(name))
                    }
                    it.type.isAssignableFrom(IntArray::class.java) -> {
                        it.set(any, getIntArray(name))
                    }
                    it.type.isAssignableFrom(LongArray::class.java) -> {
                        it.set(any, getLongArray(name))
                    }
                    it.type.isAssignableFrom(FloatArray::class.java) -> {
                        it.set(any, getFloatArray(name))
                    }
                    it.type.isAssignableFrom(DoubleArray::class.java) -> {
                        it.set(any, getDoubleArray(name))
                    }
                    it.type.isAssignableFrom(Array<String>::class.java) -> {
                        it.set(any, getStringArray(name))
                    }
                    it.type.isAssignableFrom(Array<CharSequence>::class.java) -> {
                        it.set(any, getCharSequence(name))
                    }
                    it.type.isAssignableFrom(Array<Parcelable>::class.java) -> {
                        it.set(any, getParcelableArray(name))
                    }
                    it.type.isAssignableFrom(ArrayList::class.java) -> {
                        if (it.genericType is ParameterizedType) {
                            //todo
                        }
                    }
                }
            }
        }
    }
}