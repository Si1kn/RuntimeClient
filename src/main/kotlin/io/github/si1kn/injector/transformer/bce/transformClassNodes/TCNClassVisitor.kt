package io.github.si1kn.injector.transformer.bce.transformClassNodes

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TCNClassVisitor(api: Int, classVisitor: ClassVisitor, private val className: String) :
    ClassVisitor(api, classVisitor) {
    
    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        if (classInfoList[className] == null) {
            classInfoList[className] = ArrayList()
            classInfoList[className]?.add(ClassMethodInfo(name.toString(), descriptor.toString(), signature.toString()));
        } else {
            classInfoList[className]?.add(ClassMethodInfo(name.toString(), descriptor.toString(), signature.toString()));
        }
        
        return TCNMethodVisitor(Opcodes.ASM9, cv.visitMethod(access, name, descriptor, signature, exceptions), name.toString())
    }
}

