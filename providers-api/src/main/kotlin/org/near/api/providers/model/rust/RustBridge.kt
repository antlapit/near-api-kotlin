package org.near.api.providers.model.rust

/**
 * Indicates that sealed class/interface is rust-like enum
 * @link https://doc.rust-lang.org/book/ch06-01-defining-an-enum.html
 */
@Target(AnnotationTarget.CLASS)
annotation class RustEnum

/**
 * Indicates that class contains single property and should be deserialized unwrapped
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RustSinglePropertyEnumItem
