package io.github.si1kn.injector.transformer.bce.fromClass.struct

import io.github.si1kn.injector.util.AbstractInsn

/**
 *  @author Si1kn: https://github.com/si1kn
 *  Created at: 20/01/2023
 */
class ClazzInfo(val clazzName: String, val methods: ArrayList<MethodInfo>, val classAnnotations: ArrayList<Annotation>)

class MethodInfo(val methodName: String, val descriptor: String, val signatureMethod: String, val methodInstrustions :ArrayList<AbstractInsn>, val annotations: ArrayList<Annotation>)

//TODO: Change string type to an enum for easier reading
class Annotation(val mapping: HashMap<String, String>, val type: String)
