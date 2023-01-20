package io.github.si1kn.injector.transformer.bce.toClass


import io.github.si1kn.injector.annotations.OverrideType
import io.github.si1kn.injector.transformer.bce.fromClass.visitors.clazz.TCNClassVisitor
import io.github.si1kn.injector.util.AbstractInsn
import org.objectweb.asm.*

/**
 *  @author Si1kn: https://github.com/si1kn
 *  Created at: 18/01/2023
 */
class CustomClassVisitor(api: Int, classVisitor: ClassVisitor?, val className: String, private val transformingClassNodes: HashMap<String, ClassReader>) :
    ClassVisitor(api, classVisitor) {
    
    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        var insnList = ArrayList<AbstractInsn>()
        
        
        val toRemove = ArrayList<String>()
        
        var overrideType = OverrideType.TOP;
        
        for (transformingClassNode in transformingClassNodes) {
            val nameClass = transformingClassNode.key
            val projectingClassNode = transformingClassNode.value
            
            
            val writer = ClassWriter(0)
            
            val tcnClassVisitor = TCNClassVisitor(Opcodes.ASM9, writer, nameClass)
            projectingClassNode.accept(tcnClassVisitor, ClassReader.EXPAND_FRAMES)
            
            
            val clazzData = tcnClassVisitor.clazzInfo
            
            
            if (clazzData != null) {
                
                var classThatIsRefencering = "";
                for (classAnnotation in clazzData.classAnnotations) classThatIsRefencering = classAnnotation.mapping.toString().split("=")[1].replace("}", "");
                
                
                for (method in clazzData.methods) {
                    
                    for (annotation in method.annotations) {
                        if (annotation.type == "PatchVoid") {
                            
                            
                            val at = annotation.mapping["at"].toString()
                            val stringMethod = annotation.mapping["method"].toString()
                            val otherClassDescriptor = annotation.mapping["descriptor"].toString()
                            
                            
                            
                            if (classThatIsRefencering == className) {
                                if (name.toString().contains(stringMethod) && descriptor.toString().contains(otherClassDescriptor)) {
                                    toRemove.add(clazzData.clazzName)
                                    val tempinsnList = method.methodInstrustions
                                    
                                    
                                    for (abstractInsn in tempinsnList) if (abstractInsn.opcode == Opcodes.RETURN && (OverrideType.valueOf(at)) != OverrideType.OVERRIDE) tempinsnList.remove(abstractInsn)
                                    insnList = tempinsnList;
                                    
                                    overrideType = OverrideType.valueOf(at);
                                }
                            }
                        } else {
                            println("type is not PatchVoid, please report me as a bug! ${annotation.type}")
                        }
                    }
                }
            }
            
            
            writer.toByteArray()
        }
        
        return CustomMethodVisitor(Opcodes.ASM9, cv.visitMethod(access, name, descriptor, signature, exceptions), insnList, toRemove, className, overrideType)
    }
}