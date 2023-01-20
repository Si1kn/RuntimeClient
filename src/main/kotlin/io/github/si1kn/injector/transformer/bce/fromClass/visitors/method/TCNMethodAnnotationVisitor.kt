package io.github.si1kn.injector.transformer.bce.fromClass.visitors.method

import io.github.si1kn.injector.transformer.bce.fromClass.struct.Annotation
import org.objectweb.asm.AnnotationVisitor

class TCNMethodAnnotationVisitor(api: Int, annotationVisitor: AnnotationVisitor, private val annotations: ArrayList<Annotation>, val type: String) :
    AnnotationVisitor(api, annotationVisitor) {
    
    private val tempMap = HashMap<String, String>()
    
    override fun visit(name: String?, value: Any?) {
        tempMap[name.toString()] = value.toString()
        super.visit(name, value)
    }
    
    override fun visitEnum(name: String?, descriptor: String?, value: String?) {
        tempMap[name.toString()] = value.toString();
        super.visitEnum(name, descriptor, value)
    }
    
    override fun visitEnd() {
        annotations.add(Annotation(tempMap, type));
        super.visitEnd()
    }
}