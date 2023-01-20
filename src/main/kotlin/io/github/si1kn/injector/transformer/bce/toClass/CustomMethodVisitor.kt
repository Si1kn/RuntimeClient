package io.github.si1kn.injector.transformer.bce.toClass


import io.github.si1kn.injector.annotations.OverrideType
import io.github.si1kn.injector.util.*
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 *  @author Si1kn: https://github.com/si1kn
 *  Created at: 18/01/2023
 */
class CustomMethodVisitor(api: Int, methodVisitor: MethodVisitor, private val insnList: ArrayList<AbstractInsn>, private val toRemove: ArrayList<String>?, private val className: String, val locationToInsert: OverrideType) :
    MethodVisitor(api, methodVisitor) {
    
    
    override fun visitInsn(opcode: Int) {
        if (opcode == Opcodes.RETURN && locationToInsert == OverrideType.BOTTOM) {
            insertCode()
        }
        super.visitInsn(opcode)
    }
    
    
    override fun visitCode() {
        if (locationToInsert == OverrideType.TOP || locationToInsert == OverrideType.OVERRIDE) {
            insertCode()
        }
        super.visitCode()
    }
    
    private fun insertCode() {
        if (insnList.isNotEmpty()) {
            for (ai in insnList) {
                when (ai) {
                    is BlankInsn -> super.visitInsn(ai.opcode)
                    is IntInsn -> super.visitIntInsn(ai.opcode, ai.operand)
                    is VarInsn -> super.visitVarInsn(ai.opcode, ai.varIndex)
                    is TypeInsn -> super.visitTypeInsn(ai.opcode, ai.type)
                    is FieldInsn -> {
                        var s = ai.owner
                        
                        if (ai.opcode == Opcodes.GETFIELD && (toRemove?.contains(ai.owner.toString()) == true)) {
                            s = className
                        }
                        
                        super.visitFieldInsn(ai.opcode, s, ai.name, ai.descriptor)
                    }
                    
                    is MethodInsn -> super.visitMethodInsn(ai.opcode, ai.operand, ai.name, ai.descriptor, ai.isInterface)
                    is InvokeDynamicInsn -> super.visitInvokeDynamicInsn(ai.name, ai.descriptor, ai.bootstrapMethodHandle, ai.bootstrapMethodArguments)
                    is JumpInsn -> super.visitJumpInsn(ai.opcode, ai.operand)
                    is LabelInsn -> super.visitLabel(ai.label)
                    is LdcInsn -> super.visitLdcInsn(ai.operand)
                    is IincInsn -> super.visitIincInsn(ai.varIndex, ai.increment)
                    is TableSwitchInsn -> super.visitTableSwitchInsn(ai.operand, ai.increment, ai.dflt, *ai.labels)
                    is LookupSwitchInsn -> super.visitLookupSwitchInsn(ai.dflt, ai.keys, ai.labels)
                    is MultiANewArrayInsn -> super.visitMultiANewArrayInsn(ai.descriptor, ai.numDimensions)
                    is InsnAnnotation -> super.visitInsnAnnotation(ai.typeRef, ai.typePath, ai.descriptor, ai.visible)
                }
            }
        }
    }
    
    
}