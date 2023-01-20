package io.github.si1kn.injector.transformer.bce.transformClassNodes

import io.github.si1kn.injector.util.*
import org.objectweb.asm.*

class TCNMethodVisitor(api: Int, methodVisitor: MethodVisitor, private val methodName: String) :
    MethodVisitor(api, methodVisitor) {
    
    private val instructions: ArrayList<AbstractInsn>?;
    
    init { //Populate arraylist so it doesn't null out.
        classInsnInstructions[methodName] = ArrayList();
        instructions = classInsnInstructions[methodName];
    }
    
    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        return TCNAnnotationVisitor(Opcodes.ASM9, mv.visitAnnotation(descriptor, visible), descriptor.toString().substring(descriptor.toString().lastIndexOf('/') + 1), methodName)
    }
    
    override fun visitInsn(opcode: Int) {
        instructions?.add(BlankInsn(opcode, -1))
        super.visitInsn(opcode)
    }
    
    
    override fun visitIntInsn(opcode: Int, operand: Int) {
        instructions?.add(IntInsn(opcode, operand));
        super.visitIntInsn(opcode, operand)
    }
    
    override fun visitVarInsn(opcode: Int, varIndex: Int) {
        instructions?.add(VarInsn(opcode, varIndex));
        super.visitVarInsn(opcode, varIndex)
    }
    
    override fun visitTypeInsn(opcode: Int, type: String?) {
        instructions?.add(TypeInsn(opcode, type));
        super.visitTypeInsn(opcode, type)
    }
    
    override fun visitFieldInsn(opcode: Int, owner: String?, name: String?, descriptor: String?) {
        instructions?.add(FieldInsn(opcode, owner, name, descriptor));
        super.visitFieldInsn(opcode, owner, name, descriptor)
    }
    
    override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, descriptor: String?, isInterface: Boolean) {
        instructions?.add(MethodInsn(opcode, owner, name, descriptor, isInterface));
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
    }
    
    override fun visitInvokeDynamicInsn(name: String?, descriptor: String?, bootstrapMethodHandle: Handle?, vararg bootstrapMethodArguments: Any?) {
        instructions?.add(InvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments));
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, *bootstrapMethodArguments)
    }
    
    override fun visitJumpInsn(opcode: Int, label: Label?) {
        instructions?.add(JumpInsn(opcode, label));
        super.visitJumpInsn(opcode, label)
    }
    
    override fun visitLabel(label: Label?) {
        instructions?.add(LabelInsn(label));
        super.visitLabel(label)
    }
    
    override fun visitLdcInsn(value: Any?) {
        instructions?.add(LdcInsn(value));
        super.visitLdcInsn(value)
    }
    
    override fun visitIincInsn(varIndex: Int, increment: Int) {
        instructions?.add(IincInsn(varIndex, increment));
        
        super.visitIincInsn(varIndex, increment)
    }
    
    override fun visitTableSwitchInsn(min: Int, max: Int, dflt: Label?, vararg labels: Label?) {
        instructions?.add(TableSwitchInsn(min, max, dflt, labels));
        super.visitTableSwitchInsn(min, max, dflt, *labels)
    }
    
    override fun visitLookupSwitchInsn(dflt: Label?, keys: IntArray?, labels: Array<out Label>?) {
        instructions?.add(LookupSwitchInsn(dflt, keys, labels));
        super.visitLookupSwitchInsn(dflt, keys, labels)
    }
    
    override fun visitMultiANewArrayInsn(descriptor: String?, numDimensions: Int) {
        instructions?.add(MultiANewArrayInsn(descriptor, numDimensions));
        super.visitMultiANewArrayInsn(descriptor, numDimensions)
    }
    
    override fun visitInsnAnnotation(typeRef: Int, typePath: TypePath?, descriptor: String?, visible: Boolean): AnnotationVisitor {
        instructions?.add(InsnAnnotation(typeRef, typePath, descriptor, visible));
        return super.visitInsnAnnotation(typeRef, typePath, descriptor, visible)
    }
}


