package io.github.si1kn.injector.transformer.bce


import io.github.si1kn.injector.transformer.bce.toClass.CustomClassVisitor
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.io.File

/**
 *  @author Si1kn: https://github.com/si1kn
 *  Created at: 17/01/2023
 */
class EditByteCode(private var transformingClassNodes: HashMap<String, ClassReader>) {
    
    fun transform(className: String, classBytes: ByteArray): ByteArray {
        val reader = ClassReader(classBytes)
        val writer = ClassWriter(0)
        
        val mt = CustomClassVisitor(Opcodes.ASM9, writer, className, transformingClassNodes)
        
        reader.accept(mt, ClassReader.EXPAND_FRAMES)
        
        
        if (className.contains("aya")) FileUtils.writeByteArrayToFile(File("bytes.class"), writer.toByteArray())
        return writer.toByteArray()
    }
}