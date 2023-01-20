package io.github.si1kn.injector.util

import org.objectweb.asm.Handle
import org.objectweb.asm.Label
import org.objectweb.asm.TypePath

/**
 *  @author Si1kn: https://github.com/si1kn
 *  Created at: 19/01/2023
 */
open class AbstractInsn(val opcode: Int)

class BlankInsn(opcode: Int, val type: Int) : AbstractInsn(opcode)

class FieldInsn(opcode: Int, val owner: String?, val name: String?, val descriptor: String?) : AbstractInsn(opcode)

class IntInsn(opcode: Int, val operand: Int) : AbstractInsn(opcode)

class VarInsn(opcode: Int, val varIndex: Int) : AbstractInsn(opcode)

class TypeInsn(opcode: Int, val type: String?) : AbstractInsn(opcode)

class MethodInsn(opcode: Int, val operand: String?, val name: String?, val descriptor: String?, val isInterface: Boolean) :
    AbstractInsn(opcode)

class InvokeDynamicInsn(val name: String?, val descriptor: String?, val bootstrapMethodHandle: Handle?, val bootstrapMethodArguments: Any?) :
    AbstractInsn(-1)

class JumpInsn(opcode: Int, val operand: Label?) : AbstractInsn(opcode)

class LabelInsn(val label: Label?) : AbstractInsn(-1)

class LdcInsn(val operand: Any?) : AbstractInsn(-1)

class IincInsn(val varIndex: Int, val increment: Int) : AbstractInsn(-1)

class TableSwitchInsn(val operand: Int, val increment: Int, val dflt: Label?, val labels: Array<out Label?>) :
    AbstractInsn(-1)

class LookupSwitchInsn(val dflt: Label?, val keys: IntArray?, val labels: Array<out Label>?) : AbstractInsn(-1)

class MultiANewArrayInsn(val descriptor: String?, val numDimensions: Int) : AbstractInsn(-1)

class InsnAnnotation(val typeRef: Int, val typePath: TypePath?, val descriptor: String?, val visible: Boolean) :
    AbstractInsn(-1)