package io.github.si1kn.injector.transformer.bce


import io.github.si1kn.injector.util.*
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class CustomMethodVisitor(api: Int, methodVisitor: MethodVisitor, val insnList: ArrayList<AbstractInsn>, val toRemove: ArrayList<String>?, val className: String) :
    MethodVisitor(api, methodVisitor) {
    
    
    override fun visitCode() {
        
        if (insnList.isNotEmpty()) {
            for (ai in insnList) {
                when (ai) {
                    is BlankInsn -> super.visitInsn(ai.opcode)
                    is IntInsn -> super.visitIntInsn(ai.opcode, ai.operand)
                    is VarInsn -> super.visitVarInsn(ai.opcode, ai.varIndex)
                    is TypeInsn -> super.visitTypeInsn(ai.opcode, ai.type)
                    is FieldInsn -> {
                        var s = ai.owner;
                        
                        if (ai.opcode == Opcodes.GETFIELD && (toRemove?.contains(ai.owner.toString()) == true)) {
                            s = className;
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
        
        super.visitCode();
    }
}