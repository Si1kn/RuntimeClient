package io.github.si1kn.injector.transformer.bce.transformClassNodes

import io.github.si1kn.injector.util.AbstractInsn


val classInfoList: HashMap<String, ArrayList<ClassMethodInfo>> = HashMap()
val classAnnotationList: HashMap<String, ClassAnnoationsInfo> = HashMap()
var classInsnInstructions: HashMap<String, ArrayList<AbstractInsn>> = HashMap()

class ClassMethodInfo(val name: String, val descriptor: String, val signature: String)
class ClassAnnoationsInfo(val string: String, val whatIs: String)