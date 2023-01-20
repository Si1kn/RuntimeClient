package io.github.si1kn.injector.annotations


/**
 *  @author Si1kn: https://github.com/si1kn
 *  Created at: 17/01/2023
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class Override(val method: String)

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class SetClass(val clazz: String)

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class PatchVoid(val method: String, val at: OverrideType, val descriptor: String)

enum class OverrideType {
    TOP, BOTTOM, OVERRIDE
}