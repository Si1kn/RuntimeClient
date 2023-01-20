package io.github.si1kn.injector.util

import org.objectweb.asm.Handle
import org.objectweb.asm.Label
import org.objectweb.asm.TypePath

/**
 *  @author Si1kn: https://github.com/si1kn
 *  Created at: 19/01/2023
 */
open class AbstractInsn();

class BlankInsn(val opcode: Int, val type: Int) : AbstractInsn();

class FieldInsn(val opcode: Int, val owner: String?, val name: String?, val descriptor: String?) : AbstractInsn()

class IntInsn(val opcode: Int, val operand: Int) : AbstractInsn()

class VarInsn(val opcode: Int, val varIndex: Int) : AbstractInsn()

class TypeInsn(val opcode: Int, val type: String?) : AbstractInsn()

class MethodInsn(val opcode: Int, val operand: String?, val name: String?, val descriptor: String?, val isInterface: Boolean) :
    AbstractInsn()

class InvokeDynamicInsn(val name: String?, val descriptor: String?, val bootstrapMethodHandle: Handle?, val bootstrapMethodArguments: Any?) :
    AbstractInsn()

class JumpInsn(val opcode: Int, val operand: Label?) : AbstractInsn()

class LabelInsn(val label: Label?) : AbstractInsn()

class LdcInsn(val operand: Any?) : AbstractInsn()

class IincInsn(val varIndex: Int, val increment: Int) : AbstractInsn()

class TableSwitchInsn(val operand: Int, val increment: Int, val dflt: Label?, val labels: Array<out Label?>) :
    AbstractInsn()

class LookupSwitchInsn(val dflt: Label?, val keys: IntArray?, val labels: Array<out Label>?) : AbstractInsn()

class MultiANewArrayInsn(val descriptor: String?, val numDimensions: Int) : AbstractInsn()

class InsnAnnotation(val typeRef: Int, val typePath: TypePath?, val descriptor: String?, val visible: Boolean) :
    AbstractInsn()