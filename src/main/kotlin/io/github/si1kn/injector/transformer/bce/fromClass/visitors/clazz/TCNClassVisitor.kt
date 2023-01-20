package io.github.si1kn.injector.transformer.bce.fromClass.visitors.clazz

import io.github.si1kn.injector.transformer.bce.fromClass.struct.Annotation
import io.github.si1kn.injector.transformer.bce.fromClass.struct.ClazzInfo
import io.github.si1kn.injector.transformer.bce.fromClass.struct.MethodInfo
import io.github.si1kn.injector.transformer.bce.fromClass.visitors.method.TCNMethodVisitor
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TCNClassVisitor(api: Int, classVisitor: ClassVisitor, private val className: String) :
    ClassVisitor(api, classVisitor) {
    
    var clazzInfo: ClazzInfo? = null
    
    private val methods = ArrayList<MethodInfo>()
    
    private val clazzAnnotations = ArrayList<Annotation>()
    
    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        val method = MethodInfo(name.toString(), descriptor.toString(), signature.toString(), ArrayList(), ArrayList())
        methods.add(method)
        return TCNMethodVisitor(Opcodes.ASM9, cv.visitMethod(access, name, descriptor, signature, exceptions), method)
    }
    
    
    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        val array = descriptor.toString().split("/");
        return TCNClassAnnotationVisitor(Opcodes.ASM9, cv.visitAnnotation(descriptor, visible), clazzAnnotations, array[array.size - 1].replace(";", ""))
    }
    
    override fun visitEnd() {
        clazzInfo = ClazzInfo(className, methods, clazzAnnotations)
        super.visitEnd()
    }
}

