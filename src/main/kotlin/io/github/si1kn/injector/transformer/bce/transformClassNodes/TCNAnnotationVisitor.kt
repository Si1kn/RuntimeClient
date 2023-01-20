package io.github.si1kn.injector.transformer.bce.transformClassNodes

import org.objectweb.asm.AnnotationVisitor

class TCNAnnotationVisitor(api: Int, annotationVisitor: AnnotationVisitor, private val desc: String, private val methodOwner: String) :
    AnnotationVisitor(api, annotationVisitor) {
    
    private val temp: ArrayList<String> = ArrayList()
    override fun visit(name: String?, value: Any?) {
        
        temp.add("$name,$value")
        if (temp.size == 4) {
            classAnnotationList[methodOwner] = ClassAnnoationsInfo(temp.toString(), desc)
        }
        
        super.visit(name, value)
    }
}