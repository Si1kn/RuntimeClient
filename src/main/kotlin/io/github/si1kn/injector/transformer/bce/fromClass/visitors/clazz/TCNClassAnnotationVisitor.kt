package io.github.si1kn.injector.transformer.bce.fromClass.visitors.clazz

import io.github.si1kn.injector.transformer.bce.fromClass.struct.Annotation
import org.objectweb.asm.AnnotationVisitor

/**
 *  @author Si1kn: https://github.com/si1kn
 *  Created at: 20/01/2023
 */
class TCNClassAnnotationVisitor(api: Int, av: AnnotationVisitor, private val clazzAnnotations: ArrayList<Annotation>, private val type: String) :
    AnnotationVisitor(api, av) {
    private val map = HashMap<String, String>()
    
    override fun visit(name: String?, value: Any?) {
        map[name.toString()] = value.toString()
        super.visit(name, value)
    }
    
    override fun visitEnd() {
        clazzAnnotations.add(Annotation(map, type))
        super.visitEnd()
    }
}