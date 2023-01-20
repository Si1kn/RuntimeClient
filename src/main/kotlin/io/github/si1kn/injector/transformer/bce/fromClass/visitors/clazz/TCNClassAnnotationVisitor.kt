package io.github.si1kn.injector.transformer.bce.fromClass.visitors.clazz

import io.github.si1kn.injector.transformer.bce.fromClass.struct.Annotation
import org.objectweb.asm.AnnotationVisitor

/**
 *  @author Si1kn: https://github.com/si1kn
 *  Created at: 20/01/2023
 */
class TCNClassAnnotationVisitor(api: Int, av: AnnotationVisitor, private val clazzAnnotations: ArrayList<Annotation>, private val type: String) :
    AnnotationVisitor(api, av) {
    
    
    override fun visit(name: String?, value: Any?) {
        val tempMap = HashMap<String, String>()
        tempMap[name.toString()] = value.toString()
        clazzAnnotations.add(Annotation(tempMap, type))
        super.visit(name, value)
    }
}