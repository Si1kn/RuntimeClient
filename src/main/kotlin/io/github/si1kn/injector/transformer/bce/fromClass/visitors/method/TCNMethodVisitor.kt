package io.github.si1kn.injector.transformer.bce.fromClass.visitors.method

import io.github.si1kn.injector.transformer.bce.fromClass.struct.MethodInfo
import io.github.si1kn.injector.util.*
import org.objectweb.asm.*

class TCNMethodVisitor(api: Int, methodVisitor: MethodVisitor, private val methodInfo: MethodInfo) :
    MethodVisitor(api, methodVisitor) {
    
    
    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        val array = descriptor.toString().split("/");
        return TCNMethodAnnotationVisitor(Opcodes.ASM9, mv.visitAnnotation(descriptor, visible), methodInfo.annotations, array[array.size - 1].replace(";", ""))
    }
    
    override fun visitInsn(opcode: Int) {
        methodInfo.methodInstrustions.add(BlankInsn(opcode, -1))
        super.visitInsn(opcode)
    }
    
    
    override fun visitIntInsn(opcode: Int, operand: Int) {
        methodInfo.methodInstrustions.add(IntInsn(opcode, operand))
        super.visitIntInsn(opcode, operand)
    }
    
    override fun visitVarInsn(opcode: Int, varIndex: Int) {
        methodInfo.methodInstrustions.add(VarInsn(opcode, varIndex))
        super.visitVarInsn(opcode, varIndex)
    }
    
    override fun visitTypeInsn(opcode: Int, type: String?) {
        methodInfo.methodInstrustions.add(TypeInsn(opcode, type))
        super.visitTypeInsn(opcode, type)
    }
    
    override fun visitFieldInsn(opcode: Int, owner: String?, name: String?, descriptor: String?) {
        methodInfo.methodInstrustions.add(FieldInsn(opcode, owner, name, descriptor))
        super.visitFieldInsn(opcode, owner, name, descriptor)
    }
    
    override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, descriptor: String?, isInterface: Boolean) {
        methodInfo.methodInstrustions.add(MethodInsn(opcode, owner, name, descriptor, isInterface))
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
    }
    
    override fun visitInvokeDynamicInsn(name: String?, descriptor: String?, bootstrapMethodHandle: Handle?, vararg bootstrapMethodArguments: Any?) {
        methodInfo.methodInstrustions.add(InvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments))
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, *bootstrapMethodArguments)
    }
    
    override fun visitJumpInsn(opcode: Int, label: Label?) {
        methodInfo.methodInstrustions.add(JumpInsn(opcode, label))
        super.visitJumpInsn(opcode, label)
    }
    
    override fun visitLabel(label: Label?) {
        methodInfo.methodInstrustions.add(LabelInsn(label))
        super.visitLabel(label)
    }
    
    override fun visitLdcInsn(value: Any?) {
        methodInfo.methodInstrustions.add(LdcInsn(value))
        super.visitLdcInsn(value)
    }
    
    override fun visitIincInsn(varIndex: Int, increment: Int) {
        methodInfo.methodInstrustions.add(IincInsn(varIndex, increment))
        
        super.visitIincInsn(varIndex, increment)
    }
    
    override fun visitTableSwitchInsn(min: Int, max: Int, dflt: Label?, vararg labels: Label?) {
        methodInfo.methodInstrustions.add(TableSwitchInsn(min, max, dflt, labels))
        super.visitTableSwitchInsn(min, max, dflt, *labels)
    }
    
    override fun visitLookupSwitchInsn(dflt: Label?, keys: IntArray?, labels: Array<out Label>?) {
        methodInfo.methodInstrustions.add(LookupSwitchInsn(dflt, keys, labels))
        super.visitLookupSwitchInsn(dflt, keys, labels)
    }
    
    override fun visitMultiANewArrayInsn(descriptor: String?, numDimensions: Int) {
        methodInfo.methodInstrustions.add(MultiANewArrayInsn(descriptor, numDimensions))
        super.visitMultiANewArrayInsn(descriptor, numDimensions)
    }
    
    override fun visitInsnAnnotation(typeRef: Int, typePath: TypePath?, descriptor: String?, visible: Boolean): AnnotationVisitor {
        methodInfo.methodInstrustions.add(InsnAnnotation(typeRef, typePath, descriptor, visible))
        return super.visitInsnAnnotation(typeRef, typePath, descriptor, visible)
    }
    
    
}


