package io.github.si1kn.injector.transformer

import com.google.gson.Gson
import io.github.si1kn.injector.transformer.bce.EditByteCode
import org.apache.commons.io.IOUtils
import org.objectweb.asm.ClassReader
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.lang.instrument.ClassFileTransformer
import java.nio.charset.StandardCharsets
import java.security.ProtectionDomain
import java.util.jar.JarFile

class BasicTransformer : ClassFileTransformer {
    
    private val transformingClassNodes: HashMap<String, ClassReader> = HashMap()
    
    private val ebc: EditByteCode
    
    init {
        val inputJar = JarFile(File("C:\\Users\\si1kn\\IdeaProjects\\test\\target\\test-1.0-SNAPSHOT.jar"))
    
        val nameJarFileInputStream: HashMap<String, InputStream> = HashMap()
        
        for (entry in inputJar.entries()) nameJarFileInputStream[entry.name] = inputJar.getInputStream(entry)
        
        val json: String = try {
            IOUtils.toString(nameJarFileInputStream["tm.json"], StandardCharsets.UTF_8)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        
        val gson = Gson()
        
        val tranformerMap = gson.fromJson(json, TmJson::class.java).transformerMap
    
        for (mutableEntry in nameJarFileInputStream) if (mutableEntry.key.endsWith(".class")) for (s in tranformerMap) if (mutableEntry.key.replace(".class", "").replace("/", ".") == s) if (transformingClassNodes[mutableEntry.key.replace(".class", "")] == null) transformingClassNodes[mutableEntry.key.replace(".class", "")] = ClassReader(mutableEntry.value)
        
        ebc = EditByteCode(transformingClassNodes)
    }
    
    override fun transform(loader: ClassLoader, className: String, classBeingRedefined: Class<*>?, protectionDomain: ProtectionDomain?, classfileBuffer: ByteArray): ByteArray {
        return ebc.transform(className, classfileBuffer)
    }
}

class TmJson(var transformerMap: Array<String>)