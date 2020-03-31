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
    fun inject(any: Any, intent: Intent?) {
        any::class.java.declaredFields
            .filter { it.isAnnotationPresent(Autowired::class.java) }
            .forEach {
                it.getAnnotation(Autowired::class.java)?.name.apply {
                    val name: String = if (!TextUtils.isEmpty(this)) (this ?: "") else it.name
                    it.isAccessible = true
                    when {
                        it.type.isAssignableFrom(Boolean::class.java) -> {
                            it.set(any, intent?.getBooleanExtra(name, false))
                        }
                        it.type.isAssignableFrom(Byte::class.java) -> {
                            it.set(any, intent?.getByteExtra(name, 0))
                        }
                        it.type.isAssignableFrom(Short::class.java) -> {
                            it.set(any, intent?.getShortExtra(name, 0))
                        }
                        it.type.isAssignableFrom(Char::class.java) -> {
                            it.set(any, intent?.getCharExtra(name, ' '))
                        }
                        it.type.isAssignableFrom(Int::class.java) -> {
                            it.set(any, intent?.getIntExtra(name, 0))
                        }
                        it.type.isAssignableFrom(Integer::class.java) -> {
                            it.set(any, intent?.getIntExtra(name, 0))
                        }
                        it.type.isAssignableFrom(Long::class.java) -> {
                            it.set(any, intent?.getLongExtra(name, 0))
                        }
                        it.type.isAssignableFrom(Float::class.java) -> {
                            it.set(any, intent?.getFloatExtra(name, 0f))
                        }
                        it.type.isAssignableFrom(Double::class.java) -> {
                            it.set(any, intent?.getDoubleExtra(name, 0.0))
                        }
                        it.type.isAssignableFrom(String::class.java) -> {
                            it.set(any, intent?.getStringExtra(name))
                        }
                        it.type.isAssignableFrom(CharSequence::class.java) -> {
                            it.set(any, intent?.getCharSequenceExtra(name))
                        }
                        it.type.isAssignableFrom(Bundle::class.java) -> {
                            it.set(any, intent?.getBundleExtra(name))
                        }
                        it.type.isAssignableFrom(Parcelable::class.java) -> {
                            it.set(any, intent?.getParcelableExtra(name))
                        }
                        it.type.isAssignableFrom(Serializable::class.java) -> {
                            it.set(any, intent?.getSerializableExtra(name))
                        }
                        it.type.isAssignableFrom(BooleanArray::class.java) -> {
                            it.set(any, intent?.getBooleanArrayExtra(name))
                        }
                        it.type.isAssignableFrom(ByteArray::class.java) -> {
                            it.set(any, intent?.getByteArrayExtra(name))
                        }
                        it.type.isAssignableFrom(ShortArray::class.java) -> {
                            it.set(any, intent?.getShortArrayExtra(name))
                        }
                        it.type.isAssignableFrom(CharArray::class.java) -> {
                            it.set(any, intent?.getCharArrayExtra(name))
                        }
                        it.type.isAssignableFrom(IntArray::class.java) -> {
                            it.set(any, intent?.getIntArrayExtra(name))
                        }
                        it.type.isAssignableFrom(LongArray::class.java) -> {
                            it.set(any, intent?.getLongArrayExtra(name))
                        }
                        it.type.isAssignableFrom(FloatArray::class.java) -> {
                            it.set(any, intent?.getFloatArrayExtra(name))
                        }
                        it.type.isAssignableFrom(DoubleArray::class.java) -> {
                            it.set(any, intent?.getDoubleArrayExtra(name))
                        }
                        it.type.isAssignableFrom(Array<String>::class.java) -> {
                            it.set(any, intent?.getStringArrayExtra(name))
                        }
                        it.type.isAssignableFrom(Array<CharSequence>::class.java) -> {
                            it.set(any, intent?.getCharSequenceArrayExtra(name))
                        }
                        it.type.isAssignableFrom(Array<Parcelable>::class.java) -> {
                            it.set(any, intent?.getParcelableArrayExtra(name))
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

    fun inject(any: Any, bundle: Bundle?) {
        any::class.java.declaredFields
            .filter { it.isAnnotationPresent(Autowired::class.java) }
            .forEach {
                it.getAnnotation(Autowired::class.java)?.name.apply {
                    val name: String = if (!TextUtils.isEmpty(this)) (this ?: "") else it.name
                    it.isAccessible = true
                    when {
                        it.type.isAssignableFrom(Boolean::class.java) -> {
                            it.set(any, bundle?.getBoolean(name, false))
                        }
                        it.type.isAssignableFrom(Byte::class.java) -> {
                            it.set(any, bundle?.getByte(name, 0))
                        }
                        it.type.isAssignableFrom(Short::class.java) -> {
                            it.set(any, bundle?.getShort(name, 0))
                        }
                        it.type.isAssignableFrom(Char::class.java) -> {
                            it.set(any, bundle?.getChar(name, ' '))
                        }
                        it.type.isAssignableFrom(Int::class.java) -> {
                            it.set(any, bundle?.getInt(name, 0))
                        }
                        it.type.isAssignableFrom(Integer::class.java) -> {
                            it.set(any, bundle?.getInt(name, 0))
                        }
                        it.type.isAssignableFrom(Long::class.java) -> {
                            it.set(any, bundle?.getLong(name, 0))
                        }
                        it.type.isAssignableFrom(Float::class.java) -> {
                            it.set(any, bundle?.getFloat(name, 0f))
                        }
                        it.type.isAssignableFrom(Double::class.java) -> {
                            it.set(any, bundle?.getDouble(name, 0.0))
                        }
                        it.type.isAssignableFrom(String::class.java) -> {
                            it.set(any, bundle?.getString(name))
                        }
                        it.type.isAssignableFrom(CharSequence::class.java) -> {
                            it.set(any, bundle?.getCharSequence(name))
                        }
                        it.type.isAssignableFrom(Bundle::class.java) -> {
                            it.set(any, bundle?.getBundle(name))
                        }
                        it.type.isAssignableFrom(Parcelable::class.java) -> {
                            it.set(any, bundle?.getParcelable(name))
                        }
                        it.type.isAssignableFrom(Serializable::class.java) -> {
                            it.set(any, bundle?.getSerializable(name))
                        }
                        it.type.isAssignableFrom(BooleanArray::class.java) -> {
                            it.set(any, bundle?.getBooleanArray(name))
                        }
                        it.type.isAssignableFrom(ByteArray::class.java) -> {
                            it.set(any, bundle?.getByteArray(name))
                        }
                        it.type.isAssignableFrom(ShortArray::class.java) -> {
                            it.set(any, bundle?.getShortArray(name))
                        }
                        it.type.isAssignableFrom(CharArray::class.java) -> {
                            it.set(any, bundle?.getCharArray(name))
                        }
                        it.type.isAssignableFrom(IntArray::class.java) -> {
                            it.set(any, bundle?.getIntArray(name))
                        }
                        it.type.isAssignableFrom(LongArray::class.java) -> {
                            it.set(any, bundle?.getLongArray(name))
                        }
                        it.type.isAssignableFrom(FloatArray::class.java) -> {
                            it.set(any, bundle?.getFloatArray(name))
                        }
                        it.type.isAssignableFrom(DoubleArray::class.java) -> {
                            it.set(any, bundle?.getDoubleArray(name))
                        }
                        it.type.isAssignableFrom(Array<String>::class.java) -> {
                            it.set(any, bundle?.getStringArray(name))
                        }
                        it.type.isAssignableFrom(Array<CharSequence>::class.java) -> {
                            it.set(any, bundle?.getCharSequence(name))
                        }
                        it.type.isAssignableFrom(Array<Parcelable>::class.java) -> {
                            it.set(any, bundle?.getParcelableArray(name))
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