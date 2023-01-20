@file:JvmName("AgentMain")

package io.github.si1kn.injector

import io.github.si1kn.injector.transformer.BasicTransformer
import java.io.File
import java.lang.instrument.Instrumentation
import java.util.jar.JarFile


fun premain(agentops: String?, inst: Instrumentation) {
    inst.appendToSystemClassLoaderSearch(JarFile(File("C:\\Users\\si1kn\\IdeaProjects\\test\\target\\test-1.0-SNAPSHOT.jar")))
    inst.addTransformer(BasicTransformer())
}



