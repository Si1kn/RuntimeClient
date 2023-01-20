package io.github.si1kn.injector.transformer.bce


import io.github.si1kn.injector.transformer.bce.transformClassNodes.TCNClassVisitor
import io.github.si1kn.injector.transformer.bce.transformClassNodes.classAnnotationList
import io.github.si1kn.injector.transformer.bce.transformClassNodes.classInfoList
import io.github.si1kn.injector.transformer.bce.transformClassNodes.classInsnInstructions
import io.github.si1kn.injector.util.AbstractInsn
import org.objectweb.asm.*

class CustomClassVisitor(api: Int, classVisitor: ClassVisitor?, val className: String, private val transformingClassNodes: HashMap<String, ClassReader>) :
    ClassVisitor(api, classVisitor) {
    
    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        var insnList = ArrayList<AbstractInsn>();
        
        
        val toRemove = ArrayList<String>();
        
        
        for (transformingClassNode in transformingClassNodes) {
            val nameClass = transformingClassNode.key;
            val projectingClassNode = transformingClassNode.value;
            
            
            val writer = ClassWriter(0);
            
            val tcnClassVisitor = TCNClassVisitor(Opcodes.ASM9, writer, nameClass);
            projectingClassNode.accept(tcnClassVisitor, ClassReader.EXPAND_FRAMES);
            
            
            //get method
            for (mutableEntry in classInfoList) {
                
                val entryName = mutableEntry.key;
                val classInfo = mutableEntry.value;
                
                
                //get annoations for said method
                for (mutableEntry1 in classAnnotationList) {
                    
                    val classAnnoationsInfo = mutableEntry1.value;
                    
                    val rawData = classAnnoationsInfo.string;
                    
                    val strippedData = rawData.replace("[", "").replace("]", "");
                    
                    val seperatedData = strippedData.split(",");
                    
                    val clazz = seperatedData[1]
                    val atMethod = seperatedData[3]
                    val desc = seperatedData[7]
                    
                    
                    if (className == clazz) {
                        if (name.toString().contains(atMethod) && descriptor.toString().contains(desc)) { //                            for (classMethodInfo in classInfo) {
                            toRemove.add(entryName)
                            insnList = classInsnInstructions[mutableEntry1.key]!!;
                        }
                        
                    }
                }
            }
            
            writer.toByteArray();
        }
        
        return CustomMethodVisitor(Opcodes.ASM9, cv.visitMethod(access, name, descriptor, signature, exceptions), insnList, toRemove, className);
    }
}